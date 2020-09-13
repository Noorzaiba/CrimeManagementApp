package com.example.crimemanagementapp.activities.cases_information;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.crimemanagementapp.R;

import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.cases_information.crime_live_updates_details.CrimeLiveUpdateList;
import com.example.crimemanagementapp.activities.cases_information.crime_live_updates_details.CrimeLiveUpdatePost;
import com.example.crimemanagementapp.activities.cases_information.crime_scene_images_details.AddCrimeSceneImageActivity;
import com.example.crimemanagementapp.activities.cases_information.crime_scene_images_details.CrimeScenePicturesList;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details.VictimUpdate;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.activities.miscellaneous.TimePickerFragment;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.cases_information.CrimeDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeRegisterModel;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.DeleteObject;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimePut extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    Spinner spinner;
    EditText descriptionET, statusET, investigatorET,typeOfCrimeET,crimeIDET,docET,douET,idAddressET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET;
    Button submitButton,addNewPictures,viewAllPictures,updateButton,updateStatements,addNewStatements,addressByMapButton,addressWithoutMapButton,deleteCrimeButton;
    ArrayAdapter<String> myAdapter;
    TextView timeTV,dateTV;
    String loggedInEmail,loggedInToken;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeTV.setText(hourOfDay+":"+minute);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_put);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        spinner = findViewById(R.id.spinner);
        myAdapter = new ArrayAdapter<String>(CrimePut.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type_of_crime));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        descriptionET=findViewById(R.id.descriptionET);
        statusET=findViewById(R.id.statusET);
        timeTV=findViewById(R.id.timeTV);
        dateTV=findViewById(R.id.dateTV);
        deleteCrimeButton=findViewById(R.id.deleteCrimeButton);
        investigatorET=findViewById(R.id.investigatorET);
        typeOfCrimeET=findViewById(R.id.typeOfCrimeET);
        crimeIDET=findViewById(R.id.crimeIDET);
        docET=findViewById(R.id.docET);
        douET=findViewById(R.id.douET);
        this.addressET=findViewById(R.id.addressET);
        this.cityET=findViewById(R.id.cityET);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);
        this.submitButton = findViewById(R.id.submitButton);
        this.idAddressET = findViewById(R.id.idAddressET);
        addNewPictures=findViewById(R.id.addNewPictures);
        viewAllPictures=findViewById(R.id.viewAllPictures);

        submitButton=findViewById(R.id.submitButton);
        updateButton=findViewById(R.id.updateButton);
        addNewStatements=findViewById(R.id.addNewStatements);
        updateStatements=findViewById(R.id.updateStatements);

        this.addressByMapButton = findViewById(R.id.addressByMapButton);
        this.addressWithoutMapButton = findViewById(R.id.addressWithoutMapButton);
        this.addressByMapButton.setText("update Address By Map");
        this.addressWithoutMapButton.setText("update Address without Map");



        viewAllPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), CrimeScenePicturesList.class);
                intent.putExtra("crime_id",crimeIDET.getText().toString());
                startActivity(intent);
                finish();

            }
        });

        addNewPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), AddCrimeSceneImageActivity.class);
                intent.putExtra("crime_id",crimeIDET.getText().toString());
                startActivity(intent);
                finish();

            }
        });

        deleteCrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteCrime();
            }
        });
        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressET.getText().toString().equals("AddressDoesNotExists")){

                    Intent intent=new Intent(getApplicationContext(), CrimeAddressRegister.class);
                    intent.putExtra("resident_id",Integer.parseInt(crimeIDET.getText().toString()));
                    intent.putExtra("fileName","CrimeAddressRegister");
                    startActivity(intent);
                finish();}else{

                Intent intent=new Intent(getApplicationContext(), CrimeAddressPut.class);
                intent.putExtra("resident_id",Integer.parseInt(crimeIDET.getText().toString()));
                intent.putExtra("fileName","CrimeAddressPut");
                startActivity(intent);
                    finish();
            }}
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressET.getText().toString().equals("AddressDoesNotExists")){

                    Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                    intent.putExtra("resident_id",Integer.parseInt(crimeIDET.getText().toString()));
                    intent.putExtra("fileName","CrimeAddressRegister");
                    startActivity(intent);
                    finish();
                }else {

                    Intent intent = new Intent(getApplicationContext(), PermissionsActivity.class);
                    intent.putExtra("resident_id", Integer.parseInt(crimeIDET.getText().toString()));
                    intent.putExtra("fileName", "CrimeAddressPut");
                    startActivity(intent);
                    finish();
                }
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
                dialog = new DatePickerDialog(CrimePut.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();



            }
        });


        timeTV.setOnClickListener(v -> {


            DialogFragment timePicker=new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(),"Time Picker");
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


        this.addNewStatements.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CrimeLiveUpdatePost.class);
                intent.putExtra("crime_id",crimeIDET.getText().toString());
                startActivity(intent);
                finish();


            }
        });

        this.updateStatements.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), CrimeLiveUpdateList.class);
                i.putExtra("crime_id_para",Integer.parseInt(crimeIDET.getText().toString()));
                startActivity(i);
                finish();

            }
        });


    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfCrimeET.setText(spinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        getCrimeDetails();
        this.typeOfCrimeET.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Toast.makeText(CrimePut.this,"Select From dropdown to Change",Toast.LENGTH_LONG).show();
            }
        });

        this.updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

          typeOfCrimeET.requestFocus();
            }
        });

        this.submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             putCrimeDetails();

            }
        });

    }
    void deleteCrime(){
        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .deleteCrimeFacility(loggedInEmail,loggedInToken,Integer.valueOf(crimeIDET.getText().toString()));

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject d=response.body();
                    if(d.isFlag()) {


                        //deleteCrimeAddress();
                        Toast.makeText(getApplicationContext(),"Successfully Deleted",Toast.LENGTH_LONG).show();
                        Intent intent1=new Intent(getApplicationContext(), CrimeList.class);
                        startActivity(intent1);
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
void deleteCrimeAddress(){

    Call<DeleteObject> call= RetrofitClient
            .getInstance()
            .getApi()
            .deleteCrimeAddressFacility(loggedInEmail,loggedInToken,Integer.valueOf(crimeIDET.getText().toString()));

    call.enqueue(new Callback<DeleteObject>(){
        @Override
        public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
            if (response.code() == 200) {
                DeleteObject d=response.body();
                if(d.isFlag()) {


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
    void putCrimeDetails(){
        String descriptionL=descriptionET.getText().toString();
        String statusL=statusET.getText().toString();
        String dateL=dateTV.getText().toString();
        String timeL=timeTV.getText().toString();

        String investigatorEmail=investigatorET.getText().toString();
        String typeOfCrimeL=typeOfCrimeET.getText().toString();
        String docL=docET.getText().toString();
        String douL=douET.getText().toString();
        int crimeIDL=Integer.parseInt(crimeIDET.getText().toString());


        validation(descriptionL,statusL,timeL,dateL,investigatorEmail);

        CrimeRegisterModel crimeRegisterModel=new CrimeRegisterModel(crimeIDL,descriptionL,typeOfCrimeL,timeL,dateL,docL,douL,statusL,investigatorEmail);
        Call<CrimeDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .crimeUpdatePutFacility(loggedInEmail,loggedInToken,crimeIDL,crimeRegisterModel);

        call.enqueue(new Callback<CrimeDefaultResponse>() {
            @Override
            public void onResponse(Call<CrimeDefaultResponse> call, Response<CrimeDefaultResponse> response) {

                try {

                    if (200 == response.code()) {

                        CrimeDefaultResponse res=response.body();

                        if(res.isFlag())  {


                            List<CrimeRegisterModel> obj_list = res.getSerialized_data_crime_register();
                            CrimeRegisterModel crimeRegisterModelResponse=obj_list.get(0);
                           // crimeRegisterModelResponse.getDescription();

                            if("Invalid Investigator id".equals(crimeRegisterModelResponse.getDescription())){
                                Toast.makeText(getApplicationContext(),"Invalid Investigator id",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(CrimePut.this,"successfully updated",Toast.LENGTH_LONG).show();
                                typeOfCrimeET.setText(crimeRegisterModelResponse.getTypeOfCrime());
                                int spinnerPosition=myAdapter.getPosition(crimeRegisterModelResponse.getTypeOfCrime());
                                spinner.setSelection(spinnerPosition);

                            }



                        }else{
                            Toast.makeText(CrimePut.this,"Error Occured",Toast.LENGTH_LONG).show();
                        }


                    }else{
                        Toast.makeText(CrimePut.this,"Authentication Error",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){  Toast.makeText(CrimePut.this,e.getMessage(),Toast.LENGTH_LONG).show(); }

            }

            @Override
            public void onFailure(Call<CrimeDefaultResponse> call, Throwable t) {
                Toast.makeText(CrimePut.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }
    void getCrimeDetails() {
        Intent i= getIntent();
        final int pk=i.getIntExtra("pk",0);
Log.i("in g",String.valueOf(pk));
        Call<CrimeDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .crimeUpdateGetFacility(loggedInEmail,loggedInToken,pk);
        call.enqueue(new Callback<CrimeDefaultResponse>() {
            @Override
            public void onResponse(Call<CrimeDefaultResponse> call, Response<CrimeDefaultResponse> response) {
                try{
                    if(200==response.code()) {
                        CrimeDefaultResponse res = response.body();
                        if(res.isFlag()){
                          //  Toast.makeText(CrimePut.this,String.valueOf(res.isFlag()),Toast.LENGTH_LONG).show();
                            List<CrimeRegisterModel> obj_list = res.getSerialized_data_crime_register();
                            CrimeRegisterModel crimeRegisterModelResponse=obj_list.get(0);
                            descriptionET.setText(crimeRegisterModelResponse.getDescription());
                            statusET.setText(crimeRegisterModelResponse.getStatus());
                            timeTV.setText(crimeRegisterModelResponse.getTime());
                            dateTV.setText(crimeRegisterModelResponse.getDate());
                            investigatorET.setText(crimeRegisterModelResponse.getInvestigatorId());
                            docET.setText(crimeRegisterModelResponse.getDoc());
                            douET.setText(crimeRegisterModelResponse.getDou());
                            typeOfCrimeET.setText(crimeRegisterModelResponse.getTypeOfCrime());
                            String id=String.valueOf(crimeRegisterModelResponse.getId());
                            crimeIDET.setText(id);

                            getCrimeAddress(crimeRegisterModelResponse.getId());

                            int spinnerPosition=myAdapter.getPosition(crimeRegisterModelResponse.getTypeOfCrime());
                            spinner.setSelection(spinnerPosition);


                          //  Toast.makeText(CrimePut.this,crimeRegisterModelResponse.getDescription(),Toast.LENGTH_LONG).show();


                    }}

                    }catch (Exception e){
                    Toast.makeText(CrimePut.this,e.getMessage(),Toast.LENGTH_LONG).show();



                }
            }

            @Override
            public void onFailure(Call<CrimeDefaultResponse> call, Throwable t) {
                Toast.makeText(CrimePut.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }


    void validation(String descriptionL, String statusL, String timeL,String dateL,  String investigatorEmailL) {


        if (descriptionL.isEmpty()) {
            descriptionET.setError("Description cannot be empty");
            descriptionET.requestFocus();
            return;
        }

        if (statusL.isEmpty()) {
            statusET.setError("Status cannot be empty");
            statusET.requestFocus();
            return;
        }
        if (dateL.isEmpty()) {
            dateTV.setError("Crime Occured Date cannot be empty");
            dateTV.requestFocus();
            return;
        }
        if (timeL.isEmpty()) {
            timeTV.setError("Crime Occured Time cannot be empty");
            timeTV.requestFocus();
            return;
        }

        if (investigatorEmailL.isEmpty()) {
            investigatorET.setError("Investigator Email cannot be empty");
            investigatorET.requestFocus();
            return;
        }

    }

    void getCrimeAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailCrimeAddress(loggedInEmail,loggedInToken,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                      //  Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        AddressObject obj= res.getSerailizedData().get(0);
                        if(res.isFlag()){

                        //    Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();

                                idAddressET.setText(String.valueOf(obj.getId()));
                                addressET.setText(obj.getLocation());
                                cityET.setText(obj.getCity());
                                stateET.setText(obj.getState());
                                pincodeET.setText(String.valueOf(obj.getZipCode()));
                                longitudeET.setText(String.valueOf(obj.getLongitude()));
                                latitudeET.setText(String.valueOf(obj.getLongitude()));


                      //      Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();

                        }else{
                            addressET.setText(obj.getLocation());
                            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
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
