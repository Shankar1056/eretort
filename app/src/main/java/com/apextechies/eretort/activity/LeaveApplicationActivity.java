package com.apextechies.eretort.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.model.TeacherDetailsModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shankar on 3/10/2018.
 */

public class LeaveApplicationActivity extends AppCompatActivity implements OnItemSelectedListener, DatePickerDialog.OnDateSetListener{
    private ArrayList<TeacherDetailsModel> teacherlist = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    Spinner spinner;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;
    TextView tv_fromDate, tv_toDate;
    private EditText textName, textDescription;
    private Button submit;
    private boolean from = true;
    private String spinneritem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaveapplication);
        initWidgit();
        openDatepicker();
        getTeacherDetails();
    }

    private void openDatepicker() {
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = true;
                datePickerDialog = DatePickerDialog.newInstance(LeaveApplicationActivity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#009688"));

                datePickerDialog.setTitle("Select Date From DatePickerDialog");

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });
        tv_toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = false;
                datePickerDialog = DatePickerDialog.newInstance(LeaveApplicationActivity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#009688"));

                datePickerDialog.setTitle("Select Date From DatePickerDialog");

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });
    }


    private void getTeacherDetails() {
            Utilz.showDailog(LeaveApplicationActivity.this, getResources().getString(R.string.please_wait));
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            Download_web web = new Download_web(LeaveApplicationActivity.this, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String response) {
                    Utilz.closeDialog();
                    if (response!=null && response.length()>0){
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optString("status").equalsIgnoreCase("true")){

                                JSONArray array = object.getJSONArray("data");
                                for (int i=0; i<array.length(); i++) {
                                    JSONObject jo = array.getJSONObject(i);
                                    teacherlist.add(new TeacherDetailsModel(jo.optString("teacher_id"),jo.optString("teacherRegistrationId"),
                                            jo.optString("teacherName"),jo.optString("teacherQualification"),jo.optString("teacherSubject"),
                                            jo.optString("teacherSchoolName"),jo.optString("teacherStatus")));
                                    list.add(jo.optString("teacherName"));
                                }

                                setAdapter();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            web.setReqType(true);
            web.execute(WebServices.TEACHERDETAILS);
    }

    private void setAdapter() {
        ArrayAdapter dataAdapter = new ArrayAdapter (this, android.R.layout.simple_spinner_item, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.leave_appliction));
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        tv_fromDate = (TextView)findViewById(R.id.tv_fromDate);
        tv_toDate = (TextView)findViewById(R.id.tv_toDate);
        submit = (Button) findViewById(R.id.submit);
        textName = (EditText) findViewById(R.id.textName);
        textDescription = (EditText) findViewById(R.id.textDescription);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLeaveRequest();
            }
        });
    }

    private void saveLeaveRequest() {
        Utilz.showDailog(LeaveApplicationActivity.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(LeaveApplicationActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utilz.closeDialog();
                if (response!=null && response.length()>0){
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equalsIgnoreCase("true")){

                            finish();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("description", textDescription.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("name", textName.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("fromtime", tv_fromDate.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("totime", tv_toDate.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("approver", spinneritem));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.LEAVEAPPLICATION);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinneritem = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.new_color));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (from)
        {
            tv_fromDate.setText(dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
        }else {
            tv_toDate.setText(dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
        }
    }
}
