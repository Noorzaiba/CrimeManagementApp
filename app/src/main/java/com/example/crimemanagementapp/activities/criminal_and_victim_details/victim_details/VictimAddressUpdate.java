package com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details;

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
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VictimAddressUpdate extends AppCompatActivity {
    private EditText addressET,cityET,stateET,pincodeET,latitudeET,longitudeET,idET;
    private Button submitButton ;
    Intent intent;
    String fileName;
    String loggedInEmail,loggedInToken;
    int resident_id,publicUserAddressId;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_entries);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken= SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();


        this.addressET=findViewById(R.id.addressET);
        this.cityET=findViewById(R.id.cityET);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);
        this.submitButton = findViewById(R.id.submitButton);
        this.idET = findViewById(R.id.idET);

        intent=getIntent();
        fileName=intent.getStringExtra("fileName");
        resident_id=intent.getIntExtra("resident_id",0);
        Log.i("resident_id",String.valueOf(resident_id));
        getPublicUserAddress(resident_id);
        Log.i("TREE",String.valueOf(fileName.equals("AddressTransferActivity")));



        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePublicUserAddress();
            }
        });

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
    void updatePublicUserAddress(){
        String addressR=addressET.getText().toString().trim();
        String cityR=cityET.getText().toString().trim();
        String stateR=stateET.getText().toString().trim();
        String pincodeR=pincodeET.getText().toString().trim();
        String latitudeR=latitudeET.getText().toString().trim();
        String longitudeR=longitudeET.getText().toString().trim();
        int id=Integer.parseInt(idET.getText().toString());

        boolean test=validation(addressR,cityR,stateR,pincodeR,latitudeR,longitudeR);

        if(test==true){
            long pincode=Long.parseLong(pincodeR);
            double latitude=Double.parseDouble(latitudeR);
            double longitude=Double.parseDouble(longitudeR);
            publicUserAddressId=Integer.parseInt(idET.getText().toString());
            Log.i("id",String.valueOf(id));
            AddressObject address =new AddressObject(publicUserAddressId,addressR,cityR,stateR,longitude,latitude,pincode,resident_id);
            Call<AddressObjectDefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .putDetailVictimAddress(loggedInEmail,loggedInToken,resident_id,address);
            call.enqueue(new Callback<AddressObjectDefaultResponse>(){
                             @Override
                             public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                                 try{
                                     if(200==response.code()) {

                                         AddressObjectDefaultResponse res= response.body();
                                         AddressObject addressObjectLoaded= res.getSerailizedData().get(0);
                                         addressET.setText(addressObjectLoaded.getLocation());
                                         cityET.setText(addressObjectLoaded.getCity());
                                         stateET.setText(addressObjectLoaded.getState());
                                         pincodeET.setText(String.valueOf(addressObjectLoaded.getZipCode()));
                                         longitudeET.setText(String.valueOf(addressObjectLoaded.getLongitude()));
                                         latitudeET.setText(String.valueOf(addressObjectLoaded.getLongitude()));


                                   //      Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                                         if(res.isFlag()){

                                         }else{
                                             Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
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
                         }
            );


        }

    }
    void getPublicUserAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailVictimAddress(loggedInEmail,loggedInToken,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                      //  Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        if(res.isFlag()){
                            AddressObject   obj= res.getSerailizedData().get(0);
                           // Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();
                            Log.i("in above ",String.valueOf(fileName.equals("AddressTransferActivity")));
                            idET.setText(String.valueOf(obj.getId()));
                            if(fileName.equals("AddressTransferActivity")){
                                AddressObject obj1=(AddressObject) intent.getSerializableExtra("addressObject");
                                String location=obj1.getLocation();
                                addressET.setText(location);
                                cityET.setText(obj1.getCity());
                                stateET.setText(obj1.getState());
                                pincodeET.setText(String.valueOf(obj1.getZipCode()));
                                longitudeET.setText(String.valueOf(obj1.getLongitude()));
                                latitudeET.setText(String.valueOf(obj1.getLongitude()));
                                Log.i("TREE in ",obj1.getLocation());
                                Log.i("TREE in ",obj1.getCity());
                            }else{
                                addressET.setText(obj.getLocation());
                                cityET.setText(obj.getCity());
                                stateET.setText(obj.getState());
                                pincodeET.setText(String.valueOf(obj.getZipCode()));
                                longitudeET.setText(String.valueOf(obj.getLongitude()));
                                latitudeET.setText(String.valueOf(obj.getLongitude()));
                            }



                          //  Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
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
