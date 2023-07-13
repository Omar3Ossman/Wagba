package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wagba.adapter.BestForYouAdapter;
import com.example.wagba.model.BestForYou;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView bestForYouRecycler;
    BestForYouAdapter bestForYouAdapter;
    List<BestForYou> bestForYouList = new ArrayList<BestForYou>();
    ImageView profile,cart,track;
    Button btnLogout;
    FirebaseAuth auth;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btnLogout = findViewById(R.id.logouthere);
        cart = findViewById(R.id.ShoppingCart);
        profile = findViewById(R.id.imageView7);
        track=findViewById(R.id.track_button);

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,TrackOrder.class);
                startActivity(intent);
            }
        });
        auth = FirebaseAuth.getInstance();
        // Log.d("user",auth.getCurrentUser().getUid());
        bestForYouRecycler = findViewById(R.id.best_for_you_recycle);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager( this, RecyclerView.HORIZONTAL, false);
        bestForYouRecycler.setLayoutManager(layoutManager);
        bestForYouAdapter = new BestForYouAdapter(this, getApplicationContext(), bestForYouList);


        btnLogout.setOnClickListener(view -> {
            auth.signOut();
            startActivity(new Intent(HomePage.this, Login.class));
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String resID = snap.getKey();
                    String resImage = (String) snap.child("Image").getValue();
                    String resName = (String) snap.child("Name").getValue();
                    bestForYouList.add(new BestForYou(resID,resName,resImage));
                }
                bestForYouRecycler.setAdapter(bestForYouAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent(HomePage.this, Profile.class);
                    startActivity(intent);
                }
            });

            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomePage.this,CartActivity.class);
                    startActivity(intent);
                }
            });


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(HomePage.this, Login.class));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(HomePage.this,RestaurantPage.class);
        intent.putExtra("resName", bestForYouList.get(position).getName());
        intent.putExtra("resImage", bestForYouList.get(position).getImageURL());
        intent.putExtra("resID", bestForYouList.get(position).getResID());
        startActivity(intent);
    }
}