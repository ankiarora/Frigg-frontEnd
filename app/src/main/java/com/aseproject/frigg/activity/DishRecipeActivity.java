package com.aseproject.frigg.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aseproject.frigg.R;
import com.aseproject.frigg.fragment.DishRecipeFragment;
import com.aseproject.frigg.fragment.FoodDetailFragment;

public class DishRecipeActivity extends FriggActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_recipe);

        DishRecipeFragment dishRecipeFragment = new DishRecipeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, dishRecipeFragment).commit();
    }
}