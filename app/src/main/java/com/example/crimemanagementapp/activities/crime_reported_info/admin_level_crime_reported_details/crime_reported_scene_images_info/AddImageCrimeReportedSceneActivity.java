package com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details.crime_reported_scene_images_info;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.cases_information.CrimePut;
import com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details.CrimeReportedUpdateDetails;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedSceneDefaultResponse;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedScenePicturesModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImageCrimeReportedSceneActivity extends AppCompatActivity {
    private Button registerButton ,backButton;
    ImageView imageIV;
    TextView imageTV;
    private Bitmap bitmap;
    EditText crimeIdET;
    private final String string="Preview  Photo";
    private int ch;
    private int PICK_IMAGE_REQUEST = 1;
    String loggedInEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_crime_reported_scene);
        imageIV=findViewById(R.id.imageIV);
        imageTV=findViewById(R.id.imageTV);
        crimeIdET=findViewById(R.id.crimeIdET);
        this.registerButton=findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();

        Intent i=getIntent();
        String crime_id=i.getStringExtra("crime_id");
        crimeIdET.setText(crime_id);


        this.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), CrimeReportedUpdateDetails.class);
                i.putExtra("pk",Integer.parseInt(crime_id));
                startActivity(i);
                finish();


            }
        });
        this.registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                register();

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

            if (ch==1){
                bitmap=getBitmap(filePath, imageIV,imageTV);
            }

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
    private boolean validation() {


        if (bitmap == null) {
            Toast.makeText(getApplicationContext(), "Please select the profile image", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void register(){
        String crimeId=crimeIdET.getText().toString().trim();


        boolean test=validation();

        if(test==true){

            String imgOne=getStringImage(bitmap);
            CrimeReportedScenePicturesModel obj=new CrimeReportedScenePicturesModel(crimeId,imgOne);
            Call<CrimeReportedSceneDefaultResponse> call= RetrofitClient
                    .getInstance()
                    .getApi()

                    . crimeReportingSceneImageRegisterFacility(loggedInEmail,obj);


            call.enqueue(new Callback<CrimeReportedSceneDefaultResponse>() {
                @Override
                public void onResponse(Call<CrimeReportedSceneDefaultResponse> call, Response<CrimeReportedSceneDefaultResponse> response) {


                    if(200==response.code()){
                        CrimeReportedSceneDefaultResponse res=response.body();
                        Toast.makeText(getApplicationContext(),String.valueOf(res.isFlag()),Toast.LENGTH_LONG).show();

                        if(res.isFlag()){
                            CrimeReportedScenePicturesModel obj=res.getSerialized_data().get(0);
                            Toast.makeText(getApplicationContext(),obj.getImage_name(),Toast.LENGTH_LONG).show();
                            if(crimeIdET.getText().toString().equals(obj.getCrime_id())){
                                Toast.makeText(getApplicationContext(),"successfull",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(), CrimeReportedImageDetailSceneForAdmin.class);
                                i.putExtra("pk",obj.getId());
                                startActivity(i);
                                finish();


                            }else{
                                Toast.makeText(getApplicationContext(),"Unsuccessfull",Toast.LENGTH_LONG).show(); }

                        }else{
                            Toast.makeText(getApplicationContext(),"Error Error",Toast.LENGTH_LONG).show();
                        }
                    }else{

                        Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_LONG).show();
                    }



                }

                @Override
                public void onFailure(Call<CrimeReportedSceneDefaultResponse> call, Throwable t) {
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






