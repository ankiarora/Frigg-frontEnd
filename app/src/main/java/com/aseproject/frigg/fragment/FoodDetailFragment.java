package com.aseproject.frigg.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aseproject.frigg.R;
import com.aseproject.frigg.activity.FoodDetailActivity;
import com.aseproject.frigg.activity.NavActivity;
import com.aseproject.frigg.common.CommonDialogFragment;
import com.aseproject.frigg.model.GroceryItem;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodDetailFragment extends Fragment{

    private String type = "";
    private Context context;

    private LinearLayout llFoodItem;
    private ImageView ivItemImage;
    private Object foodItem;
    private TextView tvItemName;
    private TextView tvItemAmount;

    public FoodDetailFragment(String type) {
        this.type = type;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((FoodDetailActivity) context).setTitle(context.getString(R.string.food_detail_title));
        llFoodItem = view.findViewById(R.id.ll_food_item);
        tvItemName = view.findViewById(R.id.foodItemName);
        ivItemImage = view.findViewById(R.id.iv_item_image);
        tvItemAmount = view.findViewById(R.id.foodAmt);

        foodItem = getActivity().getIntent().getSerializableExtra("FOOD_ITEM");

        if(foodItem instanceof GroceryItem)
            setDetails((GroceryItem) foodItem);
    }

    private void setDetails(GroceryItem foodItem) {
        tvItemName.setText(foodItem.getItemName());
        tvItemAmount.setText(Integer.toString(foodItem.getQuantity()));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler();
        executor.execute(() -> {
            try {
                String imageURL = context.getString(R.string.image_url, foodItem.getItemName());
                InputStream inputStream = new URL(imageURL).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                // Only for making changes in UI
                handler.post(() -> ivItemImage.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}