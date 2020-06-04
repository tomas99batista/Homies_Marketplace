package com.example.homiesmarketplaceapp.ui.home;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.adapter.FeedAdapter;
import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.model.PlaceId;
import com.example.homiesmarketplaceapp.model.Success;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    ProgressDialog progressDialog;
    View root;

    String email;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        email=prefs.getString("email", "");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        getPlaces();
        return root;
    }

    private void getPlaces(){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Place>> call_places=service.getAllPlaces();

        call_places.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                progressDialog.dismiss();
                //check if list is empty
                if (response.body()==null){
                    Toast.makeText(getContext(), "Error connecting to api!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getContext(), "We have nothing to show you", Toast.LENGTH_SHORT).show();
                    } else {
                        generateFeed(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("response", t.getMessage());
            }
        });
    }

    private void generateFeed(final List<Place> placeList) {
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        FeedAdapter adapter = new FeedAdapter(getContext(), placeList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("placeId", Long.toString(placeList.get(position).getId()));
                Bundle bundle = new Bundle();
                bundle.putLong("placeId", placeList.get(position).getId());
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.feed_to_details,bundle);
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
