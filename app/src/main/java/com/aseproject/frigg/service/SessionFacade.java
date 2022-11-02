package com.aseproject.frigg.service;

import android.content.Context;

import com.aseproject.frigg.fragment.RecommendFragment;
import com.aseproject.frigg.model.FoodItem;
import com.aseproject.frigg.util.Constants;

import java.util.List;

public class SessionFacade {
    public void getGroceries(Context context, String purpose, FoodService.FoodServiceGetListener listener) {
        final String url = Constants.BASE_URL + "GroceryList/";
        FoodService.getInstance().getGroceries(url, context, purpose, listener);
    }

    public void getFridgeList(Context context, String purpose, FoodService.FoodServiceGetListener listener) {
        final String url = Constants.BASE_URL + "FridgeList/";
        FoodService.getInstance().getFridgeItems(url, context, purpose, listener);
    }

    public void setGroceries(Context context, String purpose, FoodService.FoodServicePostListener listener, List<FoodItem> groceries) {
        FoodService.getInstance().setGroceries(context, purpose, listener, groceries);
    }

    public void setFridgeList(Context context, String purpose, FoodService.FoodServicePostListener listener, List<FoodItem> fridgeItems) {
        FoodService.getInstance().setFridgeItem(context, purpose, listener, fridgeItems);
    }

    public void addItem(Context context, FoodItem foodItem, String url, String purpose, FoodService.FoodServicePostListener listener) {
        FoodService.getInstance().addGroceryItem(context, purpose, url, listener, foodItem);
    }

    public void searchIngredients(Context context, String purpose, RecommendService.RecommendListener listener, String url) {
        RecommendService.getInstance().searchIngredients(context, purpose, listener, url);
    }

    public void setIngredients(Context context, String purpose, RecommendService.RecommendPostListener listener, List<FoodItem> list) {
        RecommendService.getInstance().setIngredients(context, purpose, listener, list);
    }
}