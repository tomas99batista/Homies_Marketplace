package com.example.homiesmarketplaceapp.network;

import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.model.PlaceId;
import com.example.homiesmarketplaceapp.model.Review;
import com.example.homiesmarketplaceapp.model.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
<<<<<<< HEAD
import retrofit2.http.GET;
=======
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetDataService {

<<<<<<< HEAD
    @GET("/places/")
    Call<List<Place>> getAllPlaces();

    @GET("/places/{id}/")
=======
    @GET("api/places/")
    Call<List<Place>> getAllPlaces();

    @GET("api/places/{id}/")
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
    Call<Place> getPlaceDetails(@Path("id") long placeId);

    @GET
    Call<List<Place>> search(@Url String url);

<<<<<<< HEAD
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
=======
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

    @POST("/api/users/{email}/booking/")
    Call<String> addHouseBooking(@Path("email") String email, @Body PlaceId body);


    @HTTP(method = "DELETE", path = "api/users/{email}/favorites/", hasBody = true)
    Call<String> removeFavoriteHouse(@Path("email") String email, @Body PlaceId body);

>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
}
