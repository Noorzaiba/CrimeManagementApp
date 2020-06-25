package com.example.crimemanagementapp.activities.crime_reported_info.for_other_investigators;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.UpdateInvestigatorProfile;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.cases_information.CrimePut;
import com.example.crimemanagementapp.activities.cases_information.crime_live_updates_details.CrimeLiveUpdateList;
import com.example.crimemanagementapp.activities.crime_reported_info.for_other_investigators.crime_repoorted_scene_images_details.CrimeReportedSceneImagesListActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.cases_information.CrimeDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeRegisterModel;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedByPublicModel;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeReportedPut extends AppCompatActivity {
EditText idAddressET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET,descriptionET, idET,statusET,timeET,dateET, userET,typeOfCrimeET,docET,douET;
    Button saveButton,updateButton;
    Button viewAllPictures;
    int pk;
    String loggedInEmail;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_reported_put);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        this.addressET=findViewById(R.id.addressET);
        this.cityET=findViewById(R.id.cityET);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);
        this.idAddressET = findViewById(R.id.idAddressET);
        this.idET=findViewById(R.id.idET);
        this.descriptionET=findViewById(R.id.descriptionET);
        this.statusET=findViewById(R.id.statusET);
        this.timeET=findViewById(R.id.timeET);
        this.dateET=findViewById(R.id.dateET);
        this.userET=findViewById(R.id.userET);
        this.typeOfCrimeET=findViewById(R.id.typeOfCrimeET);
        this.docET=findViewById(R.id.docET);
        this.douET=findViewById(R.id.douET);
        this.saveButton=findViewById(R.id.saveButton);
        this.updateButton=findViewById(R.id.updateButton);
        this.viewAllPictures=findViewById(R.id.viewAllPictures);
        getCrimeReportedDetails();


        this.updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"you can update only status",Toast.LENGTH_LONG).show();
               statusET.requestFocus();

            }
        });
        this.saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                putCrimeDetails();
            }
        });

        viewAllPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), CrimeReportedSceneImagesListActivity.class);
                intent.putExtra("crime_id",idET.getText().toString());
                startActivity(intent);

            }
        });


    }


    void getCrimeReportedDetails() {
        Intent i= getIntent();
         pk=i.getIntExtra("pk",0);

        Call<CrimeReportedDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailCrimeReporedFacility(loggedInEmail,pk);
        call.enqueue(new Callback<CrimeReportedDefaultResponse>() {
            @Override
            public void onResponse(Call<CrimeReportedDefaultResponse> call, Response<CrimeReportedDefaultResponse> response) {
                try{
                    if(200==response.code()) {
                        CrimeReportedDefaultResponse res = response.body();
                        if(res.isFlag()){
                            Toast.makeText(getApplicationContext(),String.valueOf(res.isFlag()),Toast.LENGTH_LONG).show();
                            List<CrimeReportedByPublicModel> obj_list = res.getSerialized_data_crime_reported();
                            CrimeReportedByPublicModel obj=obj_list.get(0);
                            String id=String.valueOf(obj.getId());
                            idET.setText(id);
                            descriptionET.setText(obj.getDescription());
                            statusET.setText(obj.getStatus());
                            timeET.setText(obj.getTime());
                            dateET.setText(obj.getDate());
                            userET.setText(obj.getUser());
                            docET.setText(obj.getDoc());
                            douET.setText(obj.getDou());
                            typeOfCrimeET.setText(obj.getTypeOfCrime());
                            Toast.makeText(getApplicationContext(),obj.getDescription(),Toast.LENGTH_LONG).show();
                            getCrimeReportedAddress(obj.getId());
                        }else{
                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
                        }


                    }else{
                        Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();



                }
            }

            @Override
            public void onFailure(Call<CrimeReportedDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }


    void putCrimeDetails(){
        String descriptionL=descriptionET.getText().toString();
        String statusL=statusET.getText().toString();
        String dateL=dateET.getText().toString();
        String timeL=timeET.getText().toString();
        String userEmail=userET.getText().toString();
        String typeOfCrimeL=typeOfCrimeET.getText().toString();
        String docL=docET.getText().toString();
        String douL=douET.getText().toString();
        int crimeIDL=Integer.parseInt(idET.getText().toString());


      boolean test= validation(statusL);
      if(test==true){


          CrimeReportedByPublicModel obj=new  CrimeReportedByPublicModel(crimeIDL,descriptionL,typeOfCrimeL,timeL,dateL,docL,douL,statusL,userEmail);
          Call<CrimeReportedDefaultResponse> call = RetrofitClient
                  .getInstance()
                  .getApi()
                  . PutCrimeReporedFacility(loggedInEmail,pk,obj);

          call.enqueue(new Callback<CrimeReportedDefaultResponse>() {
              @Override
              public void onResponse(Call<CrimeReportedDefaultResponse> call, Response<CrimeReportedDefaultResponse> response) {

                  try {

                      if (200 == response.code()) {

                          CrimeReportedDefaultResponse res=response.body();
                          List<CrimeReportedByPublicModel>  obj_list=  res.getSerialized_data_crime_reported();
                          CrimeReportedByPublicModel crimeReportedByPublicModel=obj_list.get(0);

                          Toast.makeText(getApplicationContext(),crimeReportedByPublicModel.getStatus(),Toast.LENGTH_LONG).show();
                          if("userDoesExists@gmail.com".equals(crimeReportedByPublicModel.getDescription())){
                              Toast.makeText(getApplicationContext(),"Invalid user email id",Toast.LENGTH_LONG).show();
                          }else{
                              Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                          }


                      }else{
                          Toast.makeText(getApplicationContext(),"Authentication Occured",Toast.LENGTH_LONG).show();
                      }
                  }catch (Exception e){
                      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show(); }
              }




              @Override
              public void onFailure(Call<CrimeReportedDefaultResponse> call, Throwable t) {
                  Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

              }
          });


      }else{ Toast.makeText(getApplicationContext(),"Validation Error",Toast.LENGTH_LONG).show();}



    }
   boolean validation( String statusL) {




        if (statusL.isEmpty()) {
            statusET.setError("Status cannot be empty");
            statusET.requestFocus();
            return false;
        }
       return true;

    }
    void getCrimeReportedAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailCrimeReportedAddress(loggedInEmail,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                        Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        if(res.isFlag()){
                            AddressObject obj= res.getSerailizedData().get(0);
                            Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();
                            idAddressET.setText(String.valueOf(obj.getId()));
                            addressET.setText(obj.getLocation());
                            cityET.setText(obj.getCity());
                            stateET.setText(obj.getState());
                            pincodeET.setText(String.valueOf(obj.getZipCode()));
                            longitudeET.setText(String.valueOf(obj.getLongitude()));
                            latitudeET.setText(String.valueOf(obj.getLongitude()));
                            Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Id", Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddressObjectDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
