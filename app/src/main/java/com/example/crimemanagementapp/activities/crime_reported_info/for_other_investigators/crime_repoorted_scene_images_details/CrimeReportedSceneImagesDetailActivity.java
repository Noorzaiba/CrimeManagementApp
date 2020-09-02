package com.example.crimemanagementapp.activities.crime_reported_info.for_other_investigators.crime_repoorted_scene_images_details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedSceneDefaultResponse;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedScenePicturesModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeReportedSceneImagesDetailActivity extends AppCompatActivity {

    private Button backButton;
    ImageView imageIV;
    TextView imageTV;
    EditText crimeIdET,imageNameET;


    String loggedInEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_reported_scene_images_detail);
        imageIV=findViewById(R.id.imageIV);
        imageTV=findViewById(R.id.imageTV);
        imageNameET=findViewById(R.id.imageNameET);
        crimeIdET=findViewById(R.id.crimeIdET);

        this.backButton=findViewById(R.id.backButton);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();



        getImageDetails();


        this.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), CrimeReportedSceneImagesListActivity.class);
                intent.putExtra("crime_id",crimeIdET.getText().toString());
                startActivity(intent);

            }
        });

    }





    private void getImageDetails(){
        Intent i=getIntent();
        int pk=i.getIntExtra("pk",0);

        boolean final_submit=i.getBooleanExtra("final_submit",false);
        Log.i("in delete",String.valueOf(final_submit));

        Call<CrimeReportedSceneDefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                . crimeReportedSceneGetDetailImage(loggedInEmail,pk);


        call.enqueue(new Callback<CrimeReportedSceneDefaultResponse>() {
            @Override
            public void onResponse(Call<CrimeReportedSceneDefaultResponse> call, Response<CrimeReportedSceneDefaultResponse> response) {


                if(200==response.code()){
                    CrimeReportedSceneDefaultResponse res=response.body();
                  //  Toast.makeText(getApplicationContext(),String.valueOf(res.isFlag()),Toast.LENGTH_LONG).show();

                    if(res.isFlag()){
                        CrimeReportedScenePicturesModel obj=res.getSerialized_data().get(0);
                      //  Toast.makeText(getApplicationContext(),obj.getImage_name(),Toast.LENGTH_LONG).show();
                        Picasso.get()
                                .load(ApiContants.CRIME_REPORTED_SECENE_IMAGES+obj.getImage_name())
                                .resize(50, 50)
                                .centerCrop()
                                .into(imageIV);
                        crimeIdET.setText(obj.getCrime_id());
                        imageNameET.setText(obj.getImage_name());

                    }else{
                        Toast.makeText(getApplicationContext()," Error Occured",Toast.LENGTH_LONG).show();
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






