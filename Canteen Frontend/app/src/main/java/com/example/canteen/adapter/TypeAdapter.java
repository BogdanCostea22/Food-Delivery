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
import com.example.canteen.model.Subcategory;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {
    //Variables
    private List<Subcategory> lista;
    private iOnClickListener instance;
    private Context context;

    public TypeAdapter(Context context, iOnClickListener instance){
        this.context = context;
        this.instance = instance;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {
        Subcategory restaurant = lista.get(position);

        holder.name.setText(restaurant.getName());
        holder.price.setText(restaurant.getPrice() + " price");

        //Set a Random image for each restaurant
        int number = (int)(Math.random()*50 + 1)%5 + 1;
        String name = "subcategory_" + number;
        holder.restaurantImage.setImageResource(context.getResources().getIdentifier(name, "drawable", context.getPackageName()));

    }

    @Override
    public int getItemCount() {
        return lista == null? 0: lista.size();
    }

    public void setSubcategory(List<Subcategory> list)
    {
        this.lista = list;
        notifyDataSetChanged();
    }



    public class TypeViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView price;
        TextView location;
        TextView phone;
        ImageView restaurantImage;

        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.restaurant_name);
            price = itemView.findViewById(R.id.category_price);
            restaurantImage = itemView.findViewById(R.id.category_imageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            instance.onClickListener(lista.get(getAdapterPosition()));
        }
    }
}
