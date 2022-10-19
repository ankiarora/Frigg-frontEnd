package com.aseproject.frigg.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aseproject.frigg.R;
import com.aseproject.frigg.activity.NavActivity;
import com.aseproject.frigg.common.CommonDialogFragment;
import com.aseproject.frigg.model.FridgeItem;
import com.aseproject.frigg.model.GroceryItem;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.GroceryHolder> {

    private static final String TAG = FoodAdapter.class.getSimpleName();
    private final Context context;
    private final boolean enableEditMode;
    private final GroceryHolderListener listener;
    private List foodList = new ArrayList<>();

    public <T> FoodAdapter(Context context, List<T> foodItems, boolean enableEditMode, GroceryHolderListener listener) {
        Log.d(TAG, "ADAPTER: " + foodItems);
        this.listener = listener;

        if (foodItems != null) {
            this.foodList = foodItems;
        }
        this.context = context;
        this.enableEditMode = enableEditMode;
    }

    @Override
    public GroceryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new GroceryHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(GroceryHolder holder, int position) {
        if (foodList.get(position) instanceof GroceryItem) {
            GroceryItem foodItem = (GroceryItem) foodList.get(position);
            holder.bind(foodItem);
        } else {
            FridgeItem foodItem = (FridgeItem) foodList.get(position);
            holder.bind(foodItem);
        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + foodList.size());
        return foodList.size();
    }

    public interface GroceryHolderListener {
        <T> void setGroceryList(List<T> foodItems);
    }


    public class GroceryHolder extends RecyclerView.ViewHolder implements CommonDialogFragment.DialogInterface {

        private ImageView ivItemImage;
        private LinearLayout llAddSubtract;
        private TextView tvFoodCountCrack;
        private ImageView ivSubtractItem;
        private ImageView ivAddItem;
        private Object foodItem;
        private TextView tvItemName;
        private TextView tvItemAmount;

        private GroceryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.food_item, parent, false));

            tvItemName = itemView.findViewById(R.id.foodItemName);
            ivItemImage = itemView.findViewById(R.id.iv_item_image);
            tvItemAmount = itemView.findViewById(R.id.foodAmt);
            ivSubtractItem = itemView.findViewById(R.id.subtract_item);
            ivAddItem = itemView.findViewById(R.id.add_item);
            llAddSubtract = itemView.findViewById(R.id.llAddSubtract);
            tvFoodCountCrack = itemView.findViewById(R.id.food_count_track);
        }

        public void bind(FridgeItem foodItem) {
            if (enableEditMode) {
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
                listener.setGroceryList(foodList);
            });

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler();
            executor.execute(() -> {
                try {
                    String imageURL = "https://frigg-images.s3.amazonaws.com/"+foodItem.getItemName()+".jpg";
                    InputStream inputStream = new URL(imageURL).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                    // Only for making changes in UI
                    handler.post(() -> ivItemImage.setImageBitmap(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            ivSubtractItem.setOnClickListener(view -> {
                int qty = foodItem.getQuantity();
                qty--;
                if (qty < 1) {
                    //show alert and remove the item from list
                    CommonDialogFragment dialogFragment =
                            new CommonDialogFragment(
                                    this,
                                    context.getResources().getString(R.string.grocery_delete_item_title),
                                    context.getResources().getString(R.string.grocery_delete_item_message),
                                    context.getResources().getString(R.string.yes),
                                    context.getResources().getString(R.string.no));
                    dialogFragment.show(((NavActivity) context).getSupportFragmentManager(), "");
                } else {
                    foodItem.setQuantity(qty);
                    tvItemAmount.setText(Integer.toString(foodItem.getQuantity()));
                    tvFoodCountCrack.setText(Integer.toString(foodItem.getQuantity()));
                }
                listener.setGroceryList(foodList);
            });
        }

        public void bind(GroceryItem foodItem) {
            if (enableEditMode) {
                llAddSubtract.setVisibility(View.VISIBLE);
            }
            this.foodItem = foodItem;

            if (foodItem != null && foodItem instanceof GroceryItem) {
                tvItemName.setText(foodItem.getItemName());
                tvItemAmount.setText(Integer.toString(foodItem.getQuantity()));
                tvFoodCountCrack.setText(Integer.toString(foodItem.getQuantity()));

                ivAddItem.setOnClickListener(view -> {
                    int qty = foodItem.getQuantity();
                    qty++;
                    foodItem.setQuantity(qty);
                    tvItemAmount.setText(Integer.toString(foodItem.getQuantity()));
                    tvFoodCountCrack.setText(Integer.toString(foodItem.getQuantity()));
                    listener.setGroceryList(foodList);
                });
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler();
            executor.execute(() -> {
                try {
                    String imageURL = "https://frigg-images.s3.amazonaws.com/"+foodItem.getItemName()+".jpg";
                    InputStream inputStream = new URL(imageURL).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                    // Only for making changes in UI
                    handler.post(() -> ivItemImage.setImageBitmap(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            ivSubtractItem.setOnClickListener(view -> {
                int qty = foodItem.getQuantity();
                qty--;
                if (qty < 1) {
                    //show alert and remove the item from list
                    CommonDialogFragment dialogFragment =
                            new CommonDialogFragment(
                                    this,
                                    context.getResources().getString(R.string.grocery_delete_item_title),
                                    context.getResources().getString(R.string.grocery_delete_item_message),
                                    context.getResources().getString(R.string.yes),
                                    context.getResources().getString(R.string.no));
                    dialogFragment.show(((NavActivity) context).getSupportFragmentManager(), "");
                } else {
                    foodItem.setQuantity(qty);
                    tvItemAmount.setText(Integer.toString(foodItem.getQuantity()));
                    tvFoodCountCrack.setText(Integer.toString(foodItem.getQuantity()));
                }
                listener.setGroceryList(foodList);
            });
        }

        @Override
        public void onSelectedPosBtn() {
            //remove item
            foodList.remove(foodItem);
            if(foodList.isEmpty()) {
                listener.setGroceryList(foodList);
            }
            notifyDataSetChanged();
        }

        @Override
        public void onSelectedNegBtn() {
            //do nothing
        }
    }
}
