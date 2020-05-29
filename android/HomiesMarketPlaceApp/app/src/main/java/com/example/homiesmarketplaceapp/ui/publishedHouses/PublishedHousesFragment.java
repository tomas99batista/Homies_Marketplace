package com.example.homiesmarketplaceapp.ui.publishedHouses;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.adapter.FavoritesAdapter;
import com.example.homiesmarketplaceapp.model.Place;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublishedHousesFragment extends Fragment {
    View root;
    FloatingActionButton addHouse;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_published_houses, container, false);
        addHouse=root.findViewById(R.id.addHouse);

        addHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("adding house", "add");
                NavHostFragment.findNavController(PublishedHousesFragment.this).navigate(R.id.publishedHouses_to_new);
            }
        });
        getUserPublishedHouses();
        return root;
    }

    private void getUserPublishedHouses(){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        String email="jose@email.com";
        Call<List<Place>> callPublishedHouses=service.getPublishedHouses(email);
        callPublishedHouses.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                generatePublishedHouses(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {

            }
        });
    }

    private void generatePublishedHouses(final List<Place> placeList){
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_publishedHouses);
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
                NavHostFragment.findNavController(PublishedHousesFragment.this).navigate(R.id.publishedHouses_to_details,bundle);
            }
        });
    }
}
