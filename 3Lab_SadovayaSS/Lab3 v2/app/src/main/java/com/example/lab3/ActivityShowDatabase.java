package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ActivityShowDatabase extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);

        RecyclerView rv_listStudents = findViewById(R.id.rv_listStudents);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_listStudents.setLayoutManager(layoutManager);
        rv_listStudents.setHasFixedSize(true); //Тк знаем размер списка

        AdapterListStudents adapter = new AdapterListStudents(this);
        rv_listStudents.setAdapter(adapter);
    }
}