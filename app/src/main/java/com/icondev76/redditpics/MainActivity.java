package com.icondev76.redditpics;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.icondev76.redditpics.model.Feed;
import com.icondev76.redditpics.model.children.Children;
import com.icondev76.redditpics.network.RedditAPI;
import com.icondev76.redditpics.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  {

    private DrawerLayout drawerLayout;
    private static final String BASE_URL = "https://www.reddit.com/r/";
    private String currentFeed = "AsianguysNSFW";
    private static final String TAG = "MainActivity";
    List<Children>childrenList;

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        RedditAPI service = RetrofitClientInstance.getRetrofitInstance().create(RedditAPI.class);

        Call<Feed> call = service.getFeed(currentFeed, 100);
        call.enqueue(new Callback<Feed>() {

            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                progressDoalog.dismiss();
                List<Children> childrenList = response.body().getData().getChildren();
                //Log.d(TAG, "onResponse: entrys: " + response.body().getData().getChildren().toString());

                generateDataList(childrenList);

                for (int i = 0; i < childrenList.size(); i++) {
                    Log.d(TAG, "onResponse: URL " + childrenList.get(i).getData().getUrl() + "\n");


                }



            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                progressDoalog.dismiss();
                Log.d(TAG, "onResponse: response: " + t.toString());

                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Children> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }




}
