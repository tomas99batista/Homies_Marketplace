package com.example.homiesmarketplaceapp.model;

import com.google.gson.annotations.SerializedName;

public class PlaceId {

    @SerializedName("id")
    private long id;

    public PlaceId(){

    }
    public PlaceId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
