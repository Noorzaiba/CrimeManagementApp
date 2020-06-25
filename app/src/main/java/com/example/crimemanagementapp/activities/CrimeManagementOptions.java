package com.example.crimemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.UpdateInvestigatorProfile;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LogoutActivity;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_details_for_other_investigators.InvestigatorListForOtherInvestigators;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_password_reset.OTPVerficationForPasswordReset;
import com.example.crimemanagementapp.activities.Investigator_details.query_report_facilitiy_for_investigator.ContactUsRegisterActivity;
import com.example.crimemanagementapp.activities.cases_information.CrimeList;
import com.example.crimemanagementapp.activities.cases_information.CrimeRegister;
import com.example.crimemanagementapp.activities.crime_reported_info.for_other_investigators.CrimeReportedList;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalList;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalRegister;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details.VictimList;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details.VictimRegister;
import com.example.crimemanagementapp.activities.public_users_info.PublicUserList;
import com.example.crimemanagementapp.storage.SharedPrefManager;

public class CrimeManagementOptions extends AppCompatActivity {
CardView crime_list;
CardView crime_register;
CardView victim_list;
CardView victim_register;
CardView criminal_list;
CardView criminal_register;
CardView public_user_list;
CardView crime_reported_list;
CardView investigator_details_list;
Button adminMainMenuButton;
    String loggedInEmail;
    boolean isSuperuser;
    boolean isStaff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_management_options);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();

        this.crime_list=findViewById(R.id.crime_list);
        this.investigator_details_list=findViewById(R.id.investigator_details_list);
        this.victim_list=findViewById(R.id.victim_list);
        this.crime_register=findViewById(R.id.crime_register);
        this.victim_register=findViewById(R.id.victim_register);
        this.criminal_list=findViewById(R.id.criminal_list);
        this.criminal_register=findViewById(R.id.criminal_register);
        this.public_user_list=findViewById(R.id.public_user_list);
        this.crime_reported_list=findViewById(R.id.crime_reported_list);
        this.adminMainMenuButton=findViewById(R.id.adminMainMenuButton);
        isSuperuser=SharedPrefManager.getInstance(getApplicationContext()).getUser().isSuperuser();
        isStaff=SharedPrefManager.getInstance(getApplicationContext()).getUser().isStaff();
         if(isSuperuser &&  isStaff){
             adminMainMenuButton.setVisibility(View.VISIBLE);
         }else{
             adminMainMenuButton.setVisibility(View.INVISIBLE);
         }


        adminMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), AdminMainMenuActivity.class);
                startActivity(intent1);
            }
        });

        investigator_details_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), InvestigatorListForOtherInvestigators.class);
                startActivity(intent1);
            }
        });
        crime_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), CrimeList.class);
                startActivity(intent1);
            }
        });
        victim_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(getApplicationContext(), VictimList.class);
                startActivity(intent5);
            }
        });
        crime_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getApplicationContext(), CrimeRegister.class);
                startActivity(intent2);
            }
        });
        victim_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(getApplicationContext(), VictimRegister.class);
                startActivity(intent6);
            }
        });
        criminal_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(getApplicationContext(), CriminalList.class);
                startActivity(intent3);
            }
        });
        criminal_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(getApplicationContext(), CriminalRegister.class);
                startActivity(intent4);
            }
        });
        public_user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7=new Intent(getApplicationContext(), PublicUserList.class);
                startActivity(intent7);
            }
        });
        crime_reported_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7=new Intent(getApplicationContext(), CrimeReportedList.class);
                startActivity(intent7);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.view_my_profile:
                Intent intent1=new Intent(getApplicationContext(), UpdateInvestigatorProfile.class);
                startActivity(intent1);

                return true;
            case R.id.password_reset:
                Intent intent2=new Intent(getApplicationContext(), OTPVerficationForPasswordReset.class);
                startActivity(intent2);
                return true;
            case R.id.contact_us:
                Intent intent3=new Intent(getApplicationContext(), ContactUsRegisterActivity.class);
                startActivity(intent3);
                return true;
            case R.id.logout:
                Intent intent4=new Intent(getApplicationContext(), LogoutActivity.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
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
