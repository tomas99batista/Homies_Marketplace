package com.example.homiesmarketplaceapp.ui.search_results;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
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

public class SearchResultsFragment extends Fragment {

    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search_results, container, false);

        Bundle bundle=this.getArguments();
        if (bundle!=null){
            if (bundle.getString("query")!=null){
                String city=bundle.getString("query");
                searchByCity(city);
            }
        }
        return root;
    }

    private void searchByCity(String city){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Place>> callSearch=service.search(city);
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
        FavoritesAdapter adapter = new FavoritesAdapter(getContext(), placeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("event_id", Long.toString(placeList.get(position).getId()));
                Log.d("clicked", Integer.toString(position));
                Bundle bundle = new Bundle();
                bundle.putLong("event_id", placeList.get(position).getId());
                NavHostFragment.findNavController(SearchResultsFragment.this).navigate(R.id.search_results_to_details,bundle);
            }
        });
    }
}
