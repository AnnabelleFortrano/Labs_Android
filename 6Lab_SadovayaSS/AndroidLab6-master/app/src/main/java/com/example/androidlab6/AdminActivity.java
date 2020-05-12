package com.example.androidlab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends FragmentActivity {
    AdminRecyclerFragment adminRecyclerFragment;
    AdminViewPagerFragment adminViewPagerFragment;
    static String TAG_1 = "ADMIN_RECYCLER_FRAGMENT";
    static String TAG_2 = "ADMIN_VIEW_PAGER_FRAGMENT";
    Click click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        click = new Click() {
            @Override
            public void click(int position) {
                adminViewPagerFragment = new AdminViewPagerFragment(position);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_admin, adminViewPagerFragment, TAG_2);
                transaction.commit();
            }
        };
        adminRecyclerFragment = new AdminRecyclerFragment(click);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_admin, adminRecyclerFragment, TAG_1);
        transaction.commit();
        Button button = findViewById(R.id.add_btn_admin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag(TAG_2) != null) {
            adminRecyclerFragment = new AdminRecyclerFragment(click);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_admin, adminRecyclerFragment)
                    .commit();
        }
        else {
            finish();
        }
    }
}
