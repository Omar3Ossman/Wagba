package com.example.wagba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.Listener.CartLoadListener;
import com.example.wagba.Listener.IRecyclerViewClickListener;
import com.example.wagba.R;
import com.example.wagba.eventbus.MyUpdateCartEvent;
import com.example.wagba.model.CartModel;
import com.example.wagba.model.OrdersModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {


    private Context context;
    private List<OrdersModel>ordersModelList;
    private CartLoadListener iCartLoadListener;

    public MyOrderAdapter(Context context, List<OrdersModel> ordersModelList, CartLoadListener iCartLoadListener) {
        this.context = context;
        this.ordersModelList = ordersModelList;
        this.iCartLoadListener = iCartLoadListener;
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        Glide.with(context).load(ordersModelList.get(position).getImage()).into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("$").append(ordersModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(ordersModelList.get(position).getName()));

        holder.setListener((view, adapterPosition) -> {
            addToCart(ordersModelList.get(position));
        });

    }

    private void addToCart(OrdersModel ordersModel) {
        DatabaseReference userCart= FirebaseDatabase
                .getInstance().getReference("Cart").child("Key_Id");

        userCart.child((ordersModel.getKey()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())  //already item in cart
                        {
                            //update quantity and price
                            CartModel cartModel = snapshot.getValue(CartModel.class);
                            Map<String,Object> updateData = new HashMap<>();
                            updateData.put("quantity",cartModel.getQuantity()+1);
                            updateData.put("total price",cartModel.getQuantity()*Float
                                    .parseFloat(cartModel.getPrice()));

                            userCart.child(ordersModel.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(aVoid ->{
                                       iCartLoadListener.onCartFailure("Add to Cart Succesfully");
                                    })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartFailure(e.getMessage()));

                        }
                        else        //if cart is empty,then add items
                        {
                            CartModel cartModel = new CartModel();
                            cartModel.setName(ordersModel.getName());
                            cartModel.setImage(ordersModel.getImage());
                            cartModel.setKey(ordersModel.getKey());
                            cartModel.setPrice(ordersModel.getPrice());
                            cartModel.setQuantity(1);
                            cartModel.setTotalPrice(Float.parseFloat
                                    (ordersModel.getPrice()));

                            userCart.child(ordersModel.getKey()).
                                    setValue(cartModel)
                                    .addOnSuccessListener(aVoid ->{
                                        iCartLoadListener.onCartFailure("Add to Cart Succesfully");
                                    })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartFailure(e.getMessage()));

                        }

                        EventBus.getDefault().postSticky(new MyUpdateCartEvent());
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iCartLoadListener.onCartSuccess(error.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return ordersModelList.size();
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageview)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtprice)
        TextView txtPrice;

        IRecyclerViewClickListener listener;

        public void setListener(IRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        private Unbinder unbinder;
        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecyclerClick(v,getAbsoluteAdapterPosition());
        }
    }
}
