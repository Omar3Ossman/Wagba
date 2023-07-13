package com.example.wagba.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.RecyclerViewInterface;
import com.example.wagba.model.BestForYou;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BestForYouAdapter extends RecyclerView.Adapter<BestForYouAdapter.BestForYouViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context ;
    List<BestForYou> bestForYouList;

    public BestForYouAdapter(RecyclerViewInterface recyclerViewInterface, Context context, List<BestForYou> bestForYouList) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.bestForYouList = bestForYouList;
    }

    @NonNull
    @Override
    public BestForYouAdapter.BestForYouViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.best_for_you_row , parent, false);

        return new BestForYouViewHolder(view, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull BestForYouAdapter.BestForYouViewHolder holder, int position) {

        Log.d("image",bestForYouList.get(position).getImageURL());
        //Picasso
        Picasso.get().load(bestForYouList.get(position).getImageURL()).into(holder.itemImage);
        //holder.itemImage.setImageResource(bestForYouList.get(position).getImageURL());
        holder.itemName.setText(bestForYouList.get(position).getName());
        holder.itemName.setText(bestForYouList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return bestForYouList.size();
    }

    public static final class BestForYouViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView  itemFee, itemName;

        public BestForYouViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.restaurant);
            itemFee = itemView.findViewById(R.id.price_of_meal);
            itemName = itemView.findViewById(R.id.restaurant_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
