package com.example.lr1_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Vector;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Vector<String> name;

    public MyAdapter(Vector<String> name){
        this.name = name;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView kub;
        TextView txt;
        LinearLayout l;

        public MyViewHolder(View view){
            super(view);
            l = (LinearLayout) view.findViewById(R.id.layout);
            kub = (ImageView) view.findViewById(R.id.imageView2);
            txt = (TextView) view.findViewById(R.id.textnumber);
        }

        public void setColor(int index){
            if(index%2==0) l.setBackgroundResource(R.color.colorBackgroundGrey);
            else l.setBackgroundResource(R.color.colorBackgroundWhite);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.setColor(position);
        holder.txt.setText(name.get(position));
    }

    @Override
    public int getItemCount(){
        return name.size();
    }
}
