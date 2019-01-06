package com.apextechies.eretort.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.BuildConfig;
import com.apextechies.eretort.R;
import com.apextechies.eretort.common.AppConstants;
import com.apextechies.eretort.common.CircleImageView;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.model.UserDetailsDataModel;
import com.apextechies.eretort.preference.MyPreference;
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

public class ViewTeacherProfileActivity extends AppCompatActivity {
   /* @BindView(R.id.userProfilePicIv)
    CircleImageView userProfilePicIv;*/
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.userNameTv)
    TextView userNameTv;
    @BindView(R.id.userDesignationTv)
    TextView userDesignationTv;
    @BindView(R.id.userPhoneTv)
    TextView userPhoneTv;
    @BindView(R.id.callButton)
    Button callButton;
    @BindView(R.id.userEmailIdTv)
    TextView userEmailIdTv;
    @BindView(R.id.userQualificationTv)
    TextView userQualificationTv;
    @BindView(R.id.userTeachingTv)
    TextView userTeachingTv;
    @BindView(R.id.userAddressTv)
    TextView userAddressTv;
    @BindView(R.id.teacherDetailsLl)
    LinearLayout teacherDetailsLl;
    @BindView(R.id.classNameTv)
    TextView classNameTv;
    @BindView(R.id.sectionTv)
    TextView sectionTv;
    @BindView(R.id.fatherNameTv)
    TextView fatherNameTv;
    @BindView(R.id.studentDetailsLl)
    LinearLayout studentDetailsLl;
    boolean isToShowAddteacherIcon = false, isForUpdate = false;
    private Activity mActivity;
    private int mPosition;
    private ArrayList<UserDetailsDataModel> mTeachersArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teacher_profile_activity);
        ButterKnife.bind(this);
        mActivity = this;
        getIntentData();
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Profile");
    }

    private void getIntentData() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            if (mBundle.containsKey(AppConstants.POSITION))
                mPosition = mBundle.getInt(AppConstants.POSITION);
            if (mBundle.containsKey(AppConstants.IS_FOR_UPDATE))
                isForUpdate = mBundle.getBoolean(AppConstants.IS_FOR_UPDATE);
            if (mBundle.containsKey(AppConstants.TEACHER_DETAILS))
                mTeachersArrayList = (ArrayList<UserDetailsDataModel>) mBundle.getSerializable(AppConstants.TEACHER_DETAILS);
        }
        if (MyPreference.getLoginedAs().equalsIgnoreCase(AppConstants.STUDENT)) {
            updateOnUICachedDetails();
            studentDetailsLl.setVisibility(View.VISIBLE);
            teacherDetailsLl.setVisibility(View.GONE);
            isToShowAddteacherIcon = false;
        } else {
            studentDetailsLl.setVisibility(View.GONE);
            teacherDetailsLl.setVisibility(View.VISIBLE);
            //Enabling edit/delete/add teacher feature.
            if (isForUpdate) {
                isToShowAddteacherIcon = true;
            }
        }
        if (mTeachersArrayList != null && mTeachersArrayList.size() > 0) {
            updateOnUI(mTeachersArrayList.get(mPosition));
        } else {
            updateOnUICachedDetails();
        }
    }

    private void updateOnUI(UserDetailsDataModel mTeacherListDataModel) {
        if (mTeacherListDataModel != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);
            requestOptions.fitCenter();
            String finalImageUrl = "";
            if (!TextUtils.isEmpty(mTeacherListDataModel.getProfile_pic()) && mTeacherListDataModel.getProfile_pic().contains(AppConstants.FACEBOOK_IMAGE_URL)) {
                finalImageUrl = mTeacherListDataModel.getProfile_pic();
            } else {
               // finalImageUrl = BuildConfig.WEBSITE_URL + mTeacherListDataModel.getProfile_pic();
            }
           /* Glide.with(mActivity)
                    .load(finalImageUrl)
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(userProfilePicIv);*/
            if (!TextUtils.isEmpty(mTeacherListDataModel.getName())) {
                userNameTv.setText(mTeacherListDataModel.getName());
            } else {
                userNameTv.setText(mActivity.getResources().getString(R.string.na));
            }
            if (!TextUtils.isEmpty(mTeacherListDataModel.getDesignation())) {
                userDesignationTv.setText(mTeacherListDataModel.getDesignation());
            } else {
                userDesignationTv.setText(mActivity.getResources().getString(R.string.na));
            }
            if (!TextUtils.isEmpty(mTeacherListDataModel.getMobile())) {
                userPhoneTv.setText(mTeacherListDataModel.getMobile());
            } else if (!TextUtils.isEmpty(mTeacherListDataModel.getAlternate_number())) {
                userPhoneTv.setText(mTeacherListDataModel.getAlternate_number());
            } else {
                userPhoneTv.setText(mActivity.getResources().getString(R.string.na));
            }
            if (!TextUtils.isEmpty(mTeacherListDataModel.getEmail())) {
                userEmailIdTv.setText(mTeacherListDataModel.getEmail());
            } else {
                userEmailIdTv.setText(mActivity.getResources().getString(R.string.na));
            }
            if (!TextUtils.isEmpty(mTeacherListDataModel.getQualification())) {
                userQualificationTv.setText(mTeacherListDataModel.getQualification());
            } else {
                userQualificationTv.setText(mActivity.getResources().getString(R.string.na));
            }
            if (!TextUtils.isEmpty(mTeacherListDataModel.getWhat_teach())) {
                userTeachingTv.setText(mTeacherListDataModel.getWhat_teach());
            } else {
                userTeachingTv.setText(mActivity.getResources().getString(R.string.na));
            }
            if (!TextUtils.isEmpty(mTeacherListDataModel.getAddress())) {
                userAddressTv.setText(mTeacherListDataModel.getAddress());
            } else {
                userAddressTv.setText(mActivity.getResources().getString(R.string.na));
            }
        }
    }


    private void updateOnUICachedDetails() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);
        requestOptions.fitCenter();
        String finalImageUrl = ClsGeneral.getStrPreferences(AppConstants.PROFILE_PIC);
        if (!TextUtils.isEmpty(finalImageUrl) && !finalImageUrl.contains(AppConstants.FACEBOOK_IMAGE_URL)) {
          //  finalImageUrl = BuildConfig.WEBSITE_URL + finalImageUrl;
        }
       /* Glide.with(mActivity)
                .load(finalImageUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(userProfilePicIv);*/
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.NAME))) {
            userNameTv.setText(ClsGeneral.getStrPreferences(AppConstants.NAME));
        } else {
            userNameTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.NAME))) {
            userDesignationTv.setText(ClsGeneral.getStrPreferences(AppConstants.DESIGNATION));
        } else {
            userDesignationTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.PHONE_NO))) {
            userPhoneTv.setText(ClsGeneral.getStrPreferences(AppConstants.PHONE_NO));
        } else if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.ALTERNTE_NUMBER))) {
            userPhoneTv.setText(ClsGeneral.getStrPreferences(AppConstants.ALTERNTE_NUMBER));
        } else {
            userPhoneTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.EMAIL))) {
            userEmailIdTv.setText(ClsGeneral.getStrPreferences(AppConstants.EMAIL));
        } else {
            userEmailIdTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.QUALIFICATION))) {
            userQualificationTv.setText(ClsGeneral.getStrPreferences(AppConstants.QUALIFICATION));
        } else {
            userQualificationTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.WHAT_TEACH))) {
            userTeachingTv.setText(ClsGeneral.getStrPreferences(AppConstants.WHAT_TEACH));
        } else {
            userTeachingTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.ADDRESS))) {
            userAddressTv.setText(ClsGeneral.getStrPreferences(AppConstants.ADDRESS));
        } else if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.PERMANENT_ADDRESS))) {
            userAddressTv.setText(ClsGeneral.getStrPreferences(AppConstants.PERMANENT_ADDRESS));
        } else {
            userAddressTv.setText(mActivity.getResources().getString(R.string.na));
        }

        //Students Details
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.CLAS))) {
            classNameTv.setText(ClsGeneral.getStrPreferences(AppConstants.CLAS));
        } else {
            classNameTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.SEC))) {
            sectionTv.setText(ClsGeneral.getStrPreferences(AppConstants.SEC));
        } else {
            sectionTv.setText(mActivity.getResources().getString(R.string.na));
        }
        if (!TextUtils.isEmpty(ClsGeneral.getStrPreferences(AppConstants.FATHER_NAME))) {
            fatherNameTv.setText(ClsGeneral.getStrPreferences(AppConstants.FATHER_NAME));
        } else {
            fatherNameTv.setText(mActivity.getResources().getString(R.string.na));
        }
    }

    @OnClick(R.id.callButton)
    public void onViewClicked() {
        if (userPhoneTv != null && !TextUtils.isEmpty(userPhoneTv.getText().toString().trim())) {
            Utilz.openDialer(mActivity, userPhoneTv.getText().toString().trim());
        } else {
            Toast.makeText(mActivity, R.string.phone_no_not_provide_yet, Toast.LENGTH_SHORT).show();
        }
    }
}
