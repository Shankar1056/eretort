package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.model.LeaveRequestModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Shankar on 3/17/2018.
 */

public class LeaveRequestDetails extends AppCompatActivity {
    private TextView tv_fromDate, tv_toDate, textName, textDescription;
    private ArrayList<LeaveRequestModel> leaveRequestLists = new ArrayList<>();
    private Button accept, reject;
    private int pos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaverequestdetails);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidgit();
    }

    private void initWidgit() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_fromDate = (TextView) findViewById(R.id.tv_fromDate);
        tv_toDate = (TextView) findViewById(R.id.tv_toDate);
        textName = (TextView) findViewById(R.id.textName);
        textDescription = (TextView) findViewById(R.id.textDescription);
        accept = (Button) findViewById(R.id.accept);
        reject = (Button) findViewById(R.id.reject);

        leaveRequestLists = getIntent().getParcelableArrayListExtra("list");
        pos = getIntent().getIntExtra("pos", 0);

        tv_fromDate.setText(leaveRequestLists.get(0).getFromtime());
        tv_toDate.setText(leaveRequestLists.get(0).getTotime());
        textName.setText(leaveRequestLists.get(0).getName());
        textDescription.setText(leaveRequestLists.get(0).getDescription());

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLeaveStatus("Accepted");
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLeaveStatus("Rejected");
            }
        });
    }

    private void updateLeaveStatus(String accept) {
        Utilz.showDailog(LeaveRequestDetails.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(LeaveRequestDetails.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Utilz.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.optString("status").equals("true")) {

                            finish();
                        } else {
                            Toast.makeText(LeaveRequestDetails.this, "" + object.opt("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("id", leaveRequestLists.get(pos).getApplication_id()));
        nameValuePairs.add(new BasicNameValuePair("status", accept));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.UPDATELEAVESTATUS);

    }
}
