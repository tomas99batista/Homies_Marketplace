package com.example.homiesmarketplaceapp.ui.profile;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import androidx.preference.PreferenceManager;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.ui.LoginActivity;

public class ProfileFragment extends Fragment {
    View root;
    Button seePublishedHouses;
    Button logout;

    String email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        seePublishedHouses=root.findViewById(R.id.seePublishedHouses);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        email=prefs.getString("email", "");
        logout=root.findViewById(R.id.logout);

        seePublishedHouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("publishedHouses", "published");
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.profile_to_publishedHouses);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

}
