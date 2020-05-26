package com.example.homiesmarketplaceapp.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.adapter.FavoritesAdapter;
import com.example.homiesmarketplaceapp.adapter.FeedAdapter;
import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_favorites, container, false);
        String email="jose@email.com";

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
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_favorites);
        FavoritesAdapter adapter = new FavoritesAdapter(getContext(), placeList);
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
        });


    }
}