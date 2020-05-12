package com.example.lr2_5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class FragmentForPager extends Fragment {
    private String text;
    private String imgUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_pager, container, false);
        TextView textView = view.findViewById(R.id.textView2);
        ImageView imageView = view.findViewById(R.id.imageView2);

        if(this.getArguments()!=null){
            text = this.getArguments().getString("text");
            imgUrl = this.getArguments().getString("imgUrl");
        }

        textView.setText(text);
        String url ="https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/"+imgUrl;
        Picasso.get().load(url).into(imageView);
        return view;
    }
}
