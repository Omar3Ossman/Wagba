package com.example.wagba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<Meals> mealsRestaurants;

    public MealsAdapter(Context context, ArrayList<Meals> mealsRestaurants,
                        RecyclerViewInterface recyclerViewInterface){
        this.context=context;
        this.mealsRestaurants = mealsRestaurants;
        this.recyclerViewInterface= recyclerViewInterface;
    }

    @NonNull
    @Override
    public MealsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meals_row, parent, false);

        return new MealsAdapter.MyViewHolder(view, recyclerViewInterface) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.MyViewHolder holder, int position) {

        holder.name.setText(mealsRestaurants.get(position).getMealName());
        holder.price.setText(mealsRestaurants.get(position).getMealPrice());
        holder.mealRating.setRating(mealsRestaurants.get(position).getRating());
        Picasso.get().load(mealsRestaurants.get(position).getMealImage()).into(holder.imageView);
        holder.add.setOnClickListener(view -> {
            FirebaseDatabase.getInstance().getReference().child("cart").push().setValue(mealsRestaurants.get(holder.getAbsoluteAdapterPosition()));
            Toast.makeText(view.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
        });
        holder.checkout.setOnClickListener(view ->{
            Intent intent  = new Intent(context, CartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mealsRestaurants.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,price;
        RatingBar mealRating;
        Button add;
        Button checkout;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView12);
            name = itemView.findViewById(R.id.name_of_meal);
            price = itemView.findViewById(R.id.price_of_meal);
            mealRating = itemView.findViewById(R.id.rating_meal);
            add = itemView.findViewById(R.id.add);
            checkout = itemView.findViewById(R.id.payment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });


        }
    }
}
