package com.example.androidlab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class LikedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);
        RecyclerView view = findViewById(R.id.liked_recycler);
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LikedRecyclerAdapter adapter = new LikedRecyclerAdapter(getApplicationContext());
        view.setAdapter(adapter);
    }
}
