package com.example.crimemanagementapp.activities.cases_information;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.activities.miscellaneous.TimePickerFragment;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.cases_information.CrimeDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeRegisterModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeRegister extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Spinner spinner;
    String type_of_crime;
    EditText descriptionET, statusET, investigatorET;
    TextView timeTV,dateTV,addressByMapButton,addressWithoutMapButton;
    Button submitButton;
    int id;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String loggedInEmail,loggedInToken;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeTV.setText(hourOfDay+":"+minute);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_register);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CrimeRegister.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type_of_crime));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        type_of_crime=this.spinner.getSelectedItem().toString();
        this.descriptionET = findViewById(R.id.descriptionET);
        this.statusET = findViewById(R.id.statusET);
        this.timeTV = findViewById(R.id.timeTV);
        this.dateTV = findViewById(R.id.dateTV);
        this.addressByMapButton = findViewById(R.id.addressByMapButton);
        this.addressWithoutMapButton = findViewById(R.id.addressWithoutMapButton);
        this.investigatorET = findViewById(R.id.investigatorET);
        this.submitButton = findViewById(R.id.submitButton);
        this.addressByMapButton.setEnabled(false);
        this.addressWithoutMapButton.setEnabled(false);

        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), CrimeAddressRegister.class);
                intent.putExtra("resident_id",id);
                intent.putExtra("fileName","CrimeAddressRegister");
                startActivity(intent);
                finish();
            }
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                intent.putExtra("resident_id",id);
                intent.putExtra("fileName","CrimeAddressRegister");
                startActivity(intent);
                finish();
            }
        });
        //setting the DOB
        dateTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //current date
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(CrimeRegister.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();



            }
        });


        timeTV.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {


                                          DialogFragment timePicker=new TimePickerFragment();
                                          timePicker.show(getSupportFragmentManager(),"Time Picker");
                                      }
                                  });





            mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d("TAG","onDateSet"+year+"-"+month+"-"+dayOfMonth);
                String date=year+"-"+month+"-"+dayOfMonth;
                dateTV.setText(date);

            }
        };





        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();


            }
        });

    }


    void submitData() {
        String description = descriptionET.getText().toString().trim();
        String status = statusET.getText().toString().trim();
        String time = timeTV.getText().toString().trim();
        String date = dateTV.getText().toString().trim();
        String investigatorEmail = investigatorET.getText().toString().trim();

        boolean test=validation(description, status, date,time, investigatorEmail);
        if(test==true){
            posting_data(this.spinner.getSelectedItem().toString(),description,status,date,time,investigatorEmail);
        }



    }

    boolean validation(String descriptionL, String statusL, String date,String time,  String investigatorEmailL) {


        if (descriptionL.isEmpty()) {
            descriptionET.setError("Description cannot be empty");
            descriptionET.requestFocus();
            return false;
        }

        if (statusL.isEmpty()) {
            statusET.setError("Status cannot be empty");
            statusET.requestFocus();
            return false;
        }
        if (date.isEmpty()) {
           dateTV.setError("Crime Occured Date cannot be empty");
            dateTV.requestFocus();
            Toast.makeText(CrimeRegister.this,"Crime Occured Date cannot be empty",Toast.LENGTH_LONG).show();
            return false;
        }
        if (time.isEmpty()) {
            timeTV.setError("Crime Occured Time cannot be empty");
            Toast.makeText(CrimeRegister.this,"Crime Occured Time cannot be empty",Toast.LENGTH_LONG).show();
           timeTV.requestFocus();

            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(investigatorEmailL).matches()){
            investigatorET.setError("Invalid Email address");
            investigatorET.requestFocus();
            return false;
        }
     return true;
    }

    void posting_data(String typeOfCrimeL, String descriptionL, String statusL, String dateL,String timeL,String investigatorEmailL) {
       Log.i(typeOfCrimeL,typeOfCrimeL);
       Log.i(descriptionL,descriptionL);
       Log.i(statusL,statusL);
      Log.i(dateL,dateL);
      Log.i(timeL,timeL);
       Log.i(investigatorEmailL,investigatorEmailL);


        CrimeRegisterModel crimeRegisterModel = new CrimeRegisterModel(descriptionL, typeOfCrimeL,timeL,dateL , statusL, investigatorEmailL);

        Call<CrimeDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .crimeRegisterFacility(loggedInEmail,loggedInToken,crimeRegisterModel);


        call.enqueue(new Callback<CrimeDefaultResponse>() {
            @Override
            public void onResponse(Call<CrimeDefaultResponse> call, Response<CrimeDefaultResponse> response) {


                try {

                    if (200 == response.code()) {

                        CrimeDefaultResponse res=response.body();

                        if(res.isFlag())  {


                            List<CrimeRegisterModel> obj_list = res.getSerialized_data_crime_register();
                            CrimeRegisterModel crimeRegisterModelResponse=obj_list.get(0);

                          //  Toast.makeText(CrimeRegister.this,crimeRegisterModelResponse.getDescription(),Toast.LENGTH_LONG).show();
                            if("Invalid Investigator id".equals(crimeRegisterModelResponse.getDescription())){

                            }else{
                                Toast.makeText(CrimeRegister.this,"Please enter the Address",Toast.LENGTH_LONG).show();
                                id=crimeRegisterModelResponse.getId();
                                submitButton.setEnabled(false);
                                addressByMapButton.setEnabled(true);
                                addressWithoutMapButton.setEnabled(true);

                            }

                        }else{
                            Toast.makeText(CrimeRegister.this,"Error Occured",Toast.LENGTH_LONG).show();
                        }


                    }else{
                        Toast.makeText(CrimeRegister.this,"Authentication Error",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){  Toast.makeText(CrimeRegister.this,e.getMessage(),Toast.LENGTH_LONG).show(); }
            }



            @Override
            public void onFailure(Call<CrimeDefaultResponse> call, Throwable t) {
                Toast.makeText(CrimeRegister.this,t.getMessage(),Toast.LENGTH_LONG).show();

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