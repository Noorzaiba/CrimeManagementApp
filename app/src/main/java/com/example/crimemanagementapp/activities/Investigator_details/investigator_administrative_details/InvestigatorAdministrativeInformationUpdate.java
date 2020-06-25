package com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorAdministrativeInformationModel;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorDefaultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestigatorAdministrativeInformationUpdate extends AppCompatActivity {
    private EditText salaryET,idET,achivementsET,postionET,emailIdET;
    private Button updateButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_administrative_information_put);
        salaryET=findViewById(R.id.salaryET);
        idET=findViewById(R.id.idET);
        achivementsET=findViewById(R.id.achivementsET);
        postionET=findViewById(R.id.positionET);
        emailIdET=findViewById(R.id.emailIdET);
        updateButton=findViewById(R.id.updateButton);
        getDetails();
        this.updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                putDetails();

            }
        });

    }


    boolean validation(String salaryR,String positionR,String achivementsR,String emailR){
        if (!Patterns.EMAIL_ADDRESS.matcher(emailR).matches()){
            emailIdET.setError("Invalid Email address ");
            emailIdET.requestFocus();
            return false;

        }

        if (salaryR.isEmpty()){
            salaryET.setError("Salary is required");
            salaryET.requestFocus();
            return false;
        }

        if (positionR.isEmpty()){
            postionET.setError("position is required");
            postionET.requestFocus();
            return false;
        }
        if (achivementsR.isEmpty()){
            achivementsET.setError("state is required");
            achivementsET.requestFocus();
            return false;
        }



        return true;

    }
    void getDetails(){

        Intent i= getIntent();
        final int pk=i.getIntExtra("pk",0);
        Call<InvestigatorDefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .getInvestigatorAdministrativeFaciltiy(pk);
        call.enqueue(new Callback<InvestigatorDefaultResponse>(){
            @Override
            public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {

                    try{
                        if(response.code()==200){
                            InvestigatorDefaultResponse responseObject=response.body();
                            if(responseObject.isFlag()){
                                List<InvestigatorAdministrativeInformationModel> investigatorAdministrativeInformationModels=responseObject.getSerailizedData();
                                InvestigatorAdministrativeInformationModel  obj=investigatorAdministrativeInformationModels.get(0);
                                Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,String.valueOf(obj.getPosition()),Toast.LENGTH_LONG).show();
                                String id=String.valueOf(obj.getId());
                                String salary=String.valueOf(obj.getSalary());
                                idET.setText(id);
                                emailIdET.setText(obj.getEmail());
                                salaryET.setText(salary);
                                postionET.setText(obj.getPosition());
                                achivementsET.setText(obj.getAchivements());

                            }else{

                                Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,"Error Occured",Toast.LENGTH_LONG);
                            }


                        }else{
                            Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,"Technical Error",Toast.LENGTH_LONG);
                        }

                    }catch (Exception e){
                        Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,e.getMessage(),Toast.LENGTH_LONG);
                    }




            }

            @Override
            public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,t.getMessage(),Toast.LENGTH_LONG);
            }
        });


    }

    void putDetails(){

        String salaryR=salaryET.getText().toString().trim();
        String achivementsR=achivementsET.getText().toString().trim();
        String positionR=postionET.getText().toString().trim();
        String emailR=emailIdET.getText().toString().trim();
        String idR=idET.getText().toString().trim();
        boolean test=validation(salaryR,positionR,achivementsR,emailR);

        if(test==true){


            InvestigatorAdministrativeInformationModel obj=new InvestigatorAdministrativeInformationModel(Integer.parseInt(idR),Double.parseDouble(salaryR),achivementsR, positionR,  emailR);
            Call<InvestigatorDefaultResponse> call= RetrofitClient
                    .getInstance()
                    .getApi()
                    .putInvestigatorAdministrativeFaciltiy(Integer.parseInt(idR),obj);

            call.enqueue(new Callback<InvestigatorDefaultResponse>(){
                @Override
                public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
                    try{
                        if(response.code()==200){

                            InvestigatorDefaultResponse responseObject=response.body();
                            Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,String.valueOf(responseObject.isFlag())+"jfkfj",Toast.LENGTH_LONG).show();
                            if(responseObject.isFlag()){
                                List<InvestigatorAdministrativeInformationModel> investigatorAdministrativeInformationModels=responseObject.getSerailizedData();
                                InvestigatorAdministrativeInformationModel  obj=investigatorAdministrativeInformationModels.get(0);

                                Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,"Successfully Updated",Toast.LENGTH_LONG).show();


                            }else{
                                Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,"Error Occured",Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,"Technical Error",Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){

                        Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                    Toast.makeText(InvestigatorAdministrativeInformationUpdate.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });




        }else{

        }
    }




}
