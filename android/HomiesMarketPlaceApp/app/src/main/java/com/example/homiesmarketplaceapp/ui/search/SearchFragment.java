package com.example.homiesmarketplaceapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.homiesmarketplaceapp.R;

public class SearchFragment extends Fragment {

    View root;
    EditText searchView;
    ImageButton searchByName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);
        searchView=root.findViewById(R.id.search_view);
        searchByName=root.findViewById(R.id.sendQuery);

        searchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Search for "+searchView.getText().toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("query", searchView.getText().toString());
                NavHostFragment.findNavController(SearchFragment.this).navigate(R.id.search_to_results,bundle);
            }
        });
        return root;
    }
}
