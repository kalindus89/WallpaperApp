package com.wllpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.wllpaperapp.adapter.AdapterForSync;
import com.wllpaperapp.api.ApiClient;
import com.wllpaperapp.api.ApiInterface;
import com.wllpaperapp.models.ImageModel;
import com.wllpaperapp.models.SearchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<ImageModel> imageModelList;
    RecyclerView recyclerView;
    CardView mNature, mBus,mCar, mTrain, mTrending;
    EditText editText;
    ImageButton search;
    AdapterForSync adapterForSync;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      getSupportActionBar().hide();

        recyclerView=findViewById(R.id.recyclerView);

        mNature=findViewById(R.id.nature);
        mBus=findViewById(R.id.bus);
        mCar=findViewById(R.id.car);
        mTrain=findViewById(R.id.train);
        mTrending=findViewById(R.id.trending);

        editText=findViewById(R.id.editText);
        search=findViewById(R.id.search);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);

        imageModelList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        adapterForSync= new AdapterForSync(this,imageModelList);
        recyclerView.setAdapter(adapterForSync);
        
        findPhotos();

        mNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSearchImage("nature");
            }
        });

        mCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSearchImage("car");
            }
        });

        mTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSearchImage("train");
            }
        });

        mBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSearchImage("bus");
            }
        });

        mTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhotos();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String query=editText.getText().toString().trim().toLowerCase();
               if(query.isEmpty()){
                   Toast.makeText(getApplicationContext(), "Enter Something", Toast.LENGTH_SHORT).show();
               }else{
                   getSearchImage(query);
               }
            }
        });


    }

    private void findPhotos() {

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        recyclerView.setVisibility(View.GONE);

        ApiClient.getApiClient().create(ApiInterface.class).getImages(2,40).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                imageModelList.clear();
                if(response.isSuccessful()){

                    imageModelList.addAll(response.body().getPhotos());
                    adapterForSync.notifyDataSetChanged();

                    recyclerView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }else{
                    Toast.makeText(getApplicationContext(), "Not able to get data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }

    private void getSearchImage(String query) {

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();

        ApiClient.getApiClient().create(ApiInterface.class).getSearchImages(query,1,40).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                imageModelList.clear();
                if(response.isSuccessful()){
                    imageModelList.addAll(response.body().getPhotos());
                    adapterForSync.notifyDataSetChanged();

                    recyclerView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }else{
                    Toast.makeText(getApplicationContext(), "Not able to get search data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }
}