package com.example.lr2_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

public class ListAdapter extends ArrayAdapter<Row> {
    private Row[] rows;
    private LayoutInflater inflater;
    private int layout;

    public ListAdapter(Context context, int resource, Row[]rows){
        super(context, resource, rows);
        this.rows=rows;
        layout=resource;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layout, parent, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);

        textView.setText(rows[position].getName());

        String url ="https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/"+rows[position].getImgUrl();
        Picasso.get().load(url).into(imageView);

        return view;
    }
}
