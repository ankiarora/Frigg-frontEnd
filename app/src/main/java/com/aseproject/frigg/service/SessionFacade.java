package com.aseproject.frigg.service;

import android.content.Context;

import com.aseproject.frigg.BuildConfig;

public class SessionFacade {
    public void getGroceries(Context context, String purpose, GroceryService.GroceryServiceListener listener) {
        GroceryService.getInstance().getGroceries(context, purpose, listener);
    }

}