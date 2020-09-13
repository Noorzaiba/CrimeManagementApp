package com.example.crimemanagementapp.activities.Investigator_details.admin_level_investigator_details;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.InvestigatorAddressRegister;
import com.example.crimemanagementapp.activities.Investigator_details.UpdateInvestigatorAddress;
import com.example.crimemanagementapp.activities.Investigator_details.UpdateInvestigatorProfile;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details.InvestigatorAdministrativeInformationUpdate;
import com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details.CrimeReportedUpdateDetails;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalList;
import com.example.crimemanagementapp.activities.map.PermissionsActivity;
import com.example.crimemanagementapp.activities.public_users_info.admin_level_public_user_info.UpdatePublicUserProfile;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorAdministrativeInformationModel;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorDefaultResponse;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorRegisterModel;
import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
import com.example.crimemanagementapp.model.miscellaneous.DeleteObject;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestigatorPut extends AppCompatActivity {
    private EditText firstNameET,idET,lastNameET,idAddressET,achivementsET,salaryET,emailIdET,phoneNumberET,positionET,adhaarNoET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET,InvestigatorAdministrativeTypeDetailEIdT;
    private TextView dob;
    private RadioGroup genderRG,isSuperuserRG,isStaffRG,isActiveRG;
    private RadioButton genderValue,maleRB,femaleRB;
    private RadioButton superuserValue,trueSuperuserRB,falseSuperuserRB;
    private RadioButton staffValue,trueStaffRB,falseStaffRB;
    private RadioButton activeValue,trueActiveRB,falseActiveRB;
    String receivedEmail;
    String loggedInEmail,loggedInToken;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private  int investigatorId;
    ImageView imageIV;
    TextView imageTV,imageNameTV;
    private Bitmap bitmap;
    private final String string="Preview  Photo";
    private int ch;
    private int PICK_IMAGE_REQUEST = 1;
    private Button deleteRecord,updateProfile,updateProfileSave,backButton,addressByMapButton,addressWithoutMapButton,investigatorAdminInfoUpdateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_put);
        imageIV=findViewById(R.id.imageIV);
        imageNameTV=findViewById(R.id.imageNameTV);
        imageTV=findViewById(R.id.imageTV);

        this.firstNameET=findViewById(R.id.firstNameET);
        this.investigatorAdminInfoUpdateButton = findViewById(R.id.investigatorAdminInfoUpdateButton);
        this.InvestigatorAdministrativeTypeDetailEIdT= findViewById(R.id.InvestigatorAdministrativeTypeDetailEIdT);
        this.backButton = findViewById(R.id.backButton);
        this.idAddressET = findViewById(R.id.idAddressET);
        this.idET = findViewById(R.id.idET);
        this.genderRG = findViewById(R.id.genderRG);
        this.isSuperuserRG = findViewById(R.id.isSuperuserRG);
        this.isStaffRG = findViewById(R.id.isStaffRG);
        this.isActiveRG = findViewById(R.id.isActiveRG);

        this.maleRB=findViewById(R.id.maleRB);
        this.femaleRB=findViewById(R.id.femaleRB);

        this.trueSuperuserRB=findViewById(R.id.trueSuperuserRB);
        this.falseSuperuserRB=findViewById(R.id.falseSuperuserRB);

        this.trueStaffRB=findViewById(R.id.trueStaffRB);
        this.falseStaffRB=findViewById(R.id.falseStaffRB);

        this.trueActiveRB=findViewById(R.id.trueActiveRB);
        this.falseActiveRB=findViewById(R.id.falseActiveRB);



        this.lastNameET=findViewById(R.id.lastNameET);
        this.emailIdET=findViewById(R.id.emailIDET);
        this.phoneNumberET=findViewById(R.id.phoneNumberET);


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

        this.deleteRecord = findViewById(R.id.deleteRecord);
        this.deleteRecord.setText("Delete Investigator Record");



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

        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInvestigatorRecord();
            }
        });
        dob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //current date
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(InvestigatorPut.this,
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
               dob.setText(date);

            }
        };
        investigatorAdminInfoUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=InvestigatorAdministrativeTypeDetailEIdT.getText().toString()+"null";
                Log.i("inn",id);
