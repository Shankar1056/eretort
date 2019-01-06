package com.apextechies.eretort.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.TeacherListAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.common.AppConstants;
import com.apextechies.eretort.model.TeacherListDataModel;
import com.apextechies.eretort.model.TeacherListModel;
import com.apextechies.eretort.model.UserDetailsDataModel;
import com.apextechies.eretort.retrofit.DownlodableCallback;
import com.apextechies.eretort.retrofit.RetrofitDataProvider;
import com.apextechies.eretort.utilz.Utilz;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherListActivity extends AppCompatActivity {
    @BindView(R.id.rv_common)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    boolean isForReadOnly = true;
    Activity mActivity;
    @BindView(R.id.noRecordFoundTv)
    TextView noRecordFoundTv;
    @BindView(R.id.mainCardView)
    CardView mainCardView;
    @BindView(R.id.noRecordFoundCardView)
    CardView noRecordFoundCardView;
    ArrayList<TeacherListDataModel> teachersArrayList = new ArrayList<>();
    private RetrofitDataProvider retrofitDataProvider;
    private boolean onResumeEnabled = false, isAPICalling = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topperlist);
        mActivity = this;
        ButterKnife.bind(this);
        getIntentData();
        initViews();
        retrofitDataProvider = new RetrofitDataProvider(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        initToolbar();
        if (Utilz.isInternetConnected(mActivity)) {
            getTopperList();
        } else {
            Utilz.showNoInternetConnectionDialog(mActivity);
        }
    }

    @SuppressLint("RestrictedApi")
    private void initViews() {
        FloatingActionButton fab = findViewById(R.id.addTeacherFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent studentIntent = new Intent(mActivity, AddUpdateDeleteTeacherActivity.class);
                studentIntent.putExtra(AppConstants.IS_FOR_UPDATE, false);
                startActivity(studentIntent);
            }
        });
        if (isForReadOnly) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
            onResumeEnabled = true;
        }
    }

    private void getIntentData() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            if (mBundle.containsKey(AppConstants.IS_FOR_READ_ONLY))
                isForReadOnly = mBundle.getBoolean(AppConstants.IS_FOR_READ_ONLY);
        }
    }

    private void getTopperList() {
        if (!isAPICalling) {
            isAPICalling = true;
            Utilz.showDailog(TeacherListActivity.this, getResources().getString(R.string.pleasewait));
            retrofitDataProvider.teacherList(mActivity, new DownlodableCallback<TeacherListModel>() {
                @Override
                public void onSuccess(final TeacherListModel result) {
                    Utilz.closeDialog();
                    isAPICalling = false;
                    if (result != null && result.getStatus().contains(AppConstants.TRUE)) {
                        if (result.getData() != null && result.getData().size() > 0) {
                            noRecordFoundCardView.setVisibility(View.GONE);
                            mainCardView.setVisibility(View.VISIBLE);
                            teachersArrayList = removeTeachersHavingZeroStatus(result.getData());
                            recyclerView.setAdapter(new TeacherListAdapter(TeacherListActivity.this, teachersArrayList, new OnClickListener() {
                                @Override
                                public void onclick(int position) {

                                    if (teachersArrayList != null && teachersArrayList.size() > 0) {
                                        //Coppying data from Teacher model to User model
                                        ArrayList<UserDetailsDataModel> mTeachersArrayList = new ArrayList<>();
                                        for (int i = 0; i < teachersArrayList.size(); i++) {
                                            TeacherListDataModel model = teachersArrayList.get(position);
                                            if (model != null) {
                                                mTeachersArrayList.add(new UserDetailsDataModel(
                                                        model.getUserId(), model.getName(), model.getEmail_id(), model.getMobile(), model.getJoining_date(), model.getImage(), model.getStatus(),
                                                        model.getQualification(), model.getAlternate_number(), model.getClassteacher_for(), model.getAddress(), model.getFacebook_id(), model.getWhat_teach(), model.getDesignation()
                                                ));
                                            }
                                        }
                                        Intent teacherIntent;
                                        if (isForReadOnly)
                                            teacherIntent = new Intent(mActivity, ViewTeacherProfileActivity.class);
                                        else
                                            teacherIntent = new Intent(mActivity, AddUpdateDeleteTeacherActivity.class);
                                        teacherIntent.putExtra(AppConstants.TEACHER_DETAILS, mTeachersArrayList);
                                        teacherIntent.putExtra(AppConstants.IS_FOR_UPDATE, true);
                                        teacherIntent.putExtra(AppConstants.POSITION, position);
                                        startActivity(teacherIntent);
                                    }
                                }
                            }));
                        }
                    }
                }

                @Override
                public void onFailure(String error) {
                    Utilz.closeDialog();
                    isAPICalling = false;
                    Toast.makeText(mActivity, R.string.something_went_wrong_error_message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onUnauthorized(int errorNumber) {
                    Utilz.closeDialog();
                    isAPICalling = false;
                    Toast.makeText(mActivity, R.string.something_went_wrong_error_message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Teacher List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private ArrayList<TeacherListDataModel> removeTeachersHavingZeroStatus(ArrayList<TeacherListDataModel> teacherListDataModelList) {
        ArrayList<TeacherListDataModel> finalList = new ArrayList<>();
        if (teacherListDataModelList != null) {
            for (int i = 0; i < teacherListDataModelList.size(); i++) {
                if (teacherListDataModelList.get(i).getStatus().equalsIgnoreCase("1")) {
                    finalList.add(teacherListDataModelList.get(i));
                }
            }
        }
        return finalList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (onResumeEnabled && Utilz.isInternetConnected(mActivity))
            getTopperList();
    }
}
