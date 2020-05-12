package com.mirea.lab8;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("formatted_address")
    public String address;

    @SerializedName("geometry")
    public Geometry geometry;
}
