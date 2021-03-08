package com.example.loginapp.Service;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class LocationTrack extends Service implements LocationListener {

    private final Context mContext;
    boolean checkGps = false;
    boolean checkNetwork = false;
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_FOR_UPDATES = 10; // 10 m
    private static final long MIN_TIME_BW_UPDATES = 1000*60*1; // 1 min
    protected LocationManager locationManager;

    public LocationTrack(Context Context) {
        this.mContext = Context;
        getLocation();
    }

    private Location getLocation(){

        try{
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            //get Gps status
            checkGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //get network provider status
            checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!checkGps && !checkNetwork){
                Toast.makeText(mContext, "No Service Provider is available", Toast.LENGTH_SHORT).show();
            }else{
                this.canGetLocation = true;

                /*if(checkNetwork){
                    if(ActivityCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},101);
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_FOR_UPDATES, this);

                    if(locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }*/


                //if Gps is enabled get latitude/longitude using Gps Service
                if(checkGps) {
                    if (location == null) {
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                        }

                        //method of the LocationManager class is used to register the current activity to be notified periodically by the named provider
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }



            }

        }catch (Exception exception){
            exception.printStackTrace();
        }

        return location;
    }

    public double getLatitude(){
        /*if (location != null){
            latitude = location.getLatitude();
        }*/
        return latitude;
    }

    public double getLongitude(){
        /*if (location != null){
            latitude = location.getLongitude();
        }*/
        return longitude;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS is not enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    //is invoked periodically based upon the minTime and minDistance, whichever comes first
    @Override
    public void onLocationChanged(@NonNull Location location) {
    }

    // is called upon the locationManager object and is used to check whether GPS/Network Provider is enabled or not
    //If the Providers aren’t enabled we’re calling the method showSettingsAlert() that shows a prompt to enable GPS.
    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
