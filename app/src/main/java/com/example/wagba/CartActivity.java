package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wagba.Listener.CartLoadListener;
import com.example.wagba.adapter.MyCartAdapter;
import com.example.wagba.model.CartModel;
import com.example.wagba.model.OrdersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity{

    /*@BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;
    @BindView(R.id.mainLayout2)
    RelativeLayout mainLayout2;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
*/

    CartLoadListener cartLoadListener;
    ArrayList<Meals> cart;
    Button placeOrder;
    TextView totalfees;

private float calculateTotalCart(){
    float total = 0;
    for(int i =0; i<cart.size();i++){
        try{
            total+=Float.parseFloat(cart.get(i).getMealPrice());
        }catch(Exception e){
            String price = cart.get(i).getMealPrice();
            price=price.substring(0,price.indexOf(" LE"));
            total+=Float.parseFloat(price);
        }
    }
    return total;
}
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        totalfees = findViewById(R.id.totalfees);
        cart = new ArrayList<Meals>();

        placeOrder = findViewById(R.id.confirmation);
        placeOrder.setOnClickListener(view -> {
            Date date = new Date();
            float cartTotal = calculateTotalCart();
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("order").setValue(
                            new OrdersModel(
                                    "Order#",
                                    Float.toString(cartTotal),
                                    "Gate 4",
                                    "Abdo Pasha",
                                    "Confirmed",
                                    cart,
                                   date.toString()
                            )
                    );
            //Clear the cart
        });
        init();
    }

    private void init() {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        /*recyclerCart.setLayoutManager(layoutManager);
        recyclerCart.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        btnBack.setOnClickListener(v -> finish());*/


        FirebaseDatabase.getInstance().getReference().child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Cartsss", cart.toString());
                for (DataSnapshot restaurants : snapshot.getChildren()) {
                    final String mealName = restaurants.child("mealName").getValue(String.class);
                    final String mealPrice = restaurants.child("mealPrice").getValue(String.class);
                    final String mealImage = restaurants.child("mealImage").getValue(String.class);
                    final Integer rating = restaurants.child("rating").getValue(Integer.class);
                    Meals resmodels = new Meals(mealName, mealPrice, mealImage, rating);
                    cart.add(resmodels);
                }
                float total = calculateTotalCart();
                totalfees.setText(Float.toString(total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


}