package com.example.homiesmarketplaceapp.ui.details;

import android.content.DialogInterface;
<<<<<<< HEAD
=======
import android.content.SharedPreferences;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
<<<<<<< HEAD
=======
import androidx.preference.PreferenceManager;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.adapter.ReviewAdapter;
import com.example.homiesmarketplaceapp.model.Place;
<<<<<<< HEAD
=======
import com.example.homiesmarketplaceapp.model.PlaceId;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import com.example.homiesmarketplaceapp.model.Review;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    View root;
    ImageView placeImage;
    TextView placeTitle;
    TextView placeCity;
    TextView placePrice;
<<<<<<< HEAD
    TextView placeRating;
=======
    RatingBar placeRating;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
    TextView placeNoBedrooms;
    TextView placeNoBathrooms;
    TextView placeType;
    TextView placeFeatures;
    RecyclerView reviews;

    ReviewAdapter adapter;
<<<<<<< HEAD
=======
    String email;
    List<Review> reviewList;
    Button addBooking;

>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_details, container, false);
        placeImage=root.findViewById(R.id.details_place_image);
        placeTitle=root.findViewById(R.id.details_place_title);
        placeCity=root.findViewById(R.id.details_place_city);
        placePrice=root.findViewById(R.id.details_place_price);
        placeFeatures=root.findViewById(R.id.details_place_features);
        placeRating=root.findViewById(R.id.details_place_rating);
        placeNoBedrooms=root.findViewById(R.id.details_place_no_bedrooms);
        placeNoBathrooms=root.findViewById(R.id.details_place_no_bathrooms);
        placeType=root.findViewById(R.id.details_place_type);
        reviews=root.findViewById(R.id.recycler_view_reviews);
<<<<<<< HEAD
=======
        addBooking=root.findViewById(R.id.addBooking);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        email=prefs.getString("email", "");
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

        Bundle bundle=this.getArguments();
        if (bundle!=null) {
            if (bundle.getLong("placeId") != -1) {
                final long placeId=bundle.getLong("placeId");
                Log.d("placeIdINDetails", " "+placeId);

                getPlaceDetails(placeId);
                getPlaceReviews(placeId);

                Button button = root.findViewById(R.id.addReviewBtn);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        showReviewDialog(placeId);
                    }
                });
<<<<<<< HEAD
=======

                addBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                        Call<String> callAddBooking=service.addHouseBooking(email,new PlaceId(placeId));
                        callAddBooking.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.body().equals("true")){
                                    Log.d("book added", "book added");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                });
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
            }
        }
        return root;
    }

    private void getPlaceReviews(long placeId){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Review>> callPlaceReviews=service.getPlaceReviews(placeId);
        callPlaceReviews.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                showReviews(response.body());
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });

    }

    private void getPlaceDetails(long placeId){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Place> callPlaceDetails=service.getPlaceDetails(placeId);
        callPlaceDetails.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                Log.d("place response", response.body().toString());
                showData(response.body());
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {

            }
        });
    }

    private void showData(Place place){
        Glide.with(getContext()).load(place.getPhotos()).into(placeImage);
        placeTitle.setText(place.getTitle());
        placeCity.setText(place.getCity());
<<<<<<< HEAD
        placePrice.setText(String.valueOf(place.getPrice()));
        placeFeatures.setText(place.getFeatures().toString());
        placeRating.setText(String.valueOf(place.getRating()));
        placeNoBedrooms.setText(String.valueOf(place.getNumberBedrooms()));
        placeNoBathrooms.setText(String.valueOf(place.getNumberBathrooms()));
        placeType.setText(place.getType());
    }

    private void showReviews(List<Review> reviewList){
=======
        placePrice.setText(((int)place.getPrice())+ " €/month");
        placeFeatures.setText(place.getAllFeatures());
        placeRating.setRating((float)place.getRating());
        placeNoBedrooms.setText(place.getNumberBedrooms() + " bedrooms");
        placeNoBathrooms.setText(place.getNumberBathrooms()+ " bathrooms");
        placeType.setText(place.getType());
    }

    private void showReviews(List<Review> reviewListResponse){
        reviewList=reviewListResponse;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
        adapter = new ReviewAdapter(getContext(), reviewList);
        adapter.notifyDataSetChanged();
        reviews.setLayoutManager(new LinearLayoutManager(getContext()));
        reviews.setAdapter(adapter);
    }

    private void showReviewDialog(final long placeId){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter your review");
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.new_review, null);
        builder.setView(dialogLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comment = ((EditText)dialogLayout.findViewById(R.id.NewReviewText)).getText().toString();
                float rating =((RatingBar)dialogLayout.findViewById(R.id.NewReviewRating)).getRating();
                postReview(placeId, comment, rating);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

<<<<<<< HEAD
    private void postReview(long placeId, String comment, float rating){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> callAddReview=service.addReview(placeId, new Review("jose@email.com",comment, rating));
=======
    private void postReview(final long placeId, final String comment, final float rating){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> callAddReview=service.addReview(placeId, new Review(email,comment, rating));
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
        callAddReview.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("true")){
                    Log.d("revieew added", "review added");
<<<<<<< HEAD
=======
                    reviewList.add(0,new Review(email,comment, rating));
                    adapter.notifyItemInserted(0);
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
