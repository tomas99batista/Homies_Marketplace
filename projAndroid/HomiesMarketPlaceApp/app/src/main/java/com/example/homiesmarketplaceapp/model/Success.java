package com.example.homiesmarketplaceapp.model;

import com.google.gson.annotations.SerializedName;

public class Success {

    @SerializedName("success")
    private boolean success;


    public Success(){

    }
    public Success(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
