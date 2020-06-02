package com.example.homiesmarketplaceapp.ui.favorites;

<<<<<<< HEAD
=======
import android.content.SharedPreferences;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
<<<<<<< HEAD
=======
import androidx.preference.PreferenceManager;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.adapter.FavoritesAdapter;
import com.example.homiesmarketplaceapp.adapter.FeedAdapter;
import com.example.homiesmarketplaceapp.model.Place;
<<<<<<< HEAD
=======
import com.example.homiesmarketplaceapp.model.PlaceId;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {
    View root;
<<<<<<< HEAD
=======
    String email;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_favorites, container, false);
<<<<<<< HEAD
        String email="jose@email.com";
=======
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        email=prefs.getString("email", "");
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

        getFavorites(email);

        return root;
    }

    private void getFavorites(String email){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Place>> call_favorite_places=service.getFavoritePlaces(email);
        call_favorite_places.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if (response.body()==null){
                    Toast.makeText(getContext(), "Error connecting to api!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getContext(), "We have nothing to show you", Toast.LENGTH_SHORT).show();
                    } else {
                        generateFavorites(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {

            }
        });

    }

    private void generateFavorites(final List<Place> placeList){
<<<<<<< HEAD
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_favorites);
        FavoritesAdapter adapter = new FavoritesAdapter(getContext(), placeList);
=======
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view_favorites);
        final FavoritesAdapter adapter = new FavoritesAdapter(getContext(), placeList);
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("clicked", "clicked favorites");
                Log.d("placeId", Long.toString(placeList.get(position).getId()));
                Bundle bundle = new Bundle();
                bundle.putLong("placeId", placeList.get(position).getId());
                NavHostFragment.findNavController(FavoritesFragment.this).navigate(R.id.favorites_to_details,bundle);
            }
<<<<<<< HEAD
=======

            @Override
            public void onRemovingFromFavoritesClick(final int position) {
                GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<String> callRemoveFromFavorites=service.removeFavoriteHouse(email, new PlaceId(placeList.get(position).getId()));

                callRemoveFromFavorites.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("true")){
                            Toast.makeText(getContext(), "Place removed from favorites", Toast.LENGTH_SHORT).show();
                            placeList.remove(position);
                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
        });


    }
}
