package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class ActivityMenu extends AppCompatActivity {

    private DateFormat format = new SimpleDateFormat("HH:mm:ss "); //"yyyy.MM.dd 'at'
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM students");
        insertStartInfo();

        dbHelper.close();

        Button btn_openDB = findViewById(R.id.btn_openDB);
        Button btn_addItemDB = findViewById(R.id.btn_addItemDB);
        Button btn_replace = findViewById(R.id.btn_replace);


        btn_openDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenu.this, ActivityShowDatabase.class);
                startActivity(intent);
            }
        });

        btn_addItemDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();

                Random random = new Random();
                int number;
                number = random.nextInt(name.size());
                Calendar thisDate = Calendar.getInstance();
                String data = format.format(thisDate.getTime());
                database.execSQL("INSERT INTO students(name, time) VALUES (\'" + name.get(number)+ "\','" + data + "');");
                name.remove(number);

                dbHelper.close();
            }
        });

        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();

                database.execSQL("UPDATE students SET name = 'Иванов Иван Иванович' WHERE id = (SELECT max(id) FROM students)");

                dbHelper.close();
            }
        });
    }

    private void insertStartInfo() {

        name = new ArrayList<>();
        name.add("Александров Александр Иванович");
        name.add("Ананьева Мария Александровна");
        name.add("Благирев Михаил Михайлович");
        name.add("Галактионов Данил Александрович");
        name.add("Деревлев Егор Сергеевич");
        name.add("Дорохин Михаил Александрович");
        name.add("Зубреев Антон Игоревич");
        name.add("Иванов Виталий Андреевич");
        name.add("Казакевич Игорь Дмитриевич");
        name.add("Кащеев Максим Игоревич");
        name.add("Кубракова Ирина Дмитриевна");
        name.add("Лалаян Альберт Григорьевич");
        name.add("Муравкин Даниил Александрович");
        name.add("Овчинников Игорь Андреевич");
        name.add("Пылаев Кирилл Дмитриевич");
        name.add("Радашевский Дмитрий Дмитриевич");
        name.add("Садовая Софья Сергеевна");
        name.add("Симон Никита Андреевич");
        name.add("Соколов Максим Игоревич");
        name.add("Сорокин Глеб Олегович");
        name.add("Топоркова Ольга Евгеньевна");
        name.add("Федодеев Артем Евгеньевич");
        name.add("Фуфурин Артемий Михайлович");
        name.add("Хаустов Иван Кириллович");
        name.add("Хмыз Лев Владимирович");
        name.add("Чемерев Егор Михайлович");
        name.add("Чухаев Михаил Михайлович");
        name.add("Шаульский Вадим Александрович");
        name.add("Шошников Иван Кириллович");
        name.add("Юркевич Глеб Артемович");

        Random random = new Random();
        int number;

        for (int i = 0; i < 5; i++) {

            number = random.nextInt(name.size());

            Calendar thisDate = Calendar.getInstance();
            String data = format.format(thisDate.getTime());

            database.execSQL("INSERT INTO students(name, time) VALUES (\'" + name.get(number)+ "\',\'" + data + "\');");
            name.remove(number);
        }

    }
}