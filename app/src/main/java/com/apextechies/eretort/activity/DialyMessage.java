package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 12/17/2017.
 */

public class DialyMessage extends AppCompatActivity {

    private TextView text_subject,text_message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialy_message);
        initWidgit();
        callApi();
    }

    private void callApi() {
        Utilz.showProgress(DialyMessage.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(DialyMessage.this, new OnTaskCompleted() {
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
                                String dialymsg_id = jo.optString("dialymsg_id");
                                String dialyMessage = jo.optString("dialyMessage");
                                String messageSubject = jo.optString("messageSubject");
                                String schoolName = jo.optString("schoolName");

                                setData(dialyMessage, messageSubject, schoolName);
                            }

                        }
                        else
                        {
                            Toast.makeText(DialyMessage.this, ""+object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.DIALY_MESSAGE);
    }

    private void setData(String dialyMessage, String messageSubject, String schoolName) {
        text_subject.setText(messageSubject);
        text_message.setText(dialyMessage);
    }


    private void initWidgit() {
        text_subject = (TextView)findViewById(R.id.text_subject);
        text_message = (TextView)findViewById(R.id.text_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.dialy_message));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
