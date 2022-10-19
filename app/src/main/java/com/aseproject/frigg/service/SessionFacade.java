package com.aseproject.frigg.service;

import android.content.Context;

import com.aseproject.frigg.model.FridgeItem;
import com.aseproject.frigg.model.GroceryItem;
import com.aseproject.frigg.util.Constants;

import java.util.List;

public class SessionFacade {
    public void getGroceries(Context context, String purpose, FoodService.FoodServiceGetListener listener) {
        final String url = Constants.BASE_URL+"GroceryList/";
        FoodService.getInstance().getGroceries(url, context, purpose, listener);
    }

    public void getFridgeList(Context context, String purpose, FoodService.FoodServiceGetListener listener) {
        final String url = Constants.BASE_URL+"FridgeList/";
        FoodService.getInstance().getFridgeItems(url, context, purpose, listener);
    }

    public void setGroceries(Context context, String purpose, FoodService.FoodServicePostListener listener, List<GroceryItem> groceries) {
        FoodService.getInstance().setGroceries(context, purpose, listener, groceries);
    }

    public void setFridgeList(Context context, String purpose, FoodService.FoodServicePostListener listener, List<FridgeItem> fridgeItems) {
        FoodService.getInstance().setFridgeItem(context, purpose, listener, fridgeItems);
    }
}