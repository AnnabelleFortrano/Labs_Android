package com.mirea.lab8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.android.PolyUtil;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PathActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static String TAG = PathActivity.class.getSimpleName();

    private Double lat1;
    private Double lng1;
    private Double lat2;
    private Double lng2;
    private List<LatLng> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle arguments = getIntent().getExtras();
        lat1 = arguments.getDouble(Constants.LAT1_KEY);
        lng1 = arguments.getDouble(Constants.LNG1_KEY);
        lat2 = arguments.getDouble(Constants.LAT2_KEY);
        lng2 = arguments.getDouble(Constants.LNG2_KEY);

        places.add(new LatLng(lat1, lng1));
        places.add(new LatLng(lat2, lng2));
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        setMarkers(googleMap);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DirectionsAPI directionsApi = retrofit.create(DirectionsAPI.class);
        Call<RouteResponse> routeResponseCall = directionsApi.getRoute(lat1 + "," + lng1,
                                                                    lat2 + "," + lng2, Constants.MAPS_KEY);

        routeResponseCall.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                if (response.body().status.equals(Constants.STATUS_OK)) {
                    List<LatLng> mPoints = PolyUtil.decode(response.body().getPoints());

                    PolylineOptions line = new PolylineOptions();
                    line.width(4f).color(R.color.colorAccent);

                    LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
                    for (int i = 0; i < mPoints.size(); i++) {

                        line.add(mPoints.get(i));
                        latLngBuilder.include(mPoints.get(i));
                    }
                    googleMap.addPolyline(line);

                    int size = getResources().getDisplayMetrics().widthPixels;
                    LatLngBounds latLngBounds = latLngBuilder.build();
                    CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
                    googleMap.moveCamera(track);
                } else {
                    TextView textView = findViewById(R.id.text);
                    textView.setText(R.string.result);
                }
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                Log.e(TAG, "Error! " + t);
            }
        });
    }

    private void setMarkers(GoogleMap googleMap) {
        MarkerOptions[] markers = new MarkerOptions[places.size()];
        for (int i = 0; i < places.size(); i++) {
            markers[i] = new MarkerOptions().position(places.get(i));
            googleMap.addMarker(markers[i]);
        }
    }
}
