package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/2/2018.
 */

public class AboutUs extends AppCompatActivity {

    private TextView about_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (Utilz.isInternetConnected(AboutUs.this)) {
            callAboutUsApi();
        } else {
            Toast.makeText(AboutUs.this, "" + getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    private void callAboutUsApi() {
        Utilz.showDailog(AboutUs.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(AboutUs.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Utilz.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        initWidgit(object);
                        if (object.optString("status").equals("true")) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("id","1"));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.ABOUTUS);
    }

    private void initWidgit(JSONObject object) throws JSONException {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(object.getJSONArray("data").getJSONObject(0).optString("heading"));
//        about_content = (TextView)findViewById(R.id.about_content);
//        about_content.setText(Html.fromHtml(object.getJSONArray("data").getJSONObject(0).optString("contant")));

        TextView htmlTextView = (TextView) findViewById(R.id.about_content);

// loads html from string and displays cat_pic.png from the app's drawable folder
        htmlTextView.setText(Html.fromHtml(object.getJSONArray("data").getJSONObject(0).optString("contant")));


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
