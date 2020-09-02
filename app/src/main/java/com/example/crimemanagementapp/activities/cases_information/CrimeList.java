package com.example.crimemanagementapp.activities.cases_information;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.crimemanagementapp.R;

import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.cases_information.CrimeDefaultResponse;
import com.example.crimemanagementapp.model.cases_information.CrimeRegisterModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CrimeList extends AppCompatActivity {
    ListView listView;
    String description_list [];
    int  crime_id_list [];
    List<CrimeRegisterModel> crime_list;
    int crime_list_size;
    String loggedInEmail,loggedInToken;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_list);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
        Log.i("token",loggedInToken+"yes");
        listView = findViewById(R.id.listView);

     Call<CrimeDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .crimeRegisterGETFacility(loggedInEmail,loggedInToken);
        call.enqueue(new Callback<CrimeDefaultResponse>(){
            @Override
            public void onResponse(Call<CrimeDefaultResponse> call, Response<CrimeDefaultResponse> response) {

       try{
            if(200==response.code()){
             CrimeDefaultResponse crimeDefaultResponse=response.body();
             crime_list=crimeDefaultResponse.getSerialized_data_crime_register();
             crime_list_size=crime_list.size();

             description_list= new String[crime_list_size];
             crime_id_list =new int[crime_list_size];

             for(int i=0;i<crime_list.size();i++){
                description_list[i]=crime_list.get(i).getDescription();
                crime_id_list[i]=crime_list.get(i).getId();
             }

                for(int i=0;i<crime_list.size();i++){
                    Log.i("i",description_list[i]);

                }

                Log.i("list",String.valueOf(crime_list.size()));
            // Toast.makeText(CrimeList.this,crime_list.get(0).getTypeOfCrime(),Toast.LENGTH_LONG).show();

                CustomAdapter customAdapter=new CustomAdapter();
                listView.setAdapter(customAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        TextView crimeIDET=view.findViewById(R.id.idET);
                        Intent i=new Intent(getApplicationContext(),CrimePut.class);
                        i.putExtra("pk",Integer.parseInt(crimeIDET.getText().toString()));
                        startActivity(i);

                    }
                });







    }else{
        Toast.makeText(CrimeList.this,"Authentication Error",Toast.LENGTH_LONG).show();

    }
       }catch(Exception e){
           Toast.makeText(CrimeList.this,e.getMessage(),Toast.LENGTH_LONG).show();



       }


            }

            @Override
            public void onFailure(Call<CrimeDefaultResponse> call, Throwable t) {

                Toast.makeText(CrimeList.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });





    }


    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return crime_list_size;
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
            // convertView = LayoutInflater.from(CrimeList.this).inflate(R.layout.activity_crime_list_item, parent, false);
            convertView=getLayoutInflater().inflate(R.layout.activity_crime_list_item,null);
           TextView idET = convertView.findViewById(R.id.idET);
           TextView descriptionET = convertView.findViewById(R.id.descriptionET);
            Log.i("tagging",String.valueOf(crime_id_list[position]));
            idET.setText(String.valueOf(crime_id_list[position]));
            descriptionET.setText(description_list[position]);
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
