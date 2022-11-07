package com.aseproject.frigg.service;

import android.content.Context;

import com.android.volley.VolleyError;
import com.aseproject.frigg.common.AppSessionManager;
import com.aseproject.frigg.errorhandling.VolleyErrorHandler;
import com.aseproject.frigg.interfaces.GetListener;
import com.aseproject.frigg.model.FamilyMember;
import com.aseproject.frigg.network.GetClient;
import com.aseproject.frigg.util.Constants;
import com.google.gson.Gson;

public class FamilyMembersService implements GetListener {
    private static FamilyMembersService familyMembersService;
    private FamilyMemberListener listener;
    private Context context;

    public static FamilyMembersService getInstance() {
        if (familyMembersService == null) {
            return new FamilyMembersService();
        }
        return familyMembersService;
    }

    public void getConnectedFamilyMembers(Context context, FamilyMemberListener listener, String purpose) {
        this.context = context;
        this.listener = listener;
        String url = Constants.BASE_URL + "FridgeUser/GetUsersByFridgeId/"+ AppSessionManager.getInstance().getFridgeId();
        GetClient getClient = new GetClient(this, context);
        getClient.fetch(url, null, purpose);
    }

    @Override
    public void notifyFetchSuccess(String parseSuccess, String purpose) {
        FamilyMember[] familyMembers = new Gson().fromJson(parseSuccess, FamilyMember[].class);
        listener.notifyFetchSuccess(familyMembers, purpose);
    }

    @Override
    public void notifyFetchError(VolleyError error, String purpose) {
        listener.notifyFetchError(VolleyErrorHandler.handleError(error, context), purpose);
    }

    public interface FamilyMemberListener {
        void notifyFetchSuccess(FamilyMember[] familyMembers, String purpose);

        void notifyFetchError(String error, String purpose);
    }
}
