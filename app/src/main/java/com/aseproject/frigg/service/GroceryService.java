package com.aseproject.frigg.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.aseproject.frigg.R;
import com.aseproject.frigg.common.AppSessionManager;
import com.aseproject.frigg.errorhandling.VolleyErrorHandler;
import com.aseproject.frigg.interfaces.GetListener;
import com.aseproject.frigg.model.FoodItem;
import com.aseproject.frigg.network.GetClient;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GroceryService implements GetListener {
    private static final String TAG = GroceryService.class.getSimpleName();
    private static GroceryService labResultsService;
    private GroceryServiceListener getListener;
    private Context context;

    private static GroceryService groceryService;

    public static GroceryService getInstance() {
        if (groceryService == null) {
            return new GroceryService();
        }
        return groceryService;
    }

    public void getGroceries(Context context, String purpose, GroceryServiceListener listener) {
        this.getListener = listener;
        final String url = "";
        this.context = context;
        if (AppSessionManager.getInstance().getGroceries().isEmpty()) {
            GetClient getClient = new GetClient(this, context);
            getClient.fetch(url, null, purpose);
        } else {
            listener.notifyFetchSuccess(AppSessionManager.getInstance().getGroceries());
        }
    }

    @Override
    public void notifyFetchSuccess(String parseSuccess, String purpose) {
        try {
            FoodItem[] labResults = new Gson().fromJson(parseSuccess, FoodItem[].class);
            AppSessionManager.getInstance().setGroceries(new LinkedList<>(Arrays.asList(labResults)));
            Log.d(TAG, "Lab Results retrieved: " + AppSessionManager.getInstance().getGroceries().size());
            getListener.notifyFetchSuccess(new LinkedList<>(Arrays.asList(labResults)));
        } catch (JsonParseException exception) {
            Log.e(TAG, "exception:" + exception);
            getListener.notifyFetchError(context.getString(R.string.error_400), purpose);
        }
    }

    @Override
    public void notifyFetchError(VolleyError error, String purpose) {
        final String url = "";
        Log.d("Error.Response", error.getMessage());
        getListener.notifyFetchError(VolleyErrorHandler.handleError(error, context), purpose);
    }

    public interface GroceryServiceListener {
        void notifyFetchSuccess(List<FoodItem> labResults);

        void notifyFetchError(String error, String purpose);
    }
}
