package com.aseproject.frigg.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.aseproject.frigg.R;
import com.aseproject.frigg.activity.DishRecipeActivity;
import com.aseproject.frigg.activity.NavActivity;
import com.aseproject.frigg.adapter.RecommendAdapter;
import com.aseproject.frigg.common.FriggRecyclerView;
import com.aseproject.frigg.model.FoodItem;
import com.aseproject.frigg.service.RecommendService;
import com.aseproject.frigg.service.SessionFacade;
import com.aseproject.frigg.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends Fragment implements RecommendService.RecommendListener, RecommendAdapter.RecommendListener {

    private SearchView svSearchItem;
    private Context context;
    private ArrayAdapter<String> adapter;
    private FriggRecyclerView recommendedDishList;
    private SessionFacade sessionFacade;
    private static final String PURPOSE_DISHES = "PURPOSE_DISHES";
    private LinearLayout mEmptyView;
    private RecommendAdapter recommendAdapter;
    private LinearLayout btnSaveEditedItems;
    private String[] dishes;
    private List<FoodItem> foodItems = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((NavActivity) context).setTitle("Recommend");

        svSearchItem = view.findViewById(R.id.etSearchItem);
        mEmptyView = view.findViewById(R.id.groceries_empty_view);
        recommendedDishList = view.findViewById(R.id.recommendedDishList);
        sessionFacade = new SessionFacade();

        downloadDishes();
        setRecyclerView();
        setSearchView();
    }

    private void setSearchView() {
        svSearchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recommendAdapter.filterItems(s);
                return false;
            }
        });

        svSearchItem.setOnCloseListener(() -> {
            recommendAdapter.notifyDataSetChanged();
            return false;
        });
    }

    private void downloadDishes() {
        ((NavActivity) context).showActivityIndicator(context.getString(R.string.fetching_data));
        String url = Constants.BASE_URL + "DishNameSuggestion";
        sessionFacade.searchIngredients(context, PURPOSE_DISHES, this, url);
    }

    private void setRecyclerView() {
        recommendedDishList.setHasFixedSize(true);
        recommendedDishList.setItemAnimator(null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recommendedDishList.setLayoutManager(layoutManager);
        recommendedDishList.setEmptyView(mEmptyView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        recommendedDishList.addItemDecoration(dividerItemDecoration);
    }

    private void updateUI(String[] dishes) {
        this.dishes = dishes;
        recommendAdapter = new RecommendAdapter(context, this.dishes, this.dishes, this);
        recommendedDishList.setAdapter(recommendAdapter);

        recommendedDishList.getRecycledViewPool().clear();
        recommendAdapter.notifyDataSetChanged();
    }

    @Override
    public <T> void notifyFetchSuccess(T obj, String purpose) {
        ((NavActivity) context).hideActivityIndicator();
        updateUI((String[]) obj);
    }

    @Override
    public void notifyFetchError(String error, String purpose) {
        ((NavActivity) context).hideActivityIndicator();
    }

    @Override
    public void onClickListener(String dishName) {
        Intent intent = new Intent(((NavActivity)context), DishRecipeActivity.class);
        intent.putExtra("DISH_NAME", dishName);
        startActivity(intent);
    }
}