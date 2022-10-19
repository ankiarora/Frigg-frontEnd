package com.aseproject.frigg.common;

import android.util.Log;

import com.aseproject.frigg.model.GroceryItem;

import java.util.ArrayList;
import java.util.List;

public class AppSessionManager {

    private static final String TAG = "AppSessionManager";
    private static volatile AppSessionManager mInstance;
    private List<GroceryItem> groceries = new ArrayList<>();
    private List<GroceryItem> fridgeItems = new ArrayList<>();

    //Private constructor
    private AppSessionManager() {
        //Protect from the reflection api.
        if (mInstance != null) {
            Log.e(TAG, "Exception: Use getInstance() method to get the single instance of this class.");
        }
    }

    public static AppSessionManager getInstance() {
        synchronized (AppSessionManager.class) {
            if (mInstance == null) mInstance = new AppSessionManager();
        }
        return mInstance;
    }

    public List<GroceryItem> getGroceries() {
        return groceries;
    }

    public List<GroceryItem> getFridgeList() {
        return fridgeItems;
    }

    public void setFridgeItems(List<GroceryItem> fridgeItems) {
        this.fridgeItems = fridgeItems;
    }

    public void setGroceries(List<GroceryItem> groceries) {
        this.groceries = groceries;
    }
}
