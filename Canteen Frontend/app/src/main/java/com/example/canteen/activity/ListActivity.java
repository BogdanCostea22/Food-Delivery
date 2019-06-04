package com.example.canteen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.canteen.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Open restaurants fragment
        getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_container, new RestaurantFragment(), RestaurantFragment.TAG)
                    .addToBackStack(RestaurantFragment.TAG)
                    .commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() != 1)
            super.onBackPressed();
        else
            finish();
    }
}
