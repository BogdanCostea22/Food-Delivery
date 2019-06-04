package com.example.canteen.network;

import com.example.canteen.model.Order;
import com.example.canteen.model.Restaurant;
import com.example.canteen.model.Subcategory;
import com.example.canteen.model.Type;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantService {
    @GET("/api/restaurants")
    Call<List<Restaurant>> getAllRestaurants();

    @GET("/api/adminRestaurants/{id}")
    Call<List<Restaurant>> getRestaurantsForAdmin(@Path("id") Long id);

    @GET("/api/getAllTypes")
    Call<List<Type>> getTypes();

    @GET("/api/getSubcategoriesRestaurant/{id}/{typeId}/")
    Call<List<Subcategory>> getSubcategory(@Path("id") Long restaurantId, @Path("typeId") Long typeId);

    @POST("/api/addOrder/{number}/{restaurant_id}/{user_id}")
    Call<Map<String,String>> addOrder(@Body Subcategory subcategory, @Path("number") int number, @Path("restaurant_id") int restaurant_id, @Path("user_id") int userId);

    @POST("/api/addRestaurant/{id}/")
    Call<Map<String, String>> addRestaurant(@Body Restaurant restaurant, @Path("id") Long id);

    @POST("/api/addMenu/{restaurant_id}/{type_id}")
    Call<Map<String, String>> addCategory(@Body Subcategory subcategory, @Path("restaurant_id") Long restaurantId, @Path("type_id") Long type_id);

    @GET("/api/getOrders/{id}")
    Call<List<Order>> getOrders(@Path("id") int id);

    @PUT("/api/deliverOrder/{id}")
    Call<Map<String, String>> deliverOrder(@Path("id") Long id);
}
