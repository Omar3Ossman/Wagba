package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.wagba.adapter.BestForYouAdapter;
import com.example.wagba.model.BestForYou;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantPage extends AppCompatActivity implements RecyclerViewInterface{

    String resID,resName,resImage;
    ArrayList<Meals> meals = new ArrayList<>();
    ArrayList<BestForYou> resList = new ArrayList<>();
    MealsAdapter mealsAdapter;

    int[] mealImages = {R.drawable.big_tasty, R.drawable.mc_royale, R.drawable.fish_fillet, R.drawable.happy_meal,
            R.drawable.hawawshi,R.drawable.molokhia, R.drawable.mix_grill,R.drawable.tarb, R.drawable.super_crunchy,
            R.drawable.shish_tawook,R.drawable.onion_rings,R.drawable.friskes_fries,R.drawable.dinner_box,
            R.drawable.mighty_zinger_combo,R.drawable.rizo,R.drawable.bucket,R.drawable.fries,R.drawable.water
            ,R.drawable.chicken_ranch,R.drawable.chicken_bbq,R.drawable.nutella_waffle,R.drawable.lotus_waffle
            ,R.drawable.pepsi,R.drawable.chicken_shawerma,R.drawable.shawerma_egyptian,R.drawable.chocobon
            ,R.drawable.caramel_pecan,R.drawable.classic,R.drawable.mushroom_burger,R.drawable.bbq_burger};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        RecyclerView recyclerView = findViewById(R.id.meals_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //RV.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealsAdapter = new MealsAdapter(getApplicationContext(),meals,this);

        resID = getIntent().getStringExtra("resID");
        resName = getIntent().getStringExtra("resName");
        resImage = getIntent().getStringExtra("resImage");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants").child(resID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dishes : snapshot.child("dishes").getChildren()) {

                    final String getName = dishes.child("Name").getValue(String.class);
                    final String getImageURL = (String) dishes.child("Image").getValue();
                    final String getFee = (String) dishes.child("Fee").getValue();

                    meals.add(new Meals(getName,getFee,getImageURL));
                    //BestForYou resmodel = new BestForYou(getName, getImageURL);
                    //resList.add(resmodel);
                }
                recyclerView.setAdapter(mealsAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        //recyclerView.setAdapter(new BestForYouAdapter(this, RestaurantPage.this, resList));

    }
      /*  setUpMeals();

        MealsAdapter adapter= new MealsAdapter(this,meals, this);
        recyclerView.setAdapter(adapter); */



    /* private void setUpMeals() {
       String[] restaurantName = getResources().getStringArray(R.array.restaurants);
       String[] mealName = getResources().getStringArray(R.array.meals);

       ArrayList<ArrayList<String>> meals2 = new ArrayList<ArrayList<String>>();
       ArrayList<ArrayList<Integer>> images = new ArrayList<ArrayList<Integer>>();

       ArrayList<String> mcdonalds_meals =new ArrayList<String>();
       ArrayList<Integer> mcdonalds_images = new ArrayList<Integer>();
       mcdonalds_meals.add("Big Tasty");
       mcdonalds_meals.add("McRoyale");
       mcdonalds_meals.add("Fish Fillet");
       mcdonalds_meals.add("Happy Meal");

       mcdonalds_images.add(R.drawable.big_tasty);
       mcdonalds_images.add(R.drawable.mc_royale);
       mcdonalds_images.add(R.drawable.fish_fillet);
       mcdonalds_images.add(R.drawable.happy_meal);

       images.add(mcdonalds_images);
       meals2.add(mcdonalds_meals);


       ArrayList<String> eldahan_meals =new ArrayList<String>();
       eldahan_meals.add("Hawawshi");
       eldahan_meals.add("Molokhia");
       eldahan_meals.add("Mix Grill");
       eldahan_meals.add("Tarb");

       ArrayList<Integer> eldahan_images = new ArrayList<Integer>();

       eldahan_images.add(R.drawable.hawawshi);
       eldahan_images.add(R.drawable.molokhia);
       eldahan_images.add(R.drawable.mix_grill);
       eldahan_images.add(R.drawable.tarb);

       images.add(eldahan_images);
       meals2.add(eldahan_meals);



       for (int i = 0; i <  restaurantName.length; i++) {
           for (int j = 0; j < meals2.get(i).size(); j++) {
               meals.add(new Meals(restaurantName[i], meals2.get(i).get(j), images.get(i).get(j), 4));
           }
       }
   }
*/
    @Override
    public void onItemClick(int position) {
        //Intent intent = new Intent(RestaurantPage.this,.class);
        //startActivity(intent);
        //intent.putExtra("Name",meals.get(position).getMealName());
    }
}