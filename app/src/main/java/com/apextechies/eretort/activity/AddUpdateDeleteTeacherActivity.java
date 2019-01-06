package com.apextechies.eretort.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.BuildConfig;
import com.apextechies.eretort.R;
import com.apextechies.eretort.common.AppConstants;
import com.apextechies.eretort.common.CircleImageView;
import com.apextechies.eretort.model.StuTeaModel;
import com.apextechies.eretort.model.UserDetailsDataModel;
import com.apextechies.eretort.retrofit.DownlodableCallback;
import com.apextechies.eretort.retrofit.RetrofitDataProvider;
import com.apextechies.eretort.utilz.Utilz;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUpdateDeleteTeacherActivity extends AppCompatActivity {
    private static final String TAG = AddUpdateDeleteTeacherActivity.class.getSimpleName();
    /*@BindView(R.id.userProfilePicIv)
    CircleImageView userProfilePicIv;*/
    @BindView(R.id.teacher_id)
    TextView teacher_id;
    @BindView(R.id.input_name)
    EditText input_name;
    @BindView(R.id.input_qualification)
    EditText input_qualification;
    @BindView(R.id.input_mobile)
    EditText input_mobile;
    @BindView(R.id.input_alternatenumber)
    EditText input_alternatenumber;
    @BindView(R.id.input_emailid)
    EditText input_emailid;
    @BindView(R.id.input_classtea)
    EditText input_classtea;
    @BindView(R.id.input_joindate)
    EditText input_joindate;
    @BindView(R.id.input_what_teaches)
    EditText input_what_teaches;
    @BindView(R.id.input_address)
    EditText input_address;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.addButtonLl)
    LinearLayout addButtonLl;
    @BindView(R.id.updateButton)
    Button updateButton;
    @BindView(R.id.deleteButton)
    Button deleteButton;
    @BindView(R.id.updateButtonLl)
    LinearLayout updateButtonLl;
    Calendar myCalendar = Calendar.getInstance();
    private boolean isForUpdate;
    private int mPosition = -1;
    private String mStrTteacherId = "", mStrImageUrl = "";
    private Activity mActivity;
    private UserDetailsDataModel mTeacherModel;
    private RetrofitDataProvider retrofitDataProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_addteacher);
        ButterKnife.bind(this);
        mActivity = this;
        retrofitDataProvider = new RetrofitDataProvider(this);
        getIntentData();
        initToolbar();
        initView();
        fillAllTeacherDetailsIntoFields();
        handleClicks();
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getIntentData() {
        Bundle mBundle = getIntent().getExtras();
        ArrayList<UserDetailsDataModel> mTeacherArrayList = new ArrayList<>();
        if (mBundle != null) {
            if (mBundle.containsKey(AppConstants.IS_FOR_UPDATE))
                isForUpdate = mBundle.getBoolean(AppConstants.IS_FOR_UPDATE);
            if (mBundle.containsKey(AppConstants.TEACHER_DETAILS))
                mTeacherArrayList = (ArrayList<UserDetailsDataModel>) mBundle.getSerializable(AppConstants.TEACHER_DETAILS);
            if (mBundle.containsKey(AppConstants.POSITION))
                mPosition = mBundle.getInt(AppConstants.POSITION);

            if (mTeacherArrayList != null && mTeacherArrayList.size() > 0 && mPosition >= 0)
                mTeacherModel = mTeacherArrayList.get(mPosition);
        }
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
    }

    private void initView() {
        sendButton.setText(mActivity.getResources().getString(R.string.add_teacher));
        if (isForUpdate) {
            getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.update_details));
            updateButtonLl.setVisibility(View.VISIBLE);
            addButtonLl.setVisibility(View.GONE);
        } else {
            updateButtonLl.setVisibility(View.GONE);
            addButtonLl.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.add_teacher));
        }
    }

    private void fillAllTeacherDetailsIntoFields() {
        if (mTeacherModel != null) {
            String finalImageUrl = "";
            if (!TextUtils.isEmpty(mTeacherModel.getProfile_pic()) && mTeacherModel.getProfile_pic().contains(AppConstants.FACEBOOK_IMAGE_URL)) {
                finalImageUrl = mStrImageUrl = mTeacherModel.getProfile_pic();
            } else {
                mStrImageUrl = mTeacherModel.getProfile_pic();
                //finalImageUrl = BuildConfig.WEBSITE_URL + mTeacherModel.getProfile_pic();
            }
           // showImage(finalImageUrl);
            if (!TextUtils.isEmpty(mTeacherModel.getUser_id())) {
                teacher_id.setText("Id : " + mTeacherModel.getUser_id());
                mStrTteacherId = mTeacherModel.getUser_id();
                teacher_id.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(mTeacherModel.getName()))
                input_name.setText(mTeacherModel.getName());

            if (!TextUtils.isEmpty(mTeacherModel.getQualification()))
                input_qualification.setText(mTeacherModel.getQualification());

            if (!TextUtils.isEmpty(mTeacherModel.getMobile()))
                input_mobile.setText(mTeacherModel.getMobile());

            if (!TextUtils.isEmpty(mTeacherModel.getAlternate_number()))
                input_alternatenumber.setText(mTeacherModel.getAlternate_number());

            if (!TextUtils.isEmpty(mTeacherModel.getEmail()))
                input_emailid.setText(mTeacherModel.getEmail());

            if (!TextUtils.isEmpty(mTeacherModel.getClassteacher_for()))
                input_classtea.setText(mTeacherModel.getClassteacher_for());

            if (!TextUtils.isEmpty(mTeacherModel.getWhat_teach()))
                input_what_teaches.setText(mTeacherModel.getWhat_teach());

            if (!TextUtils.isEmpty(mTeacherModel.getJoining_date()))
                input_joindate.setText(mTeacherModel.getJoining_date());

            if (!TextUtils.isEmpty(mTeacherModel.getAddress()))
                input_address.setText(mTeacherModel.getAddress());
            else if (!TextUtils.isEmpty(mTeacherModel.getPermanent_address()))
                input_address.setText(mTeacherModel.getPermanent_address());
        }
    }

    private void handleClicks() {
        addButtonLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUpdateTeacherDetail(true, AppConstants.ADD);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUpdateTeacherDetail(false, AppConstants.UPDATE);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStudentFromSchool();
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        input_joindate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddUpdateDeleteTeacherActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        input_joindate.setText(sdf.format(myCalendar.getTime()));
    }

    private void addUpdateTeacherDetail(boolean isToAdd, final String mStrOperation) {
        Utilz.showDailog(AddUpdateDeleteTeacherActivity.this, getResources().getString(R.string.pleasewait));
        String name = input_name.getText().toString().trim();
        String qualification = input_qualification.getText().toString().trim();
        String mobile = input_mobile.getText().toString().trim();
        String alternatenumber = input_alternatenumber.getText().toString().trim();
        String emailid = input_emailid.getText().toString().trim();
        String classtea = input_classtea.getText().toString().trim();
        String joindate = input_joindate.getText().toString().trim();
        String whatTeach = input_what_teaches.getText().toString().trim();
        String address = input_address.getText().toString().trim();
        String facebookId = "";
        if (isToAdd) {
            mStrTteacherId = Utilz.getRandomUserName(name);
            teacher_id.setText("Id : " + mStrTteacherId + "\nPassword : " + mStrTteacherId);
            teacher_id.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(mStrTteacherId)) {
            Toast.makeText(mActivity, R.string.user_id_cannot_empty, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(mActivity, R.string.enter_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(qualification)) {
            Toast.makeText(mActivity, R.string.enter_qualification, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(mActivity, R.string.enter_phone, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(alternatenumber)) {
            Toast.makeText(mActivity, R.string.enter_alternat_phone, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(emailid)) {
            Toast.makeText(mActivity, R.string.enter_email, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(classtea)) {
            Toast.makeText(mActivity, R.string.enter_phone, Toast.LENGTH_SHORT).show();
        }/* else if (TextUtils.isEmpty(address)) {
        }*/ else {
            if (Utilz.isInternetConnected(mActivity)) {
                retrofitDataProvider.addteacher(mActivity, mStrTteacherId, name, qualification, mobile, alternatenumber,
                        emailid, classtea, joindate, address, mStrOperation, mStrImageUrl, facebookId, whatTeach, new DownlodableCallback<StuTeaModel>() {
                            @Override
                            public void onSuccess(final StuTeaModel result) {
                                //  closeDialog();
                                Utilz.closeDialog();
                                if (result != null && (AppConstants.TRUE).contains(result.getStatus())) {
                                    if (AppConstants.UPDATE.equalsIgnoreCase(mStrOperation))
                                        Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.updated_successfully), "", AppConstants.OK);
                                    else if (AppConstants.ADD.equalsIgnoreCase(mStrOperation))
                                        Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.added_successfully), "", AppConstants.OK);
                                    else
                                        Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.deleted_successfully), "", AppConstants.OK);
                                }
                            }

                            @Override
                            public void onFailure(String error) {
                                // closeDialog();
                                Utilz.closeDialog();
                                Toast.makeText(mActivity, mActivity.getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onUnauthorized(int errorNumber) {
                                Utilz.closeDialog();
                                Toast.makeText(mActivity, mActivity.getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Utilz.showNoInternetConnectionDialog(mActivity);
            }
        }
    }

    private void deleteStudentFromSchool() {
        if (TextUtils.isEmpty(teacher_id.getText().toString().trim())) {
            Toast.makeText(mActivity, getString(R.string.invalid_teacher_id), Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (Utilz.isInternetConnected(mActivity)) {
                addUpdateTeacherDetail(false, AppConstants.DELETE);
            } else {
                Utilz.showNoInternetConnectionDialog(mActivity);
            }
        }
    }

    private void showImage(String mImageUrl) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);
        requestOptions.fitCenter();
        /*Glide.with(mActivity)
                .load(mImageUrl)
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

}
