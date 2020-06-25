package com.example.crimemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.admin_level_investigator_details.InvestigatorList;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details.InvestigatorAdministrativeInformationList;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details.InvestigatorAdministrativeInformationRegister;
import com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details.CrimeReportedListForAdmin;
import com.example.crimemanagementapp.activities.public_users_info.admin_level_public_user_info.PublicUserListForAdmin;
import com.example.crimemanagementapp.activities.queries_info.queries_by_investigators.QueryListInvesitgator;
import com.example.crimemanagementapp.activities.queries_info.qureries_by_public_users.QueryListPublicUser;

public class AdminMainMenuActivity extends AppCompatActivity {
    CardView investigator_list;
    CardView investigator_admin_list;
    CardView public_user_list;
    CardView investigator_admin_register;
    CardView crime_reported_list;
    CardView contact_us_list_of_investigator;
    CardView contact_us_list_of_public_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.investigator_list=findViewById(R.id.investigator_list);
        this.investigator_admin_list=findViewById(R.id.investigator_admin_list);
        this.public_user_list=findViewById(R.id.public_user_list);
        this.investigator_admin_register=findViewById(R.id.investigator_admin_register);
        this.crime_reported_list=findViewById(R.id.crime_reported_list);
        this.contact_us_list_of_investigator=findViewById(R.id.contact_us_list_of_investigator);
        this.contact_us_list_of_public_user=findViewById(R.id.contact_us_list_of_public_user);

        contact_us_list_of_investigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), QueryListInvesitgator.class);
                startActivity(intent1);
            }
        });
        contact_us_list_of_public_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), QueryListPublicUser.class);
                startActivity(intent1);
            }
        });
        crime_reported_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), CrimeReportedListForAdmin.class);
                startActivity(intent1);
            }
        });

        investigator_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), InvestigatorList.class);
                startActivity(intent1);
            }
        });
        investigator_admin_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(getApplicationContext(), InvestigatorAdministrativeInformationList.class);
                startActivity(intent5);
            }
        });
        public_user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getApplicationContext(), PublicUserListForAdmin.class);
                startActivity(intent2);
            }
        });
        investigator_admin_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(getApplicationContext(), InvestigatorAdministrativeInformationRegister.class);
                startActivity(intent6);
            }
        });
    }


}
