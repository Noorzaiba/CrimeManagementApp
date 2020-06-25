package com.example.crimemanagementapp.activities.Investigator_details.query_report_facilitiy_for_investigator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorDefaultResponse;
import com.example.crimemanagementapp.model.query_reporting_contact_us.InvestigatorContactUsDefaultResponse;
import com.example.crimemanagementapp.model.query_reporting_contact_us.InvestigatorContactUsModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactUsRegisterActivity extends AppCompatActivity {
    EditText descriptionET;
    Button submitButton,resetButton;
    String loggedInEmail,loggedInToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        descriptionET = findViewById(R.id.descriptionET);
        submitButton = findViewById(R.id.submitButton);
        resetButton = findViewById(R.id.resetButton);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();


            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               descriptionET.setText("");


            }
        });


    }

    void postData() {
        String description = descriptionET.getText().toString();
        boolean test = validation(description);
        if (test == true) {

            InvestigatorContactUsModel obj = new InvestigatorContactUsModel(description,loggedInEmail,"Query posted");
            Call<InvestigatorContactUsDefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .registerQueryFacility(loggedInEmail,loggedInToken,obj);
            call.enqueue(new Callback<InvestigatorContactUsDefaultResponse>() {
                @Override
                public void onResponse(Call<InvestigatorContactUsDefaultResponse> call, Response<InvestigatorContactUsDefaultResponse> response) {

                try{
                    if(response.code()==200){
                        InvestigatorContactUsDefaultResponse obj = response.body();
                        InvestigatorContactUsModel obj2=obj.getSerailizedData().get(0);
                        if (obj.isFlag()) {
                            if(obj2.getDescription().equals("Invalid Email Id")){
                                Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
                            }else{

                                Toast.makeText(getApplicationContext(), "Query has been reported we will get back to you soon", Toast.LENGTH_LONG).show();
                            }



                        } else {
                            Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_LONG).show();
                        }
                    }
                  else{
                    Toast.makeText(getApplicationContext(), "Technical Error", Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

                }

                @Override
                public void onFailure(Call<InvestigatorContactUsDefaultResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Validation pending", Toast.LENGTH_LONG).show();
        }
    }

    boolean validation(String description) {


        if (description.isEmpty()) {
           descriptionET.setError("Query can be Empty");
           descriptionET.requestFocus();
            return false;
        }



        return true;
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