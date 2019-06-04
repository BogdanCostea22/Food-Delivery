package com.example.canteen.network;

import com.example.canteen.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitInstance {
    private static  Retrofit retrofit;

    public static Retrofit getRetrofit()
    {
        if(retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }
}
