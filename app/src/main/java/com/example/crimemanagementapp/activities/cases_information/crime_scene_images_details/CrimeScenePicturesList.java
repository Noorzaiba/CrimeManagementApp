package com.example.crimemanagementapp.activities.cases_information.crime_scene_images_details;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.cases_information.CrimePut;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalList;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalUpdate;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.cases_information.CrimeScenePicturesDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeScenePicturesModel;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalDefaultResponse;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalRegisterModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeScenePicturesList extends AppCompatActivity {
    int id_list[];
    String name_list[];
    int crime_id[];
    Button backButton;
    int size;
    ListView listView;
    String loggedInEmail,loggedInToken;
    String image_list[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_scene_pictures_list);
        listView=findViewById(R.id.listView);
        backButton = findViewById(R.id.backButton);

        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken= SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();

         Intent i=getIntent();
         int crime_id_received=Integer.valueOf(i.getStringExtra("crime_id"));
        this.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), CrimePut.class);
                i.putExtra("pk",crime_id_received);
                startActivity(i);
                finish();


            }
        });
        Call<CrimeScenePicturesDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .crimeSceneImageGetAll(loggedInEmail,loggedInToken,crime_id_received);

        call.enqueue(new Callback<CrimeScenePicturesDefaultResponse>(){
            @Override
            public void onResponse(Call<CrimeScenePicturesDefaultResponse> call, Response<CrimeScenePicturesDefaultResponse> response) {
                try{

                    if(response.code()==200){
                        CrimeScenePicturesDefaultResponse res=response.body();
                        if(res.isFlag()){

                            List<CrimeScenePicturesModel> obj_list=res.getSerialized_data();
                            size=obj_list.size();
                            crime_id=new int[size ];
                            id_list=new int[ size];
                            name_list=new String[size];
                            image_list=new String[size];
                            for(int i=0;i<size;i++){

                                crime_id[i]=Integer.valueOf(obj_list.get(i).getCrime_id());
                                id_list[i]=obj_list.get(i).getId();
                                name_list[i]=obj_list.get(i).getImage_name();
                                image_list[i]=obj_list.get(i).getImage_name();

                            }
                           CrimeScenePicturesList.CustomAdapter customAdapter=new CrimeScenePicturesList.CustomAdapter();
                            listView.setAdapter(customAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    TextView idET=view.findViewById(R.id.idET);
                                    Intent i=new Intent(getApplicationContext(), CrimeSceneDetailActivity.class);
                                    i.putExtra("pk",Integer.parseInt(idET.getText().toString()));
                                    startActivity(i);
                                    finish();

                                }
                            });




                           // Toast.makeText(getApplicationContext(),String.valueOf(obj_list.get(0).getId()),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"No records Found",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CrimeScenePicturesDefaultResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });





    }




    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=getLayoutInflater().inflate(R.layout.activity_crime_scene_pictures_list_item,null);
            TextView idET = convertView.findViewById(R.id.idET);
            TextView crime_idET = convertView.findViewById(R.id.crimeIdET);
            TextView nameET = convertView.findViewById(R.id.nameET);
            ImageView imageIV=convertView.findViewById(R.id.imageIV);
            Log.i("tagging",String.valueOf(id_list[position]));
            idET.setText(String.valueOf(id_list[position]));
            crime_idET.setText(String.valueOf(crime_id[position]));
            nameET.setText(name_list[position]);

            imageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog =new Dialog(CrimeScenePicturesList.this);
                    dialog.setContentView(R.layout.activity_pop_up_image);
                    dialog.setTitle("Image");
                    EditText namePOP=(EditText) dialog.findViewById(R.id.namePOPET);
                    ImageView image=(ImageView) dialog.findViewById(R.id.imagePOPIV);
                    Button okButton=(Button) dialog.findViewById(R.id.okButton);
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }});
                    namePOP.setText( nameET.getText().toString());
                    Picasso.get()
                            .load(ApiContants.CRIME+image_list[position])
                            .resize(70, 70)
                            .centerCrop()
                            .into(image);
                    dialog.show();


                }
            });


            Picasso.get()
                    .load(ApiContants.CRIME+image_list[position])
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageIV);

            return convertView;


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