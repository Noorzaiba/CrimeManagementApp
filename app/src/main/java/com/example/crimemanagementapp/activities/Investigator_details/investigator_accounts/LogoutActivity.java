package com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.EntryActivty;
import com.example.crimemanagementapp.storage.SharedPrefManager;


public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        Log.i("checking", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).isLogIn()));
        SharedPrefManager.getInstance(getApplicationContext())
                .clear();
        Toast.makeText(getApplicationContext(), "successfull logout", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), EntryActivty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Log.i("checking", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).isLogIn()));
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