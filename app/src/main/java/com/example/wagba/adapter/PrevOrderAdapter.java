package com.example.wagba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.model.OrdersModel;

import java.util.ArrayList;

public class PrevOrderAdapter extends RecyclerView.Adapter<PrevOrderAdapter.PrevOrderViewHolder> {

    Context context ;
    ArrayList<OrdersModel> previousOrderList;

    public PrevOrderAdapter(Context context, ArrayList<OrdersModel> previousOrderList) {
        this.context = context;
        this.previousOrderList = previousOrderList;
    }

    @NonNull
    @Override
    public PrevOrderAdapter.PrevOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.previous_order_item , parent, false);

        return new PrevOrderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PrevOrderViewHolder holder, int position) {

        holder.prevOrder_state.setText(previousOrderList.get(position).getState());
        holder.prevorder_price.setText(previousOrderList.get(position).getPrice());
        holder.prevorder_number.setText(previousOrderList.get(position).getKey());
        holder.prevorder_gate.setText(previousOrderList.get(position).getGate());
        holder.prevorder_address.setText(previousOrderList.get(position).getAddress());

    }



    @Override
    public int getItemCount() {
        return previousOrderList.size();
    }

    public static final class PrevOrderViewHolder extends RecyclerView.ViewHolder{

        TextView  prevOrder_state,prevorder_price, prevorder_number,prevorder_gate,prevorder_address;

        public PrevOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            prevOrder_state = itemView.findViewById(R.id.prevOrder_state);
            prevorder_price = itemView.findViewById(R.id.prevorder_price);
            prevorder_number = itemView.findViewById(R.id.prevorder_number);
            prevorder_gate = itemView.findViewById(R.id.prevorder_gate);
            prevorder_address = itemView.findViewById(R.id.prevorder_address);


        }
    }
}
