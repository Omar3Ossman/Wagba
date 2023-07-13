package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.wagba.model.OrdersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrackOrder extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    OrdersModel order;
    MutableLiveData<OrdersModel> orderLive = new MutableLiveData<>();
    TextView orderID,orderTotalPrice,orderGate,orderDate,orderStatus;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        order = new OrdersModel();
        orderID=findViewById(R.id.order_id);
        orderTotalPrice=findViewById(R.id.total_price);
        orderGate=findViewById(R.id.order_gate);
        orderDate = findViewById(R.id.order_date);
        orderStatus = findViewById(R.id.order_status);
        fetchOrder();
        orderLive.observe(this, new Observer<OrdersModel>() {
            @Override
            public void onChanged(OrdersModel ordersModel) {
                Log.d("Track","It came");
                orderID.setText(ordersModel.getKey());
                orderTotalPrice.setText(ordersModel.getPrice());
                orderGate.setText(ordersModel.getGate());
                orderDate.setText(ordersModel.getDate());
                orderStatus.setText(ordersModel.getState());
            }
        });


    }
    private void fetchOrder(){
        String user = auth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user)
                .child("order").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){
                                OrdersModel ordersModel = new OrdersModel();
                                ordersModel.setAddress((String) snapshot.child("address").getValue());
                                ordersModel.setDate((String) snapshot.child("date").getValue());
                                ordersModel.setGate((String) snapshot.child("gate").getValue());
                                ordersModel.setMeals((ArrayList<Meals>) snapshot.child("meals").getValue());
                                ordersModel.setPrice((String) snapshot.child("price").getValue());
                                ordersModel.setState((String) snapshot.child("state").getValue());
                                ordersModel.setKey((String) snapshot.child("key").getValue());
                                orderLive.setValue(ordersModel);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}