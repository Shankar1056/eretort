package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shankar on 12/10/2017.
 */

public class DirectorMessage extends AppCompatActivity {

    private TextView director_message;
    private ImageView profileImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.director_mesage);
        initWidgit();
        callApi();
    }

    private void callApi() {
        Utilz.showProgress(DirectorMessage.this, getResources().getString(R.string.please_wait));
        Download_web web = new Download_web(DirectorMessage.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                if (response.length()>0)
                {
                    Utilz.closeDialog();
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true"))
                        {

                            JSONArray jsonArray = object.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                String msg_id = jo.optString("id");
                                String directorName = jo.optString("directorName");
                                String directorMessage = jo.optString("directorMessage");
                                String schoolName = jo.optString("schoolName");
                                String directorImage = jo.optString("directorImage");
                                String schoolLogo = jo.optString("schoolLogo");

                                setData(directorName, directorMessage, schoolName, directorImage, schoolLogo);
                            }

                        }
                        else
                        {
                            Toast.makeText(DirectorMessage.this, ""+object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        web.setReqType(true);
        web.execute(WebServices.DIRECTOR_MESSAGE);
    }

    private void setData(String directorName, String directorMessage, String schoolName, String directorImage, String schoolLogo) {

        director_message.setText(directorMessage);
        Picasso.with(DirectorMessage.this).load(directorImage).into(profileImage);
    }

    private void initWidgit() {
        director_message = (TextView)findViewById(R.id.director_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.dorector_messae));
        profileImage = (ImageView)findViewById(R.id.profileImage);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
