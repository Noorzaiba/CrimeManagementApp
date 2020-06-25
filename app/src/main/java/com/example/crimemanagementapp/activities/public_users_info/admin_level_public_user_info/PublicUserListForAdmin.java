package com.example.crimemanagementapp.activities.public_users_info.admin_level_public_user_info;

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
import com.example.crimemanagementapp.activities.public_users_info.PublicUserList;
import com.example.crimemanagementapp.api.RetrofitClient;
import com.example.crimemanagementapp.constants.ApiContants;
import com.example.crimemanagementapp.model.public_user_details.PublicUserDefaultResponse;
import com.example.crimemanagementapp.model.public_user_details.PublicUserModel;
import com.example.crimemanagementapp.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicUserListForAdmin extends AppCompatActivity {
    ListView listView;
    int size;
    int id_list[];
    String email_list[];
    String first_name_list[];
    String image_list[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_user_list);
        listView=findViewById(R.id.listView);


        Call<PublicUserDefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllPublicUser();
        call.enqueue(new Callback<PublicUserDefaultResponse>(){
            @Override
            public void onResponse(Call<PublicUserDefaultResponse> call, Response<PublicUserDefaultResponse> response) {

                try{

                    if(response.code()==200){
                        PublicUserDefaultResponse res=response.body();
                        Log.i("flag",String.valueOf(res.isFlag()));

                        if(res.isFlag()){
                            List<PublicUserModel> obj=res.getSerailizedData();
                            size=obj.size();
                            email_list=new String[size ];
                            id_list=new int[ size];
                            first_name_list=new String[size];
                            image_list=new String[size];
                            for(int i=0;i<size;i++){

                                email_list[i]=obj.get(i).getEmail_id();
                                id_list[i]=obj.get(i).getId();
                                first_name_list[i]=obj.get(i).getFirst_name();
                                image_list[i]=obj.get(i).getProfile_image();

                            }
                           PublicUserListForAdmin.CustomAdapter customAdapter=new PublicUserListForAdmin.CustomAdapter();
                            listView.setAdapter(customAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    TextView emailET = view.findViewById(R.id.emailET);
                                    Intent i = new Intent(getApplicationContext(), UpdatePublicUserProfile.class);
                                    i.putExtra("email",emailET.getText().toString());
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
            public void onFailure(Call<PublicUserDefaultResponse> call, Throwable t) {
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

            convertView=getLayoutInflater().inflate(R.layout.activity_public_user_list_item,null);
            TextView idET = convertView.findViewById(R.id.idET);
            TextView emailET = convertView.findViewById(R.id.emailET);
            TextView firstNameET = convertView.findViewById(R.id.firstNameET);
            ImageView imageIV=convertView.findViewById(R.id.imageIV);
            Log.i("tagging",String.valueOf(id_list[position]));
            idET.setText(String.valueOf(id_list[position]));
            emailET.setText(String.valueOf(email_list[position]));
            firstNameET.setText(first_name_list[position]);

            imageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog =new Dialog(PublicUserListForAdmin.this);
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
                    namePOP.setText(firstNameET.getText().toString());
                    Picasso.get()
                            .load(ApiContants.PUBLIC_USER+image_list[position])
                            .resize(70, 70)
                            .centerCrop()
                            .into(image);
                    dialog.show();


                }
            });


            Picasso.get()
                    .load(ApiContants.PUBLIC_USER+image_list[position])
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