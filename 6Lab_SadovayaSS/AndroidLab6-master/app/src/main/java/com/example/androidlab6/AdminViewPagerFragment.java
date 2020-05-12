package com.example.androidlab6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class AdminViewPagerFragment extends Fragment {
    private int position;
    DataChangedListener listener;
    public AdminViewPagerFragment(int position) {
        super();
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewpager_fragment_admin, container, false);
        ViewPager2 viewPager2 = v.findViewById(R.id.view_pager_admin);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        Click click = new Click() {
            @Override
            public void click(int position) {
                getActivity().onBackPressed();
            }
        };
        final AdminViewPagerAdapter adminViewPagerAdapter = new AdminViewPagerAdapter(click);
        listener = new DataChangedListener() {
            @Override
            public void notifyDataChanged() {
                adminViewPagerAdapter.notifyDataSetChanged();
            }
        };
        ItemService.getInstance().addDataChangedListener(listener);
        viewPager2.setAdapter(adminViewPagerAdapter);
        viewPager2.setCurrentItem(position);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ItemService.getInstance().removeListener(listener);
    }
}
