package com.example.crimemanagementapp.activities.Investigator_details;

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
import android.util.Patterns;
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
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.api.RetrofitClient;

import com.example.crimemanagementapp.model.investigator_details.InvestigatorDefaultResponse;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorRegisterModel;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestigatorRegisterActivity  extends AppCompatActivity {


    private EditText mBirthDate;
    private Button registerButton,addressByMapButton,addressWithoutMapButton;
    private RadioGroup gender;
    private EditText firstNameET,lastNameET,emailIdET,phoneNumberET,passwordET,adhaarNoET;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private RadioButton genderValue;
    int id;
    ImageView imageIV;
    TextView imageTV;
    private Bitmap bitmap;
    private final String string="Preview  Photo";
    private int ch;
    private int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_register);
        mBirthDate=findViewById(R.id.dateOfBirthTV);
        this.registerButton=findViewById(R.id.registerButton);
        this.firstNameET=findViewById(R.id.firstNameET);
        this.lastNameET=findViewById(R.id.lastNameET);
        this.emailIdET=findViewById(R.id.emailIDET);
        this.phoneNumberET=findViewById(R.id.phoneNumberET);
        this.gender=findViewById(R.id.genderRG);
        int idGendervalue=gender.getCheckedRadioButtonId();
        this.genderValue=findViewById(idGendervalue);
        this.passwordET=findViewById(R.id.passwordET);
        this.adhaarNoET=findViewById(R.id.addharNoET);
        this.addressByMapButton = findViewById(R.id.addressByMapButton);
        this.addressWithoutMapButton = findViewById(R.id.addressWithoutMapButton);
        this.addressByMapButton.setEnabled(false);
        this.addressWithoutMapButton.setEnabled(false);

        imageIV=findViewById(R.id.imageIV);
        imageTV=findViewById(R.id.imageTV);

        this.registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                register();

            }
        });

        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), InvestigatorAddressRegister.class);
                intent.putExtra("resident_id",id);
                intent.putExtra("fileName","InvestigatorAddressRegister");
                startActivity(intent);
            }
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                intent.putExtra("resident_id",id);
                intent.putExtra("fileName","InvestigatorAddressRegister");
                startActivity(intent);
            }
        });


        //setting the DOB
        mBirthDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //current date
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(InvestigatorRegisterActivity.this,
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
                mBirthDate.setText(date);

            }
        };




        imageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch=1;
                Log.i("FDFd",String.valueOf(ch));
                showFileChooser();



            }
        });

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
private boolean validation(String firstNameR,String lastNameR,String emailIdR,String dobR,String phoneNumberR,String passwordR,String adhaarNoR ){


    if (bitmap==null){
        Toast.makeText(getApplicationContext(),"Please select the profile image",Toast.LENGTH_LONG).show();
        return false;
    }

    if (firstNameR.isEmpty()){
        firstNameET.setError("First Name is required");
        firstNameET.requestFocus();
        return false;
    }
    if (lastNameR.isEmpty()){
        lastNameET.setError("Last Name is required");
        lastNameET.requestFocus();
        return  false;
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(emailIdR).matches()){
        emailIdET.setError("Invalid Email address ");
        emailIdET.requestFocus();
        return false;
    }

    if (dobR.isEmpty())
    {
        mBirthDate.setError("Date is required");
        mBirthDate.requestFocus();
        return false;
    }


    if (phoneNumberR.length()<10){
        phoneNumberET.setError("Phone Number digits must be 10");
        phoneNumberET.requestFocus();
        return false;
    }
    if (passwordR.length()<8){
        passwordET.setError("Password must have atleast 8 characters");
        passwordET.requestFocus();
        return false;
    }



    if (adhaarNoR.length()<12){
        adhaarNoET.setError("Adhaar Number must have  12 characters");
        adhaarNoET.requestFocus();
        return false;
    }


        return true;}

    private void register(){
        String firstNameR=firstNameET.getText().toString().trim();
        String lastNameR=lastNameET.getText().toString().trim();
        String emailIdR=emailIdET.getText().toString().trim();
        String phoneNumberR=phoneNumberET.getText().toString().trim();
        String genderR=genderValue.getText().toString().trim();
        String passwordR=passwordET.getText().toString().trim();

        String dobR=mBirthDate.getText().toString().trim();
        String adhaarNoR=adhaarNoET.getText().toString().trim();

        boolean test=validation(firstNameR,lastNameR,emailIdR,dobR,phoneNumberR,passwordR,adhaarNoR);

       if(test==true){

           long PhoneNo=Long.parseLong(phoneNumberR);

           long adhaarno=Long.parseLong(adhaarNoR);


           String imgOne=getStringImage(bitmap);

           InvestigatorRegisterModel Investigator=new InvestigatorRegisterModel(firstNameR,lastNameR,emailIdR,dobR,PhoneNo,genderR,passwordR,adhaarno,imgOne);
           Call<InvestigatorDefaultResponse> call= RetrofitClient
                   .getInstance()
                   .getApi()
                   . createInvestigatorProfile(Investigator);

           Log.i("User",Investigator.toString());
           call.enqueue(new Callback<InvestigatorDefaultResponse>() {
               @Override
               public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {


                    if(200==response.code()){
                         InvestigatorDefaultResponse res=response.body();
                        Toast.makeText(InvestigatorRegisterActivity.this,String.valueOf(res.isFlag()),Toast.LENGTH_LONG).show();

                        if(res.isFlag()){
                            InvestigatorRegisterModel obj=res.getSerailizedInvestigatorModel().get(0);
                            Toast.makeText(InvestigatorRegisterActivity.this,obj.getEmail_id(),Toast.LENGTH_LONG).show();

                            if(obj.getEmail_id().equals("noInternet@gmail.com")){

                            }else{
                                id=obj.getId();
                                registerButton.setEnabled(false);
                                addressByMapButton.setEnabled(true);
                                addressWithoutMapButton.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Please fill the address details and  check your email for Email Verification",Toast.LENGTH_LONG).show();
                            }


                        }else{
                            InvestigatorRegisterModel investigatorRegisterModel=res.getSerailizedInvestigatorModel().get(0);

                            Toast.makeText(InvestigatorRegisterActivity.this,investigatorRegisterModel.getEmail_id(),Toast.LENGTH_LONG).show();
                        }
                    }else{

                        Toast.makeText(InvestigatorRegisterActivity.this,"Technical Error",Toast.LENGTH_LONG).show();
                    }



               }

               @Override
               public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                   Toast.makeText(InvestigatorRegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

               }
           });






       }


    }




}












