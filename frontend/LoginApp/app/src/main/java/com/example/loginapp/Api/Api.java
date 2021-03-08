package com.example.loginapp.Api;

import com.example.loginapp.model.User;
import com.example.loginapp.model.UserLocation;
import com.example.loginapp.model.UserLogin;
import com.example.loginapp.model.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @POST("users/login")
    Call<UserLogin> Login(@Body UserLogin userLogin);

    @POST("users/register")
    Call<UserRegister> Register(@Body UserRegister userRegister);

    @GET("users/me")
    Call<User> UserDetails();

    @POST("locations/createLocation")
    Call<UserLocation> CreateLocation(@Body UserLocation userLocation);
}
