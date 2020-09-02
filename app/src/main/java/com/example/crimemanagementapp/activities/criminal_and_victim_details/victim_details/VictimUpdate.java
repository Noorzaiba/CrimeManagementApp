
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
        import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalList;
        import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalUpdate;
        import com.example.crimemanagementapp.activities.map.PermissionsActivity;
        import com.example.crimemanagementapp.api.RetrofitClient;
        import com.example.crimemanagementapp.constants.ApiContants;
        import com.example.crimemanagementapp.model.investigator_details.InvestigatorRegisterModel;
        import com.example.crimemanagementapp.model.miscellaneous.AddressObject;
        import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalDefaultResponse;
        import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalRegisterModel;
        import com.example.crimemanagementapp.model.miscellaneous.AddressObjectDefaultResponse;
        import com.example.crimemanagementapp.model.miscellaneous.DeleteObject;
        import com.example.crimemanagementapp.storage.SharedPrefManager;
        import com.squareup.picasso.Picasso;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class VictimUpdate extends AppCompatActivity {
    private RadioGroup gender;

    private EditText firstNameET,idET,docET,douET,lastNameET,remarksET,idAddressET,emailIdET,phoneNumberET,occupationET,salaryET,ageET,crimeIdET,addressET,cityET,stateET,pincodeET,latitudeET,longitudeET,adhaarNoET;
    private RadioButton genderValue,unknownRB,maleRB,femalRB;
    private Button updateButton,addressByMapButton,addressWithoutMapButton,victimToCriminalAndCriminalToVictimButton,deleteRecord;
    int victim_id;
    String loggedInEmail,loggedInToken;
    ImageView imageIV;
    TextView imageTV,imageNameTV,updateProfileTV;
    private Bitmap bitmap;
    private final String string="Preview  Photo";
    private int ch;
    private int PICK_IMAGE_REQUEST = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_criminal_put);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();

        imageIV=findViewById(R.id.imageIV);
        imageNameTV=findViewById(R.id.imageNameTV);
        imageTV=findViewById(R.id.imageTV);
        updateProfileTV=findViewById(R.id.updateProfileTV);
        updateProfileTV.setText("Update Victim Profile");
        this.idAddressET=findViewById(R.id.idAddressET);
        this.idET=findViewById(R.id.idET);
        this.docET=findViewById(R.id.docET);
        this.douET=findViewById(R.id.douET);
        this.updateButton = findViewById(R.id.updateButton);
        this.firstNameET = findViewById(R.id.firstNameET);
        this.lastNameET = findViewById(R.id.lastNameET);
        this.emailIdET = findViewById(R.id.emailIDET);
        this.phoneNumberET = findViewById(R.id.phoneNumberET);
        this.occupationET = findViewById(R.id.occupationET);
        this.salaryET = findViewById(R.id.salaryET);
        this.ageET = findViewById(R.id.ageET);
        this.crimeIdET = findViewById(R.id.crimeIdET);
        this.gender = findViewById(R.id.genderRG);
        this.addressET = findViewById(R.id.addressET);
        this.unknownRB=findViewById(R.id.unknownRB);
        this.maleRB=findViewById(R.id.maleRB);
        this.femalRB=findViewById(R.id.femaleRB);
        this.cityET = findViewById(R.id.cityET);
        this.stateET = findViewById(R.id.stateET);
        this.pincodeET = findViewById(R.id.pincodeET);
        this.latitudeET = findViewById(R.id.latitudeET);
        this.longitudeET = findViewById(R.id.longitudeET);
        this.adhaarNoET = findViewById(R.id.adhaarNoET);
        this.remarksET = findViewById(R.id.remarksET);
        this.updateButton=findViewById(R.id.updateButton);
        this.victimToCriminalAndCriminalToVictimButton=findViewById(R.id.victimToCriminalAndCriminalToVictimButton);
        this.addressByMapButton = findViewById(R.id.addressByMapButton);
        this.addressWithoutMapButton = findViewById(R.id.addressWithoutMapButton);
        this.addressByMapButton.setText("update Address By Map");
        this.addressWithoutMapButton.setText("update Address without Map");
        this.victimToCriminalAndCriminalToVictimButton.setText("Click to Transfer to Criminal Record");

        this.deleteRecord = findViewById(R.id.deleteRecord);
        this.deleteRecord.setText("Delete Victim Record");

        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteVictimRecord();
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
        victimToCriminalAndCriminalToVictimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addressET.getText().toString().equals("AddressDoesNotExists")){
                    Toast.makeText(getApplicationContext(),"Please Update The Addresss To Transfer",Toast.LENGTH_LONG).show();

                }else{
                Intent intent=new Intent(getApplicationContext(), VictimToCriminalActivity.class);
                intent.putExtra("pk",idET.getText().toString());
                startActivity(intent);
            }}
        });
        addressWithoutMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("criminal_id",String.valueOf(victim_id));
                if (addressET.getText().toString().equals("AddressDoesNotExists")){

                    Intent intent=new Intent(getApplicationContext(),VictimAddressRegister.class);
                    intent.putExtra("resident_id",victim_id);
                    intent.putExtra("fileName","VictimAddressRegister");
                    startActivity(intent);}else{

                    Intent intent=new Intent(getApplicationContext(), VictimAddressUpdate.class);
                intent.putExtra("resident_id",victim_id);
                intent.putExtra("fileName","VictimAddressUpdate");
                startActivity(intent);
            }}
        });

        addressByMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addressET.getText().toString().equals("AddressDoesNotExists")) {

                    Intent intent = new Intent(getApplicationContext(), PermissionsActivity.class);
                    intent.putExtra("resident_id", victim_id);
                    intent.putExtra("fileName", "VictimAddressRegister");
                    startActivity(intent);
                } else {

                    Intent intent=new Intent(getApplicationContext(), PermissionsActivity.class);
                intent.putExtra("resident_id",victim_id);
                intent.putExtra("fileName","VictimAddressUpdate");
                startActivity(intent);
            }}
        });

        getVictimDetails();


        this.updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                putVictimDetails();
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
    void deleteVictimRecord(){

        Call<DeleteObject> call= RetrofitClient
                .getInstance()
                .getApi()
                .victimDeleteFacility(loggedInEmail,loggedInToken,Integer.valueOf(idET.getText().toString()));

        call.enqueue(new Callback<DeleteObject>(){
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject d=response.body();
                    if(d.isFlag()) {
                     //   deleteVictimAddressRecord();
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent3 = new Intent(getApplicationContext(), VictimList.class);
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
    void deleteVictimAddressRecord() {

        Call<DeleteObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteVictimAddress(loggedInEmail, loggedInToken, Integer.valueOf(idET.getText().toString()));

        call.enqueue(new Callback<DeleteObject>() {
            @Override
            public void onResponse(Call<DeleteObject> call, Response<DeleteObject> response) {
                if (response.code() == 200) {
                    DeleteObject obj = response.body();

                    if (obj.isFlag()) {
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent3 = new Intent(getApplicationContext(), VictimList.class);
                        startActivity(intent3);
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

        void putVictimDetails(){
        String idR=idET.getText().toString();
        String docR=docET.getText().toString();
        String douR=douET.getText().toString();
        String firstNameR=firstNameET.getText().toString();
        String lastNameR=lastNameET.getText().toString();
        String emailIdR=emailIdET.getText().toString();
        String phoneNumberR=phoneNumberET.getText().toString();
        String remarksR=remarksET.getText().toString();
        String ageR=ageET.getText().toString();
        String occupationR=occupationET.getText().toString();
        String salaryR=salaryET.getText().toString();
        String crimeIdR=crimeIdET.getText().toString();
        String adhaarNoR=adhaarNoET.getText().toString();
        String idAddressR=idAddressET.getText().toString();
        String addressR=addressET.getText().toString();
        String cityR=cityET.getText().toString();
        String stateR=stateET.getText().toString();
        String pincodeR=pincodeET.getText().toString();
        String latitudeR=latitudeET.getText().toString();
        String longitudeR=longitudeET.getText().toString();
        int idGendervalue = gender.getCheckedRadioButtonId();
        this.genderValue = findViewById(idGendervalue);
        String genderR=genderValue.getText().toString();
            VictimCriminalRegisterModel victimCriminalRegisterModel;

        boolean  test=validation(firstNameR,lastNameR,emailIdR,phoneNumberR,remarksR,ageR,occupationR,salaryR,crimeIdR,adhaarNoR,addressR,cityR,stateR,pincodeR,latitudeR,longitudeR);
        if(test==true){
            if(bitmap==null){
                Log.i("in ","bitmap is null");
       victimCriminalRegisterModel=new VictimCriminalRegisterModel(Integer.parseInt(idR),firstNameR,lastNameR,emailIdR,Integer.parseInt(ageR),Long.parseLong(phoneNumberR),remarksR,genderR,Long.parseLong(salaryR),occupationR,Integer.parseInt(crimeIdR),docR,douR,Long.parseLong(adhaarNoR),imageNameTV.getText().toString());

            }else{
                Log.i("in ","bitmap is not null");
                String new_profile_image_string=getStringImage(bitmap);
                victimCriminalRegisterModel=new VictimCriminalRegisterModel(Integer.parseInt(idR),firstNameR,lastNameR,emailIdR,Integer.parseInt(ageR),Long.parseLong(phoneNumberR),remarksR,genderR,Long.parseLong(salaryR),occupationR,Integer.parseInt(crimeIdR),docR,douR,Long.parseLong(adhaarNoR),new_profile_image_string);
            }
            Log.i("values",victimCriminalRegisterModel.toString());
            Call<VictimCriminalDefaultResponse> call= RetrofitClient

                    .getInstance()
                    .getApi()
                    .victimPutDetailFacility(loggedInEmail,loggedInToken,Integer.parseInt(idR),victimCriminalRegisterModel);

            call.enqueue(new Callback<VictimCriminalDefaultResponse>() {
                @Override
                public void onResponse(Call<VictimCriminalDefaultResponse> call, Response<VictimCriminalDefaultResponse> response) {


                    try{
                        if(response.code()==200){
                            VictimCriminalDefaultResponse res=response.body();
                            if(res.isFlag()){

                                VictimCriminalRegisterModel obj=res.getVictimCriminalRegisterModelList().get(0);


                                if("Invalid_Crime_id".equals(obj.getEmail())){
                                    Toast.makeText(getApplicationContext(),"Invalid Crime id",Toast.LENGTH_LONG).show();

                                }else{


                                    Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                                    Intent i=new Intent(getApplicationContext(), VictimUpdate.class);
                                    i.putExtra("pk",Integer.parseInt(idET.getText().toString()));
                                    startActivity(i);
                                }

                            }else{
                                Toast.makeText(VictimUpdate.this," Error Occured",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(VictimUpdate.this,"Authentication Error",Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e){
                        Toast.makeText(VictimUpdate.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<VictimCriminalDefaultResponse> call, Throwable t) {
                    Toast.makeText(VictimUpdate.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(VictimUpdate.this,"Validation Pending",Toast.LENGTH_LONG).show();
        }

    }


    void getVictimDetails() {
        Intent intent = getIntent();
        final int pk = intent.getIntExtra("pk", 0);

        Call<VictimCriminalDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .victimGETDetailFacility(loggedInEmail,loggedInToken,pk);

        call.enqueue(new Callback<VictimCriminalDefaultResponse>() {
            @Override
            public void onResponse(Call<VictimCriminalDefaultResponse> call, Response<VictimCriminalDefaultResponse> response) {

                try{

                    if(response.code()==200){
                        VictimCriminalDefaultResponse res=response.body();
                        if(res.isFlag()){
                            List<VictimCriminalRegisterModel> victimCriminalRegisterModelList=res.getVictimCriminalRegisterModelList();
                          //  Toast.makeText(VictimUpdate.this,String.valueOf(victimCriminalRegisterModelList.get(0).getId()),Toast.LENGTH_LONG).show();
                            idET.setText(String.valueOf(victimCriminalRegisterModelList.get(0).getId()));

                            victim_id=victimCriminalRegisterModelList.get(0).getId();
                            docET.setText(victimCriminalRegisterModelList.get(0).getDoc());
                            douET.setText(victimCriminalRegisterModelList.get(0).getDou());
                            Log.i("real value",victimCriminalRegisterModelList.get(0).getGender());
                            setRadioButton(victimCriminalRegisterModelList.get(0).getGender());


                            firstNameET.setText(victimCriminalRegisterModelList.get(0).getFirst_name());
                            lastNameET.setText(victimCriminalRegisterModelList.get(0).getLast_name());
                            emailIdET.setText(victimCriminalRegisterModelList.get(0).getEmail());
                            occupationET.setText(victimCriminalRegisterModelList.get(0).getOccupation());
                            phoneNumberET.setText(String.valueOf(victimCriminalRegisterModelList.get(0).getPhone_no()));
                            remarksET.setText(victimCriminalRegisterModelList.get(0).getRemarks());
                            salaryET.setText(String.valueOf(victimCriminalRegisterModelList.get(0).getSalary()));
                            crimeIdET.setText(String.valueOf(victimCriminalRegisterModelList.get(0).getCrime_id()));
                            ageET.setText(String.valueOf(victimCriminalRegisterModelList.get(0).getAge()));
                            adhaarNoET.setText(String.valueOf(victimCriminalRegisterModelList.get(0).getAdhaar_no()));
                            Picasso.get()
                                    .load(ApiContants.VICTIM_CRIMINAL+victimCriminalRegisterModelList.get(0).getProfile_image())
                                    .resize(50, 50)
                                    .centerCrop()
                                    .into(imageIV);
                            imageNameTV.setText(victimCriminalRegisterModelList.get(0).getProfile_image());
                            getVictimAddress(victim_id);


                        }else{


                            Toast.makeText(VictimUpdate.this,"Error Occured",Toast.LENGTH_LONG).show();

                        }


                    }else{
                        Toast.makeText(VictimUpdate.this,"Authentication Error",Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){
                    Toast.makeText(VictimUpdate.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<VictimCriminalDefaultResponse> call, Throwable t) {
                Toast.makeText(VictimUpdate.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    boolean validation(String firstNameR,String lastNameR,String emailIdR, String phoneNumberR,String remarksR,String ageR,String occupationR,String salaryR,String crimeIdR, String adhaarNoR, String addressR,String cityR,String stateR,String pincodeR,String latitudeR,String longitudeR){

        if (firstNameR.isEmpty()){
            this.firstNameET.setError("First Name is required if not known add unknown  as value");
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
        if (phoneNumberR.length()==1){

        }
        else{
            if(phoneNumberR.length()<10){
                this.phoneNumberET.setError("phone number is required");
                this.phoneNumberET.requestFocus();
                return false;}}



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

    public void setRadioButton(String gender_value){


        Log.i("real-vaue",gender_value);

        Log.i("male",String.valueOf(this.maleRB.getText().equals(gender_value)));
        Log.i("female",String.valueOf(this.femalRB.getText().equals(gender_value)));
        Log.i("unknown",String.valueOf(this.unknownRB.getText().equals(gender_value)));
        if(this.maleRB.getText().equals(gender_value)){
            this.unknownRB.setChecked(false);
            this.femalRB.setChecked(false);
            this.maleRB.setChecked(true);
        }
        if(this.femalRB.getText().equals(gender_value)){
            this.unknownRB.setChecked(false);
            this.femalRB.setChecked(true);
            this.maleRB.setChecked(false);
        }
        if(this.unknownRB.getText().equals(gender_value)){
            this.unknownRB.setChecked(true);
            this.femalRB.setChecked(false);
            this.maleRB.setChecked(false);
        }




    }
    void getVictimAddress(int resident_id){
        Call<AddressObjectDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetailVictimAddress(loggedInEmail,loggedInToken,resident_id);

        call.enqueue(new Callback<AddressObjectDefaultResponse>(){
            @Override
            public void onResponse(Call<AddressObjectDefaultResponse> call, Response<AddressObjectDefaultResponse> response) {
                try{
                    if(200==response.code()) {

                        AddressObjectDefaultResponse res= response.body();
                      //  Toast.makeText(getApplicationContext(), String.valueOf(res.isFlag()), Toast.LENGTH_LONG).show();
                        AddressObject   obj= res.getSerailizedData().get(0);
                        if(res.isFlag()){

                        Log.i("address id",String.valueOf(obj.getId()));
                            idAddressET.setText(String.valueOf(obj.getId()));
                                addressET.setText(obj.getLocation());
                                cityET.setText(obj.getCity());
                                stateET.setText(obj.getState());
                                pincodeET.setText(String.valueOf(obj.getZipCode()));
                                longitudeET.setText(String.valueOf(obj.getLongitude()));
                                latitudeET.setText(String.valueOf(obj.getLongitude()));




                        //    Toast.makeText(getApplicationContext(),String.valueOf(obj.getId()), Toast.LENGTH_LONG).show();

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


