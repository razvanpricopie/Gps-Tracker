package com.example.loginapp.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.R;
import com.example.loginapp.Api.Api;
import com.example.loginapp.model.UserRegister;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment{

    private EditText email, password, confirmPassword, firstName, lastName;
    private Button buttonRegister;
    private TextView textViewResult;

    public RegisterFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_register, container, false);

       firstName = (EditText) v.findViewById(R.id.et_firstName);
       lastName = (EditText) v.findViewById(R.id.et_lastName);
       email = (EditText) v.findViewById(R.id.et_email);
       password = (EditText) v.findViewById(R.id.et_password);
       confirmPassword = (EditText) v.findViewById(R.id.et_repassword);
       buttonRegister = (Button) v.findViewById(R.id.btn_register);

       buttonRegister.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               String firstNameText = firstName.getText().toString();
               String lastNameText = lastName.getText().toString();
               String emailText = email.getText().toString();
               String passwordText = password.getText().toString();
               String confirmPasswordText = confirmPassword.getText().toString();

               UserRegister userRegister = new UserRegister(firstNameText, lastNameText, emailText, passwordText, confirmPasswordText);

               Retrofit retrofit = new Retrofit.Builder()
                       .baseUrl("http://192.168.100.4:8080/")
                       .addConverterFactory(GsonConverterFactory.create())
                       .build();

               Api api = retrofit.create(Api.class);
               //RegisterDTO registerDTO = new RegisterDTO(userRegister);
               Call<UserRegister> callRegister = api.Register(userRegister);

               callRegister.enqueue(new Callback<UserRegister>() {
                   @Override
                   public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {

                       if (!response.isSuccessful()) {
                           try {
                               String s = response.errorBody().string();
                               JSONObject jsonObjectError = new JSONObject(s);
                               Toast.makeText(getContext(), response.code()+ " : "+ jsonObjectError.getString("message"),Toast.LENGTH_SHORT).show();

                           }catch(Exception exception){
                               Toast.makeText(getContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
                           }
                       }
                   }

                   @Override
                   public void onFailure(Call<UserRegister> call, Throwable t) {
                        Toast.makeText(getContext(), "Code" + t.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });



       return v;
       }

}