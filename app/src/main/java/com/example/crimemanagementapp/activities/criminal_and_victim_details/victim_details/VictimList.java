package com.example.crimemanagementapp.activities.criminal_and_victim_details.victim_details;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.activities.Investigator_details.investigator_accounts.LoginActivity;
import com.example.crimemanagementapp.activities.criminal_and_victim_details.criminal_details.CriminalList;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalDefaultResponse;
import com.example.crimemanagementapp.model.criminal_and_victim_details.VictimCriminalRegisterModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VictimList extends AppCompatActivity {
    int id_list[];
    String name_list[];
    int crime_id[];
    int size;
    TextView listTV;
    ListView listView;
    String image_list[];
    String loggedInEmail,loggedInToken;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_criminal_list);
        listTV=findViewById(R.id.listTV);
        listTV.setText("Victim List");
        loggedInEmail= SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        loggedInToken=SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken();

        listView=findViewById(R.id.listView);
        Call<VictimCriminalDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .victimGETFacility(loggedInEmail,loggedInToken);

        call.enqueue(new Callback<VictimCriminalDefaultResponse>(){
            @Override
            public void onResponse(Call<VictimCriminalDefaultResponse> call, Response<VictimCriminalDefaultResponse> response) {
                try{

                    if(response.code()==200){
                        VictimCriminalDefaultResponse res=response.body();
                        if(res.isFlag()){

                            List<VictimCriminalRegisterModel> victimCriminalRegisterModelList=res.getVictimCriminalRegisterModelList();
                            size=victimCriminalRegisterModelList.size();
                            crime_id=new int[size ];
                            id_list=new int[ size];
                            name_list=new String[size];
                            image_list=new String[size];
                            for(int i=0;i<size;i++){

                                crime_id[i]=victimCriminalRegisterModelList.get(i).getCrime_id();
                                id_list[i]=victimCriminalRegisterModelList.get(i).getId();
                                name_list[i]=victimCriminalRegisterModelList.get(i).getFirst_name();
                                image_list[i]=victimCriminalRegisterModelList.get(i).getProfile_image();

                            }
                            CustomAdapter customAdapter=new CustomAdapter();
                            listView.setAdapter(customAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    TextView idET=view.findViewById(R.id.idET);
                                    Intent i=new Intent(getApplicationContext(), VictimUpdate.class);
                                    i.putExtra("pk",Integer.parseInt(idET.getText().toString()));
                                    startActivity(i);

                                }
                            });




                           // Toast.makeText(VictimList.this,String.valueOf(victimCriminalRegisterModelList.get(0).getId()),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(VictimList.this,"No records Found",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(VictimList.this,"Authentication Error",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){

                    Toast.makeText(VictimList.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<VictimCriminalDefaultResponse> call, Throwable t) {

                Toast.makeText(VictimList.this,t.getMessage(),Toast.LENGTH_LONG).show();
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

            convertView=getLayoutInflater().inflate(R.layout.activity_victim_criminal_list_item,null);
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
                    final Dialog dialog =new Dialog(VictimList.this);
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
                            .load(ApiContants.VICTIM_CRIMINAL+image_list[position])
                            .resize(70, 70)
                            .centerCrop()
                            .into(image);
                    dialog.show();


                }
            });

            Picasso.get()
                    .load(ApiContants.VICTIM_CRIMINAL+image_list[position])
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