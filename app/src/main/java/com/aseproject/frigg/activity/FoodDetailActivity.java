package com.aseproject.frigg.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aseproject.frigg.R;
import com.aseproject.frigg.fragment.FoodDetailFragment;
import com.aseproject.frigg.fragment.FoodFragment;

public class FoodDetailActivity extends FriggActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        FoodDetailFragment foodFragment = new FoodDetailFragment(getIntent().getStringExtra("TYPE"));
        getSupportFragmentManager().beginTransaction().replace(R.id.container, foodFragment).commit();
    }
}