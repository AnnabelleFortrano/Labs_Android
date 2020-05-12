package com.mirea.lab8;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteResponse {

    @SerializedName("routes")
    public List<Route> routes;

    public String getPoints() {
        return this.routes.get(0).overview_polyline.points;
    }

    @SerializedName("status")
    public String status;

    class Route {
        OverviewPolyline overview_polyline;
    }

    class OverviewPolyline {
        String points;
    }
}
