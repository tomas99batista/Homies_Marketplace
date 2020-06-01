package com.example.homiesmarketplaceapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.model.User;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText new_firstName;
    EditText new_lastName;
    EditText new_email;
    EditText new_password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new_firstName=findViewById(R.id.choose_firstname);
        new_lastName=findViewById(R.id.choose_lastname);
        new_password=findViewById(R.id.choose_password);
        new_email=findViewById(R.id.choose_email);
        register=findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //call api to register new user
                register();
            }
        });
    }

    private void register(){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<User> registerUser = service.registerUser(new User(new_email.getText().toString(), new_password.getText().toString(),
                new_firstName.getText().toString(), new_lastName.getText().toString(), "city"));
        registerUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("user created", response.body().toString());
                if (response.body()!=null){

                    Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("user not created", t.getLocalizedMessage());

            }
        });
    }


}
