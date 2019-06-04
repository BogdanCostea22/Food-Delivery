package com.example.canteen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteen.R;
import com.example.canteen.activity.iOnClickListener;
import com.example.canteen.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
   //Variables
    private List<Restaurant> lista;
    private iOnClickListener instance;
    private Context context;

    public RestaurantAdapter(Context context, iOnClickListener instance){
        this.context = context;
        this.instance = instance;
           }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_layout, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = lista.get(position);

        holder.name.setText(restaurant.getName());
        holder.description.setText(restaurant.getDescription());
        holder.phone.setText(restaurant.getPhone());
        holder.location.setText(restaurant.getLocation());

        //Set a Random image for each restaurant
        int number = (int)(Math.random()*50 + 1)%5 + 1;
        String name = "restaurant_" + number;
        holder.restaurantImage.setImageResource(context.getResources().getIdentifier(name, "drawable", context.getPackageName()));

    }

    @Override
    public int getItemCount() {
        return lista == null? 0: lista.size();
    }

    public void setRestaurants(List<Restaurant> list)
    {
        this.lista = list;
        notifyDataSetChanged();
    }



    public class RestaurantViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView description;
        TextView location;
        TextView phone;
        ImageView restaurantImage;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.restaurant_name);
            description = itemView.findViewById(R.id.restaurant_description);
            phone = itemView.findViewById(R.id.restaurant_phone);
            location = itemView.findViewById(R.id.restaurant_location);
            restaurantImage = itemView.findViewById(R.id.restaurant_imageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            instance.onClickListener(lista.get(getAdapterPosition()));
        }
    }
}
