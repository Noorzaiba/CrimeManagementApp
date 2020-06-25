package com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details.crime_reported_scene_images_info.AddImageCrimeReportedSceneActivity;
import com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details.crime_reported_scene_images_info.CrimeReportedSceneImageListForAdmin;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.activities.miscellaneous.TimePickerFragment;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedByPublicModel;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.DeleteObject;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeReportedUpdateDetails extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    EditText idAddressET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET,descriptionET, idET,statusET, userET,typeOfCrimeET,docET,douET;
    Button saveButton,addressByMapButton,addressWithoutMapButton,deleteRecord,addNewPicturesButton,viewAllPictures;
    int pk;
    Spinner spinner;
    ArrayAdapter<String> myAdapter;
    String loggedInEmail;
    TextView timeTV,dateTV;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeTV.setText(hourOfDay+":"+minute);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_reported_update_details);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        this.addressET=findViewById(R.id.addressET);
        this.cityET=findViewById(R.id.cityET);
        spinner = findViewById(R.id.spinner);
        myAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type_of_crime));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);
        this.idAddressET = findViewById(R.id.idAddressET);
        this.idET=findViewById(R.id.idET);
        timeTV=findViewById(R.id.timeTV);
        dateTV=findViewById(R.id.dateTV);

        this.descriptionET=findViewById(R.id.descriptionET);
        this.statusET=findViewById(R.id.statusET);

        this.userET=findViewById(R.id.userET);
        this.typeOfCrimeET=findViewById(R.id.typeOfCrimeET);
        this.docET=findViewById(R.id.docET);
        this.douET=findViewById(R.id.douET);
        this.saveButton=findViewById(R.id.saveButton);

        this.addNewPicturesButton=findViewById(R.id.addNewPicturesButton);
        this.viewAllPictures=findViewById(R.id.viewAllPictures);

        this.deleteRecord = findViewById(R.id.deleteRecord);
        this.deleteRecord.setText("Delete Crime Reported Record");

        this.addressByMapButton = findViewById(R.id.addressByMapButton);
        this.addressWithoutMapButton = findViewById(R.id.addressWithoutMapButton);
        this.addressByMapButton.setText("update Address By Map");
        this.addressWithoutMapButton.setText("update Address without Map");

        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCrimeReportedRecord();
            }
        });
        getCrimeReportedDetails();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfCrimeET.setText(spinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        viewAllPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), CrimeReportedSceneImageListForAdmin.class);
                intent.putExtra("crime_id",idET.getText().toString());
                startActivity(intent);

            }
        });

        addNewPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent4=new Intent(getApplicationContext(), AddImageCrimeReportedSceneActivity.class);
                intent4.putExtra("crime_id",idET.getText().toString());
                startActivity(intent4);

            }
        });
        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), CrimeReportedUpdateAddressDetails.class);
                intent.putExtra("resident_id",Integer.parseInt(idET.getText().toString()));
                intent.putExtra("fileName","CrimeReportedUpdateAddressDetails");
                startActivity(intent);
            }
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                intent.putExtra("resident_id",Integer.parseInt(idET.getText().toString()));
                intent.putExtra("fileName","CrimeReportedUpdateAddressDetails");
                startActivity(intent);
            }
        });




        this.saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                putCrimeDetails();
            }
        });


        dateTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //current date
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(CrimeReportedUpdateDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();



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
        this.typeOfCrimeET.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Select From dropdown to Change",Toast.LENGTH_LONG).show();
            }
        });


        timeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Time Picker");
            }
        });







    }
    void deleteCrimeReportedRecord(){

        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .deleteCrimeReporedFacility(loggedInEmail,Integer.valueOf(idET.getText().toString()));

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject d=response.body();
                    if(d.isFlag()) {
                       // deleteCrimeReportedAddressRecord();
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent7=new Intent(getApplicationContext(), CrimeReportedListForAdmin.class);
                        startActivity(intent7);
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
    void deleteCrimeReportedAddressRecord() {

        Call<DeleteObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .deletetCrimeReportedAddress(loggedInEmail, Integer.valueOf(idET.getText().toString()));

        call.enqueue(new Callback<DeleteObject>() {
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject obj = response.body();

                    if (obj.isFlag()) {

                    } else {
                        Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Authentication Error", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<DeleteObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    void getCrimeReportedDetails() {
        Intent i= getIntent();
        pk=i.getIntExtra("pk",0);

        Call<CrimeReportedDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailCrimeReporedFacility(loggedInEmail,pk);
        call.enqueue(new Callback<CrimeReportedDefaultResponse>() {
            @Override
            public void onResponse(Call<CrimeReportedDefaultResponse> call, Response<CrimeReportedDefaultResponse> response) {
                try{
                    if(200==response.code()) {
                        CrimeReportedDefaultResponse res = response.body();
                        if(res.isFlag()){
                            Toast.makeText(getApplicationContext(),String.valueOf(res.isFlag()),Toast.LENGTH_LONG).show();
                            List<CrimeReportedByPublicModel> obj_list = res.getSerialized_data_crime_reported();
                            CrimeReportedByPublicModel obj=obj_list.get(0);
                            String id=String.valueOf(obj.getId());
                            idET.setText(id);
                            descriptionET.setText(obj.getDescription());
                            statusET.setText(obj.getStatus());
                            timeTV.setText(obj.getTime());
                            dateTV.setText(obj.getDate());
                            userET.setText(obj.getUser());
                            docET.setText(obj.getDoc());
                            douET.setText(obj.getDou());
                            typeOfCrimeET.setText(obj.getTypeOfCrime());
                            int spinnerPosition=myAdapter.getPosition(obj.getTypeOfCrime());
                            spinner.setSelection(spinnerPosition);

                            Toast.makeText(getApplicationContext(),obj.getDescription(),Toast.LENGTH_LONG).show();
                            getCrimeReportedAddress(obj.getId());
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
            public void onFailure(Call<CrimeReportedDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }


    void putCrimeDetails(){
        String descriptionL=descriptionET.getText().toString();
        String statusL=statusET.getText().toString();
        String dateL=dateTV.getText().toString();
        String timeL=timeTV.getText().toString();
        String userEmail=userET.getText().toString();
        String typeOfCrimeL=typeOfCrimeET.getText().toString();
        String docL=docET.getText().toString();
        String douL=douET.getText().toString();
        int crimeIDL=Integer.parseInt(idET.getText().toString());


        boolean test= validation(statusL);
        if(test==true){


            CrimeReportedByPublicModel obj=new  CrimeReportedByPublicModel(crimeIDL,descriptionL,typeOfCrimeL,timeL,dateL,docL,douL,statusL,userEmail);
            Call<CrimeReportedDefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    . PutCrimeReporedFacility(loggedInEmail,pk,obj);

            call.enqueue(new Callback<CrimeReportedDefaultResponse>() {
                @Override
                public void onResponse(Call<CrimeReportedDefaultResponse> call, Response<CrimeReportedDefaultResponse> response) {

                    try {

                        if (200 == response.code()) {

                            CrimeReportedDefaultResponse res=response.body();
                            List<CrimeReportedByPublicModel>  obj_list=  res.getSerialized_data_crime_reported();
                            CrimeReportedByPublicModel crimeReportedByPublicModel=obj_list.get(0);

                            Toast.makeText(getApplicationContext(),crimeReportedByPublicModel.getStatus(),Toast.LENGTH_LONG).show();
                            if("userDoesExists@gmail.com".equals(crimeReportedByPublicModel.getDescription())){
                                Toast.makeText(getApplicationContext(),"Invalid user email id",Toast.LENGTH_LONG).show();
                            }else{

                                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                                typeOfCrimeET.setText(crimeReportedByPublicModel.getTypeOfCrime());
                                int spinnerPosition=myAdapter.getPosition(crimeReportedByPublicModel.getTypeOfCrime());
                                spinner.setSelection(spinnerPosition);

                            }



                        }else{
                            Toast.makeText(getApplicationContext(),"Authentication Occured",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show(); }
                }




                @Override
                public void onFailure(Call<CrimeReportedDefaultResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }else{ Toast.makeText(getApplicationContext(),"Validation Error",Toast.LENGTH_LONG).show();}



    }
    boolean validation( String statusL) {




        if (statusL.isEmpty()) {
            statusET.setError("Status cannot be empty");
            statusET.requestFocus();
            return false;
        }
        return true;

    }
    void getCrimeReportedAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailCrimeReportedAddress(loggedInEmail,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                        Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        if(res.isFlag()){
                            AddressObject obj= res.getSerailizedData().get(0);
                            Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();
                            idAddressET.setText(String.valueOf(obj.getId()));
                            addressET.setText(obj.getLocation());
                            cityET.setText(obj.getCity());
                            stateET.setText(obj.getState());
                            pincodeET.setText(String.valueOf(obj.getZipCode()));
                            longitudeET.setText(String.valueOf(obj.getLongitude()));
                            latitudeET.setText(String.valueOf(obj.getLongitude()));
                            Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
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
