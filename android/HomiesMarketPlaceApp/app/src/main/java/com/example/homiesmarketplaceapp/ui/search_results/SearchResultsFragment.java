package com.example.homiesmarketplaceapp.ui.search_results;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.adapter.FavoritesAdapter;
import com.example.homiesmarketplaceapp.adapter.FeedAdapter;
import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.model.PlaceId;
import com.example.homiesmarketplaceapp.model.Success;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsFragment extends Fragment {

    View root;
    String email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search_results, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        email=prefs.getString("email", "");
        Bundle bundle=this.getArguments();
        if (bundle!=null){
                String city=bundle.getString("city");
                String minPrice=bundle.getString("minPrice");
                String maxPrice=bundle.getString("maxPrice");
                String bedrooms=bundle.getString("bedrooms");
                String bathrooms=bundle.getString("bathrooms");
                String rating=bundle.getString("rating");
                String type=bundle.getString("type");
                search(city, type, minPrice, maxPrice, bedrooms, bathrooms, rating);

        }
        return root;
    }

    private void search(String city, String type, String minPrice, String maxPrice, String bedrooms, String bathrooms, String rating){
        String initialUrl="http://192.168.160.30:8080/api/search/";
        String searchUrl="";
        if (city!=null && !city.equals("")){
            searchUrl+="?city="+city;
        }
        if (type!=null && !type.equals("") && searchUrl.isEmpty()){
            searchUrl+="?type="+type;
        }
        if (type!=null && !type.equals("") && !searchUrl.isEmpty() && !searchUrl.contains("type")){
            searchUrl+="&type="+type;
        }
        if (minPrice!=null && !minPrice.equals("") && searchUrl.isEmpty()){
            searchUrl+="?minPrice="+minPrice;
        }
        if (minPrice!=null && !minPrice.equals("") && !searchUrl.isEmpty() && !searchUrl.contains("minPrice")){
            searchUrl+="&minPrice="+minPrice;
        }
        if (maxPrice!=null && !maxPrice.equals("") && searchUrl.isEmpty()){
            searchUrl+="?maxPrice="+maxPrice;
        }
        if (maxPrice!=null && !maxPrice.equals("") && !searchUrl.isEmpty() && !searchUrl.contains("maxPrice")){
            searchUrl+="&maxPrice="+maxPrice;
        }
        if (bedrooms!=null && !bedrooms.equals("") && searchUrl.isEmpty()){
            searchUrl+="?bedrooms="+bedrooms;
        }
        if (bedrooms!=null && !bedrooms.equals("") && !searchUrl.isEmpty() && !searchUrl.contains("bedrooms")){
            searchUrl+="&bedrooms="+bedrooms;
        }

        if (bathrooms!=null && !bathrooms.equals("") && searchUrl.isEmpty()){
            searchUrl+="?bathrooms="+bathrooms;
        }
        if (bathrooms!=null && !bathrooms.equals("") && !searchUrl.isEmpty() && !searchUrl.contains("bathrooms")){
            searchUrl+="&bathrooms="+bathrooms;
        }

        if (rating!=null && !rating.equals("0.0") && searchUrl.isEmpty()){
            searchUrl+="?rating="+rating;
        }
        if (rating!=null && !rating.equals("0.0") && !searchUrl.isEmpty() && !searchUrl.contains("rating")){
            searchUrl+="&rating="+rating;
        }

        Log.d("url", searchUrl);
        String fullUrl=initialUrl+searchUrl;

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Place>> callSearch=service.search(fullUrl);

        callSearch.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                Log.d("response in search", response.body().toString());
                generateResults(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {

            }
        });


    }

    private void generateResults(final List<Place> placeList){
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_search_results);
        FeedAdapter adapter = new FeedAdapter(getContext(), placeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("placeId", Long.toString(placeList.get(position).getId()));
                Log.d("clicked", Integer.toString(position));
                Bundle bundle = new Bundle();
                bundle.putLong("placeId", placeList.get(position).getId());
                NavHostFragment.findNavController(SearchResultsFragment.this).navigate(R.id.search_results_to_details,bundle);
            }

            @Override
            public void onAddToFavorites(int position) {
                Log.d("adding to favorites", "adding to favorites");
                addPlaceToFavorites(email, placeList.get(position).getId());
            }
        });
    }

    private void addPlaceToFavorites(String email, long placeId){

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Success> callAddToFavorites=service.addPlaceToFavorites(email, new PlaceId(placeId));
        callAddToFavorites.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                Log.d("response", response.body().toString());
                if (response.body().isSuccess()) {
                    Toast.makeText(getContext(), "Place added to favorites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                Log.e("response", t.getLocalizedMessage().toString());
            }
        });
    }
}