package com.example.homiesmarketplaceapp.network;

import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.model.PlaceId;
import com.example.homiesmarketplaceapp.model.Review;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/places/")
    Call<List<Place>> getAllPlaces();

    @GET("/places/{id}/")
    Call<Place> getPlaceDetails(@Path("id") long placeId);

    @GET("/search/")
    Call<List<Place>> search(@Query("city") String city);

    @GET("/places/{id}/reviews/")
    Call<List<Review>> getPlaceReviews(@Path("id") long placeId);


    @POST("/users/{email}/favorites/")
    Call<String> addPlaceToFavorites(@Path("email") String email, @Body PlaceId body);

    @GET("/users/{email}/favorites/")
    Call<List<Place>> getFavoritePlaces(@Path("email") String email);

}
