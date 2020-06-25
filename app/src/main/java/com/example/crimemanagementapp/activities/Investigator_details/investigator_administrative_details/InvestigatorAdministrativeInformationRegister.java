package com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details;

import android.os.Bundle;
import android.util.Log;
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

public class InvestigatorAdministrativeInformationRegister  extends AppCompatActivity {
    private EditText salaryET,achivementsET,postionET,emailET;
    private Button registerButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_administrative_information_register);
        salaryET=findViewById(R.id.salaryET);
        achivementsET=findViewById(R.id.achivementsET);
        postionET=findViewById(R.id.positionET);
        emailET=findViewById(R.id.emailIdET);
        registerButton=findViewById(R.id.registerButton);
        this.registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerDetails();

            }
        });








    }
    boolean validation(String salaryR,String positionR,String achivementsR,String emailR){
        if (!Patterns.EMAIL_ADDRESS.matcher(emailR).matches()){
            emailET.setError("Invalid Email address ");
            emailET.requestFocus();
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

    void registerDetails(){
        String salaryR=salaryET.getText().toString().trim();
        String achivementsR=achivementsET.getText().toString().trim();
        String positionR=postionET.getText().toString().trim();
        String emailR=emailET.getText().toString().trim();
        boolean test=validation(salaryR,positionR,achivementsR,emailR);



        if(test==true){
            InvestigatorAdministrativeInformationModel obj=new InvestigatorAdministrativeInformationModel(Double.parseDouble(salaryR),achivementsR, positionR,  emailR);
           Log.i("obj",obj.toString());
            Call<InvestigatorDefaultResponse>call= RetrofitClient
                    .getInstance()
                    .getApi()
                    .createInvestigatorAdministrativeFaciltiy(obj);

            call.enqueue(new Callback<InvestigatorDefaultResponse>(){
                @Override
                public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
                    try{
                        if(response.code()==200){

                            InvestigatorDefaultResponse responseObject=response.body();
                                Toast.makeText(InvestigatorAdministrativeInformationRegister.this,String.valueOf(responseObject.isFlag())+"jfkfj",Toast.LENGTH_LONG).show();
                                if(responseObject.isFlag()){
                                List<InvestigatorAdministrativeInformationModel> investigatorAdministrativeInformationModels=responseObject.getSerailizedData();
                                InvestigatorAdministrativeInformationModel  obj=investigatorAdministrativeInformationModels.get(0);
                                if(String.valueOf(obj.getEmail()).equals(emailR)){
                                    Toast.makeText(InvestigatorAdministrativeInformationRegister.this,String.valueOf(obj.getEmail()),Toast.LENGTH_LONG).show();
                                }else if(obj.getEmail().equals("1@gmail.com")){
                                    Toast.makeText(InvestigatorAdministrativeInformationRegister.this,"Email id doesnot exists in records",Toast.LENGTH_LONG).show();
                                }else if(obj.getEmail().equals("2@gmail.com")){
                                    Toast.makeText(InvestigatorAdministrativeInformationRegister.this,"Email already exists in Table",Toast.LENGTH_LONG).show();
                                }



                            }else{
                               Toast.makeText(InvestigatorAdministrativeInformationRegister.this,"Error Occured",Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(InvestigatorAdministrativeInformationRegister.this,"Technical Error",Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){

                        Toast.makeText(InvestigatorAdministrativeInformationRegister.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                    Toast.makeText(InvestigatorAdministrativeInformationRegister.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });


        }else{
            Toast.makeText(InvestigatorAdministrativeInformationRegister.this,"validation pending",Toast.LENGTH_LONG).show();
        }

    }


}
