package com.example.loginapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.loginapp.Api.Api;
import com.example.loginapp.Api.BasicAuth;
import com.example.loginapp.R;
import com.example.loginapp.Service.LocationTrack;
import com.example.loginapp.model.UserLocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentLocationActivity extends AppCompatActivity {
    private Button buttonUserDetails;
    private Button buttonSendLocation;
    private Button buttonSendLocationAuto;
    private Button buttonStopSendLocation;
    private int delayTime = 5000; // 1min
    boolean ok = false;
    protected String longitude, latitude;
    Runnable runnable;
    private Handler handler = new Handler();
    LocationTrack locationTrack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        buttonSendLocation = findViewById(R.id.btn_sendLocation);
        buttonSendLocationAuto = findViewById(R.id.btn_sendLocationAuto);
        buttonStopSendLocation = findViewById(R.id.btn_stopSendLocation);

        buttonStopSendLocation.setEnabled(false);

        try{
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }


        buttonSendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


        buttonSendLocationAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok = false;
                handler.postDelayed(runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(ok == false){
                            handler.postDelayed(runnable,delayTime);
                            buttonStopSendLocation.setEnabled(true);
                            getLocation();
                        }
                    }
                }, delayTime);

            }
        });

        buttonStopSendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok = true;
                buttonStopSendLocation.setEnabled(false);
            }
        });
    }

    private void getLocation(){
        locationTrack = new LocationTrack(CurrentLocationActivity.this);

        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        if(locationTrack.canGetLocation()){
            longitude = String.valueOf(locationTrack.getLongitude());
            latitude = String.valueOf(locationTrack.getLatitude());
        }



        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuth(email,password))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        Api api = retrofit.create(Api.class);
        UserLocation userLocation = new UserLocation(email, longitude, latitude);
        Call<UserLocation> call = api.CreateLocation(userLocation);

        call.enqueue(new Callback<UserLocation>() {
            @Override
            public void onResponse(Call<UserLocation> call, Response<UserLocation> response) {

                try{
                    String s = response.errorBody().string();
                    JSONObject jsonObjectError = new JSONObject(s);
                    Toast.makeText(CurrentLocationActivity.this, response.code()+ " : "+ jsonObjectError.getString("message"), Toast.LENGTH_SHORT).show();
                }catch (JSONException | IOException exception){
                    Toast.makeText(CurrentLocationActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserLocation> call, Throwable t) {
                Toast.makeText(CurrentLocationActivity.this, "Code" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
