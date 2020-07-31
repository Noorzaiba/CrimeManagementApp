package com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.CrimeManagementOptions;
import com.example.crimemanagementapp.activities.EntryActivty;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_password_reset.OTPVerificationForForgotPassword;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.accounts.LoginResponse;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText emailIDET,passwordET;

    Button loginButton,forgotButton,mainMenuButton;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_login);
        emailIDET=findViewById(R.id.emailIDET);
        passwordET=findViewById(R.id.passwordET);
        loginButton=findViewById(R.id.loginButton);
        forgotButton=findViewById(R.id.forgotButton);
        mainMenuButton=findViewById(R.id. mainMenuButton);


        this.loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                login();

            }
        });

        this. mainMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               Intent i=new Intent(getApplicationContext(), EntryActivty.class);
               startActivity(i);
               finish();

            }
        });

        this.forgotButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), OTPVerificationForForgotPassword.class);
                startActivity(intent);

            }
        });




    }


    void login(){
        String emailIDL = emailIDET.getText().toString().trim();
        String passwordL = passwordET.getText().toString().trim();


        if (!Patterns.EMAIL_ADDRESS.matcher(emailIDL).matches()) {
            emailIDET.setError("Invalid Email address ");
            emailIDET.requestFocus();
            return;
        }


        if (passwordL.isEmpty()) {
            passwordET.setError("Password cannot be empty");
            passwordET.requestFocus();
            return;
        }


        Call<LoginResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .Login(passwordL,emailIDL);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                try {

                    if (200 == response.code()) {

                      LoginResponse res=response.body();
                      if(res.isFlag()){
                          Log.i("FF", res.getUser().getEmail());
                          Log.i("FF",res.getUser().getToken());
                          Log.i("FF",res.getUser().getName());
                          Log.i("FF", String.valueOf((res.getUser().getId())));
                          Log.i("FF", String.valueOf((res.getUser().isStaff())));
                          Log.i("FF", String.valueOf((res.getUser().isSuperuser())));
                          Toast.makeText(LoginActivity.this, res.getMsg(), Toast.LENGTH_LONG).show();
                          SharedPrefManager.getInstance(LoginActivity.this)
                                  .saveUser(res.getUser());
                          Intent intent=new Intent(LoginActivity.this, CrimeManagementOptions.class);
                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          startActivity(intent);
                      }else{
                          Toast.makeText(LoginActivity.this, res.getMsg(), Toast.LENGTH_LONG).show();
                      }


                    }else{
                        Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show(); }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }





    protected void onStart() {
        super.onStart();
        Log.i("df",String.valueOf(SharedPrefManager.getInstance(this).isLogIn()));
        if(SharedPrefManager.getInstance(this).isLogIn()){
            Intent intent=new Intent(getApplicationContext(),CrimeManagementOptions.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }





}