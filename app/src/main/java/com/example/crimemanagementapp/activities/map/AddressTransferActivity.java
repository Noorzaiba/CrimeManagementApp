package com.example.crimemanagementapp.activities.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.InvestigatorAddressRegister;
import com.example.crimemanagementapp.activities.Investigator_details.UpdateInvestigatorAddress;
import com.example.crimemanagementapp.activities.Investigator_details.admin_level_investigator_details.InvestigatorAddressPut;
import com.example.crimemanagementapp.activities.Investigator_details.admin_level_investigator_details.InvestigatorAddressRegisterForAdmin;
import com.example.crimemanagementapp.activities.cases_information.CrimeAddressPut;
import com.example.crimemanagementapp.activities.cases_information.CrimeAddressRegister;
import com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details.CrimeReportedUpdateAddressDetails;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalAddressRegister;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalAddressUpdate;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details.VictimAddressRegister;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details.VictimAddressUpdate;
import com.example.crimemanagementapp.activities.public_users_info.admin_level_public_user_info.PublicUserAddressRegister;
import com.example.crimemanagementapp.activities.public_users_info.admin_level_public_user_info.UpdatePublicUserAddress;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;

public class AddressTransferActivity extends AppCompatActivity {
    int resident_id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_object_transfer);
        Intent intent=getIntent();
        String fileName=intent.getStringExtra("fileName");
        resident_id=intent.getIntExtra("resident_id",0);
        Log.i("id",String.valueOf(resident_id));
        AddressObject addressObject=(AddressObject) intent.getSerializableExtra("addressObject");
        Log.i("id",String.valueOf(resident_id));
        Log.i("id in tran",fileName);
        Log.i("id in tran",String.valueOf(fileName.equals("CrimeReportedAddressRegister")));

        if(fileName.equals("InvestigatorAddressPut")){
            Log.i("in","InvestigatorAddressPut");
            Log.i("im resodemt",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), InvestigatorAddressPut.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }
        if(fileName.equals("CrimeReportedUpdateAddressDetails")){
            Log.i("in","CrimeReportedUpdateAddressDetails");
            Log.i("im resodemt",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), CrimeReportedUpdateAddressDetails.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }
        if(fileName.equals("PublicUserAddressRegister")){
            Log.i("in","PublicUserAddressRegister");
            Log.i("im resodemt",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), PublicUserAddressRegister.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);


        }
        if(fileName.equals("CrimeAddressRegister")){
            Log.i("in","CrimeAddressRegister");
            Log.i("im resodemt",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), CrimeAddressRegister.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }
        if(fileName.equals("CrimeAddressPut")){
            Log.i("in","CrimeAddressPut");
            Log.i("resident id",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), CrimeAddressPut.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }
        if(fileName.equals("CriminalAddressRegister")){
            Log.i("in","CriminalAddressRegister");
            Log.i("resident id",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), CriminalAddressRegister.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }
        if(fileName.equals("VictimAddressRegister")){
            Log.i("in","VictimAddressRegister");
            Log.i("resident id",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(),VictimAddressRegister.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }
        if(fileName.equals("CriminalAddressUpdate")){
            Log.i("in","CriminalAddressUpdate");
            Log.i("resident id",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), CriminalAddressUpdate.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }

        if(fileName.equals("VictimAddressUpdate")){
            Log.i("in","VictimAddressUpdate");
            Log.i("resident id",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), VictimAddressUpdate.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }

        if(fileName.equals("UpdatePublicUserAddress")){
            Log.i("in","UpdatePublicUserAddress");
            Log.i("im resodemt",String.valueOf(resident_id));
            Intent i=new Intent(getApplicationContext(), UpdatePublicUserAddress.class);
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }
        if(fileName.equals("InvestigatorAddressRegister")){
            Log.i("in","InvestigatorAddressRegister");
            Intent i=new Intent(getApplicationContext(), InvestigatorAddressRegister.class);
            i.putExtra("resident_id",resident_id);
            Log.i("im resodemt",String.valueOf(resident_id));
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }
        if(fileName.equals("InvestigatorAddressRegisterForAdmin")){
            Log.i("in","InvestigatorAddressRegisterForAdmin");
            Intent i=new Intent(getApplicationContext(), InvestigatorAddressRegisterForAdmin.class);
            i.putExtra("resident_id",resident_id);
            Log.i("im resodemt",String.valueOf(resident_id));
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }

        if(fileName.equals("UpdateInvestigatorAddress")){
            Intent i=new Intent(getApplicationContext(), UpdateInvestigatorAddress.class);
            Log.i("in","UpdateInvestigatorAddress");
            Log.i("im resodemt",String.valueOf(resident_id));
            i.putExtra("resident_id",resident_id);
            i.putExtra("fileName","AddressTransferActivity");
            i.putExtra("addressObject",addressObject);
            startActivity(i);


        }


    }


}