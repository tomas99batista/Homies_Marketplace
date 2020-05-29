package com.example.homiesmarketplaceapp.ui.addHouse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.model.Review;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHouseFragment extends Fragment {
    EditText NewplaceImage;
    EditText New_place_title;
    EditText New_place_city;
    EditText New_place_price;
    EditText New_place_features;
    EditText New_place_type;
    EditText New_place_no_bedrooms;
    EditText New_place_no_bathrooms;
    Button submitHouse;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_house, container, false);
        NewplaceImage=root.findViewById(R.id.NewplaceImage);
        New_place_title=root.findViewById(R.id.New_place_title);
        New_place_city=root.findViewById(R.id.New_place_city);
        New_place_price=root.findViewById(R.id.New_place_price);
        New_place_features=root.findViewById(R.id.New_place_features);
        New_place_type=root.findViewById(R.id.New_place_type);
        New_place_no_bedrooms=root.findViewById(R.id.New_place_no_bedrooms);
        New_place_no_bathrooms=root.findViewById(R.id.New_place_no_bathrooms);
        submitHouse=root.findViewById(R.id.submitHouse);

        submitHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("adding house", "adding house");
                addHouse();
            }
        });

        return root;
    }

    private void addHouse(){
        String email="jose@email.com";
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        String imageUrl= NewplaceImage.getText().toString();
        String placeTitle=New_place_title.getText().toString();
        String placeCity=New_place_city.getText().toString();
        String placePrice=New_place_price.getText().toString();
        String features= New_place_features.getText().toString();
        String placeType=New_place_type.getText().toString();
        String no_bedrooms=New_place_no_bedrooms.getText().toString();
        String no_bathrooms=New_place_no_bathrooms.getText().toString();
        double price=Double.parseDouble(placePrice);
        int No_bedrooms=Integer.parseInt(no_bedrooms);
        int No_bathrooms=Integer.parseInt(no_bathrooms);
        ArrayList<String> placeFeatures= new ArrayList<>();
        placeFeatures.add(features);
        Place place= new Place(0L, placeTitle, price, 0.0, placeFeatures, No_bathrooms, No_bedrooms, placeType, placeCity, new ArrayList<Long>(), imageUrl);
        Call<String> addPublishedHouse=service.addPublishedHouse(email, place);
        addPublishedHouse.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("response", response.body().toString());
                if (response.body().equals("true")){
                    Log.d("house created", "house created");
                    NavHostFragment.findNavController(AddHouseFragment.this).navigate(R.id.new_house_to_houses);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
