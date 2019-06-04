package com.example.canteen.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteen.R;
import com.example.canteen.adapter.RestaurantAdapter;
import com.example.canteen.model.Order;
import com.example.canteen.model.Restaurant;
import com.example.canteen.network.RestaurantService;
import com.example.canteen.network.RetrofitInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantFragment extends Fragment implements iOnClickListener {
    //Constants
    public static final String TAG = RestaurantFragment.class.getName();
    public static Long restaurantId = new Long(-1);

    //Variables
    private FloatingActionButton floatingActionButton;
    RestaurantAdapter adapter ;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurants_fragment, container, false);

        //Set floating action button
        floatingActionButton = view.findViewById(R.id.restaurant_floating_action);

        //Set adapter
        adapter =  new RestaurantAdapter(getContext(), this);

        //Set recyclerView
        RecyclerView recyclerView =  view.findViewById(R.id.restaurant_recycler_view);
        recyclerView.setHasFixedSize(true);

        if(LoginFragment.role) {
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddRestaurantDialog();
                }
            });

        }

        //Retrieve restaurants
        retrieveRestaurants();

        //Set recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



        return view;
    }

    @Override
    public void onClickListener(Object restaurant) {
            Restaurant restaurant1 = (Restaurant) restaurant;

            Log.i("Selected restaurant", restaurant1.getId() + "");
            restaurantId = restaurant1.getId();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, new RestaurantMenuFragment(), RestaurantMenuFragment.TAG)
                    .addToBackStack(RestaurantMenuFragment.TAG)
                    .commit();
    }

    //Retrieve restaurants
    private void retrieveRestaurants() {
        RestaurantService service = RetrofitInstance.getRetrofit().create(RestaurantService.class);

        //Check if the user is an admin
        Call<List<Restaurant>> call;
        if (!LoginFragment.role)
            call = service.getAllRestaurants();
        else
            call = service.getRestaurantsForAdmin(LoginFragment.id);

        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                adapter.setRestaurants(response.body());
                Log.i("ResponseRestaurants", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.i("ResponseRestaurants", t.toString());
            }
        });
    }

    public void showAddRestaurantDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.restaurant_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText restaurantNameET = (EditText) dialogView.findViewById(R.id.dialog_restaurant_name);
        final EditText restaurantDescriptionET = dialogView.findViewById(R.id.dialog_restaurant_description);
        final EditText restaurantLocationET = (EditText) dialogView.findViewById(R.id.dialog_restaurant_location);
        final EditText restaurantPhoneET = dialogView.findViewById(R.id.dialog_restaurant_phone);


        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                boolean numeric = true;

                RestaurantService service = RetrofitInstance.getRetrofit().create(RestaurantService.class);

                Restaurant restaurant = new Restaurant(restaurantNameET.getText().toString(), restaurantLocationET.getText().toString()
                                , restaurantPhoneET.getText().toString(), restaurantDescriptionET.getText().toString());

                Call<Map<String, String>> call = service.addRestaurant(restaurant, LoginFragment.id);

                call.enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        retrieveRestaurants();
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        Log.i("Response create restaurant ERROR", t.toString());
                    }
                });



            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
