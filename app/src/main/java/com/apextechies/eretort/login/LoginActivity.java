package com.apextechies.eretort.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.activity.MainActivity;
import com.apextechies.eretort.activity.SkipActivity;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/29/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText input_email, input_password;
    private Button login, skip;
    private CheckBox cb_student, cb_teacher;
    private boolean studentSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        if (!ClsGeneral.getPreferences(LoginActivity.this, PreferenceName.ID).equalsIgnoreCase("")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        initWidgit();
        clickListener();
    }

    private void clickListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_email.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Enter your email id ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_password.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Eter password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    callApi();
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.LOgINTYPE, "skip");
                startActivity(new Intent(LoginActivity.this, SkipActivity.class));
                finish();
            }
        });
    }

    private void callApi() {
        Utilz.showDailog(LoginActivity.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(LoginActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utilz.closeDialog();
                if (response.length() > 0) {
                    String id, teacher_id,registrationId, teacherName,   teacherSchoolName,
                            teacherStatus, type;
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equalsIgnoreCase("true")) {
                            JSONArray array = object.getJSONArray("data");
                            id = array.getJSONObject(0).optString("id");
                            if (studentSelected){
                                registrationId = array.getJSONObject(0).optString("regestration_id");
                                teacherName = array.getJSONObject(0).optString("name");
                                String studentPhone = array.getJSONObject(0).optString("mobile");
                                String studentEmailId = array.getJSONObject(0).optString("email");
                                String studentClass = array.getJSONObject(0).optString("class");
                                String studentSection = array.getJSONObject(0).optString("sec");
//                                String student_branch_id = array.getJSONObject(0).optString("student_branch_id");
                                teacherStatus = array.getJSONObject(0).optString("status");
                                type = array.getJSONObject(0).optString("type");
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.MOBILE, studentPhone);
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.USER_EMAIL, studentEmailId);
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.CLASS, studentClass);
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.SECTION, studentSection);

                            }
                            else {
                                id = array.getJSONObject(0).optString("id");
                                registrationId = array.getJSONObject(0).optString("teacher_id");
                                teacherName = array.getJSONObject(0).optString("name");
                                String teacherQualification = array.getJSONObject(0).optString("qualification");
                                String teacherSubject = array.getJSONObject(0).optString("teacherSubject");
                                teacherStatus = array.getJSONObject(0).optString("status");
                                type = array.getJSONObject(0).optString("type");
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.QUALIFICATION, teacherQualification);
                                ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.SUBJECT, teacherSubject);
                            }


                            ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.ID, id);
                            ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.REESTRATIONID, registrationId);
                            ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.NAME, teacherName);
                            ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.STATUS, teacherStatus);
                            ClsGeneral.setPreferences(LoginActivity.this, PreferenceName.LOgINTYPE, type);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("email", input_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", input_password.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("studentSelected", ""+studentSelected));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.TEACHERLOGIN);
    }

    private void initWidgit() {
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.login);
        skip = (Button) findViewById(R.id.skip);
        cb_student = (CheckBox) findViewById(R.id.cb_student);
        cb_teacher = (CheckBox) findViewById(R.id.cb_teacher);

        cb_teacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_student.setChecked(false);
                    cb_teacher.setChecked(true);
                    studentSelected = false;
                } else {
                    cb_student.setChecked(true);
                    cb_teacher.setChecked(false);
                    studentSelected = true;
                }
            }
        });
        cb_student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_student.setChecked(true);
                    cb_teacher.setChecked(false);
                    studentSelected = true;
                } else {
                    cb_student.setChecked(true);
                    cb_teacher.setChecked(true);
                    studentSelected = false;
                }
            }
        });

        studentSelected = true;
    }
}
