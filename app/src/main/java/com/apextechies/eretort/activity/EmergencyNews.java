package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shankar on 12/17/2017.
 */

public class EmergencyNews extends AppCompatActivity {
    private TextView text_subject,text_message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialy_message);
        initWidgit();
        callApi();
    }
    private void callApi() {
        Utilz.showProgress(EmergencyNews.this, getResources().getString(R.string.please_wait));
        Download_web web = new Download_web(EmergencyNews.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Utilz.closeDialog();
                if (response.length()>0)
                {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true"))
                        {
                            JSONArray jsonArray = object.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                String emergencynews_id = jo.optString("id");
                                String emergencySubject = jo.optString("emergencySubject");
                                String emergencyNews = jo.optString("emergencyNews");
                                String schoolName = jo.optString("schoolName");

                                setData(emergencyNews, emergencySubject, schoolName);
                            }

                        }
                        else
                        {
                            Toast.makeText(EmergencyNews.this, ""+object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setReqType(true);
        web.execute(WebServices.EMERGENCYNEWS);
    }

    private void setData(String emergencyNews, String emergencySubject, String schoolName) {
        text_subject.setText(emergencySubject);
        text_message.setText(emergencyNews);
    }

    private void initWidgit() {
        text_subject = (TextView)findViewById(R.id.text_subject);
        text_message = (TextView)findViewById(R.id.text_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.emerency_news));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
