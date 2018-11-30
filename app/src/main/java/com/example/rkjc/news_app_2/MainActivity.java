package com.example.rkjc.news_app_2;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private RecyclerView newsRecyclerView;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private IntentFilter mFilter;
    private NewsItemViewModel newsItemViewModel;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        newsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(this, newsItemViewModel);
        newsRecyclerView.setAdapter(newsRecyclerViewAdapter);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        NewsJobDispatcher.syncDatabaseWithDataFromAPI(this);

        newsItemViewModel.returnLiveData().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                newsRecyclerViewAdapter.setNewsList(newsItems);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_search) {
            newsItemViewModel.getNewInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
