package com.example.canteen.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.canteen.R;
import com.example.canteen.model.User;
import com.example.canteen.network.LoginService;
import com.example.canteen.network.RetrofitInstance;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    public static final String TAG = RegisterFragment.class.getName();
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private EditText adminCode;
    private Button registerButton;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        username = view.findViewById(R.id.register_username);
        password = view.findViewById(R.id.register_password);
        confirmPassword = view.findViewById(R.id.register_confirm_password);
        adminCode = view.findViewById(R.id.register_admin_code);

        //Set listener for creating new account
        registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();
                String confirmPasswordString = confirmPassword.getText().toString();
                String adminCodeString = adminCode.getText().toString();

                if(!usernameString.isEmpty() && !passwordString.isEmpty() && !confirmPasswordString.isEmpty() )
                {
                    User user = new User(usernameString, passwordString);

                    //Create Login service instance
                    LoginService service = RetrofitInstance.getRetrofit().create(LoginService.class);

                    //Check admin property
                    if(!adminCodeString.isEmpty())
                    {
                        user.setRole(true);

                        Call<Map<String, String>> call = service.registerUser(user);

                        call.enqueue(new Callback<Map<String, String>>() {
                            @Override
                            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                getActivity().onBackPressed();
                            }

                            @Override
                            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                getActivity().onBackPressed();
                            }
                        });


                    }
                    else
                    {
                        user.setRole(false);

                        Call<Map<String, String>> call = service.registerUser(user);

                        call.enqueue(new Callback<Map<String, String>>() {
                            @Override
                            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                getActivity().onBackPressed();
                            }

                            @Override
                            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                getActivity().onBackPressed();
                            }
                        });

                    }
                }

            }
        });
        return view;
    }
}
