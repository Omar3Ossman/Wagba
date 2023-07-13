package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wagba.adapter.PrevOrderAdapter;
import com.example.wagba.model.OrdersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    RecyclerView previous_orders_rcv;
    PrevOrderAdapter prevOrdersAdapter;
    ArrayList<OrdersModel> prevOrders = new ArrayList<>();

    MutableLiveData<ArrayList<OrdersModel>> previous_orders =new MutableLiveData<>();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        previous_orders_rcv = findViewById(R.id.order_history);
        loadPrevOrdersFromFirebase();
        previous_orders.observe(this, new Observer<ArrayList<OrdersModel>>() {
            @Override
            public void onChanged(ArrayList<OrdersModel> ordersModels) {
                prevOrders=ordersModels;
                prevOrdersAdapter.notifyDataSetChanged();
            }
        });
        prevOrdersAdapter = new PrevOrderAdapter(
                this,
                prevOrders
        );


        /**
         * previousOrders' Recycler View setting the adapter.
         **/
        previous_orders_rcv.setAdapter(prevOrdersAdapter);
        previous_orders_rcv.setLayoutManager(new LinearLayoutManager(this));
    }
    private void loadPrevOrdersFromFirebase() {
        String user = auth.getCurrentUser().getUid();

        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user)
                .child("previousOrders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){
                            for (DataSnapshot orderSnapshot:snapshot.getChildren())
                            {
                                OrdersModel ordersModel = orderSnapshot.getValue(OrdersModel.class);
                                ordersModel.setKey(orderSnapshot.getKey());
                                prevOrders.add(ordersModel);
                            }
                            previous_orders.setValue(prevOrders);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}