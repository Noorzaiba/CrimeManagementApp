package com.example.crimemanagementapp.activities.cases_information.crime_live_updates_details;

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
import com.example.crimemanagementapp.activities.cases_information.CrimePut;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.cases_information.CrimeDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeLiveUpdationModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeLiveUpdatePost extends AppCompatActivity {
    EditText crimeIdET,statementET;
    Button submitButton ,backButton;
    String loggedInEmail,loggedInToken;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_live_update_post);
        crimeIdET=findViewById(R.id.crimeIdET);
        statementET=findViewById(R.id.statementET);
        submitButton=findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        Intent intent= getIntent();
        final String crime_id=intent.getStringExtra("crime_id");
        this.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), CrimePut.class);
                i.putExtra("pk",Integer.parseInt(crime_id));
                startActivity(i);
                finish();


            }
        });
        crimeIdET.setText(crime_id);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                submitData();


            }
        });



    }
    protected void submitData(){

        String statement=statementET.getText().toString().trim();
        String crimeID=crimeIdET.getText().toString().trim();
        validation(crimeID,statement);
        int crimeIDNumber=Integer.parseInt(crimeIdET.getText().toString().trim());
        posting_data(crimeIDNumber,statement);



    }

     void validation(String crimeIDL,String statementL){
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

  void posting_data(int crimeIDNumberL,String statementL){
      CrimeLiveUpdationModel crimeLiveUpdationModel =new CrimeLiveUpdationModel(crimeIDNumberL,statementL);

      Call<CrimeDefaultResponse> call= RetrofitClient
              .getInstance()
              .getApi()
              .crimeLiveUpdatePostFacility(loggedInEmail,loggedInToken,crimeLiveUpdationModel);
      call.enqueue(new Callback<CrimeDefaultResponse>() {
          @Override
          public void onResponse(Call<CrimeDefaultResponse> call, Response<CrimeDefaultResponse> response) {
              try {

                  if (200 == response.code()) {

                      CrimeDefaultResponse res=response.body();


                    if(res.isFlag())  {

                        List<CrimeLiveUpdationModel> obj_list = res.getSerialized_data_crime_live_update();
                        CrimeLiveUpdationModel crimeLiveUpdationModelResponse=obj_list.get(0);
                        crimeLiveUpdationModelResponse.getComments();
                        String num=String.valueOf(crimeLiveUpdationModelResponse.getId());
                        //Toast.makeText(CrimeLiveUpdatePost.this,num+"djfkj",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),CrimePut.class);
                        i.putExtra("pk",Integer.parseInt(crimeIdET.getText().toString()));
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(CrimeLiveUpdatePost.this,"error occured",Toast.LENGTH_LONG).show();
                    }




                  }else{
                      Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                  }
              }catch (Exception e){  Toast.makeText(CrimeLiveUpdatePost.this,e.getMessage()+"djfkj",Toast.LENGTH_LONG).show(); }
          }

          @Override
          public void onFailure(Call<CrimeDefaultResponse> call, Throwable t) {
              Toast.makeText(CrimeLiveUpdatePost.this,t.getMessage()+"djfkj",Toast.LENGTH_LONG).show();
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
    }}