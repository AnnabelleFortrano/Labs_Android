package com.example.lab3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterListStudents extends RecyclerView.Adapter<AdapterListStudents.MyViewHolder>{

    public DatabaseHelper dbHelper;
    public SQLiteDatabase database;
    public Cursor cursor;


    private int size;

    public AdapterListStudents(Context context) {

        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

        cursor = database.rawQuery("SELECT COUNT(*) FROM students", null);
        cursor.moveToFirst();
        this.size =  cursor.getInt(0);
        cursor = database.rawQuery("SELECT * FROM students", null);
        cursor.moveToFirst();
        Log.d("SIZE_STUDENTS", String.valueOf(size));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int idForItem = R.layout.item_list_students;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(idForItem, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id;
        TextView tv_name;
        TextView tv_time;
        String FIO;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);

        }

        void bind() {
            if (!cursor.isAfterLast()) {
                tv_id.setText(cursor.getString(0));

                FIO = cursor.getString(1);
                String[] arrFIO = FIO.split(" ");
                FIO = "";
                for (int i = 0; i < arrFIO.length; i++) {
                    if(i == arrFIO.length - 1) {
                        FIO += arrFIO[i];
                    } else {
                        FIO += arrFIO[i] + "\n";
                    }
                }

                tv_name.setText(FIO);
                tv_time.setText(cursor.getString(2));
                cursor.moveToNext();
            } else {
                cursor.close();
                dbHelper.close();
            }
        }
    }
}