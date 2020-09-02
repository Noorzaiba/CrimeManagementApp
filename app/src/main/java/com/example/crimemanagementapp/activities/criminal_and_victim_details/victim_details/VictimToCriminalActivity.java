package com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;

import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalUpdate;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalDefaultResponse;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalRegisterModel;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.DeleteObject;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VictimToCriminalActivity extends AppCompatActivity {
      Intent i;
      int victim_id_received;
    String loggedInEmail,loggedInToken;
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_to_criminal_or_criminal_to_victim_shifting);
          loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
          loggedInToken= SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();


          i=getIntent();
        victim_id_received=Integer.valueOf(i.getStringExtra("pk"));

       getVictimDetails();

    }

    void registerVictimToCriminal(VictimCriminalRegisterModel victimCriminalRegisterModel){




        Call<VictimCriminalDefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .criminalRegisterFacility(loggedInEmail,loggedInToken,victimCriminalRegisterModel);


        call.enqueue(new Callback<VictimCriminalDefaultResponse>(){
            @Override
            public void onResponse(Call<VictimCriminalDefaultResponse> call, Response<VictimCriminalDefaultResponse> response) {

                try{

                    if(response.code()==200){

                        VictimCriminalDefaultResponse res=response.body();
                        if(res.isFlag()){

                            List<VictimCriminalRegisterModel> victimCriminalRegisterModelList=res.getVictimCriminalRegisterModelList();
                          //  Toast.makeText(getApplicationContext(),String.valueOf(victimCriminalRegisterModelList.get(0).getId()),Toast.LENGTH_LONG).show();
                            getVictimAddress(Integer.valueOf(victimCriminalRegisterModelList.get(0).getId()));
                        }else{
                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
                        }



                    }else{
                        Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){

                    Log.i("error on catch",e.toString());
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<VictimCriminalDefaultResponse> call, Throwable t) {
                Log.i("error on failure",t.toString());
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    void victimDelete(int newCriminalValue){

        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .victimDeleteFacility(loggedInEmail,loggedInToken,victim_id_received);

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject obj=response.body();

                    if(obj.isFlag()){
                        //victimAddressDelete();
                        Intent i = new Intent(getApplicationContext(), CriminalUpdate.class);
                        i.putExtra("pk", newCriminalValue);
                        startActivity(i);

                    } else{
                        Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();}



                }else{
                    Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onFailure(Call<DeleteObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });






    }

    void victimAddressDelete(){

        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .deleteVictimAddress(loggedInEmail,loggedInToken,victim_id_received);

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject obj=response.body();

                    if(obj.isFlag()){

                    } else{
                        Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();}



                }else{
                    Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onFailure(Call<DeleteObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });






    }


    void registerVictimAddressToCriminalAddress(AddressObject obj,int newCriminalId){


        AddressObject newAddressObject=new AddressObject(obj.getLocation(),obj.getCity(),obj.getState(),obj.getLongitude(),obj.getLatitude(),obj.getZipCode(),newCriminalId);
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .criminalAddressRegisterFacility(loggedInEmail,loggedInToken,newAddressObject);


        call.enqueue(new Callback<AddressObjectDefaultResponse>() {
            @Override
            public void onResponse(Call< AddressObjectDefaultResponse> call, Response< AddressObjectDefaultResponse> response) {


                try {

                    if (200 == response.code()) {

                        AddressObjectDefaultResponse res=response.body();
                        AddressObject obj=res.getSerailizedData().get(0);
                        victimDelete(newCriminalId);
                       // Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()),Toast.LENGTH_LONG).show();


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

   void getVictimDetails() {


        Call<VictimCriminalDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .victimGETDetailFacility(loggedInEmail,loggedInToken,victim_id_received);

        call.enqueue(new Callback<VictimCriminalDefaultResponse>() {
            @Override
            public void onResponse(Call<VictimCriminalDefaultResponse> call, Response<VictimCriminalDefaultResponse> response) {

                try{

                    if(response.code()==200){
                        VictimCriminalDefaultResponse res=response.body();
                        if(res.isFlag()){
                            VictimCriminalRegisterModel victimCriminalRegisterModel=res.getVictimCriminalRegisterModelList().get(0);

                            registerVictimToCriminal(victimCriminalRegisterModel);


                        }else{


                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();

                        }


                    }else{
                        Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<VictimCriminalDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    void getVictimAddress(int newCriminalId){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailVictimAddress(loggedInEmail,loggedInToken,victim_id_received);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                      //  Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        if(res.isFlag()){
                            AddressObject   obj= res.getSerailizedData().get(0);
                            registerVictimAddressToCriminalAddress(obj,newCriminalId);


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