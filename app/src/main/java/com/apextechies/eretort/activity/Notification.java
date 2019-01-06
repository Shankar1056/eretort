package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.NoticeBoardAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.NoticeBoardModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shankar on 12/12/17.
 */

public class Notification extends AppCompatActivity {
    private ArrayList<NoticeBoardModel> list = new ArrayList<>();
    private NoticeBoardAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_toolbar);
        initWidgit();
        callNoticeApi();
    }

    private void callNoticeApi() {
        Utilz.showProgress(Notification.this, getResources().getString(R.string.please_wait));
        Download_web web = new Download_web(Notification.this, new OnTaskCompleted() {
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
                                list.add(new NoticeBoardModel(jo.optString("notification_id"),jo.optString("notificationHeading"),
                                        jo.optString("notificationTitle"),jo.optString("notificationDescription"),jo.optString("notificationDate"),
                                        jo.optString("schoolName")));
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(Notification.this, ""+object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setReqType(true);
        web.execute(WebServices.NOTIFICATION);
    }
    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.notification));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(Notification.this, LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapter = new NoticeBoardAdapter(Notification.this,list, R.layout.notification_row,"notification", new OnClickListener() {
            @Override
            public void onclick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
