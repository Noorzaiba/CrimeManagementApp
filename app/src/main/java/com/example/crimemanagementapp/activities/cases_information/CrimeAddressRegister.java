package com.example.crimemanagementapp.activities.cases_information;

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
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeAddressRegister extends AppCompatActivity {

    private EditText addressET,cityET,stateET,pincodeET,latitudeET,longitudeET;
    private Button submitButton ;
    private  int resident_id;
    private Intent intent;
    private String fileName;
    private TextView operationTV;
    String loggedInEmail,loggedInToken;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_reported_address_post);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        this.operationTV=findViewById(R.id.operationTV);
        this.addressET=findViewById(R.id.addressET);
        this.cityET=findViewById(R.id.cityET);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);

        this.submitButton = findViewById(R.id.submitButton);
        this.operationTV.setText("Register Address");


        intent=getIntent();
        fileName=intent.getStringExtra("fileName");
        resident_id=intent.getIntExtra("resident_id",0);
        Log.i("id",String.valueOf(resident_id));
        if(fileName.equals("AddressTransferActivity")){
            AddressObject addressObject=(AddressObject) intent.getSerializableExtra("addressObject");
            String pincodeS=String.valueOf(addressObject.getZipCode());
            String latitudeS=String.valueOf(addressObject.getLatitude());
            String longitudeS=String.valueOf(addressObject.getLongitude());
            addressET.setText(addressObject.getLocation());
            cityET.setText((addressObject.getCity()));
            stateET.setText((addressObject.getState()));
            longitudeET.setText(longitudeS);
            latitudeET.setText(latitudeS);
            pincodeET.setText(pincodeS);


        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();

            }
        });





    }


    void submitData(){
        String addressR=addressET.getText().toString().trim();
        String cityR=cityET.getText().toString().trim();
        String stateR=stateET.getText().toString().trim();
        String pincodeR=pincodeET.getText().toString().trim();
        String latitudeR=latitudeET.getText().toString().trim();
        String longitudeR=longitudeET.getText().toString().trim();

        boolean test=validation(addressR,cityR,stateR,pincodeR,latitudeR,longitudeR);


        if(test==true){
            long pincode=Long.parseLong(pincodeR);
            double latitude=Double.parseDouble(latitudeR);
            double longitude=Double.parseDouble(longitudeR);
            AddressObject addressObject=new AddressObject(addressR,cityR,stateR,longitude,latitude,pincode,resident_id);
            post_data(addressObject);

        }else{
            Toast.makeText(getApplicationContext(),"Validation pending",Toast.LENGTH_LONG).show();
        }

    }
    boolean validation(String addressR,String cityR,String stateR,String pincodeR,String latitudeR,String longitudeR){



        if (addressR.isEmpty()){
            addressET.setError("Address is required");
            addressET.requestFocus();
            return false;
        }
        if (cityR.isEmpty()){
            cityET.setError("City is required");
            cityET.requestFocus();
            return false;
        }

        if (stateR.isEmpty()){
            stateET.setError("state is required");
            stateET.requestFocus();
            return false;
        }

        if (pincodeR.isEmpty()){
            pincodeET.setError("Pin code is required");
            pincodeET.requestFocus();
            return false;
        }
        if (latitudeR.isEmpty()){
            latitudeET.setError("Latitude is required");
            latitudeET.requestFocus();
            return false;
        }

        if (longitudeR.isEmpty()){
            longitudeET.setError("Longitude is required");
            longitudeET.requestFocus();
            return false;
        }


        return true;
    }

    void post_data(AddressObject addressObject){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .crimeAddressRegisterFacility(loggedInEmail,loggedInToken,addressObject);


        call.enqueue(new Callback<AddressObjectDefaultResponse>() {
            @Override
            public void onResponse(Call< AddressObjectDefaultResponse> call, Response< AddressObjectDefaultResponse> response) {


                try {

                    if (200 == response.code()) {

                        AddressObjectDefaultResponse res=response.body();
                        AddressObject obj=res.getSerailizedData().get(0);

                      //  Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()),Toast.LENGTH_LONG).show();


                        Intent i=new Intent(getApplicationContext(),CrimePut.class);
                        i.putExtra("pk",obj.getResidentId());
                                  startActivity(i);
                                  finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show(); }
            }



            @Override
            public void onFailure(Call< AddressObjectDefaultResponse> call, Throwable t) {
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

