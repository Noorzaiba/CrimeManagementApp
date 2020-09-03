package com.example.crimemanagementapp.activities.public_users_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.model.public_user_details.PublicUserDefaultResponse;
import com.example.crimemanagementapp.model.public_user_details.PublicUserModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicUserDetailsActivity extends AppCompatActivity {
    private EditText firstNameET, lastNameET, emailIdET,idAddressET,genderET, phoneNumberET,dobET ,adhaarNoET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET;
    String publicUseEmailId,loggedInEmail;
    ImageView imageIV;
    TextView imageTV;
    int publicUserId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_user_details);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();

        imageIV=findViewById(R.id.imageIV);

        imageTV=findViewById(R.id.imageTV);
        this.addressET=findViewById(R.id.addressET);
        this.idAddressET = findViewById(R.id.idAddressET);
        this.cityET=findViewById(R.id.cityET);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);
        this.firstNameET = findViewById(R.id.firstNameET);
        this.lastNameET = findViewById(R.id.lastNameET);
        this.emailIdET = findViewById(R.id.emailIDET);
        this.genderET = findViewById(R.id.genderET);
        this.phoneNumberET = findViewById(R.id.phoneNumberET);
        this.adhaarNoET = findViewById(R.id.adhaarNoET);
        this.dobET = findViewById(R.id.dobET);





        getPublicUser();


    }


    void getPublicUser(){
        Intent i=getIntent();
        String publicUserEmail=i.getStringExtra("email");
        Log.i("in p",publicUserEmail);
        Call<PublicUserDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailPublicUser(loggedInEmail,publicUserEmail);

        call.enqueue(new Callback<PublicUserDefaultResponse>(){
            @Override
            public void onResponse(Call<PublicUserDefaultResponse> call, Response<PublicUserDefaultResponse> response) {

                try{

                    if(200==response.code()) {

                        PublicUserDefaultResponse res= response.body();
                        PublicUserModel obj= res.getSerailizedData().get(0);
                        if(res.isFlag()){
                          //  Toast.makeText(getApplicationContext(), obj.getEmail_id()+"HHH", Toast.LENGTH_LONG).show();
                            setPublicUserData(obj);


                        }else{
                            Toast.makeText(getApplicationContext(), obj.getEmail_id(), Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Technical Error", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PublicUserDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });





    }
    void setPublicUserData(PublicUserModel obj){
        Log.i("i","im here");
        publicUserId= obj.getId();
        publicUseEmailId=obj.getEmail_id();
        firstNameET.setText(obj.getFirst_name());
        lastNameET.setText(obj.getLast_name());
        emailIdET.setText(obj.getEmail_id());
        dobET.setText(obj.getDob());
        genderET.setText(obj.getGender());
        phoneNumberET.setText(String.valueOf(obj.getPhone_no()));
        adhaarNoET.setText(String.valueOf(obj.getAdhaar_no()));
        Picasso.get()
                .load(ApiContants.PUBLIC_USER+obj.getProfile_image())
                .resize(50, 50)
                .centerCrop()
                .into(imageIV);
        getPublicUserAddress(publicUserId);


    }







    void getPublicUserAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailPublicUserAddress(loggedInEmail,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                        AddressObject obj= res.getSerailizedData().get(0);
                    //    Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        if(res.isFlag()){
                           // Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();
                            setAddressData(obj);

                        }else{
                            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Address Id", Toast.LENGTH_LONG).show();
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
    void setAddressData(AddressObject obj){
        idAddressET.setText(String.valueOf(obj.getId()));
        addressET.setText(obj.getLocation());
        cityET.setText(obj.getCity());
        stateET.setText(obj.getState());
        pincodeET.setText(String.valueOf(obj.getZipCode()));
        longitudeET.setText(String.valueOf(obj.getLongitude()));
        latitudeET.setText(String.valueOf(obj.getLongitude()));
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
