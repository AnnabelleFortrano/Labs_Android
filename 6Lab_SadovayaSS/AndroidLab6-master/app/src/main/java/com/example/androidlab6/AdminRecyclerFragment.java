package com.example.androidlab6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminRecyclerFragment extends Fragment {
    Click click;
    DataChangedListener listener;
    public AdminRecyclerFragment(Click click) {
        this.click = click;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.recycler_fragment_admin, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final AdminRecyclerAdapter adminRecyclerAdapter = new AdminRecyclerAdapter(click);
        listener = new DataChangedListener() {
            @Override
            public void notifyDataChanged() {
                adminRecyclerAdapter.notifyDataSetChanged();
            }
        };
        ItemService.getInstance().addDataChangedListener(listener);
        recyclerView.setAdapter(adminRecyclerAdapter);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ItemService.getInstance().removeListener(listener);
    }
}
