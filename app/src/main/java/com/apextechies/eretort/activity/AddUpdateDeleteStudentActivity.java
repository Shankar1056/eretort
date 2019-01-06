package com.apextechies.eretort.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.DialogClickListener;
import com.apextechies.eretort.common.AppConstants;
import com.apextechies.eretort.model.CommonResponse;
import com.apextechies.eretort.model.StuTeaModel;
import com.apextechies.eretort.model.StudentListDataModel;
import com.apextechies.eretort.retrofit.DownlodableCallback;
import com.apextechies.eretort.retrofit.RetrofitDataProvider;
import com.apextechies.eretort.utilz.Utilz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUpdateDeleteStudentActivity extends AppCompatActivity {

    @BindView(R.id.studentRegIdTv)
    TextView studentRegIdTv;
    @BindView(R.id.input_rollnumber)
    EditText input_rollnumber;
    @BindView(R.id.input_name)
    EditText input_name;
    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_mobile)
    EditText input_mobile;
    @BindView(R.id.input_admission)
    EditText input_admission;
    @BindView(R.id.input_address)
    EditText input_address;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.submitButtonLl)
    LinearLayout submitButtonLl;
    @BindView(R.id.updateButton)
    Button updateButton;
    @BindView(R.id.deleteButton)
    Button deleteButton;
    @BindView(R.id.updateButtonLl)
    LinearLayout updateButtonLl;
    @BindView(R.id.classNameSpinner)
    Spinner classNameSpinner;
    @BindView(R.id.sectionNameSpinner)
    Spinner sectionNameSpinner;
    @BindView(R.id.buttonLl)
    RelativeLayout buttonLl;
    Calendar myCalendar = Calendar.getInstance();
    ArrayList<StudentListDataModel> mStudentsArrayList = new ArrayList<>();
    @BindView(R.id.input_father_name)
    EditText inputFatherName;
    @BindView(R.id.input_father_mobile)
    EditText inputFatherMobile;
    @BindView(R.id.input_mother_name)
    EditText inputMotherName;
    @BindView(R.id.input_mother_mobile)
    EditText inputMotherMobile;
    private String mStrClassName = "", mStrSectionName = "", mStrUserId = "", mStrPhoneNo = "";
    private boolean isForUpdate;
    private Activity mActivity;
    private RetrofitDataProvider retrofitDataProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);
        mActivity = this;
        ButterKnife.bind(this);
        retrofitDataProvider = new RetrofitDataProvider(this);
        getIntentData();
        initToolbar();
        initView();
        handleClicks();

    }

    private void getIntentData() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            if (mBundle.containsKey(AppConstants.IS_FOR_UPDATE))
                isForUpdate = mBundle.getBoolean(AppConstants.IS_FOR_UPDATE);
            if (mBundle.containsKey(AppConstants.STUDENTS_DETAILS))
                mStudentsArrayList = (ArrayList<StudentListDataModel>) mBundle.getSerializable(AppConstants.STUDENTS_DETAILS);
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
        List<String> classList = new ArrayList<>(), sectionList = new ArrayList<>();

        classList.addAll(Utilz.getClassList());

        sectionList.addAll(Utilz.getSectionList());

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_dropdown_item_1line, classList);
        ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_dropdown_item_1line, sectionList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        classNameSpinner.setAdapter(classAdapter);
        sectionNameSpinner.setAdapter(sectionAdapter);
        sendButton.setText(mActivity.getResources().getString(R.string.add_student));
        input_admission.setText(Utilz.getCurrentDate());

        if (isForUpdate) {
            updateButtonLl.setVisibility(View.VISIBLE);
            submitButtonLl.setVisibility(View.GONE);
            getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.update_details));
            if (mStudentsArrayList != null && mStudentsArrayList.size() > 0)
                fillAllStudentsDetailsIntoFields(mStudentsArrayList.get(0));
        } else {
            updateButtonLl.setVisibility(View.GONE);
            submitButtonLl.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.add_student));
        }
    }

    private void fillAllStudentsDetailsIntoFields(StudentListDataModel studentListDataModel) {
        if (studentListDataModel != null) {
            if (!TextUtils.isEmpty(studentListDataModel.getStudent_id())) {
                studentRegIdTv.setText("Id : " + studentListDataModel.getStudent_id() + "\nPassword : " + studentListDataModel.getPassword());
                studentRegIdTv.setVisibility(View.VISIBLE);
                mStrUserId = studentListDataModel.getStudent_id().trim();
            } else if (!TextUtils.isEmpty(studentListDataModel.getStudentId())) {
                studentRegIdTv.setText("Id : " + studentListDataModel.getStudentId() + "\nPassword : " + studentListDataModel.getPassword());
                studentRegIdTv.setVisibility(View.VISIBLE);
                mStrUserId = studentListDataModel.getStudentId().trim();
            } else {
                studentRegIdTv.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(studentListDataModel.getStudentName()))
                input_name.setText(studentListDataModel.getStudentName());

            if (studentListDataModel.getRollNo() > 0)
                input_rollnumber.setText(studentListDataModel.getRollNo() + "");

            if (!TextUtils.isEmpty(studentListDataModel.getEmail()))
                input_email.setText(studentListDataModel.getEmail());

            if (!TextUtils.isEmpty(studentListDataModel.getClassName())) {
                if (studentListDataModel.getClassName().equalsIgnoreCase("10"))
                    classNameSpinner.setSelection(1);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("9"))
                    classNameSpinner.setSelection(2);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("8"))
                    classNameSpinner.setSelection(3);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("7"))
                    classNameSpinner.setSelection(4);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("6"))
                    classNameSpinner.setSelection(5);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("5"))
                    classNameSpinner.setSelection(6);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("4"))
                    classNameSpinner.setSelection(7);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("3"))
                    classNameSpinner.setSelection(8);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("2"))
                    classNameSpinner.setSelection(9);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("1"))
                    classNameSpinner.setSelection(10);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("LKG"))
                    classNameSpinner.setSelection(11);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("UKG"))
                    classNameSpinner.setSelection(12);
                else if (studentListDataModel.getClassName().equalsIgnoreCase("NURSERY"))
                    classNameSpinner.setSelection(13);

                if (!studentListDataModel.getClassName().equalsIgnoreCase("Select Class"))
                    mStrClassName = studentListDataModel.getClassName();
            }
            if (!TextUtils.isEmpty(studentListDataModel.getAdmissionDate()))
                input_admission.setText(studentListDataModel.getAdmissionDate());

            if (!TextUtils.isEmpty(studentListDataModel.getSec()) && sectionNameSpinner != null) {
                if (studentListDataModel.getSec().equalsIgnoreCase("A"))
                    sectionNameSpinner.setSelection(1);
                else if (studentListDataModel.getSec().equalsIgnoreCase("B"))
                    sectionNameSpinner.setSelection(2);
                else if (studentListDataModel.getSec().equalsIgnoreCase("C"))
                    sectionNameSpinner.setSelection(3);
                else if (studentListDataModel.getSec().equalsIgnoreCase("D"))
                    sectionNameSpinner.setSelection(4);

                if (!studentListDataModel.getSec().equalsIgnoreCase("Select Section"))
                    mStrSectionName = studentListDataModel.getSec();
            }
            if (!TextUtils.isEmpty(studentListDataModel.getMobile())) {
                input_mobile.setText(studentListDataModel.getMobile());
                mStrPhoneNo = studentListDataModel.getMobile();
            }
            if (!TextUtils.isEmpty(studentListDataModel.getFatherName())) {
                inputFatherName.setText(studentListDataModel.getFatherName());
            }
            if (!TextUtils.isEmpty(studentListDataModel.getFatherMobile())) {
                inputFatherMobile.setText(studentListDataModel.getFatherMobile());
            }
            if (!TextUtils.isEmpty(studentListDataModel.getMotherName())) {
                inputMotherName.setText(studentListDataModel.getMotherName());
            }
            if (!TextUtils.isEmpty(studentListDataModel.getMotherMobile())) {
                inputMotherMobile.setText(studentListDataModel.getMotherMobile());
            }
            if (!TextUtils.isEmpty(studentListDataModel.getPermanent_address()))
                input_address.setText(studentListDataModel.getResidential_address());
            else if (!TextUtils.isEmpty(studentListDataModel.getPermanent_address()))
                input_address.setText(studentListDataModel.getPermanent_address());
        }
    }

    private void handleClicks() {
        submitButtonLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = Utilz.getRandomUserIdFromName(mActivity);
                studentRegIdTv.setText(userId);
                addUpdateStudentsDetail();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUpdateStudentsDetail();
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

        input_admission.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddUpdateDeleteStudentActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void deleteStudentFromSchool() {
        if (TextUtils.isEmpty(mStrUserId)) {
            Toast.makeText(mActivity, "Invalid student registration id", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (Utilz.isInternetConnected(mActivity)) {
                Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.warning),
                        mActivity.getResources().getString(R.string.delete_student_warning_message),
                        getChoiceDialogClickListener());
            } else {
                Utilz.showNoInternetConnectionDialog(mActivity);
            }
        }
    }

    private DialogClickListener getChoiceDialogClickListener() {
        return new DialogClickListener() {
            @Override
            public void onClickOfPositive() {
                deleteStudentFromDb();
            }
        };
    }

    private void addUpdateStudentsDetail() {
        if (input_rollnumber.getText().toString().trim().equals("")) {
            Toast.makeText(AddUpdateDeleteStudentActivity.this, R.string.enter_roll_no, Toast.LENGTH_SHORT).show();
            return;
        }
        if (input_name.getText().toString().trim().equals("")) {
            Toast.makeText(AddUpdateDeleteStudentActivity.this, R.string.enter_name, Toast.LENGTH_SHORT).show();
            return;
        }
        if (input_mobile != null && input_mobile.getText().toString().trim().equals("")) {
            Toast.makeText(AddUpdateDeleteStudentActivity.this, R.string.enter_phone, Toast.LENGTH_SHORT).show();
            return;
        }
        if (classNameSpinner != null && classNameSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(AddUpdateDeleteStudentActivity.this, R.string.select_class, Toast.LENGTH_SHORT).show();
            return;
        }
        if (sectionNameSpinner != null && sectionNameSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(AddUpdateDeleteStudentActivity.this, R.string.select_section, Toast.LENGTH_SHORT).show();
            return;
        }
        if (input_address.getText().toString().trim().equals("")) {
            Toast.makeText(AddUpdateDeleteStudentActivity.this, R.string.enter_address, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (Utilz.isInternetConnected(mActivity)) {
                addNewStudentInDb();
            } else {
                Utilz.showNoInternetConnectionDialog(mActivity);
            }
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        input_admission.setText(sdf.format(myCalendar.getTime()));
    }

    private void addNewStudentInDb() {
        Utilz.showDailog(AddUpdateDeleteStudentActivity.this, mActivity.getResources().getString(R.string.pleasewait));
        if (TextUtils.isEmpty(mStrUserId)) {
            mStrUserId = Utilz.getRandomUserIdFromName(mActivity);
        }
        String rollNumber = input_rollnumber.getText().toString().trim();
        String name = input_name.getText().toString().trim();
        String email = input_email.getText().toString().trim();
        String mobile = input_mobile.getText().toString().trim();
        String clas = classNameSpinner.getSelectedItem().toString().trim();
        String sec = sectionNameSpinner.getSelectedItem().toString().trim();
        String admission = input_admission.getText().toString().trim();
        String address = input_address.getText().toString().trim();
        String fatherName = inputFatherName.getText().toString().trim();
        String fatherMobile = inputFatherMobile.getText().toString().trim();
        if (TextUtils.isEmpty(fatherMobile))
            fatherMobile = mobile;
        String motherName = inputMotherName.getText().toString().trim();
        String motherMobile = inputMotherMobile.getText().toString().trim();
        if (TextUtils.isEmpty(motherMobile))
            motherMobile = mobile;
        retrofitDataProvider.addstudent(mActivity, mStrUserId, rollNumber, name, email, mobile, clas, sec, admission, address,
                fatherName, fatherMobile, motherName, motherMobile, new DownlodableCallback<StuTeaModel>() {
                    @Override
                    public void onSuccess(final StuTeaModel result) {
                        //  closeDialog();
                        Utilz.closeDialog();
                        if (result != null && (AppConstants.TRUE).contains(result.getStatus())) {
                            if (isForUpdate)
                                Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.updated_successfully), "", AppConstants.OK);
                            else
                                Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.added_successfully), "", AppConstants.OK);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        Utilz.closeDialog();
                        Toast.makeText(mActivity, mActivity.getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUnauthorized(int errorNumber) {
                        Utilz.closeDialog();
                        Toast.makeText(mActivity, mActivity.getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteStudentFromDb() {
        if (TextUtils.isEmpty(mStrUserId)) {
            return;
        }
        retrofitDataProvider.deleteStudent(mActivity, mStrUserId, new DownlodableCallback<CommonResponse>() {
            @Override
            public void onSuccess(final CommonResponse result) {
                Utilz.closeDialog();
                Utilz.showMessageOnDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.deleted_successfully), "", AppConstants.OK);
            }

            @Override
            public void onFailure(String error) {
                Utilz.closeDialog();
                Toast.makeText(mActivity, R.string.failed_to_delete, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUnauthorized(int errorNumber) {
                Utilz.closeDialog();
                Toast.makeText(mActivity, R.string.failed_to_delete, Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick({R.id.seeAttendanceButton, R.id.sendMessageButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seeAttendanceButton:
                seeAttendance();
                break;
            case R.id.sendMessageButton:
                //for using sms gateway to send sms
                Utilz.setMessageDialog(mActivity, mStrPhoneNo, true);
                break;
        }
    }

    private void seeAttendance() {
        if (!TextUtils.isEmpty(mStrClassName) && !TextUtils.isEmpty(mStrSectionName)) {
            Intent mIntent = new Intent(mActivity, SeeAttendenceActivity.class);
            mIntent.putExtra(AppConstants.CLASS_NAME, mStrClassName);
            mIntent.putExtra(AppConstants.SECTION_NAME, mStrSectionName);
            startActivity(mIntent);
        } else {
            Toast.makeText(mActivity, R.string.class_or_section_is_missing_msg, Toast.LENGTH_SHORT).show();
        }
    }
}