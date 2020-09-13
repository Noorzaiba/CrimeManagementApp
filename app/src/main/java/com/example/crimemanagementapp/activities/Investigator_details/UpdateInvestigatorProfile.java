package com.example.crimemanagementapp.activities.Investigator_details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.CrimeManagementOptions;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorAdministrativeInformationModel;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorDefaultResponse;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorRegisterModel;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInvestigatorProfile  extends AppCompatActivity {
    private EditText firstNameET,lastNameET,achivementsET,salaryET,emailIdET,phoneNumberET,genderET,positionET,adhaarNoET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET;
    private TextView dob;

    String loggedInEmail,loggedInToken;
    private  int investigatorId;
    private Button updateProfile,updateProfileSave,addressByMapButton,backButton,addressWithoutMapButton;
    ImageView imageIV;
    TextView imageTV,imageNameTV;
    private Bitmap bitmap;
    private final String string="Preview  Photo";
    private int ch;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_update_profile);
        imageIV=findViewById(R.id.imageIV);
        imageNameTV=findViewById(R.id.imageNameTV);
        imageTV=findViewById(R.id.imageTV);
        this.firstNameET=findViewById(R.id.firstNameET);
        this.backButton = findViewById(R.id.backButton);
        this.lastNameET=findViewById(R.id.lastNameET);
        this.emailIdET=findViewById(R.id.emailIDET);
        this.phoneNumberET=findViewById(R.id.phoneNumberET);
        this.genderET=findViewById(R.id.genderET);
        this.positionET=findViewById(R.id.positionET);
        this.adhaarNoET=findViewById(R.id.adhaarNoET);
        this.dob=findViewById(R.id.dateOfBirthTV);
        this.addressET=findViewById(R.id.addressET);
        this.cityET=findViewById(R.id.cityET);
        this.stateET=findViewById(R.id.stateET);
        this.longitudeET=findViewById(R.id.longitudeET);
        this.latitudeET=findViewById(R.id.latitudeET);
        this.pincodeET=findViewById(R.id.pincodeET);
        this.salaryET=findViewById(R.id.salaryET);
        this.achivementsET=findViewById(R.id.achivementsET);
        this.updateProfile=findViewById(R.id.updateProfile);
        this.updateProfileSave=findViewById(R.id.updateProfileSave);
        this.addressByMapButton = findViewById(R.id.addressByMapButton);
        this.addressWithoutMapButton = findViewById(R.id.addressWithoutMapButton);
        this.addressByMapButton.setText("update Address By Map");
        this.addressWithoutMapButton.setText("update Address without Map");
      loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
      loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        imageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch=1;
                Log.i("FDFd",String.valueOf(ch));
                showFileChooser();



            }
        });
        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressET.getText().toString().equals("AddressDoesNotExists")){

                    Intent intent=new Intent(getApplicationContext(), InvestigatorAddressRegister.class);
                    intent.putExtra("resident_id",investigatorId);
                    intent.putExtra("fileName","InvestigatorAddressRegister");
                    startActivity(intent);
                }else{

                Intent intent=new Intent(getApplicationContext(), UpdateInvestigatorAddress.class);
                intent.putExtra("resident_id",investigatorId);
                intent.putExtra("fileName","UpdateInvestigatorAddress");
                startActivity(intent);
            }}
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressET.getText().toString().equals("AddressDoesNotExists")) {

                    Intent intent = new Intent(getApplicationContext(), PermissionsActivity.class);
                    intent.putExtra("resident_id", investigatorId);
                    intent.putExtra("fileName", "InvestigatorAddressRegister");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), PermissionsActivity.class);
                    intent.putExtra("resident_id", investigatorId);
                    intent.putExtra("fileName", "UpdateInvestigatorAddress");
                    startActivity(intent);
                }
            }
        });


        getInvestigatorDetails();

        this.updateProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateInvestigatorProfile.this,"you can update Phone Number,Aadhaar Number and Address fields",Toast.LENGTH_LONG).show();
               phoneNumberET.requestFocus();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getApplicationContext(), CrimeManagementOptions.class);
                startActivity(intent1);
            }
        });

        this.updateProfileSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               //update the profile
                updateProfileInvestigator();

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
    void setTextInfo(InvestigatorRegisterModel invObj){
        String phoneNumberS=Long.toString(invObj.getPhone_no());
        String adhaarNoS=Long.toString(invObj.getAdhaar_no());
        Toast.makeText(UpdateInvestigatorProfile.this,invObj.getEmail_id(),Toast.LENGTH_LONG).show();
        Picasso.get()
                .load(ApiContants.INVESTIGATOR+invObj.getProfile_image())
                .resize(50, 50)
                .centerCrop()
                .into(imageIV);
        imageNameTV.setText(invObj.getProfile_image());
        firstNameET.setText(invObj.getFirst_name());
        lastNameET.setText(invObj.getLast_name());
        emailIdET.setText(invObj.getEmail_id());
        phoneNumberET.setText(phoneNumberS);
        dob.setText(invObj.getDob());
        genderET.setText(invObj.getGender());
        adhaarNoET.setText(adhaarNoS);
        investigatorId=invObj.getId();
        getInvestigatorAdministrativeTypeDetail();
        getInvestigatorAddress(investigatorId);
    }
     void getInvestigatorDetails(){
    Call<InvestigatorDefaultResponse> call = RetrofitClient
            .getInstance()
            .getApi()
            .getInvestigatorProfile(loggedInEmail,loggedInToken,loggedInEmail);

    call.enqueue(new Callback<InvestigatorDefaultResponse>() {
        @Override
        public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
            try{

                if(response.code()==200){
                    InvestigatorDefaultResponse res=response.body();
                    Log.i("falag",String.valueOf(res.isFlag()));
                    if(res.isFlag()){
                        InvestigatorRegisterModel invObj=res.getSerailizedInvestigatorModel().get(0);
                      //  Toast.makeText(UpdateInvestigatorProfile.this,String.valueOf(invObj.getEmail_id()),Toast.LENGTH_LONG).show();
                        setTextInfo(invObj);
                    }else{
                        Toast.makeText(UpdateInvestigatorProfile.this,"Error Occured",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(UpdateInvestigatorProfile.this,"Technical Error",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(UpdateInvestigatorProfile.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
            Toast.makeText(UpdateInvestigatorProfile.this,t.getMessage(),Toast.LENGTH_LONG).show();

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



    void updateProfileInvestigator(){
        String phoneNumberR=phoneNumberET.getText().toString().trim();
        String adhaarNoR=adhaarNoET.getText().toString().trim();
        String firstNameR = firstNameET.getText().toString().trim();
        String lastNameR = lastNameET.getText().toString().trim();
        String emailIdR = emailIdET.getText().toString().trim();
        String genderR = genderET.getText().toString().trim();
        String dobR=dob.getText().toString().trim();

  boolean test=validation(phoneNumberR,adhaarNoR);

    if(test==true){
    long PhoneNo=Long.parseLong(phoneNumberR);
    long adhaarno=Long.parseLong(adhaarNoR);
        InvestigatorRegisterModel updateObj;
        if(bitmap==null){
            Log.i("in ","bitmapnull");
  updateObj=new InvestigatorRegisterModel(investigatorId,firstNameR,lastNameR,emailIdR,dobR,PhoneNo,genderR,adhaarno,imageNameTV.getText().toString());
        }else{
            Log.i("in ","bitmapnotnull");
            String new_profile_image_string=getStringImage(bitmap);
            updateObj=new InvestigatorRegisterModel(investigatorId,firstNameR,lastNameR,emailIdR,dobR,PhoneNo,genderR,adhaarno,new_profile_image_string);
        }
    //updateInvestigatorProfile


    Call<InvestigatorDefaultResponse> call= RetrofitClient
            .getInstance()
            .getApi()
            .updateInvestigatorProfile(loggedInEmail,loggedInEmail,loggedInToken,updateObj);
    Log.i("User",updateObj.toString());



    call.enqueue(new Callback<InvestigatorDefaultResponse>() {
        @Override
        public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {



                    try{

                        if(response.code()==200){
                            InvestigatorDefaultResponse res=response.body();

                            if(res.isFlag()){
                                InvestigatorRegisterModel invObj=res.getSerailizedInvestigatorModel().get(0);

                                Toast.makeText(UpdateInvestigatorProfile.this,"Successfully Updated",Toast.LENGTH_LONG).show();
                                Log.i("in update","here");
                                Intent intent1=new Intent(getApplicationContext(), UpdateInvestigatorProfile.class);
                                startActivity(intent1);

                            }else{
                                Toast.makeText(UpdateInvestigatorProfile.this,"Error Occured",Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(UpdateInvestigatorProfile.this,"Technical Error",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(UpdateInvestigatorProfile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }


        }

        @Override
        public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
            Toast.makeText(UpdateInvestigatorProfile.this,t.getMessage(),Toast.LENGTH_LONG).show();
        }
    });


}else{ Toast.makeText(UpdateInvestigatorProfile.this,"Validation Error",Toast.LENGTH_LONG).show();}




}


    void getInvestigatorAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailInvestigatorAddress(loggedInEmail,loggedInToken,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                        AddressObject obj= res.getSerailizedData().get(0);
                     //   Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        if(res.isFlag()){
                          //  Toast.makeText(getApplicationContext(),"Successfully Updated", Toast.LENGTH_LONG).show();
                            setAddressData(obj);

                        }else{
                            addressET.setText((obj.getLocation()));
                            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid address Id", Toast.LENGTH_LONG).show();
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

        addressET.setText(obj.getLocation());
        cityET.setText(obj.getCity());
        stateET.setText(obj.getState());
        pincodeET.setText(String.valueOf(obj.getZipCode()));
        longitudeET.setText(String.valueOf(obj.getLongitude()));
        latitudeET.setText(String.valueOf(obj.getLongitude()));
    }
    void getInvestigatorAdministrativeTypeDetail(){

        Call<InvestigatorDefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .getInvestigatorAdministrativeByEmailFaciltiy(loggedInEmail,loggedInToken,loggedInEmail);
        call.enqueue(new Callback<InvestigatorDefaultResponse>(){
            @Override
            public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
                try{
                    if(response.code()==200){
                        InvestigatorDefaultResponse responseObject=response.body();
                        if(responseObject.isFlag()){
                            List<InvestigatorAdministrativeInformationModel> investigatorAdministrativeInformationModels=responseObject.getSerailizedData();
                            InvestigatorAdministrativeInformationModel  obj=investigatorAdministrativeInformationModels.get(0);
                          //  Toast.makeText(UpdateInvestigatorProfile.this,String.valueOf(obj.getPosition()),Toast.LENGTH_LONG).show();
                            String id=String.valueOf(obj.getId());
                            String salary_amt=String.valueOf(obj.getSalary());
                            salaryET.setText(salary_amt);
                            positionET.setText(obj.getPosition());
                            achivementsET.setText(obj.getAchivements());

                        }else{

                            Toast.makeText(UpdateInvestigatorProfile.this,"Error Occured",Toast.LENGTH_LONG);
                        }


                    }else{
                        Toast.makeText(UpdateInvestigatorProfile.this,"Technical Error",Toast.LENGTH_LONG);
                    }

                }catch (Exception e){
                    Toast.makeText(UpdateInvestigatorProfile.this,e.getMessage(),Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                Toast.makeText(UpdateInvestigatorProfile.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    protected void onStart() {
        super.onStart();
        Log.i("df",String.valueOf(SharedPrefManager.getInstance(this).isLogIn()));
        //if its not logged in redirect to login page
        if(!SharedPrefManager.getInstance(this).isLogIn()){
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

}