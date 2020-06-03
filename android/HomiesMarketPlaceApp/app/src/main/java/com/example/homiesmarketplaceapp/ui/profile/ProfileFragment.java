package com.example.homiesmarketplaceapp.ui.profile;

import android.content.Intent;
<<<<<<< HEAD
=======
import android.content.SharedPreferences;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
<<<<<<< HEAD
=======
import androidx.preference.PreferenceManager;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.ui.LoginActivity;

public class ProfileFragment extends Fragment {
    View root;
    Button seePublishedHouses;
    Button logout;
<<<<<<< HEAD
=======
    String email;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        seePublishedHouses=root.findViewById(R.id.seePublishedHouses);
<<<<<<< HEAD
=======
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        email=prefs.getString("email", "");
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
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
