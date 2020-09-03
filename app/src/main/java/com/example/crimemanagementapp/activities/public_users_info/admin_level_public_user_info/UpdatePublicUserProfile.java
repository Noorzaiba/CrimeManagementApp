package com.example.crimemanagementapp.activities.public_users_info.admin_level_public_user_info;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.admin_level_investigator_details.InvestigatorPut;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalList;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorRegisterModel;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.DeleteObject;
import com.example.crimemanagementapp.model.public_user_details.PublicUserDefaultResponse;
import com.example.crimemanagementapp.model.public_user_details.PublicUserModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePublicUserProfile extends AppCompatActivity {
    private EditText firstNameET, lastNameET,idET, emailIdET,idAddressET, phoneNumberET,dobET ,adhaarNoET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET;
    String loggedInEmail;
    Button updateProfileSave,clickToUpdateButton,addressByMapButton,addressWithoutMapButton,deleteRecord;;
    int publicUserId;
    String publicUserReceivedEmail;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private RadioGroup genderRG;
    private RadioButton genderValue,maleRB,femaleRB;

    ImageView imageIV;
    TextView imageTV,imageNameTV;
    private Bitmap bitmap;
    private final String string="Preview  Photo";
    private int ch;
    private int PICK_IMAGE_REQUEST = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_user_update_profile);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();

        imageIV=findViewById(R.id.imageIV);
        imageNameTV=findViewById(R.id.imageNameTV);
        imageTV=findViewById(R.id.imageTV);

        this.idET=findViewById(R.id.idET);
        this.addressET=findViewById(R.id.addressET);
        this.idAddressET = findViewById(R.id.idAddressET);
        this.cityET=findViewById(R.id.cityET);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);
        this.firstNameET = findViewById(R.id.firstNameET);
        this.lastNameET = findViewById(R.id.lastNameET);
        this.emailIdET = findViewById(R.id.emailIDET);

        this.phoneNumberET = findViewById(R.id.phoneNumberET);
        this.adhaarNoET = findViewById(R.id.adhaarNoET);
        this.dobET = findViewById(R.id.dobET);
        this.updateProfileSave=findViewById(R.id.updateProfileSave);
        this.clickToUpdateButton=findViewById(R.id.clickToUpdateButton);
        this.addressByMapButton = findViewById(R.id.addressByMapButton);
        this.addressWithoutMapButton = findViewById(R.id.addressWithoutMapButton);
        this.addressByMapButton.setText("update Address By Map");
        this.addressWithoutMapButton.setText("update Address without Map");
        this.genderRG = findViewById(R.id.genderRG);

        this.maleRB=findViewById(R.id.maleRB);
        this.femaleRB=findViewById(R.id.femaleRB);
        Intent i=getIntent();
        publicUserReceivedEmail=i.getStringExtra("email");
        this.deleteRecord = findViewById(R.id.deleteRecord);
        this.deleteRecord.setText("Delete Public User Record");

        imageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch=1;
                Log.i("FDFd",String.valueOf(ch));
                showFileChooser();



            }
        });

        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePublicUserRecord();
            }
        });

        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressET.getText().toString().equals("AddressDoesNotExists")){

                    Intent intent=new Intent(getApplicationContext(), PublicUserAddressRegister.class);
                    intent.putExtra("resident_id",Integer.valueOf(idET.getText().toString()));
                    intent.putExtra("fileName","PublicUserAddressRegister");
                    startActivity(intent);
                }else{
                Intent intent=new Intent(getApplicationContext(), UpdatePublicUserAddress.class);
                intent.putExtra("resident_id",Integer.valueOf(idET.getText().toString()));
                intent.putExtra("fileName","UpdatePublicUserAddress");
                startActivity(intent);
            }}
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressET.getText().toString().equals("AddressDoesNotExists")){

                    Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                    intent.putExtra("resident_id", Integer.valueOf(idET.getText().toString()));
                    intent.putExtra("fileName","PublicUserAddressRegister");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), PermissionsActivity.class);
                    intent.putExtra("resident_id", Integer.valueOf(idET.getText().toString()));
                    intent.putExtra("fileName", "UpdatePublicUserAddress");
                    startActivity(intent);
                } }
        });


        dobET.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //current date
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(UpdatePublicUserProfile.this,
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
                dobET.setText(date);

            }
        };
        getPublicUser();


          Log.i("in c",loggedInEmail);
        this.updateProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePublicUserData();
            }
        });
        this.clickToUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdatePublicUserProfile.this, "You can update your Phone Number and Adhaar Number", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void setRadioButtonForGender(String gender_value) {


        Log.i("real-vaue", gender_value);
        // Log.i("value1",genderValue.getText().toString());
        Log.i("male", String.valueOf(this.maleRB.getText().equals(gender_value)));
        Log.i("female", String.valueOf(this.femaleRB.getText().equals(gender_value)));

        if (this.maleRB.getText().equals(gender_value)) {

            this.femaleRB.setChecked(false);
            this.maleRB.setChecked(true);
        }
        if (this.femaleRB.getText().equals(gender_value)) {

            this.femaleRB.setChecked(true);
            this.maleRB.setChecked(false);
        }



    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            //Getting the Bitmap from Gallery
//                tvImageOne.setText(string);
            if (ch==1){
                bitmap=getBitmap(filePath, imageIV,imageTV);
            }
            //bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);

            //Setting the Bitmap to ImageView
//                imageOne.setImageBitmap(bitmap);
        }

    }


    private Bitmap getBitmap(Uri filePath , ImageView imageView, TextView textView){
        Bitmap bitmap=null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
            //Setting the Bitmap to ImageView
            imageView.setImageBitmap(bitmap);
            textView.setText(string);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    void deletePublicUserRecord(){
        Intent i=getIntent();
        publicUserReceivedEmail=i.getStringExtra("email");
        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .deletePublicUserFacility(loggedInEmail,publicUserReceivedEmail);

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject d=response.body();
                    if(d.isFlag()) {
                      //  deletePublicUserAddressRecord();
                        Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_LONG).show();
                        Intent intent3=new Intent(getApplicationContext(), PublicUserListForAdmin.class);
                        startActivity(intent3);

                    }
                    else{ Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();}



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
    void deletePublicUserAddressRecord(){

        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .deletePublicUserAddressFacility(loggedInEmail,Integer.valueOf(idET.getText().toString()));

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject obj=response.body();

                    if(obj.isFlag()){
                        Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_LONG).show();
                        Intent intent3=new Intent(getApplicationContext(), CriminalList.class);
                        startActivity(intent3);
                    } else{
                        Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();}



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
    void getPublicUser(){
        Intent i=getIntent();
        publicUserReceivedEmail=i.getStringExtra("email");
       Log.i("in p",publicUserReceivedEmail);
        Call<PublicUserDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailPublicUser(loggedInEmail,publicUserReceivedEmail);

        call.enqueue(new Callback<PublicUserDefaultResponse>(){
            @Override
            public void onResponse(Call<PublicUserDefaultResponse> call, Response<PublicUserDefaultResponse> response) {

                try{

                    if(200==response.code()) {

                        PublicUserDefaultResponse res= response.body();
                        PublicUserModel obj= res.getSerailizedData().get(0);
                        if(res.isFlag()){
                        //    Toast.makeText(UpdatePublicUserProfile.this, obj.getEmail_id()+"HHH", Toast.LENGTH_LONG).show();
                            setPublicUserData(obj);


                        }else{
                            Toast.makeText(UpdatePublicUserProfile.this, obj.getEmail_id(), Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(UpdatePublicUserProfile.this, "Technical Error", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Toast.makeText(UpdatePublicUserProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PublicUserDefaultResponse> call, Throwable t) {
                Toast.makeText(UpdatePublicUserProfile.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });





    }
    boolean validation(String phoneNumberR,String adhaarNoR){


        if (phoneNumberR.length()<10){
            phoneNumberET.setError("Phone Number digits must be 10");
            phoneNumberET.requestFocus();
            return false;
        }

        if (adhaarNoR.length()<12){
            adhaarNoET.setError("Adhaar Number must have  12 characters");
            adhaarNoET.requestFocus();
            return false;
        }


        return true;
    }


    void setPublicUserData(PublicUserModel obj){
        Log.i("i","im here");
        idET.setText(String.valueOf(obj.getId()));
        publicUserId= obj.getId();
        firstNameET.setText(obj.getFirst_name());
        lastNameET.setText(obj.getLast_name());
        emailIdET.setText(obj.getEmail_id());
        dobET.setText(obj.getDob());
        Picasso.get()
                .load(ApiContants.PUBLIC_USER+obj.getProfile_image())
                .resize(50, 50)
                .centerCrop()
                .into(imageIV);

        imageNameTV.setText(obj.getProfile_image());
        setRadioButtonForGender(obj.getGender());

        phoneNumberET.setText(String.valueOf(obj.getPhone_no()));
        adhaarNoET.setText(String.valueOf(obj.getAdhaar_no()));
        getPublicUserAddress(publicUserId);


    }






    void updatePublicUserData(){
        String firstNameR = firstNameET.getText().toString().trim();
        String lastNameR = lastNameET.getText().toString().trim();
        String emailIdR = emailIdET.getText().toString().trim();
        String phoneNumberR = phoneNumberET.getText().toString().trim();

        String adhaarNoR=adhaarNoET.getText().toString().trim();
        String dobR=dobET.getText().toString().trim();

        int idGendervalue = genderRG.getCheckedRadioButtonId();
        this.genderValue = findViewById(idGendervalue);
        String genderValueR=genderValue.getText().toString();
        boolean test=validation(phoneNumberR,adhaarNoR);


        if(test==true){
            Intent i=getIntent();
            publicUserReceivedEmail=i.getStringExtra("email");
            long PhoneNo=Long.parseLong(phoneNumberR);
            long adhaarno=Long.parseLong(adhaarNoR);
            PublicUserModel updateObj;
            if(bitmap==null){
                Log.i("in ","bitmap is null");
            publicUserId=Integer.parseInt(idET.getText().toString());
           updateObj=new PublicUserModel(publicUserId,firstNameR,lastNameR,emailIdR,PhoneNo,dobR,genderValueR,adhaarno,imageNameTV.getText().toString());
            }else{
                Log.i("in ","bitmap is not null");
                String new_profile_image_string=getStringImage(bitmap);
                updateObj=new PublicUserModel(publicUserId,firstNameR,lastNameR,emailIdR,PhoneNo,dobR,genderValueR,adhaarno,new_profile_image_string);
            }

            Call<PublicUserDefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .putDetailPublicUser(loggedInEmail,publicUserReceivedEmail,updateObj);

            call.enqueue(new Callback<PublicUserDefaultResponse>(){
                @Override
                public void onResponse(Call<PublicUserDefaultResponse> call, Response<PublicUserDefaultResponse> response) {
                    try{

                        if(200==response.code()) {

                            PublicUserDefaultResponse res= response.body();
                            PublicUserModel obj= res.getSerailizedData().get(0);
                            if(res.isFlag()){
                                Toast.makeText(UpdatePublicUserProfile.this, obj.getEmail_id(), Toast.LENGTH_LONG).show();



                                if(emailIdR.equals((obj.getEmail_id()))){
                                    Intent i = new Intent(getApplicationContext(), UpdatePublicUserProfile.class);
                                    i.putExtra("email",obj.getEmail_id());
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(UpdatePublicUserProfile.this, "Unsuccessfull", Toast.LENGTH_LONG).show();
                                    Toast.makeText(UpdatePublicUserProfile.this, obj.getEmail_id(), Toast.LENGTH_LONG).show();
                                }


                            }else{
                                Toast.makeText(UpdatePublicUserProfile.this, "Error Occured", Toast.LENGTH_LONG).show();
                                Toast.makeText(UpdatePublicUserProfile.this, obj.getEmail_id(), Toast.LENGTH_LONG).show();
                            }


                        }else{
                            Toast.makeText(UpdatePublicUserProfile.this, "Technical Error", Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(UpdatePublicUserProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PublicUserDefaultResponse> call, Throwable t) {
                    Toast.makeText(UpdatePublicUserProfile.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });



        }else{
            Toast.makeText(UpdatePublicUserProfile.this,"Validation Error",Toast.LENGTH_LONG).show();}


    }
    void getPublicUserAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailPublicUserAddress(loggedInEmail,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                        AddressObject obj= res.getSerailizedData().get(0);
                      //  Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        if(res.isFlag()){
                         //   Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();
                            setAddressData(obj);

                        }else{
                            addressET.setText(obj.getLocation());
                          //  Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Address Id", Toast.LENGTH_LONG).show();
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
    void setAddressData(AddressObject obj){
        idAddressET.setText(String.valueOf(obj.getId()));
        addressET.setText(obj.getLocation());
        cityET.setText(obj.getCity());
        stateET.setText(obj.getState());
        pincodeET.setText(String.valueOf(obj.getZipCode()));
        longitudeET.setText(String.valueOf(obj.getLongitude()));
        latitudeET.setText(String.valueOf(obj.getLongitude()));
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
