package com.example.crimemanagementapp.activities.Investigator_details.investigator_administrative_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimemanagementapp.R;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorAdministrativeInformationModel;
import com.example.crimemanagementapp.model.investigator_details.InvestigatorDefaultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestigatorAdministrativeInformationList  extends AppCompatActivity {
    ListView listView;
    int investigator_admin_list_size;
    String position_list[];
    String id_list[];
    String email_list[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_administrative_information_list);
        listView = findViewById(R.id.listView);
        Call<InvestigatorDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllInvestigatorAdministrativeFaciltiy();
        call.enqueue(new Callback<InvestigatorDefaultResponse>(){
            @Override
            public void onResponse(Call<InvestigatorDefaultResponse> call, Response<InvestigatorDefaultResponse> response) {
                try{
                    if(200==response.code()){
                        InvestigatorDefaultResponse res=response.body();
                        if(res.isFlag()){

                            List<InvestigatorAdministrativeInformationModel> investigatorAdministrativeInformationModelsList=res.getSerailizedData();
                            investigator_admin_list_size=investigatorAdministrativeInformationModelsList.size();
                            position_list=new String[investigator_admin_list_size];
                            id_list=new String[investigator_admin_list_size];
                            email_list=new String[investigator_admin_list_size];
                            for(int i=0;i<investigator_admin_list_size;i++){
                                id_list[i]=String.valueOf(investigatorAdministrativeInformationModelsList.get(i).getId());
                                email_list[i]=String.valueOf(investigatorAdministrativeInformationModelsList.get(i).getEmail());
                                position_list[i]=String.valueOf(investigatorAdministrativeInformationModelsList.get(i).getPosition());

                            }

                            CustomAdapter customAdapter=new CustomAdapter();
                            listView.setAdapter(customAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    TextView idTV=view.findViewById(R.id.idTV);
                                    Intent i=new Intent(getApplicationContext(), InvestigatorAdministrativeInformationUpdate.class);
                                    i.putExtra("pk",Integer.parseInt(idTV.getText().toString()));
                                    startActivity(i);

                                }
                            });




                            Toast.makeText(InvestigatorAdministrativeInformationList.this,String.valueOf(investigatorAdministrativeInformationModelsList.size()),Toast.LENGTH_LONG).show();
                            InvestigatorAdministrativeInformationModel  obj=investigatorAdministrativeInformationModelsList.get(0);
                            Toast.makeText(InvestigatorAdministrativeInformationList.this,String.valueOf(obj.getPosition()),Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(InvestigatorAdministrativeInformationList.this,"Error Occured",Toast.LENGTH_LONG);
                        }




                    }else{
                        Toast.makeText(InvestigatorAdministrativeInformationList.this,"Technical problem",Toast.LENGTH_LONG);
                    }

                }catch(Exception e){
                    Toast.makeText(InvestigatorAdministrativeInformationList.this,e.getMessage(),Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onFailure(Call<InvestigatorDefaultResponse> call, Throwable t) {

            }
        });





    }




    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return investigator_admin_list_size;
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

            convertView=getLayoutInflater().inflate(R.layout.activity_investigator_administrative_information_list_item,null);
            TextView idTV = convertView.findViewById(R.id.idTV);
            TextView positionTV = convertView.findViewById(R.id.positionTV);
            TextView emailIdTV = convertView.findViewById(R.id.emailIdTV);

            idTV.setText(String.valueOf(id_list[position]));
            positionTV.setText(position_list[position]);
            emailIdTV.setText(email_list[position]);
            return convertView;


        }
    }


}
