package com.example.lab3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "lab3.db";
    private static final int DB_VERSION = 2;
    private static String TABLE_NAME = "students";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, middle_name TEXT, last_name TEXT, time TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE students RENAME TO old_students;");

        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, middle_name TEXT, last_name TEXT, time TEXT);");

        Cursor cursor = db.rawQuery("SELECT * FROM old_students", null);
        cursor.moveToFirst();

        String FIO;
        while (!cursor.isAfterLast()) {
            FIO = cursor.getString(1);
            String[] arrFIO = FIO.split(" ");

            db.execSQL("INSERT INTO students(id, first_name, middle_name, last_name, time) " +
                    "VALUES (\'" + cursor.getString(0) + "\',\'" + arrFIO[1] + "\', \'" + arrFIO[2] + "\', \'" + arrFIO[0] + "\', \'" + cursor.getString(2) + "\');");

            cursor.moveToNext();
        }

        cursor.close();

        db.execSQL("DROP TABLE old_students");

//        db.execSQL("INSERT INTO students(name, time) VALUES ('new', '00:00:00');");
//        db.execSQL("INSERT INTO students(name, time) VALUES ('new', '00:00:00');");
//        db.execSQL("INSERT INTO students(name, time) VALUES ('new', '00:00:00');");
    }
}
