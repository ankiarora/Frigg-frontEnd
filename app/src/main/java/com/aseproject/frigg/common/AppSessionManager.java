package com.aseproject.frigg.common;

import android.util.Log;

import com.aseproject.frigg.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class AppSessionManager {

    private static final String TAG = "AppSessionManager";
    private static volatile AppSessionManager mInstance;
    private List<FoodItem> groceries = new ArrayList<>();

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

    public List<FoodItem> getGroceries() {
        return groceries;
    }

    public void setGroceries(List<FoodItem> groceries) {
        this.groceries = groceries;
    }
}
