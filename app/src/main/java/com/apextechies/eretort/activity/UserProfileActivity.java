package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;

/**
 * Created by Shankar on 2/11/2018.
 */

public class UserProfileActivity extends AppCompatActivity {

    private TextView tv_username,tv_userid,tv_name,tv_class,tvsec,parentname;
    private View view2,view3,view4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        initWidgit();
        setData();
    }

    private void setData() {
        String name = ClsGeneral.getPreferences(UserProfileActivity.this, PreferenceName.NAME);
        String uid = ClsGeneral.getPreferences(UserProfileActivity.this, PreferenceName.ID);
        String tclass = ClsGeneral.getPreferences(UserProfileActivity.this, PreferenceName.CLASS);
        String sec = ClsGeneral.getPreferences(UserProfileActivity.this, PreferenceName.SECTION);
        String parent = ClsGeneral.getPreferences(UserProfileActivity.this, PreferenceName.ARENTNAME);
        tv_name.setText("Name:  "+name);
        tv_username.setText("User Name: "+name);
        tv_userid.setText("Reg Id: "+uid);
        tv_class.setVisibility(View.GONE);
        tvsec.setVisibility(View.GONE);
        parentname.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_profile));

        tv_username = (TextView)findViewById(R.id.tv_username);
        tv_userid = (TextView)findViewById(R.id.tv_userid);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_class = (TextView)findViewById(R.id.tv_class);
        tvsec = (TextView)findViewById(R.id.tvsec);
        parentname = (TextView)findViewById(R.id.parentname);

        view2 = (View)findViewById(R.id.view2);
        view3 = (View)findViewById(R.id.view3);
        view4 = (View)findViewById(R.id.view4);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
