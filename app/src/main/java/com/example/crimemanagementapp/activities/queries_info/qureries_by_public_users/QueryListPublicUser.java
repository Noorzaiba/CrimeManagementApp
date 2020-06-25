package com.example.crimemanagementapp.activities.queries_info.qureries_by_public_users;

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
import com.example.crimemanagementapp.model.query_reporting_contact_us.InvestigatorContactUsDefaultResponse;
import com.example.crimemanagementapp.model.query_reporting_contact_us.InvestigatorContactUsModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryListPublicUser extends AppCompatActivity {
    ListView listView;
    int size;
    int id_list[];
    String status_list[];
    String email_list[];
    String loggedInEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_list_public_user);
        listView=findViewById(R.id.listView);
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        Call<InvestigatorContactUsDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllQueryFacilityOfPublicUser(loggedInEmail);
        call.enqueue(new Callback<InvestigatorContactUsDefaultResponse>(){
            @Override
            public void onResponse(Call<InvestigatorContactUsDefaultResponse> call, Response<InvestigatorContactUsDefaultResponse> response) {

                try{

                    if(response.code()==200){
                        InvestigatorContactUsDefaultResponse res=response.body();
                        if(res.isFlag()){
                            List<InvestigatorContactUsModel> obj=res.getSerailizedData();
                            size=obj.size();
                            status_list=new String[size ];
                            id_list=new int[ size];
                            email_list=new String[size];
                            for(int i=0;i<size;i++){

                                status_list[i]=obj.get(i).getStatus();
                                id_list[i]=obj.get(i).getId();
                                email_list[i]=obj.get(i).getEmail();

                            }
                            QueryListPublicUser.CustomAdapter customAdapter=new QueryListPublicUser.CustomAdapter();
                            listView.setAdapter(customAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    TextView idET = view.findViewById(R.id.idET);
                                    Intent i = new Intent(getApplicationContext(), QueryDetailPublicUser.class);
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
            public void onFailure(Call<InvestigatorContactUsDefaultResponse> call, Throwable t) {
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

            convertView=getLayoutInflater().inflate(R.layout.activity_query_list_item,null);
            TextView idET = convertView.findViewById(R.id.idET);
            TextView emailET = convertView.findViewById(R.id.emailET);
            TextView statusET = convertView.findViewById(R.id.statusET);
            Log.i("tagging",String.valueOf(id_list[position]));
            idET.setText(String.valueOf(id_list[position]));
            emailET.setText(String.valueOf(email_list[position]));
            statusET.setText(status_list[position]);
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