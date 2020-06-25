package com.example.crimemanagementapp.activities.Investigator_details.investigator_password_reset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.accounts.User;
import com.example.crimemanagementapp.model.miscellaneous.PasswordResetModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerficationForPasswordReset extends AppCompatActivity {
    private EditText otpET;
    private TextView msgTV;
    User user;
    String loggedInEmail;
    private Button otpSubmitButton,sendSubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verfication_for_password_reset);
        this.user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        msgTV = findViewById(R.id.msgTV);
        otpET = findViewById(R.id.otpET);
        otpSubmitButton = findViewById(R.id.otpSubmitButton);
        sendSubmitButton = findViewById(R.id.sendSubmitButton);
        otpET.setEnabled(false);
        otpSubmitButton.setEnabled(false);
         loggedInEmail=user.getEmail();
         Log.i("Ff",loggedInEmail);


        sendSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgTV.setText("Request Processing");
               forgotPassword();
                sendSubmitButton.setEnabled(false);

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
          Log.i("emailV",user.getEmail());
            PasswordResetModel obj=new PasswordResetModel(loggedInEmail,otp,false);
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
                        Intent intent=new Intent(getApplicationContext(), InvestigatorPasswordReset.class);
                        intent.putExtra("email",user.getEmail());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{

                        Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                        otpET.setText("");
                        msgTV.setText("Invalid OTP Entered");
                        otpET.setEnabled(false);
                        otpSubmitButton.setEnabled(false);
                        sendSubmitButton.setEnabled(true);

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
    private boolean validationForOTP(String otpR ){

        if (otpR.length()<7){
            otpET.setError("OTP must have only  7 characters");
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
    private void forgotPassword(){
        Log.i("emailF",loggedInEmail);
        PasswordResetModel obj=new PasswordResetModel(loggedInEmail);
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
                        Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                        msgTV.setText("Check your Email For OTP");
                        otpET.setEnabled(true);
                        otpSubmitButton.setEnabled(true);
                        otpET.requestFocus();
                    }else{
                        Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                        msgTV.setText("Error Occured While Generating OTP Please Try after sometime");
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
    protected void onStart() {
        super.onStart();
        Log.i("df",String.valueOf(SharedPrefManager.getInstance(this).isLogIn()));
        if(!SharedPrefManager.getInstance(this).isLogIn()){
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }
}
