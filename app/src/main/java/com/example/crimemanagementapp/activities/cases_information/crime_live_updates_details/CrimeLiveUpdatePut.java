package com.example.crimemanagementapp.activities.cases_information.crime_live_updates_details;

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
import com.example.crimemanagementapp.model.cases_information.CrimeDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeLiveUpdationModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeLiveUpdatePut extends AppCompatActivity {
    EditText crimeIdET, statementET, docET,idET,douET;
    Button submitButton,updateButton;
    String loggedInEmail,loggedInToken;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_live_update_put);
        crimeIdET = findViewById(R.id.crimeIdET);
        statementET = findViewById(R.id.statementET);
        docET = findViewById(R.id.docET);
        douET = findViewById(R.id.douET);
        idET=findViewById(R.id.idET);
        submitButton = findViewById(R.id.submitButton);
        updateButton = findViewById(R.id.updateButton);

        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        this.updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(CrimeLiveUpdatePut.this,"you can update Statements",Toast.LENGTH_LONG).show();
                statementET.requestFocus();

            }
        });

        getCrimeLiveDetails();

        this.submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                putCrimeLiveDetails();
            }
        });

    }

    void putCrimeLiveDetails(){
       String statements= statementET.getText().toString();
       String crimeID=crimeIdET.getText().toString();
       int id=Integer.parseInt(idET.getText().toString());
       String doc=docET.getText().toString();
       validation(statements,crimeID);
       int crimeIDNumber=Integer.parseInt(crimeID);
       CrimeLiveUpdationModel crimeLiveUpdationModel= new CrimeLiveUpdationModel(id,crimeIDNumber,statements,doc);
       Log.i("tg",crimeLiveUpdationModel.toString());
        Call<CrimeDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .crimeLiveUpdatePutFacility(loggedInEmail,loggedInToken,id, crimeLiveUpdationModel);
        call.enqueue(new Callback<CrimeDefaultResponse>() {


            @Override
            public void onResponse(Call<CrimeDefaultResponse> call, Response<CrimeDefaultResponse> response) {

                if(200==response.code()) {
                    CrimeDefaultResponse res = response.body();
                    if (res.isFlag()) {
                        List<CrimeLiveUpdationModel> obj_list = res.getSerialized_data_crime_live_update();
                        CrimeLiveUpdationModel crimeLiveUpdationModelResponse = obj_list.get(0);
                        Toast.makeText(CrimeLiveUpdatePut.this,"Updated Successfully",Toast.LENGTH_LONG).show();

                        Intent i=new Intent(getApplicationContext(), CrimeLiveUpdatePut.class);

                        i.putExtra("intent_id",String.valueOf(crimeLiveUpdationModelResponse.getId()));
                        startActivity(i);
                    }else{
                        Toast.makeText(CrimeLiveUpdatePut.this,"Some error occured",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(CrimeLiveUpdatePut.this,"Authentication Error",Toast.LENGTH_LONG).show();
                }



            }
            @Override
            public void onFailure(Call<CrimeDefaultResponse> call, Throwable t) {
                Toast.makeText(CrimeLiveUpdatePut.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }
    void getCrimeLiveDetails() {

          Intent i=getIntent();
         int pk=Integer.parseInt(i.getStringExtra("intent_id"));

        Call<CrimeDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .crimeLiveUpdateGetFacility(loggedInEmail,loggedInToken,pk);
        call.enqueue(new Callback<CrimeDefaultResponse>() {

            @Override
            public void onResponse(Call<CrimeDefaultResponse> call, Response<CrimeDefaultResponse> response) {

                try{


                    if(200==response.code()){
                        CrimeDefaultResponse res=response.body();


                        if(res.isFlag()){

                            List<CrimeLiveUpdationModel> obj_list = res.getSerialized_data_crime_live_update();

                            CrimeLiveUpdationModel crimeLiveUpdationModelResponse=obj_list.get(0);

                            String id=String.valueOf(crimeLiveUpdationModelResponse.getId());
                            String crime_id=String.valueOf(crimeLiveUpdationModelResponse.getCrimeId());

                            idET.setText(id);
                            crimeIdET.setText(crime_id);
                            statementET.setText(crimeLiveUpdationModelResponse.getComments());
                            docET.setText(crimeLiveUpdationModelResponse.getDoc());
                            douET.setText(crimeLiveUpdationModelResponse.getDou());


                        }else{Toast.makeText(CrimeLiveUpdatePut.this,"you",Toast.LENGTH_LONG).show();}

                    }else{
                        Toast.makeText(CrimeLiveUpdatePut.this,"Authentication Error",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Toast.makeText(CrimeLiveUpdatePut.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }


            @Override
            public void onFailure(Call<CrimeDefaultResponse> call, Throwable t) {
                Toast.makeText(CrimeLiveUpdatePut.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    void validation(String statementL,String crimeIDL){
        if (crimeIDL.isEmpty()){
            crimeIdET.setError("Crime ID cannot be empty");
            crimeIdET.requestFocus();
            return;
        }



        if ( statementL.isEmpty()){
            statementET.setError("Statement cannot be empty");
            statementET.requestFocus();
            return;
        }


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
