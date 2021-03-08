package com.example.loginapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginapp.Api.Api;
import com.example.loginapp.R;
import com.example.loginapp.model.UserLogin;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {
    private EditText email, password;
    private Button buttonLogin;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        email = (EditText) v.findViewById(R.id.et_email);
        password = (EditText) v.findViewById(R.id.et_password);
        buttonLogin = (Button) v.findViewById(R.id.btn_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();



                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.100.4:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                UserLogin userLogin = new UserLogin(emailText, passwordText);
                Call<UserLogin> callLogin = api.Login(userLogin);

                callLogin.enqueue(new Callback<UserLogin>() {
                    @Override
                    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {


                        if (!response.isSuccessful()) {
                            try {
                                String s = response.errorBody().string();
                                JSONObject jsonObjectError = new JSONObject(s);
                                Toast.makeText(getContext(), response.code() + " : " + jsonObjectError.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (Exception exception) {
                                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            return;
                        }

                        Intent intent = new Intent(getContext(), CurrentLocationActivity.class);
                        intent.putExtra("email",emailText);
                        intent.putExtra("password",passwordText);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<UserLogin> call, Throwable t) {
                        Toast.makeText(getContext(), "Code" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return v;
    }
}