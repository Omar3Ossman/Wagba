package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Space;

import com.example.wagba.Listener.CartLoadListener;
import com.example.wagba.Listener.OrdersLoadListener;
import com.example.wagba.adapter.MyOrderAdapter;
import com.example.wagba.eventbus.MyUpdateCartEvent;
import com.example.wagba.model.CartModel;
import com.example.wagba.model.OrdersModel;
import com.example.wagba.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddToCart extends AppCompatActivity implements OrdersLoadListener, CartLoadListener {

    @BindView(R.id.recycler_orders)
    RecyclerView recyclerOrders;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.badge)
    NotificationBadge badge;
    @BindView(R.id.btnCart)
    FrameLayout btnCart;


    OrdersLoadListener ordersLoadListener;
    CartLoadListener cartLoadListener;


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if (EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class)){
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);
        }
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUpdateCart(MyUpdateCartEvent event){
    countCartItem();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        init();
        loadOrderFromFirebase();
        countCartItem();
    }

    private void loadOrderFromFirebase() {
        List<OrdersModel> ordersModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Order")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){
                            for (DataSnapshot orderSnapshot:snapshot.getChildren())
                            {
                                OrdersModel ordersModel = orderSnapshot.getValue(OrdersModel.class);
                                ordersModel.setKey(orderSnapshot.getKey());
                                ordersModels.add(ordersModel);

                            }
                            ordersLoadListener.onOrderSuccess(ordersModels);
                        }
                        else
                            ordersLoadListener.onOrderFailure("Can't Find Your Dish");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        ordersLoadListener.onOrderFailure(error.getMessage());
                    }
                });
    }

    private void init(){
        ButterKnife.bind(this);

        ordersLoadListener= this;
        cartLoadListener = this;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerOrders.setLayoutManager(gridLayoutManager);
        recyclerOrders.addItemDecoration(new SpaceItemDecoration());
    }

    @Override
    public void onOrderSuccess(List<OrdersModel> ordersModelList) {
        MyOrderAdapter adapter = new MyOrderAdapter(this,ordersModelList,cartLoadListener);
        recyclerOrders.setAdapter(adapter);
    }

    @Override
    public void onOrderFailure(String message) {
        Snackbar.make(mainLayout,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCartSuccess(List<CartModel> cartModelList) {

        int cartSum=0;
        for (CartModel cartModel: cartModelList)
            cartSum+= cartModel.getQuantity();
        badge.setNumber(cartSum);
    }

    @Override
    public void onCartFailure(String message) {
Snackbar.make(mainLayout,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCartSuccess(String message) {

    }

    protected void onResume(){
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {
        List<CartModel> cartModels =new ArrayList<>();
        FirebaseDatabase
                .getInstance().getReference("Cart")
                .child("Key_ID")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot cartSnapshot: snapshot.getChildren()){
                            CartModel cartModel= cartSnapshot.getValue(CartModel.class);
                            cartModel.setKey(cartSnapshot.getKey());
                            cartModels.add(cartModel);
                        }
                        cartLoadListener.onCartSuccess(cartModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartFailure(error.getMessage());
                    }
                });
    }
}