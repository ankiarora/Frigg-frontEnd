package com.aseproject.frigg.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.aseproject.frigg.common.AppSessionManager;
import com.aseproject.frigg.interfaces.PostListener;
import com.aseproject.frigg.model.UserDetails;
import com.aseproject.frigg.network.PostClient;
import com.aseproject.frigg.util.Constants;
import com.google.gson.Gson;

import java.util.List;

//service where network calls are made
public class AuthService implements PostListener {

    private static final String TAG = AuthService.class.getSimpleName();
    private AuthServicePostListener postListener;
    private Context context;

    private static AuthService authService;

    public static AuthService getInstance() {
        if (authService == null) {
            authService = new AuthService();
        }
        return authService;
    }


    // a call is made when user clicks on login
    public void login(Context context, String purpose, AuthServicePostListener listener, UserDetails details) {
        Log.d(TAG, "inside login auth service");
        this.postListener = listener;
        final String url = Constants.BASE_URL + "User/login";
        this.context = context;
        PostClient postClient = new PostClient(this, context);
        postClient.sendData(url, new Gson().toJson(details), null, purpose);

    }
    // a call is made when user clicks on register
    public void register(Context context, String purpose, AuthServicePostListener listener, UserDetails details) {
        Log.d(TAG, "inside register auth service");
        this.postListener = listener;
        final String url = Constants.BASE_URL + "User";
        this.context = context;
        PostClient postClient = new PostClient(this, context);
        postClient.sendData(url, new Gson().toJson(details), null, purpose);
    }

    // a call is made when a call is successful
    @Override
    public void notifyPostSuccess(String response, String purpose) {
        postListener.notifyPostSuccess(response, purpose);
    }

    // a call is made when a call is failed
    @Override
    public void notifyPostError(VolleyError error, String message, String purpose) {
        postListener.notifyPostError(message, purpose);
    }

    public interface AuthServiceGetListener {
        <T> void notifyFetchSuccess(List<T> labResults, String purpose);

        void notifyFetchError(String error, String purpose);
    }

    public interface AuthServicePostListener {
        void notifyPostSuccess(String response, String purpose);

        void notifyPostError(String error, String purpose);
    }
}