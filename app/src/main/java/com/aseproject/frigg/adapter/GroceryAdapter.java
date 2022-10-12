package com.aseproject.frigg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aseproject.frigg.R;
import com.aseproject.frigg.model.FoodItem;
import com.aseproject.frigg.service.SessionFacade;

import java.util.ArrayList;
import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.MoreMedDocListHolder> {

    private static final String TAG = GroceryAdapter.class.getSimpleName();
    private final Context context;
    private final boolean enableEditMode;
    private List<FoodItem> groceries = new ArrayList<>();
    private final SessionFacade sessionFacade;

    public GroceryAdapter(Context context, List<FoodItem> medDocs, boolean enableEditMode) {
        Log.d(TAG, "ADAPTER: " + medDocs);
        if (groceries != null) {
            groceries = medDocs;
        }
        this.context = context;
        this.enableEditMode = enableEditMode;
        sessionFacade = new SessionFacade();
    }

    @Override
    public MoreMedDocListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new MoreMedDocListHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(MoreMedDocListHolder holder, int position) {
        FoodItem foodItem = groceries.get(position);
        holder.bind(foodItem);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + groceries.size());
        return groceries.size();
    }


    protected class MoreMedDocListHolder extends RecyclerView.ViewHolder {

        private LinearLayout llAddSubtract;
        private TextView tvFoodCountCrack;
        private ImageView ivSubtractItem;
        private ImageView ivAddItem;
        private FoodItem foodItem;
        private TextView tvItemName;
        private TextView tvItemAmount;

        public MoreMedDocListHolder(@NonNull View itemView) {
            super(itemView);
        }


        private MoreMedDocListHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.food_item, parent, false));

            tvItemName = itemView.findViewById(R.id.foodItemName);
            tvItemAmount = itemView.findViewById(R.id.foodAmt);
            ivSubtractItem = itemView.findViewById(R.id.subtract_item);
            ivAddItem = itemView.findViewById(R.id.add_item);
            llAddSubtract = itemView.findViewById(R.id.llAddSubtract);
            tvFoodCountCrack = itemView.findViewById(R.id.food_count_track);
        }

        public void bind(FoodItem foodItem) {
            if(enableEditMode) {
                llAddSubtract.setVisibility(View.VISIBLE);
            }
            this.foodItem = foodItem;
            tvItemName.setText(foodItem.getItemName());
            tvItemAmount.setText(Integer.toString(foodItem.getQuantity()));
            tvFoodCountCrack.setText(Integer.toString(foodItem.getQuantity()));

            ivAddItem.setOnClickListener(view -> {
                int qty = foodItem.getQuantity();
                qty++;
                foodItem.setQuantity(qty);
                tvItemAmount.setText(Integer.toString(foodItem.getQuantity()));
                tvFoodCountCrack.setText(Integer.toString(foodItem.getQuantity()));
            });
            ivSubtractItem.setOnClickListener(view -> {
                int qty = foodItem.getQuantity();
                qty--;
                if(qty<1) {
                    //show alert and remove the item from list
                } else {
                    foodItem.setQuantity(qty);
                    tvItemAmount.setText(Integer.toString(foodItem.getQuantity()));
                    tvFoodCountCrack.setText(Integer.toString(foodItem.getQuantity()));
                }
            });
        }
    }
}
