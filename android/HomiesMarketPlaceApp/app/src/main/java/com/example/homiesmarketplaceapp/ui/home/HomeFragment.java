package com.example.homiesmarketplaceapp.ui.home;

import android.app.ProgressDialog;
<<<<<<< HEAD
=======
import android.content.SharedPreferences;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import android.os.Bundle;
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
<<<<<<< HEAD
import androidx.recyclerview.widget.GridLayoutManager;
=======
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import androidx.recyclerview.widget.RecyclerView;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.adapter.FeedAdapter;
import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.model.PlaceId;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    ProgressDialog progressDialog;
    View root;
<<<<<<< HEAD
=======
    String email;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
<<<<<<< HEAD

=======
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        email=prefs.getString("email", "");
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
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
<<<<<<< HEAD
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
=======
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
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
<<<<<<< HEAD
                String email="jose@email.com";
=======
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
                addPlaceToFavorites(email, placeList.get(position).getId());
            }
        });
    }

    private void addPlaceToFavorites(String email, long placeId){

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> callAddToFavorites=service.addPlaceToFavorites(email, new PlaceId(placeId));
        callAddToFavorites.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("response", response.body().toString());
                if (response.body().toString().equals("true")) {
                    Toast.makeText(getContext(), "Place added to favorites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("response", t.getLocalizedMessage().toString());
            }
        });
    }
}
