package com.aseproject.frigg.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aseproject.frigg.R;
import com.aseproject.frigg.activity.NavActivity;
import com.aseproject.frigg.adapter.GroceryAdapter;
import com.aseproject.frigg.common.AppSessionManager;
import com.aseproject.frigg.common.FriggRecyclerView;
import com.aseproject.frigg.model.FoodItem;
import com.aseproject.frigg.service.GroceryService;
import com.aseproject.frigg.service.SessionFacade;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GroceryFragment extends Fragment implements GroceryService.GroceryServiceListener {

    private static final String TAG = "GroceryFragment";
    private Context context;
    private SessionFacade sessionFacade;
    private FriggRecyclerView groceriesRecyclerView;
    private TextView groceriesTextView;
    private LinearLayout mEmptyView;
    private SwipeRefreshLayout groceriesRefreshLayout;
    private static final String PURPOSE = "GROCERY_RESULTS";
    private GroceryAdapter groceryAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sessionFacade = new SessionFacade();
        return inflater.inflate(R.layout.fragment_grocery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((NavActivity)context).setTitle(getString(R.string.grocery_title));

        // Find Views
        groceriesRecyclerView = view.findViewById(R.id.groceriesRecyclerView);
        groceriesRefreshLayout = view.findViewById(R.id.groceriesRefreshLayout);
        mEmptyView = view.findViewById(R.id.groceries_empty_view);
        groceriesTextView = view.findViewById(R.id.groceries_empty_text_view);

        // Pull To Refresh
        // Refresh items
        groceriesRefreshLayout.setOnRefreshListener(this::refreshItems);

        setRecyclerView();
        downloadGroceries(getString(R.string.fetching_data));
    }

    // Swipe Refresh Layout
    private void refreshItems() {
        downloadGroceries(getString(R.string.fetching_data));
        onRefreshItemsLoadComplete();
    }

    private void onRefreshItemsLoadComplete() {
        // Update the adapter and notify data set changed
        Log.d(TAG, "ApptFragment onRefreshItemsLoadComplete() {\n");
        // Stop refresh animation
        groceriesRefreshLayout.setRefreshing(false);
    }

    public void downloadGroceries(String indicatorStr) {
        ((NavActivity) context).showActivityIndicator(indicatorStr);

        sessionFacade.getGroceries(context, PURPOSE, this);
    }

    private void setRecyclerView() {
        groceriesRecyclerView.setHasFixedSize(true);
        groceriesRecyclerView.setItemAnimator(null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        groceriesRecyclerView.setLayoutManager(layoutManager);
        groceriesRecyclerView.setEmptyView(mEmptyView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        groceriesRecyclerView.addItemDecoration(dividerItemDecoration);
    }
    private void updateUI(List<FoodItem> groceries) {
        groceryAdapter = new GroceryAdapter(context, groceries);
        groceriesRecyclerView.setAdapter(groceryAdapter);

        groceriesRecyclerView.getRecycledViewPool().clear();
        groceryAdapter.notifyDataSetChanged();

        // If the page is refreshing, we want to stay at the top of the view/
        // Otherwise, we want to scroll to the newest message.
        if (groceriesRefreshLayout.isRefreshing()) {
            onRefreshItemsLoadComplete();
        } else {
            groceriesRecyclerView.scrollToPosition(0);
        }
    }


    @Override
    public void notifyFetchSuccess(List<FoodItem> labResults) {
        updateUI(labResults);
        ((NavActivity) context).hideActivityIndicator();
    }

    @Override
    public void notifyFetchError(String error, String purpose) {
        loadJSONFromAsset();
        ((NavActivity) context).hideActivityIndicator();
    }

    public void loadJSONFromAsset() {
        String json="";
        try {
            InputStream is = context.getAssets().open("fooditems.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(!json.isEmpty()) {
            try {
                FoodItem[] labResults = new Gson().fromJson(json, FoodItem[].class);
                AppSessionManager.getInstance().setGroceries(new LinkedList<>(Arrays.asList(labResults)));
                Log.d(TAG, "Groceries retrieved: " + AppSessionManager.getInstance().getGroceries().size());
                updateUI(AppSessionManager.getInstance().getGroceries());
            } catch (JsonParseException exception) {
                Log.e(TAG, "exception:" + exception);
            }
        }
    }
}