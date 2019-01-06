package com.apextechies.eretort.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.LeaveRequestListAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.model.LeaveAllRequestModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Shankar on 3/15/2018.
 */

public class LeaveRequestList extends AppCompatActivity  {
    private RecyclerView rv_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaverequestlist);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidgit();
        if (Utilz.isInternetConnected(LeaveRequestList.this)) {
            callCatApi();
        } else {
            Toast.makeText(LeaveRequestList.this, "" + getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    private void callCatApi() {
        Utilz.showDailog(LeaveRequestList.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(LeaveRequestList.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Utilz.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        Gson gson = new Gson();
                        final LeaveAllRequestModel subCategoryModel = gson.fromJson(response, LeaveAllRequestModel.class);

                        if (subCategoryModel.getStatus().equals("true")) {

                            rv_list.setAdapter(new LeaveRequestListAdapter(LeaveRequestList.this, subCategoryModel.getData(), new OnClickListener() {
                                @Override
                                public void onclick(int position) {
                                    startActivity(new Intent(LeaveRequestList.this, LeaveRequestDetails.class)
                                            .putParcelableArrayListExtra("list", subCategoryModel.getData())
                                            .putExtra("pos", position));
                                }
                            }));

                        } else {
                            Toast.makeText(LeaveRequestList.this, "" + object.opt("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("id", ClsGeneral.getPreferences(LeaveRequestList.this, PreferenceName.NAME)));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.LEAVEREQUESTLIST);

    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("LeaveRequest");

        rv_list = (RecyclerView) findViewById(R.id.recyclerView);
        rv_list.setLayoutManager(new LinearLayoutManager(LeaveRequestList.this));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
