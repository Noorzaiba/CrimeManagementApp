package com.example.crimemanagementapp.activities.Investigator_details.investigator_password_reset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LogoutActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.miscellaneous.PasswordResetModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class InvestigatorPasswordReset  extends AppCompatActivity {
        private EditText password1ET, password2ET;
        private Button submitButton;
        String email;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_password_update);
            password1ET = findViewById(R.id.password1ET);
            password2ET = findViewById(R.id. password2ET);
            submitButton = findViewById(R.id.submitButton);
            Intent i=getIntent();
            email=i.getStringExtra("email");


            submitButton .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePassword();


                }
            });
        }


        void updatePassword() {
            String password1 = password1ET.getText().toString();
            String password2 = password2ET.getText().toString();
            boolean test = validation(password1, password2);
            if (test == true) {

                PasswordResetModel obj=new PasswordResetModel(password1,email);
                Call<PasswordResetModel> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .investigatorUpdatePassword(obj);
                call.enqueue(new Callback<PasswordResetModel>() {
                    @Override
                    public void onResponse(Call<PasswordResetModel> call, Response<PasswordResetModel> response) {

                        try{
                            if(response.code()==200){
                        PasswordResetModel obj= response.body();
                        if(obj.isFlag()){
                            Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(), LogoutActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                        }
                            }else{
                                Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_LONG).show();
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
        boolean validation(String password1,String password2){


            if (password1.length()<8){
                password1ET.setError("Password must have atleast 8 characters");
                password1ET.requestFocus();
                return false;
            }
            if (password2.length()<8){
                password2ET.setError("Password must have atleast 8 characters");
                password2ET.requestFocus();
                return false;
            }


            if (!password1.equals(password2)){
                Toast.makeText(getApplicationContext(),"passwords are not matching please enter correct passwords",Toast.LENGTH_LONG).show();
                return false;
            }

            return true;
        }

    }
