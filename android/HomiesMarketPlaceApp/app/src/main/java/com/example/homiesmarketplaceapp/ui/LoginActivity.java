package com.example.homiesmarketplaceapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
<<<<<<< HEAD
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homiesmarketplaceapp.MainActivity;
import com.example.homiesmarketplaceapp.R;
=======
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homiesmarketplaceapp.MainActivity;
import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.model.User;
import com.example.homiesmarketplaceapp.network.GetDataService;
import com.example.homiesmarketplaceapp.network.RetrofitClientInstance;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    Button register;
<<<<<<< HEAD
=======
    private SharedPreferences mPreferences;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);

<<<<<<< HEAD
=======
        email.setText("jose@email.com");
        password.setText("password");
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
    }

    private void login(){
        String mail=email.getText().toString();
        String pass=password.getText().toString();
<<<<<<< HEAD
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
=======
        postLogin(mail, pass);

>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
    }
    private void goToRegister(){
        Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
<<<<<<< HEAD
=======


    private void postLogin(String email, String password){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<User> callLogin=service.login(new User(email, password));
        callLogin.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("logged in", response.body().toString());
                if (response.body()!=null){
                    Log.d("logged in", "log");
                    writeUserEmailToPreferences(response.body());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error in credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void writeUserEmailToPreferences(User user){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("email", user.getEmail());
        editor.apply();
    }
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
}
