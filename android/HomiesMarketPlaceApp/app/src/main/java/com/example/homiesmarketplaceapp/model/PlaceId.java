package com.example.homiesmarketplaceapp.model;

import com.google.gson.annotations.SerializedName;

public class PlaceId {

    @SerializedName("placeId")
    private long placeId;

    public PlaceId(){

    }
    public PlaceId(long placeId) {
        this.placeId = placeId;
    }

    public long getPlaceId() {
        return placeId;
    }
}
