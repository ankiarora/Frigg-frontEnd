package com.aseproject.frigg.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aseproject.frigg.R;
import com.aseproject.frigg.activity.DishRecipeActivity;
import com.aseproject.frigg.activity.FamilyMemberActivity;
import com.aseproject.frigg.activity.NavActivity;
import com.aseproject.frigg.adapter.FamilyMembersAdapter;
import com.aseproject.frigg.adapter.FoodAdapter;
import com.aseproject.frigg.common.AppSessionManager;
import com.aseproject.frigg.common.FriggRecyclerView;
import com.aseproject.frigg.model.FamilyMember;
import com.aseproject.frigg.service.FamilyMembersService;
import com.aseproject.frigg.service.SessionFacade;

import org.w3c.dom.Text;

public class FamilyMemberFragment extends Fragment implements FamilyMembersService.FamilyMemberListener {

    private LinearLayout llCode;
    private TextView btn_text;
    private Context context;
    private String inviteCode;
    private static final String PURPOSE_FAMILY_MEMBERS = "PURPOSE_FAMILY_MEMBERS";
    private SessionFacade sessionFacade;
    private FriggRecyclerView rvFamilyMembers;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_family_memeber, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionFacade = new SessionFacade();
        ((FamilyMemberActivity) context).setTitle("Family Members");

        llCode = view.findViewById(R.id.llCode);
        btn_text = llCode.findViewById(R.id.btn_text);
        rvFamilyMembers = view.findViewById(R.id.rvFamilyMembers);

        btn_text.setText(AppSessionManager.getInstance().getInviteCode());
        setRecyclerView();
        downloadFamilyMembers();
    }

    private void setRecyclerView() {
        rvFamilyMembers.setHasFixedSize(true);
        rvFamilyMembers.setItemAnimator(null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFamilyMembers.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        rvFamilyMembers.addItemDecoration(dividerItemDecoration);
    }


    private void downloadFamilyMembers() {
        ((FamilyMemberActivity) context).showActivityIndicator(context.getString(R.string.fetching_data));
        sessionFacade.getConnectedFamilyMembers(context, this, PURPOSE_FAMILY_MEMBERS);
    }

    @Override
    public void notifyFetchSuccess(FamilyMember[] familyMembers, String purpose) {
        ((FamilyMemberActivity) context).hideActivityIndicator();
        updateUi(familyMembers);
    }

    private void updateUi(FamilyMember[] familyMembers) {
        FamilyMembersAdapter adapter = new FamilyMembersAdapter(context, familyMembers);
        rvFamilyMembers.setAdapter(adapter);

        rvFamilyMembers.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyFetchError(String error, String purpose) {
        ((FamilyMemberActivity) context).hideActivityIndicator();
    }
}