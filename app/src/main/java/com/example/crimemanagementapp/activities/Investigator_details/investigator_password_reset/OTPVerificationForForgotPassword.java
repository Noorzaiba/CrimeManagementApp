package com.example.crimemanagementapp.activities.Investigator_details.investigator_password_reset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.miscellaneous.PasswordResetModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationForForgotPassword extends AppCompatActivity {
    private EditText emailIdET,otpET;
    private TextView msgTV;
    private Button emailSubmitButton,otpSubmitButton,resetSubmitButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        emailIdET = findViewById(R.id.emailIdET);
        otpET = findViewById(R.id.otpET);
        msgTV = findViewById(R.id.msgTV);
        emailSubmitButton = findViewById(R.id.emailSubmitButton);
        otpSubmitButton = findViewById(R.id.otpSubmitButton);
        resetSubmitButton= findViewById(R.id. resetSubmitButton);
        otpET.setEnabled(false);
        otpSubmitButton.setEnabled(false);
        resetSubmitButton.setEnabled(false);

        resetSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgTV.setText("Request Processing");
                emailIdET.setEnabled(true);
                emailSubmitButton.setEnabled(true);
                otpET.setText("");
                otpET.setEnabled(false);
                otpSubmitButton.setEnabled(false);
                resetSubmitButton.setEnabled(false);
                emailIdET.requestFocus();
            }
        });

        emailSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             forgotPassword();
            }
        });
       otpSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           veriftyOTP();

            }
        });

    }

    private void veriftyOTP() {

        String otp = otpET.getText().toString();
        boolean test = validationForOTP(otp);
        if (test == true) {
            PasswordResetModel obj=new PasswordResetModel(emailIdET.getText().toString(),otp,false);
            Call<PasswordResetModel> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .investigatorOTPVerify(obj);


            call.enqueue(new Callback<PasswordResetModel>() {
                @Override
                public void onResponse(Call<PasswordResetModel> call, Response<PasswordResetModel> response) {
                    try{
                        if(response.code()==200){

                    PasswordResetModel obj= response.body();
                    if(obj.isFlag()){
                        Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),InvestigatorPasswordReset.class);
                        intent.putExtra("email",emailIdET.getText().toString());
                        startActivity(intent);
                    }else{

                        Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                        resetSubmitButton.setEnabled(true);
                        msgTV.setText("Invalid OTP Entered");
                        otpSubmitButton.setEnabled(false);
                    }
                        }else{

                            Toast.makeText(getApplicationContext(), "Technical Error", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PasswordResetModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        }
    }
private void forgotPassword(){
        String email_id=emailIdET.getText().toString();
        boolean test=validationForEmail(email_id);
        if(test==true){
          PasswordResetModel obj=new PasswordResetModel(email_id);
            Call<PasswordResetModel> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .investigatorPasswordReset(obj);

            call.enqueue(new Callback<PasswordResetModel>() {
                @Override
                public void onResponse(Call<PasswordResetModel> call, Response<PasswordResetModel> response) {

                    try{
                        if(response.code()==200){
                    PasswordResetModel obj= response.body();
                    if(obj.isFlag()){
                        Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                        emailIdET.setEnabled(false);
                        msgTV.setText("Check your Email For OTP");
                        emailSubmitButton.setEnabled(false);
                        otpET.setEnabled(true);
                        otpSubmitButton.setEnabled(true);

                    }else{
                        Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                        msgTV.setText("Error Occured While Generating OTP");
                    }
                        }else{

                            Toast.makeText(getApplicationContext(), "Technical Error", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PasswordResetModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }


}


    private boolean validationForEmail(String emailIdR ){


        if (!Patterns.EMAIL_ADDRESS.matcher(emailIdR).matches()){
            emailIdET.setError("Invalid Email address ");
            emailIdET.requestFocus();
            return false;
        }

         return true;
    }
    private boolean validationForOTP(String otpR ){

        if (otpR.length()<7){
            otpET.setError("OTP must have  7  characters");
            otpET.requestFocus();
            return false;
        }
        if (otpR.length()>7){
            otpET.setError("OTP must have only 7  characters");
            otpET.requestFocus();
            return false;
        }
        return true;
    }
}
