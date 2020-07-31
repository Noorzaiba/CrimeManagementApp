    package com.example.crimemanagementapp.activities.cases_information.crime_live_updates_details;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.BaseAdapter;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;
    import com.example.crimemanagementapp.R;

    import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
    import com.example.crimemanagementapp.activities.cases_information.CrimePut;
    import com.example.crimemanagementapp.api.RetrofitClient;
    import com.example.crimemanagementapp.model.cases_information.CrimeDefaultResponse;
    import com.example.crimemanagementapp.model.cases_information.CrimeLiveUpdationModel;
    import com.example.crimemanagementapp.storage.SharedPrefManager;


    import java.util.List;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;



    public class CrimeLiveUpdateList extends AppCompatActivity {
        String statement_list[];
        int id_list[];
        String loggedInEmail,loggedInToken;
        ListView listView;
        Button backButton;
        int crime_id_size = 0;
        int z=0;

        List<CrimeLiveUpdationModel> crime_live_update_list;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_crime_live_update_list);
            listView = findViewById(R.id.listView);
            loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
            loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();
            backButton = findViewById(R.id.backButton);


            Intent intent = getIntent();
            final int pk = intent.getIntExtra("crime_id_para", 0);
            this.backButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(getApplicationContext(), CrimePut.class);
                    i.putExtra("pk",pk);
                    startActivity(i);
                    finish();


                }
            });
            Toast.makeText(CrimeLiveUpdateList.this, String.valueOf(pk), Toast.LENGTH_LONG).show();
            Call<CrimeDefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .crimeLiveUpdateGetFacility(loggedInEmail,loggedInToken);
            call.enqueue(new Callback<CrimeDefaultResponse>() {
                             @Override
                             public void onResponse(Call<CrimeDefaultResponse> call, Response<CrimeDefaultResponse> response) {
    try {

        if (200 == response.code()) {

            CrimeDefaultResponse crimeDefaultResponse = response.body();
            crime_live_update_list = crimeDefaultResponse.getSerialized_data_crime_live_update();

            Intent intent = getIntent();
           int pk = intent.getIntExtra("crime_id_para", 0);

            //  Log.i("pk", String.valueOf(pk));
            // Log.i("initail crime size", String.valueOf(crime_id_size));

            for (int i = 0; i < crime_live_update_list.size(); i++) {
                if (pk == crime_live_update_list.get(i).getCrimeId()) {
                    crime_id_size = crime_id_size + 1;
                }
            }


            //  Log.i("after loop crime size", String.valueOf(crime_id_size));

            statement_list = new String[crime_id_size];
            id_list = new int[crime_id_size];

            for (int i = 0; i < crime_live_update_list.size(); i++) {

                if (pk == crime_live_update_list.get(i).getCrimeId()) {

                    statement_list[z] = crime_live_update_list.get(i).getComments();
                    id_list[z] = crime_live_update_list.get(i).getId();
                    //  Log.i("statemnts Value "+i,statement_list[z]);
                    //   Log.i("statemnts in loop "+i,crime_live_update_list.get(i).getComments());
                    //  Log.i("crime ids in loop "+i,String.valueOf(crime_live_update_list.get(i).getCrimeId()));

                    z = z + 1;
                }

            }


            for (int j = 0; j < crime_id_size; j++) {
                Log.i("statements", statement_list[j]);
                Log.i("ids", String.valueOf(id_list[j]));
            }



        }else{
            Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG).show();
        }

        if (crime_id_size > -1) {

            CrimeLiveUpdateList.CustomAdapter customAdapter = new CrimeLiveUpdateList.CustomAdapter();
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView crimeIDET=view.findViewById(R.id.idET);
                    Intent i=new Intent(getApplicationContext(), CrimeLiveUpdatePut.class);

                    i.putExtra("intent_id",String.valueOf(id_list[position]));
                    startActivity(i);
                    finish();

                }
            });

        }


        //   Toast.makeText(CrimeLiveUpdateList.this, crime_live_update_list.get(0).getComments(), Toast.LENGTH_LONG).show();


        else {
            Toast.makeText(CrimeLiveUpdateList.this, "error occured", Toast.LENGTH_LONG).show();

        }


    } catch (Exception e) {

        Toast.makeText(CrimeLiveUpdateList.this, "l "+e.getMessage() + " L", Toast.LENGTH_LONG).show();

    }
                             }


                             @Override
                             public void onFailure(Call<CrimeDefaultResponse> call, Throwable t) {

                                 Toast.makeText(CrimeLiveUpdateList.this, t.getMessage(), Toast.LENGTH_LONG).show();

                             }
                         }

            );


        }


        class CustomAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return crime_id_size;
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
                convertView = getLayoutInflater().inflate(R.layout.activity_crime_live_update_list_item, null);
                TextView idET = convertView.findViewById(R.id.idET);
                TextView statementET = convertView.findViewById(R.id.statementET);
                Log.i("tagging", String.valueOf(id_list[position]));
                idET.setText(String.valueOf(id_list[position]));
                statementET.setText(statement_list[position]);
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

