package com.example.crimemanagementapp.activities.Investigator_details.investigator_details_for_other_investigators;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.CrimeManagementOptions;
import com.example.crimemanagementapp.activities.Investigator_details.InvestigatorAddressRegister;
import com.example.crimemanagementapp.activities.Investigator_details.UpdateInvestigatorAddress;
import com.example.crimemanagementapp.activities.Investigator_details.UpdateInvestigatorProfile;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorAdministrativeInformationModel;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorDefaultResponse;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorRegisterModel;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestigatorDetailsForOtherInvestigators extends AppCompatActivity {
    private EditText firstNameET,lastNameET,achivementsET,emailIdET,phoneNumberET,genderET,positionET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET;
    private TextView dob;

    String loggedInEmail,loggedInToken;
    private  int investigatorId;

    ImageView imageIV;
    TextView imageTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_details_for_other_investigators);
        imageIV=findViewById(R.id.imageIV);

        imageTV=findViewById(R.id.imageTV);
        this.firstNameET=findViewById(R.id.firstNameET);

        this.lastNameET=findViewById(R.id.lastNameET);
        this.emailIdET=findViewById(R.id.emailIDET);
        this.phoneNumberET=findViewById(R.id.phoneNumberET);
        this.genderET=findViewById(R.id.genderET);
        this.positionET=findViewById(R.id.positionET);

        this.dob=findViewById(R.id.dateOfBirthTV);
        this.addressET=findViewById(R.id.addressET);
        this.cityET=findViewById(R.id.cityET);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);

        this.achivementsET=findViewById(R.id.achivementsET);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();

        getInvestigatorDetails();


    }








    void setTextInfo(InvestigatorRegisterModel invObj){
        String phoneNumberS=Long.toString(invObj.getPhone_no());
        String adhaarNoS=Long.toString(invObj.getAdhaar_no());
        Toast.makeText(getApplicationContext(),invObj.getEmail_id(),Toast.LENGTH_LONG).show();
        Picasso.get()
                .load(ApiContants.INVESTIGATOR+invObj.getProfile_image())
                .resize(50, 50)
                .centerCrop()
                .into(imageIV);

        firstNameET.setText(invObj.getFirst_name());
        lastNameET.setText(invObj.getLast_name());
        emailIdET.setText(invObj.getEmail_id());
        phoneNumberET.setText(phoneNumberS);
        dob.setText(invObj.getDob());
        genderET.setText(invObj.getGender());

        investigatorId=invObj.getId();
        getInvestigatorAdministrativeTypeDetail();
        getInvestigatorAddress(investigatorId);
    }
    void getInvestigatorDetails(){
        Intent i=getIntent();
        String email=i.getStringExtra("email");
        Call<InvestigatorDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getInvestigatorProfile(email,loggedInToken,loggedInEmail);

        call.enqueue(new Callback<InvestigatorDefaultResponse>() {
            @Override
            public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
                try{

                    if(response.code()==200){
                        InvestigatorDefaultResponse res=response.body();
                        Log.i("falag",String.valueOf(res.isFlag()));
                        if(res.isFlag()){
                            InvestigatorRegisterModel invObj=res.getSerailizedInvestigatorModel().get(0);
                          //  Toast.makeText(getApplicationContext(),String.valueOf(invObj.getEmail_id()),Toast.LENGTH_LONG).show();
                            setTextInfo(invObj);
                        }else{
                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }






    void getInvestigatorAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailInvestigatorAddress(loggedInEmail,loggedInToken,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                        AddressObject obj= res.getSerailizedData().get(0);
                      //  Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        if(res.isFlag()){
                          //  Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();
                            setAddressData(obj);

                        }else{
                            addressET.setText((obj.getLocation()));
                            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid address Id", Toast.LENGTH_LONG).show();
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

        addressET.setText(obj.getLocation());
        cityET.setText(obj.getCity());
        stateET.setText(obj.getState());
        pincodeET.setText(String.valueOf(obj.getZipCode()));
        longitudeET.setText(String.valueOf(obj.getLongitude()));
        latitudeET.setText(String.valueOf(obj.getLongitude()));
    }
    void getInvestigatorAdministrativeTypeDetail(){

        Call<InvestigatorDefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .getInvestigatorAdministrativeByEmailFaciltiy(loggedInEmail,loggedInToken,loggedInEmail);
        call.enqueue(new Callback<InvestigatorDefaultResponse>(){
            @Override
            public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
                try{
                    if(response.code()==200){
                        InvestigatorDefaultResponse responseObject=response.body();
                        if(responseObject.isFlag()){
                            List<InvestigatorAdministrativeInformationModel> investigatorAdministrativeInformationModels=responseObject.getSerailizedData();
                            InvestigatorAdministrativeInformationModel  obj=investigatorAdministrativeInformationModels.get(0);
                          //  Toast.makeText(getApplicationContext(),String.valueOf(obj.getPosition()),Toast.LENGTH_LONG).show();


                            positionET.setText(obj.getPosition());
                            achivementsET.setText(obj.getAchivements());

                        }else{

                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG);
                        }


                    }else{
                        Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_LONG);
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    protected void onStart() {
        super.onStart();
        Log.i("df",String.valueOf(SharedPrefManager.getInstance(this).isLogIn()));
        //if its not logged in redirect to login page
        if(!SharedPrefManager.getInstance(this).isLogIn()){
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

}
