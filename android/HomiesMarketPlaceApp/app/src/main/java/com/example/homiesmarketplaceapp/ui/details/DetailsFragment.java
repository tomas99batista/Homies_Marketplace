package com.example.homiesmarketplaceapp.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    View root;
    ImageView placeImage;
    TextView placeTitle;
    TextView placeCity;
    TextView placePrice;
    TextView placeRating;
    TextView placeNoBedrooms;
    TextView placeNoBathrooms;
    TextView placeType;
    TextView placeFeatures;

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

        Bundle bundle=this.getArguments();
        if (bundle!=null) {
            if (bundle.getLong("placeId") != -1) {
                long placeId=bundle.getLong("placeId");
                Log.d("placeIdINDetails", " "+placeId);

                getPlaceDetails(placeId);
            }
        }
        return root;
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
        placePrice.setText(String.valueOf(place.getPrice()));
        placeFeatures.setText(place.getFeatures().toString());
        placeRating.setText(String.valueOf(place.getRating()));
        placeNoBedrooms.setText(String.valueOf(place.getNumberBedrooms()));
        placeNoBathrooms.setText(String.valueOf(place.getNumberBathrooms()));
        placeType.setText(place.getType());
    }
}
