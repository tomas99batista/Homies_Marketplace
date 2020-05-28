package com.example.homiesmarketplaceapp.network;

import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.model.PlaceId;
import com.example.homiesmarketplaceapp.model.Review;
import com.example.homiesmarketplaceapp.model.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetDataService {

    @GET("/places/")
    Call<List<Place>> getAllPlaces();

    @GET("/places/{id}/")
    Call<Place> getPlaceDetails(@Path("id") long placeId);

    @GET
    Call<List<Place>> search(@Url String url);

    @GET("/places/{id}/reviews/")
    Call<List<Review>> getPlaceReviews(@Path("id") long placeId);


    @POST("/users/{email}/favorites/")
    Call<String> addPlaceToFavorites(@Path("email") String email, @Body PlaceId body);

    @GET("/users/{email}/favorites/")
    Call<List<Place>> getFavoritePlaces(@Path("email") String email);

    @POST("/places/{id}/reviews/")
    Call<String> addReview(@Path("id") long placeId, @Body Review review);

    @POST("/users/")
    Call<User> registerUser(@Body User body);

    @GET("/users/{email}/publishedHouses/")
    Call<List<Place>> getPublishedHouses(@Path("email") String email);

    @POST("/users/{email}/publishedHouses/")
    Call<String> addPublishedHouse(@Path("email") String email, @Body Place body);
}