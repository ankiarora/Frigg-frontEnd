package com.aseproject.frigg.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aseproject.frigg.R;
import com.aseproject.frigg.activity.FridgeToGroceryActivity;
import com.aseproject.frigg.activity.FridgeToGroceryActivity;
import com.aseproject.frigg.adapter.FoodAdapter;
import com.aseproject.frigg.common.AppSessionManager;
import com.aseproject.frigg.common.CommonDialogFragment;
import com.aseproject.frigg.common.FriggRecyclerView;
import com.aseproject.frigg.model.FoodItem;
import com.aseproject.frigg.service.RecommendService;
import com.aseproject.frigg.service.SessionFacade;

import java.util.ArrayList;
import java.util.List;

public class FridgeToGroceryFragment extends Fragment implements FoodAdapter.GroceryHolderListener, CommonDialogFragment.DialogInterface, RecommendService.RecommendPostListener {
    private LinearLayout btnSaveEditedItems;
    private TextView btnText;
    private SessionFacade sessionFacade;
    private LinearLayout mEmptyView;
    private FriggRecyclerView groceriesRecyclerView;
    private Context context;
    private List<FoodItem> foodItems;
    private static final String SET_GROCERIES_PURPOSE = "SET_GROCERIES_PURPOSE";
    private FoodAdapter foodAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge_to_grocery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((FridgeToGroceryActivity) context).setTitle("Fridge Threshold Items");

        mEmptyView = view.findViewById(R.id.groceries_empty_view);
        sessionFacade = new SessionFacade();
        groceriesRecyclerView = view.findViewById(R.id.groceriesRecyclerView);
        btnSaveEditedItems = view.findViewById(R.id.btnSaveEditedItems);
        btnText = btnSaveEditedItems.findViewById(R.id.btn_text);
        btnText.setText("Add to Grocery");

        Intent intent = ((FridgeToGroceryActivity) context).getIntent();
        foodItems = (List<FoodItem>) intent.getSerializableExtra("foodList");
        Toast.makeText(context, foodItems.get(0).getItemName(), Toast.LENGTH_SHORT).show();

        for (FoodItem item : foodItems) {
            item.setChecked(true);
        }
        setRecyclerView();
        setClickListeners();
        updateUI(foodItems, true);
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

    private void updateUI(List<FoodItem> foodItems, boolean enableEditMode) {
        foodAdapter = new FoodAdapter((FridgeToGroceryActivity) context, context.getString(R.string.recommend_me), foodItems, enableEditMode, this);
        groceriesRecyclerView.setAdapter(foodAdapter);

        groceriesRecyclerView.getRecycledViewPool().clear();
        foodAdapter.notifyDataSetChanged();
    }

    private void setClickListeners() {
        btnSaveEditedItems.setOnClickListener(view -> {
            setItems();
        });
    }

    private void setItems() {
        ((FridgeToGroceryActivity) context).showActivityIndicator(context.getString(R.string.saving_data));
        List<FoodItem> list = new ArrayList<>();
        for (FoodItem foodItem : foodItems) {
            if (foodItem.isChecked()) {
                list.add(foodItem);
            }
        }
        sessionFacade.setIngredients(context, SET_GROCERIES_PURPOSE, this, list);
    }


    @Override
    public void notifyPostSuccess(String response, String purpose) {
        ((FridgeToGroceryActivity) context).hideActivityIndicator();
        ((FridgeToGroceryActivity) context).onBackPressed();
    }

    @Override
    public void notifyPostError(String error, String purpose) {
        ((FridgeToGroceryActivity) context).hideActivityIndicator();
        CommonDialogFragment dialogFragment = new CommonDialogFragment(
                this,
                context.getString(R.string.error_title),
                context.getString(R.string.save_items_error),
                context.getString(R.string.ok),
                "");
        dialogFragment.show(((FridgeToGroceryActivity) context).getSupportFragmentManager(), "");
    }

    @Override
    public void onSelectedPosBtn() {

    }

    @Override
    public void onSelectedNegBtn() {

    }

    @Override
    public void setGroceryList(List<FoodItem> foodItems) {

    }

    @Override
    public void openDetailScreen(Object foodItem) {

    }
}