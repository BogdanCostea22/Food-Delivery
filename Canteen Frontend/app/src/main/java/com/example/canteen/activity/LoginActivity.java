package com.example.canteen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.canteen.R;
import com.example.canteen.activity.StartFragment;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set new fragment
        LoginFragment startFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.login_fragment_container, startFragment, LoginFragment.class.getName()).addToBackStack(LoginFragment.class.getName()).commit();

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() != 1)
            super.onBackPressed();
        else
            finish();
    }
}
