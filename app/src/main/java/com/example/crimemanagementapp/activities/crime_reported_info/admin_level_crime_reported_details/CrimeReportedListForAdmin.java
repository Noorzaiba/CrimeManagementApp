package com.example.crimemanagementapp.activities.crime_reported_info.admin_level_crime_reported_details;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedByPublicModel;
import com.example.crimemanagementapp.model.crime_reported_details.CrimeReportedDefaultResponse;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeReportedListForAdmin extends AppCompatActivity {
    ListView listView;
    int size;
    int id_list[];
    String description_list[];
    String type_list[];
    String loggedInEmail;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_reported_list);
        listView=findViewById(R.id.listView);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        Call<CrimeReportedDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllCrimeReporedAllFacility(loggedInEmail);
        call.enqueue(new Callback<CrimeReportedDefaultResponse>(){
            @Override
            public void onResponse(Call<CrimeReportedDefaultResponse> call, Response<CrimeReportedDefaultResponse> response) {

                try{

                    if(response.code()==200){
                        CrimeReportedDefaultResponse res=response.body();
                        if(res.isFlag()){
                            List<CrimeReportedByPublicModel> obj=res.getSerialized_data_crime_reported();
                            size=obj.size();
                            description_list=new String[size ];
                            id_list=new int[ size];
                            type_list=new String[size];
                            for(int i=0;i<size;i++){

                                description_list[i]=obj.get(i).getDescription();
                                id_list[i]=obj.get(i).getId();
                                type_list[i]=obj.get(i).getTypeOfCrime();

                            }
                            CrimeReportedListForAdmin.CustomAdapter customAdapter=new CrimeReportedListForAdmin.CustomAdapter();
                            listView.setAdapter(customAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    TextView idET = view.findViewById(R.id.idET);
                                    Intent i = new Intent(getApplicationContext(), CrimeReportedUpdateDetails.class);
                                    i.putExtra("pk", Integer.parseInt(idET.getText().toString()));
                                    startActivity(i);

                                }
                            });
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
            public void onFailure(Call<CrimeReportedDefaultResponse> call, Throwable t) {
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

            convertView=getLayoutInflater().inflate(R.layout.activity_crime_reported_list_item,null);
            TextView idET = convertView.findViewById(R.id.idET);
            TextView typeET = convertView.findViewById(R.id.typeET);
            TextView descriptionET = convertView.findViewById(R.id.descriptionET);
            Log.i("tagging",String.valueOf(id_list[position]));
            idET.setText(String.valueOf(id_list[position]));
            descriptionET.setText(String.valueOf(description_list[position]));
            typeET.setText(type_list[position]);
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