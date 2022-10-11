package com.aseproject.frigg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aseproject.frigg.R;
import com.aseproject.frigg.model.FoodItem;
import com.aseproject.frigg.service.SessionFacade;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.MoreMedDocListHolder> {

    private static final String TAG = GroceryAdapter.class.getSimpleName();
    private final Context context;
    private List<FoodItem> groceries = new ArrayList<>();
    private final SessionFacade sessionFacade;

    public GroceryAdapter(Context context, List<FoodItem> medDocs) {
        Log.d(TAG, "ADAPTER: " + medDocs);
        if (groceries != null) {
            groceries = medDocs;
        }
        this.context = context;
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
        }

        public void bind(FoodItem foodItem) {
            this.foodItem = foodItem;
            tvItemName.setText(foodItem.getItemName());
            tvItemAmount.setText(foodItem.getItemQty());
        }
    }
}