if(id.equals("null")){
    Toast.makeText(getApplicationContext(),"Please register this email, then try to update",Toast.LENGTH_LONG).show();
}else{
    Intent i=new Intent(getApplicationContext(), InvestigatorAdministrativeInformationUpdate.class);
    i.putExtra("pk",Integer.parseInt(InvestigatorAdministrativeTypeDetailEIdT.getText().toString()));
    startActivity(i);

}

            }
        });

        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressET.getText().toString().equals("AddressDoesNotExists")){

                    Intent intent=new Intent(getApplicationContext(), InvestigatorAddressRegisterForAdmin.class);
                    intent.putExtra("resident_id",investigatorId);
                    intent.putExtra("fileName","InvestigatorAddressRegisterForAdmin");
                    startActivity(intent);
                }else{

                Intent intent=new Intent(getApplicationContext(), InvestigatorAddressPut.class);
                intent.putExtra("resident_id",investigatorId);
                intent.putExtra("fileName","InvestigatorAddressPut");
                startActivity(intent);
            }}
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressET.getText().toString().equals("AddressDoesNotExists")){

                    Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                    intent.putExtra("resident_id",investigatorId);
                    intent.putExtra("fileName","InvestigatorAddressRegisterForAdmin");
                    startActivity(intent);
                }else{
                Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                intent.putExtra("resident_id",investigatorId);
                intent.putExtra("fileName","InvestigatorAddressPut");
                startActivity(intent);
            }}
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getApplicationContext(), InvestigatorList.class);
                startActivity(intent1);
            }
        });

        getInvestigatorDetails();

        this.updateProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Be carefull while updating the details ",Toast.LENGTH_LONG).show();
                phoneNumberET.requestFocus();

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


    public void setRadioButtonForSuperuser(String superuser_value) {


        Log.i("real-vaue ofsuper", superuser_value);
        // Log.i("value1",genderValue.getText().toString());
        Log.i("super true:", String.valueOf(this.trueSuperuserRB.getText().equals(superuser_value)));
        Log.i("super false:", String.valueOf(this.falseSuperuserRB.getText().equals(superuser_value)));

        if (this.trueSuperuserRB.getText().equals(superuser_value)) {

            this.falseSuperuserRB.setChecked(false);
            this.trueSuperuserRB.setChecked(true);
        }
        if (this.falseSuperuserRB.getText().equals(superuser_value)) {

            this.falseSuperuserRB.setChecked(true);
            this.trueSuperuserRB.setChecked(false);
        }



    }


    public void setRadioButtonForStaff(String staff_value) {


        Log.i("real-vaue", staff_value);
         Log.i("in staff","jjkj");
        Log.i("staff true", String.valueOf(this.trueStaffRB.getText().equals(staff_value)));
        Log.i("staff false", String.valueOf(this.falseStaffRB.getText().equals(staff_value)));

        if (this.trueStaffRB.getText().equals(staff_value)) {
            Log.i("1", staff_value);
            this.falseStaffRB.setChecked(false);
            Log.i("12", staff_value);
            this.trueStaffRB.setChecked(true);
            Log.i("13", staff_value);
        }
        if (this.falseStaffRB.getText().equals(staff_value)) {
            Log.i("2", staff_value);
            this.falseStaffRB.setChecked(true);
            this.trueStaffRB.setChecked(false);
        }



    }

    public void setRadioButtonForActive(String active_value) {


        Log.i("real-vaue", active_value);
        // Log.i("value1",genderValue.getText().toString());
        Log.i("active true", String.valueOf(this.trueSuperuserRB.getText().equals(active_value)));
        Log.i("active false", String.valueOf(this.falseSuperuserRB.getText().equals(active_value)));

        if (this.trueActiveRB.getText().equals(active_value)) {

            this.falseActiveRB.setChecked(false);
            this.trueActiveRB.setChecked(true);
        }
        if (this.falseActiveRB.getText().equals(active_value)) {

            this.falseActiveRB.setChecked(true);
            this.trueActiveRB.setChecked(false);
        }



    }

    void setTextInfo(InvestigatorRegisterModel invObj){
        String phoneNumberS=Long.toString(invObj.getPhone_no());
        String adhaarNoS=Long.toString(invObj.getAdhaar_no());
        Toast.makeText(getApplicationContext(),invObj.getEmail_id(),Toast.LENGTH_LONG).show();
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
        idET.setText(String.valueOf(invObj.getId()));
        adhaarNoET.setText(adhaarNoS);
        investigatorId=invObj.getId();
        Log.i("super",String.valueOf(invObj.isSuperuser()));
        Log.i("active",String.valueOf(invObj.isActive()));
        Log.i("staff",String.valueOf(invObj.isStaff()));
        Log.i("gender",String.valueOf(invObj.getGender()));
        setRadioButtonForGender(invObj.getGender());
        setRadioButtonForActive(String.valueOf(invObj.isActive()));
        setRadioButtonForSuperuser(String.valueOf(invObj.isSuperuser()));
        setRadioButtonForStaff(String.valueOf(invObj.isStaff()));

        getInvestigatorAdministrativeTypeDetail();
        getInvestigatorAddress(investigatorId);
    }
    void getInvestigatorDetails(){
        Intent i=getIntent();
        receivedEmail=i.getStringExtra("email");
        Call<InvestigatorDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getInvestigatorProfile(receivedEmail,loggedInToken,loggedInEmail);

        call.enqueue(new Callback<InvestigatorDefaultResponse>() {
            @Override
            public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
                try{

                    if(response.code()==200){
                        InvestigatorDefaultResponse res=response.body();
                        Log.i("falag",String.valueOf(res.isFlag()));
                        if(res.isFlag()){
                            InvestigatorRegisterModel invObj=res.getSerailizedInvestigatorModel().get(0);
                            Toast.makeText(getApplicationContext(),String.valueOf(invObj.getEmail_id()),Toast.LENGTH_LONG).show();
                            setTextInfo(invObj);
                        }else{
                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

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
        Intent i=getIntent();
        receivedEmail=i.getStringExtra("email");
        String phoneNumberR=phoneNumberET.getText().toString().trim();
        String adhaarNoR=adhaarNoET.getText().toString().trim();
        String firstNameR = firstNameET.getText().toString().trim();
        String lastNameR = lastNameET.getText().toString().trim();
        String emailIdR = emailIdET.getText().toString().trim();

        int idGendervalue = genderRG.getCheckedRadioButtonId();
        this.genderValue = findViewById(idGendervalue);
        String genderValueR=genderValue.getText().toString();

        int idSuperuservalue = isSuperuserRG.getCheckedRadioButtonId();
        this.superuserValue = findViewById(idSuperuservalue);
        String superuserValueR=superuserValue.getText().toString();



        int idStaffvalue = isStaffRG.getCheckedRadioButtonId();
        this.staffValue = findViewById(idStaffvalue);
        String staffValueR=staffValue.getText().toString();



        int idActivevalue = isActiveRG.getCheckedRadioButtonId();
        this.activeValue = findViewById(idActivevalue);
        String activeValueR=activeValue.getText().toString();



        String dobR=dob.getText().toString().trim();
         investigatorId=Integer.valueOf(idET.getText().toString());
        boolean test=validation(phoneNumberR,adhaarNoR);

        if(test==true){
            long PhoneNo=Long.parseLong(phoneNumberR);
            long adhaarno=Long.parseLong(adhaarNoR);
            InvestigatorRegisterModel updateObj;
            if(bitmap==null){
                Log.i("in ","bitmap is null");
            updateObj=new InvestigatorRegisterModel(investigatorId,firstNameR,lastNameR,emailIdR,dobR,PhoneNo,genderValueR,adhaarno,Boolean.parseBoolean(superuserValueR),Boolean.parseBoolean(activeValueR),Boolean.parseBoolean(staffValueR),imageNameTV.getText().toString());
            }else{
                Log.i("in ","bitmap is not null");
                String new_profile_image_string=getStringImage(bitmap);
                updateObj=new InvestigatorRegisterModel(investigatorId,firstNameR,lastNameR,emailIdR,dobR,PhoneNo,genderValueR,adhaarno,Boolean.parseBoolean(superuserValueR),Boolean.parseBoolean(activeValueR),Boolean.parseBoolean(staffValueR),new_profile_image_string);
            }
            //updateInvestigatorProfile


            Call<InvestigatorDefaultResponse> call= RetrofitClient
                    .getInstance()
                    .getApi()
                    .updateInvestigatorProfile(receivedEmail,loggedInEmail,loggedInToken,updateObj);
            Log.i("User",updateObj.toString());



            call.enqueue(new Callback<InvestigatorDefaultResponse>() {
                @Override
                public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {



                    try{

                        if(response.code()==200){
                            InvestigatorDefaultResponse res=response.body();
                            InvestigatorRegisterModel invObj=res.getSerailizedInvestigatorModel().get(0);
                            if(res.isFlag()){

                                Toast.makeText(getApplicationContext(),invObj.getEmail_id(),Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                                Log.i("in update","here");


                                if(emailIdR.equals(invObj.getEmail_id())){
                                    Intent i = new Intent(getApplicationContext(), InvestigatorPut.class);
                                    i.putExtra("email",invObj.getEmail_id());
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Unsuccessfull", Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), invObj.getEmail_id(), Toast.LENGTH_LONG).show();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(),invObj.getEmail_id(),Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });


        }else{ Toast.makeText(getApplicationContext(),"Validation Error",Toast.LENGTH_LONG).show();}




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
                          //  Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();
                            setAddressData(obj);

                        }else{

                            addressET.setText(obj.getLocation());
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }

                    }else{

                        Toast.makeText(getApplicationContext(), "Authentication Error", Toast.LENGTH_LONG).show();
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
    void getInvestigatorAdministrativeTypeDetail(){

        Call<InvestigatorDefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .getInvestigatorAdministrativeByEmailFaciltiy(loggedInEmail,loggedInToken,receivedEmail);
        call.enqueue(new Callback<InvestigatorDefaultResponse>(){
            @Override
            public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
                try{
                    if(response.code()==200){
                        InvestigatorDefaultResponse responseObject=response.body();
                        if(responseObject.isFlag()){
                            List<InvestigatorAdministrativeInformationModel> investigatorAdministrativeInformationModels=responseObject.getSerailizedData();
                            InvestigatorAdministrativeInformationModel  obj=investigatorAdministrativeInformationModels.get(0);
                         //   Toast.makeText(getApplicationContext(),String.valueOf(obj.getPosition()),Toast.LENGTH_LONG).show();
                            String id=String.valueOf(obj.getId());
                            InvestigatorAdministrativeTypeDetailEIdT.setText(String.valueOf(id));
                            String salary_amt=String.valueOf(obj.getSalary());
                            salaryET.setText(salary_amt);
                            positionET.setText(obj.getPosition());
                            achivementsET.setText(obj.getAchivements());

                        }else{


                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG);
                        }


                    }else{
                        Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_LONG);
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
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


    void deleteInvestigatorRecord(){


        Intent i=getIntent();
        receivedEmail=i.getStringExtra("email");
        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .deleteInvestigator(receivedEmail,loggedInEmail,loggedInToken);

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject d=response.body();
                    if(d.isFlag()) {
                        //deleteInvestigatorAddressRecord();
                        Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_LONG).show();
                        Intent intent3=new Intent(getApplicationContext(), InvestigatorList.class);
                        startActivity(intent3);

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
    void deleteInvestigatorAddressRecord(){

        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .deleteInvestigatorAddress(loggedInEmail,loggedInToken,Integer.valueOf(idET.getText().toString()));

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject obj=response.body();

                    if(obj.isFlag()){

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



}