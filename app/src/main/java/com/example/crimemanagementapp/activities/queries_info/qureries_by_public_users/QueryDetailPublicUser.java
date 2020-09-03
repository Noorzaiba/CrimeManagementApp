package com.example.crimemanagementapp.activities.queries_info.qureries_by_public_users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedByPublicModel;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.DeleteObject;
import com.example.crimemanagementapp.model.query_reporting_contact_us.InvestigatorContactUsDefaultResponse;
import com.example.crimemanagementapp.model.query_reporting_contact_us.InvestigatorContactUsModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryDetailPublicUser extends AppCompatActivity {
    EditText descriptionET, idET,statusET, userET,docET,douET;
    Button saveButton,backButton,deleteButton;
    int pk;
    String loggedInEmail,loggedInToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_detail_public_user);
        this.idET=findViewById(R.id.idET);
        this.descriptionET=findViewById(R.id.descriptionET);
        this.statusET=findViewById(R.id.statusET);
        this.userET=findViewById(R.id.userET);
        this.docET=findViewById(R.id.docET);
        this.douET=findViewById(R.id.douET);
        this.saveButton=findViewById(R.id.saveButton);
        this.backButton=findViewById(R.id.backButton);
        this.deleteButton=findViewById(R.id.deleteButton);
        loggedInToken= SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();

        getQueryDetails();

        this.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              deleteQueryRecord();
            }
        });
        this.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               Intent i=new Intent(getApplicationContext(),QueryListPublicUser.class);
               startActivity(i);
               finish();

            }
        });
        this.saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                putQueryDetails();
            }
        });

    }
    void deleteQueryRecord(){
        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .deleteQueryFacilityOfPublicUser(loggedInEmail,loggedInToken,Integer.valueOf(idET.getText().toString()));

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject d=response.body();
                    if(d.isFlag()) {
                        Intent i=new Intent(getApplicationContext(),QueryListPublicUser.class);
                        startActivity(i);
                        finish();

                    }
                    else{ Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();}



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
    void getQueryDetails() {
        Intent i= getIntent();
        pk=i.getIntExtra("pk",0);

        Call<InvestigatorContactUsDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllDetailQueryFacilityOfPublicUser(loggedInEmail,loggedInToken,pk);
        call.enqueue(new Callback<InvestigatorContactUsDefaultResponse>(){
            @Override
            public void onResponse(Call<InvestigatorContactUsDefaultResponse> call, Response<InvestigatorContactUsDefaultResponse> response) {
                try{
                    if(200==response.code()) {
                        InvestigatorContactUsDefaultResponse res = response.body();
                        if(res.isFlag()){
                          //  Toast.makeText(getApplicationContext(),String.valueOf(res.isFlag()),Toast.LENGTH_LONG).show();
                            List<InvestigatorContactUsModel> obj_list = res.getSerailizedData();
                            InvestigatorContactUsModel obj=obj_list.get(0);
                            String id=String.valueOf(obj.getId());
                            idET.setText(id);
                            descriptionET.setText(obj.getDescription());
                            statusET.setText(obj.getStatus());
                            userET.setText(obj.getEmail());
                            docET.setText(obj.getDoc());
                            douET.setText(obj.getDou());

                            //Toast.makeText(getApplicationContext(),obj.getDescription(),Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<InvestigatorContactUsDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }

    void putQueryDetails(){
        String descriptionL=descriptionET.getText().toString();
        String statusL=statusET.getText().toString();
        String userEmail=userET.getText().toString();
        int id=Integer.parseInt(idET.getText().toString());


        boolean test= validation(descriptionL,statusL,userEmail);
        if(test==true){


            InvestigatorContactUsModel obj=new  InvestigatorContactUsModel(descriptionL,id,userEmail,statusL);
            Call<InvestigatorContactUsDefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .putAllDetailQueryFacilityOfInvestigator(loggedInEmail,loggedInToken,pk,obj);

            call.enqueue(new Callback<InvestigatorContactUsDefaultResponse>() {
                @Override
                public void onResponse(Call<InvestigatorContactUsDefaultResponse> call, Response<InvestigatorContactUsDefaultResponse> response) {

                    try {

                        if (200 == response.code()) {

                            InvestigatorContactUsDefaultResponse res=response.body();

                            if(res.isFlag()){
                                List<InvestigatorContactUsModel>  obj_list=  res.getSerailizedData();
                                InvestigatorContactUsModel obj=obj_list.get(0);
                               // Toast.makeText(getApplicationContext(),obj.getStatus(),Toast.LENGTH_LONG).show();
                                if("Invalid Email Id".equals(obj.getDescription())){
                                    Toast.makeText(getApplicationContext(),"Email id does not exists ",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                                }
                            }else{

                                if("IdDoesNotExists".equals(obj.getDescription())){
                                    Toast.makeText(getApplicationContext(),"id does not exists ",Toast.LENGTH_LONG).show();
                                }
                                Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();
                            }


                        }else{
                            Toast.makeText(getApplicationContext(),"Authentication Occured",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show(); }
                }




                @Override
                public void onFailure(Call<InvestigatorContactUsDefaultResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }else{ Toast.makeText(getApplicationContext(),"Validation Error",Toast.LENGTH_LONG).show();}



    }
    boolean validation(String descriptionL, String statusL,String userL) {


        if (descriptionL.isEmpty()) {
            descriptionET.setError("Description cannot be empty");
            descriptionET.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userL).matches()){
            userET.setError("Invalid Email address ");
            userET.requestFocus();
            return false;
        }
        if (statusL.isEmpty()) {
            statusET.setError("Status cannot be empty");
            statusET.requestFocus();
            return false;
        }
        return true;

    }
}
