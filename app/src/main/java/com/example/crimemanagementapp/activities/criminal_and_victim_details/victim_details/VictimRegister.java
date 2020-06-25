package com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalAddressRegister;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalDefaultResponse;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalRegisterModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VictimRegister extends AppCompatActivity {  private RadioGroup gender;
    private EditText firstNameET,lastNameET,remarksET,emailIdET,phoneNumberET,occupationET,salaryET,ageET,crimeIdET,adhaarNoET;
    private RadioButton genderValue;
    private Button registerButton,addressByMapButton,addressWithoutMapButton;
    int id;
    String loggedInEmail,loggedInToken;
    ImageView imageIV;
    TextView imageTV,ProfileTV;
    private Bitmap bitmap;
    private final String string="Preview  Photo";
    private int ch;
    private int PICK_IMAGE_REQUEST = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_criminal_register);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        this.ProfileTV=findViewById(R.id.ProfileTV);
        ProfileTV.setText("Victim Registration");

        this.registerButton=findViewById(R.id.registerButton);
        this.firstNameET=findViewById(R.id.firstNameET);
        this.lastNameET=findViewById(R.id.lastNameET);
        this.emailIdET=findViewById(R.id.emailIDET);
        this.phoneNumberET=findViewById(R.id.phoneNumberET);
        this.occupationET=findViewById(R.id.occupationET);
        this.salaryET=findViewById(R.id.salaryET);
        this.ageET=findViewById(R.id.ageET);
        this.crimeIdET=findViewById(R.id.crimeIdET);
        this.gender=findViewById(R.id.genderRG);
        int idGendervalue=gender.getCheckedRadioButtonId();
        this.genderValue=findViewById(idGendervalue);
        this.adhaarNoET=findViewById(R.id.adhaarNoET);
        this.remarksET=findViewById(R.id.remarksET);
        this.registerButton=findViewById(R.id.registerButton);
        this.addressByMapButton = findViewById(R.id.addressByMapButton);
        this.addressWithoutMapButton = findViewById(R.id.addressWithoutMapButton);
        this.addressByMapButton.setEnabled(false);
        this.addressWithoutMapButton.setEnabled(false);

        imageIV=findViewById(R.id.imageIV);
        imageTV=findViewById(R.id.imageTV);

        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), VictimAddressRegister.class);
                intent.putExtra("resident_id",id);
                intent.putExtra("fileName","VictimAddressRegister");
                startActivity(intent);
            }
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                intent.putExtra("resident_id",id);
                intent.putExtra("fileName","VictimAddressRegister");
                startActivity(intent);
            }
        });



        imageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch=1;
                Log.i("FDFd",String.valueOf(ch));
                showFileChooser();



            }
        });





        this.registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                    registerVictim();





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
    boolean validation(String firstNameR,String lastNameR,String emailIdR,String remarksR, String phoneNumberR,String ageR,String occupationR,String salaryR,String crimeIdR, String adhaarNoR){

        Log.i("num",String.valueOf(phoneNumberR.length()==1));


        if (bitmap==null){
            Toast.makeText(getApplicationContext(),"Please select the profile image",Toast.LENGTH_LONG).show();
            return false;
        }


        if (firstNameR.isEmpty()){
            this.firstNameET.setError("First Name is required");
            this.firstNameET.requestFocus();
            return false;
        }



        if (lastNameR.isEmpty()){
            lastNameET.setError("Last Name is required, if not known add unknown  as value");
            lastNameET.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailIdR).matches()){
            emailIdET.setError("Invalid Email address , if not known add dummy email id  ");
            emailIdET.requestFocus();
            return false;
        }

        if (phoneNumberR.length()==1){

        }
        else{
            if(phoneNumberR.length()<10){
                this.phoneNumberET.setError("phone number is required");
                this.phoneNumberET.requestFocus();
                return false;}}

        if (ageR.isEmpty())
        {
            ageET.setError("age is required if not known add 0 dummy value ");
            ageET.requestFocus();
            return false;
        }
        if (occupationR.isEmpty()){
            occupationET.setError("occupation is required ,if not known add unknown  as value");
            occupationET.requestFocus();
            return false;
        }

        if (salaryR.isEmpty()){
            salaryET.setError("salary is required ,if not known add 0  as value");
            salaryET.requestFocus();
            return false;
        }
        if (crimeIdR.isEmpty()){
            crimeIdET.setError("Enter valid crime id");
            crimeIdET.requestFocus();
            return false;
        }


        if (remarksR.isEmpty()){
            remarksET.setError("Remarks cannot be empty if not known add dummy value as unknown");
            remarksET.requestFocus();
            return false;
        }


        if (adhaarNoR.length()==1){

        }
        else{
            if(adhaarNoR.length()<12){
                this.adhaarNoET.setError("Adhaar Number must have  12 characters, if not known add 0 as dummy value");
                this.adhaarNoET.requestFocus();
                return false;}}





        return true;

    }


    void registerVictim(){

        String firstNameR=firstNameET.getText().toString();
        String lastNameR=lastNameET.getText().toString();
        String emailIdR=emailIdET.getText().toString();
        String ageR=ageET.getText().toString();
        String genderR=genderValue.getText().toString().trim();
        String phoneNumberR=phoneNumberET.getText().toString();
        String adhaarNoR=adhaarNoET.getText().toString();

        String salaryR=salaryET.getText().toString();
        String occupationR=occupationET.getText().toString();
        String crimeIdR=crimeIdET.getText().toString();
        String remarksR=remarksET.getText().toString();

        boolean  test=validation(firstNameR,lastNameR,emailIdR,remarksR,phoneNumberR,ageR,occupationR,salaryR,crimeIdR,adhaarNoR);



        if(test==true){

            String imgOne=getStringImage(bitmap);
            VictimCriminalRegisterModel victimCriminalRegisterModel=new VictimCriminalRegisterModel(firstNameR,lastNameR,emailIdR,Integer.parseInt(ageR),Long.parseLong(phoneNumberR),remarksR,genderR,Long.parseLong(salaryR),occupationR,Integer.parseInt(crimeIdR),Long.parseLong(adhaarNoR),imgOne);
            Log.i("in creal",victimCriminalRegisterModel.toString());
            Call<VictimCriminalDefaultResponse> call= RetrofitClient
                    .getInstance()
                    .getApi()
                    .victimRegisterFacility(loggedInEmail,loggedInToken,victimCriminalRegisterModel);


            call.enqueue(new Callback<VictimCriminalDefaultResponse> (){
                @Override
                public void onResponse(Call<VictimCriminalDefaultResponse> call, Response<VictimCriminalDefaultResponse> response) {

                    try{

                        if(response.code()==200){

                            VictimCriminalDefaultResponse res=response.body();
                            if(res.isFlag()){
                                List<VictimCriminalRegisterModel> victimCriminalRegisterModelList=res.getVictimCriminalRegisterModelList();
                                VictimCriminalRegisterModel obj=victimCriminalRegisterModelList.get(0);

                            if("Invalid_Crime_id".equals(obj.getEmail())){
                                Toast.makeText(getApplicationContext(),"Invalid Crime id",Toast.LENGTH_LONG).show();
                            }else{
                                id=obj.getId();
                                registerButton.setEnabled(false);
                                addressByMapButton.setEnabled(true);
                                addressWithoutMapButton.setEnabled(true);

                                Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()),Toast.LENGTH_LONG).show();
                            }


                            }else{
                                Toast.makeText(getApplicationContext(),"error Occured1",Toast.LENGTH_LONG).show();
                            }



                        }else{
                            Toast.makeText(getApplicationContext(),"error Occured2",Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<VictimCriminalDefaultResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }





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