package com.example.crimemanagementapp.activities.miscellaneous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details.InvestigatorAdministrativeInformationList;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details.InvestigatorAdministrativeInformationRegister;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details.InvestigatorAdministrativeInformationUpdate;
import com.example.crimemanagementapp.activities.Investigator_details.InvestigatorRegisterActivity;
import com.example.crimemanagementapp.activities.crime_reported_info.for_other_investigators.CrimeReportedList;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details.VictimRegister;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.activities.map.ThePermisionActivity;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.cases_information.CrimeList;
import com.example.crimemanagementapp.activities.cases_information.CrimeRegister;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalList;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalRegister;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details.VictimList;
import com.example.crimemanagementapp.activities.public_users_info.admin_level_public_user_info.UpdatePublicUserProfile;

public class MainActivity extends AppCompatActivity {

    private Button crimeReportedListButton,mapButton,maptheButton,addInvestigatorAdminButton,publicUserUpdateButton,listInvestigatorAdminButton,updateInvestigatorAdminButton,registerButton,victimRegisterButton,victimListButton,criminalListButton,criminalRegisterButton,crimeListButton,addCrimeButton,loginButton,investigatorRegisterButton,updateInvestigatorProfileButton,logoutButton,addStatementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerButton=findViewById(R.id.registerButton);
        this.loginButton=findViewById(R.id. loginButton);
        this.investigatorRegisterButton=findViewById(R.id.investigatorRegisterButton );
        this.investigatorRegisterButton=findViewById(R.id.investigatorRegisterButton );
        this.updateInvestigatorProfileButton=findViewById(R.id.updateInvestigatorProfileButton );
        this.logoutButton=findViewById(R.id.logoutButton );
        this.victimRegisterButton=findViewById(R.id.victimRegisterButton);
        this.addCrimeButton=findViewById(R.id.addCrimeButton);
        this.criminalListButton=findViewById(R.id.criminalListButton);
        this.victimListButton=findViewById(R.id.victimListButton);
        this.crimeListButton=findViewById(R.id.crimeListButton);
        this.criminalRegisterButton=findViewById(R.id.criminalRegisterButton);
        this.addInvestigatorAdminButton=findViewById(R.id.addInvestigatorAdminButton);
        this.updateInvestigatorAdminButton=findViewById(R.id.updateInvestigatorAdminButton);
        this.listInvestigatorAdminButton=findViewById(R.id.listInvestigatorAdminButton);
        this.publicUserUpdateButton=findViewById(R.id.publicUserUpdateButton);
        this.mapButton=findViewById(R.id.mapButton);
        this.maptheButton=findViewById(R.id.maptheButton);

        this.crimeReportedListButton=findViewById(R.id.crimeReportedListButton);




        this.crimeReportedListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CrimeReportedList.class);
                startActivity(intent);


            }
        });

        this.maptheButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ThePermisionActivity.class);
                startActivity(intent);


            }
        });
        this.mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, PermissionsActivity.class);
                startActivity(intent);


            }
        });
        this.publicUserUpdateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, UpdatePublicUserProfile.class);
                startActivity(intent);


            }
        });

        this.addInvestigatorAdminButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, InvestigatorAdministrativeInformationRegister.class);
                startActivity(intent);


            }
        });
        this.listInvestigatorAdminButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, InvestigatorAdministrativeInformationList.class);
                startActivity(intent);


            }
        });

        this.updateInvestigatorAdminButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, InvestigatorAdministrativeInformationUpdate.class);
                startActivity(intent);


            }
        });
        this.criminalRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CriminalRegister.class);
                startActivity(intent);


            }
        });
        this.criminalListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CriminalList.class);
                startActivity(intent);


            }
        });
        this.victimListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, VictimList.class);
                startActivity(intent);


            }
        });

        this.victimRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, VictimRegister.class);

                startActivity(intent);


            }
        });






        this.crimeListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CrimeList.class);
                startActivity(intent);


            }
        });



        this.addCrimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CrimeRegister.class);
                startActivity(intent);


            }
        });








        this.updateInvestigatorProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {



            }
        });

        this.logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {



        }});


        this.investigatorRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, InvestigatorRegisterActivity.class);
                startActivity(intent);


            }
        });


        this.loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });


    }





    }


