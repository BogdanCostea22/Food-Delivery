package com.example.canteen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.canteen.R;
import com.example.canteen.model.LoginResponse;
import com.example.canteen.network.LoginService;
import com.example.canteen.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    //Constants
    public static final String TAG = LoginFragment.class.getName();
    public static Long id;
    public static boolean role;

    //Variables
    Button loginButton;
    EditText usernameET;
    EditText passwordET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final Activity activity = getActivity();

        view.findViewById(R.id.create_new_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction()
                                             .replace(R.id.login_fragment_container, new RegisterFragment(), RegisterFragment.TAG)
                                             .addToBackStack(RegisterFragment.TAG).commit();

            }
        });

        usernameET = view.findViewById(R.id.username_et);
        passwordET = view.findViewById(R.id.password_et);

        usernameET.setText("user");//("admin");
        passwordET.setText("parola");//("admin");

        final Context context = getContext();

        loginButton = view.findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                LoginService service = RetrofitInstance.getRetrofit().create(LoginService.class);

                //
                Call<LoginResponse> call = service.loginUser(username, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        id = response.body().getId();
                        role = response.body().isRole();

                        if(id != -1) {
                            Intent intent = new Intent(context, ListActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(context, "Problem with connection or wrong credentials", Toast.LENGTH_SHORT);
                    }
                });
            }
        });

        return view;
    }
}
