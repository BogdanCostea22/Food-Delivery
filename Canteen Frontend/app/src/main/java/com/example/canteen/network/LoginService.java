package com.example.canteen.network;

import com.example.canteen.model.LoginResponse;
import com.example.canteen.model.Restaurant;
import com.example.canteen.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {

    @GET("/api/login/{username}/{password}")
    Call<LoginResponse> loginUser(@Path("username") String username, @Path("password") String password);

    @POST("/api/registerUser")
    Call<Map<String, String>> registerUser(@Body User user);

}
