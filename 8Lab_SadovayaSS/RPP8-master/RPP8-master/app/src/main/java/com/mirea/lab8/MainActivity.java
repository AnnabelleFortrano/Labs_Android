package com.mirea.lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements PageFragment.OnFragmentDataListener {
    private ViewPager pager;

    private Double lat1 = 0.0;
    private Double lng1 = 0.0;
    private Double lat2 = 0.0;
    private Double lng2 = 0.0;
    private Double myPositionLat;
    private Double myPositionLng;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocationPermission();

        pager = findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onFragmentDataListener(Double lat, Double lng) {
        if (pager.getCurrentItem() == Constants.PAGE_FROM) {
            lat1 = lat;
            lng1 = lng;
        }
        if (pager.getCurrentItem() == Constants.PAGE_TO) {
            lat2 = lat;
            lng2 = lng;
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            flag = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        flag = false;
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                flag = true;
                setGpsLocation();
            }
        }
    }

    private void setGpsLocation() {
        try {
            if (flag) {
                Task<Location> locationResult = LocationServices
                        .getFusedLocationProviderClient(this)
                        .getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            myPositionLat = task.getResult().getLatitude();
                            myPositionLng = task.getResult().getLongitude();
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: ", e.getMessage());
        }
    }

    public void onShowPathClick(View view) {
        if (lat1 != 0.0 && lng1 != 0.0 && lat2 != 0.0 && lng2 != 0.0) {
            Intent intent = new Intent(this, PathActivity.class);
            intent.putExtra(Constants.LAT1_KEY, lat1);
            intent.putExtra(Constants.LNG1_KEY, lng1);
            intent.putExtra(Constants.LAT2_KEY, lat2);
            intent.putExtra(Constants.LNG2_KEY, lng2);
            intent.putExtra(Constants.MY_POSITION_LAT_KEY, myPositionLat);
            intent.putExtra(Constants.MY_POSITION_LNG_KEY, myPositionLng);
            startActivity(intent);
        }
    }
}
