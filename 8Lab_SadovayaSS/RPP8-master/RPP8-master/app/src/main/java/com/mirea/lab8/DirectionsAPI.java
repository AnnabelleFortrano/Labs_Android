package com.mirea.lab8;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionsAPI {
    @GET("directions/json?")
    Call<RouteResponse> getRoute(@Query("origin") String origin,
                                 @Query("destination") String destination,
                                 @Query("key") String key);
}
