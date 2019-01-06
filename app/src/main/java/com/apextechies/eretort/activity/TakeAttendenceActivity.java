package com.apextechies.eretort.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apextechies.eretort.BuildConfig;
import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.AttendenceAdapter;
import com.apextechies.eretort.common.AppConstants;
import com.apextechies.eretort.common.CircleImageView;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.model.AttendanceStatusModel;
import com.apextechies.eretort.model.CommonResponse;
import com.apextechies.eretort.model.InsertAttendanceModel;
import com.apextechies.eretort.model.StudentListByClassModel;
import com.apextechies.eretort.model.StudentListDataModel;
import com.apextechies.eretort.preference.MyPreference;
import com.apextechies.eretort.retrofit.DownlodableCallback;
import com.apextechies.eretort.retrofit.RetrofitDataProvider;
import com.apextechies.eretort.utilz.Utilz;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TakeAttendenceActivity extends AppCompatActivity {
    private static final String TAG = TakeAttendenceActivity.class.getSimpleName();
    public boolean isAttendenceTakenAndSaved = true;
    @BindView(R.id.totalStudentCount)
    TextView totalStudentCount;
    @BindView(R.id.presentStudentCount)
    TextView presentStudentCount;
    @BindView(R.id.absentStudentCount)
    TextView absentStudentCount;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_common)
    RecyclerView mRecyclerView;
    /*@BindView(R.id.userProfilePicIv)
    CircleImageView userProfilePicIv;*/
    @BindView(R.id.classTeacherNameTv)
    TextView classTeacherNameTv;
    @BindView(R.id.totalAbsentPresentCardView)
    LinearLayout totalAbsentPresentCardView;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.submitButtonLl)
    LinearLayout submitButtonLl;
    private Activity mActivity;
    private ArrayList<StudentListDataModel> studentListDataModelList;
    private String className = "", sectionName = "";
    private RetrofitDataProvider retrofitDataProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_attendance_activity);
        ButterKnife.bind(this);
        mActivity = this;
        studentListDataModelList = new ArrayList();
        retrofitDataProvider = new RetrofitDataProvider(mActivity);
        getIntentData();
        initToolbar();
        initView();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (Utilz.isInternetConnected(mActivity)) {
            getStudentListFromServer();
        } else {
            Utilz.showNoInternetConnectionDialog(mActivity);
        }
    }

    private void getIntentData() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            if (mBundle.containsKey(AppConstants.CLASS_NAME))
                className = mBundle.getString(AppConstants.CLASS_NAME);
            if (mBundle.containsKey(AppConstants.SECTION_NAME))
                sectionName = mBundle.getString(AppConstants.SECTION_NAME);
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAttendenceBeforeLeave();
            }
        });
    }

    private void initView() {
        sendButton.setText(mActivity.getResources().getString(R.string.upload_attendance));
       // getSupportActionBar().setTitle("Attendence Class-" + className + " (" + Utilz.getCurrentDayNameAndDate() + ")");

        mRecyclerView.setNestedScrollingEnabled(false);

        classTeacherNameTv.setText(ClsGeneral.getStrPreferences(AppConstants.NAME));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.user);
        requestOptions.error(R.drawable.user);
        requestOptions.fitCenter();
        String finalImageUrl = ClsGeneral.getStrPreferences(AppConstants.PROFILE_PIC);
        if (!TextUtils.isEmpty(finalImageUrl) && !finalImageUrl.contains(AppConstants.FACEBOOK_IMAGE_URL)) {
           // finalImageUrl = BuildConfig.WEBSITE_URL + finalImageUrl;
        }
        /*Glide.with(mActivity)
                .load(finalImageUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(userProfilePicIv);*/
    }

    private void getStudentListFromServer() {
        Utilz.showDailog(TakeAttendenceActivity.this, mActivity.getResources().getString(R.string.pleasewait));
        retrofitDataProvider.getStudentsListByClassWise(mActivity, className, sectionName, new DownlodableCallback<StudentListByClassModel>() {
            @Override
            public void onSuccess(final StudentListByClassModel result) {
                Utilz.closeDialog();
                if (result != null && (AppConstants.TRUE).contains(result.getStatus())) {
                    studentListDataModelList.clear();
                    studentListDataModelList = result.getData();
                    mRecyclerView.setAdapter(new AttendenceAdapter(mActivity, studentListDataModelList));
                }
            }

            @Override
            public void onFailure(String error) {
                Utilz.closeDialog();
            }

            @Override
            public void onUnauthorized(int errorNumber) {
                Utilz.closeDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAttendenceBeforeLeave();
    }

    private void saveAttendenceBeforeLeave() {
        if (!isAttendenceTakenAndSaved) {
            Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.warning), mActivity.getString(R.string.attendance_taken_not_saved_msg), AppConstants.NO, AppConstants.YES);
        }
    }

    private void uploadAttendence() {
        Utilz.showDailog(TakeAttendenceActivity.this, getResources().getString(R.string.pleasewait));
        InsertAttendanceModel insertAttendanceModel = getListOfStudentAttendance();
        retrofitDataProvider.attendance(mActivity, insertAttendanceModel, new DownlodableCallback<CommonResponse>() {
            @Override
            public void onSuccess(final CommonResponse result) {
                Utilz.closeDialog();
                isAttendenceTakenAndSaved = true;
                Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.attendance_taken_success_msg), "", AppConstants.OK);
            }

            @Override
            public void onFailure(String error) {
                Utilz.closeDialog();
            }

            @Override
            public void onUnauthorized(int errorNumber) {
                Utilz.closeDialog();
            }
        });
    }

    private InsertAttendanceModel getListOfStudentAttendance() {
        InsertAttendanceModel insertAttendanceModel = new InsertAttendanceModel();
        insertAttendanceModel.setClas(className);
        insertAttendanceModel.setSec(sectionName);
        insertAttendanceModel.setSenderType(AppConstants.STUDENT);
        insertAttendanceModel.setSendTo(AppConstants.CLASS_WISE);
        insertAttendanceModel.setSchoolId(ClsGeneral.getStrPreferences(AppConstants.SCHOOL_ID));
        insertAttendanceModel.setSendTo(ClsGeneral.getStrPreferences(AppConstants.SCHOOL_ID));
        insertAttendanceModel.setSmsAllowedStatus(ClsGeneral.getStrPreferences(AppConstants.SMS_ALLOWED_STATUS));
        insertAttendanceModel.setDate(Utilz.getCurrentDate());
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.USER_ID)))
            insertAttendanceModel.setTeacher_id(ClsGeneral.getStrPreferences(AppConstants.USER_ID));
        else
            insertAttendanceModel.setTeacher_id(MyPreference.getUserId());
        ArrayList<AttendanceStatusModel> list = new ArrayList<>();
        //Create loop here and add student present/Absent status to AttendanceStatusModel
        if (studentListDataModelList != null && studentListDataModelList.size() > 0) {
            for (int i = 0; i < studentListDataModelList.size(); i++) {
                if (studentListDataModelList.get(i).isSelected()) {
                    if (!TextUtils.isEmpty(studentListDataModelList.get(i).getStudentId())) {
                        list.add(new AttendanceStatusModel(studentListDataModelList.get(i).getStudentId(), studentListDataModelList.get(i).getStudentName(), "Present"));
                    } else {
                        list.add(new AttendanceStatusModel(studentListDataModelList.get(i).getStudent_id(), studentListDataModelList.get(i).getStudentName(), "Present"));
                    }
                } else {
                    if (!TextUtils.isEmpty(studentListDataModelList.get(i).getStudentId())) {
                        list.add(new AttendanceStatusModel(studentListDataModelList.get(i).getStudentId(), studentListDataModelList.get(i).getStudentName(), "Absent"));
                    } else {
                        list.add(new AttendanceStatusModel(studentListDataModelList.get(i).getStudent_id(), studentListDataModelList.get(i).getStudentName(), "Absent"));
                    }
                }
            }
        }

        insertAttendanceModel.setAttendance(list);
        //End loop

        return insertAttendanceModel;
    }

    public void manageAbsentPresentCount(int mIntTotalStudentCount, int mIntAbsentStudentCount) {
        if (totalStudentCount != null)
            totalStudentCount.setText("Total-" + mIntTotalStudentCount);
        if (presentStudentCount != null) {
            presentStudentCount.setText((mIntTotalStudentCount - mIntAbsentStudentCount) + "");
        }
        if (absentStudentCount != null)
            absentStudentCount.setText(mIntAbsentStudentCount + "");

        if (totalAbsentPresentCardView != null && submitButtonLl != null) {
            if ((mIntTotalStudentCount - mIntAbsentStudentCount) > 0) {
                totalAbsentPresentCardView.setVisibility(View.VISIBLE);
                submitButtonLl.setVisibility(View.VISIBLE);
                isAttendenceTakenAndSaved = false;
            } else {
                isAttendenceTakenAndSaved = true;
                totalAbsentPresentCardView.setVisibility(View.GONE);
                submitButtonLl.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.submitButtonLl)
    public void onViewClicked() {
        if (Utilz.isInternetConnected(mActivity)) {
            uploadAttendence();
        } else {
            Utilz.showNoInternetConnectionDialog(mActivity);
        }
    }

    /*public void sendSMSMessage(String mStrStudentName, String mStrPhoneNo) {
        this.mStrMessage = Utilz.getAbsentMessage(mActivity, mStrStudentName);
        this.mStrPhoneNo = mStrPhoneNo;
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.SEND_SMS}, AppConstants.MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    private String mStrMessage, mStrPhoneNo;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppConstants.MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Utilz.setMessage(mActivity, mStrPhoneNo, mStrMessage, true);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.sms_failed_to_send, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }*/
}
