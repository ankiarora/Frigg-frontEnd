package com.aseproject.frigg.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.aseproject.frigg.R;
import com.aseproject.frigg.fragment.FridgeFragment;
import com.aseproject.frigg.fragment.GroceryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavActivity extends FriggActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        bottomNavigationView = findViewById(R.id.bottomNavBar);

        handleBottomNavigation();
        bottomNavigationView.setSelectedItemId(R.id.menu_item_0);
    }

    private void handleBottomNavigation() {
        FridgeFragment fridgeFragment = new FridgeFragment();
        GroceryFragment groceryFragment = new GroceryFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener
                (item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_item_1:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, groceryFragment).commit();
                            break;
                        case R.id.menu_item_2:
                            break;
                        case R.id.menu_item_3:
                            break;
                        default:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fridgeFragment).commit();
                            break;
                    }
                    return true;
                });
    }


}