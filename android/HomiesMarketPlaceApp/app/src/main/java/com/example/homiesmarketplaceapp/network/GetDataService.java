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

    @GET("api/places/")
    Call<List<Place>> getAllPlaces();

    @GET("api/places/{id}/")
    Call<Place> getPlaceDetails(@Path("id") long placeId);

    @GET
    Call<List<Place>> search(@Url String url);

    @GET("api/places/{id}/reviews/")
    Call<List<Review>> getPlaceReviews(@Path("id") long placeId);


    @POST("api/users/{email}/favorites/")
    Call<String> addPlaceToFavorites(@Path("email") String email, @Body PlaceId body);

    @GET("api/users/{email}/favorites/")
    Call<List<Place>> getFavoritePlaces(@Path("email") String email);

    @POST("api/places/{id}/reviews/")
    Call<String> addReview(@Path("id") long placeId, @Body Review review);

    @POST("api/users/")
    Call<User> registerUser(@Body User body);

    @GET("api/users/{email}/publishedHouses/")
    Call<List<Place>> getPublishedHouses(@Path("email") String email);

    @POST("api/users/{email}/publishedHouses/")
    Call<String> addPublishedHouse(@Path("email") String email, @Body Place body);

    @POST("api/login/")
    Call<User> login(@Body User user);
}
