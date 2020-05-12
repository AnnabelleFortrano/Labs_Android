package com.example.lr2_5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PagerFragment extends Fragment {
    private String[] help_texts;
    private String[] imgsUrls;
    private int index;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_fragment, container, false);
        ViewPager viewPager = view.findViewById(R.id.pager);

        if(this.getArguments()!=null){
            imgsUrls = this.getArguments().getStringArray("imgesUrl");
            help_texts = this.getArguments().getStringArray("help_texts");
            index = this.getArguments().getInt("index");
        }

        PagerAdapter adapter = new PagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);

        return view;
    }



    class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter (@NonNull FragmentManager fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("imgUrl", imgsUrls[position]);
            bundle.putString("text", help_texts[position]);

            FragmentForPager frag = new FragmentForPager();
            frag.setArguments(bundle);
            return frag;
        }

        @Override
        public int getCount() {
            return imgsUrls.length;
        }
    }
}
