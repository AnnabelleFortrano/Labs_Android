package com.example.lr2_5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ListFragment extends Fragment {
    private String[] names;
    private String[] imagesUrls;
    private String[] help_texts;
    private ListView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        list = view.findViewById(R.id.list_item);

        if(this.getArguments()!=null){
            names = this.getArguments().getStringArray("names");
            imagesUrls = this.getArguments().getStringArray("imgs");
            help_texts = this.getArguments().getStringArray("texts");
        }

        Row[] rows = new Row[names.length];
        for(int i=0; i<names.length; i++){
            rows[i] = new Row(names[i], imagesUrls[i]);
        }

        ListAdapter adapter = new ListAdapter(view.getContext(), R.layout.row_list, rows);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putStringArray("help_texts", help_texts);
                bundle.putStringArray("imgesUrl", imagesUrls);
                bundle.putInt("index", position);

                PagerFragment pagerFragment = new PagerFragment();
                pagerFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, pagerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
