package com.example.canteen.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.canteen.R;
import com.example.canteen.activity.LoginFragment;
import com.example.canteen.activity.RegisterFragment;


public class StartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_start, container, false);

        TextView logIn = view.findViewById(R.id.login_editText);

        final Activity activity = getActivity();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container,new LoginFragment(), LoginFragment.TAG).addToBackStack(LoginFragment.TAG).commit();
            }
        });
        return view;
    }
}
