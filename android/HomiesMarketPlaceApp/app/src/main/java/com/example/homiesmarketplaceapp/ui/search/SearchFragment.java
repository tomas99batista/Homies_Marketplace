package com.example.homiesmarketplaceapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.homiesmarketplaceapp.R;

public class SearchFragment extends Fragment {

    View root;
    EditText city;
    EditText minPrice;
    EditText maxPrice;
    EditText type;
    EditText bedrooms;
    EditText bathrooms;
    RatingBar ratingBar;
    Button searchBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);
        city=root.findViewById(R.id.cityFilter);
        minPrice=root.findViewById(R.id.minPriceFilter);
        maxPrice=root.findViewById(R.id.maxPriceFilter);
        type=root.findViewById(R.id.typeFilter);
        bedrooms=root.findViewById(R.id.bedroomsFilter);
        bathrooms=root.findViewById(R.id.bathroomsFilter);
        ratingBar=root.findViewById(R.id.ratingFilter);
        searchBtn=root.findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("city",city.getText().toString());
                bundle.putString("minPrice",minPrice.getText().toString());
                bundle.putString("maxPrice", maxPrice.getText().toString());
                bundle.putString("bedrooms", bedrooms.getText().toString());
                bundle.putString("bathrooms", bathrooms.getText().toString());
                bundle.putString("rating", String.valueOf(ratingBar.getRating()));
                bundle.putString("type", type.getText().toString());
                NavHostFragment.findNavController(SearchFragment.this).navigate(R.id.search_to_results,bundle);
            }
        });
        return root;
    }
}
