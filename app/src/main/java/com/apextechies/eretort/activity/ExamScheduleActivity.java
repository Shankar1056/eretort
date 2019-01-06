package com.apextechies.eretort.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.ExamScheduleAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.model.ExamScheduleModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.tiagohm.horizontalcalendar.HorizontalCalendar;
import br.tiagohm.horizontalcalendar.HorizontalCalendarListener;

/**
 * Created by Shankar on 3/5/2018.
 */

public class ExamScheduleActivity extends AppCompatActivity {
    private HorizontalCalendar horizontalCalendar;
    private RecyclerView rv_timetable;
    ArrayList<ExamScheduleModel> tableModels = new ArrayList<>();
    private ExamScheduleAdapter homeworkAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examscedule);
        initWidgit();

    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.exam_scedule));

        rv_timetable = (RecyclerView)findViewById(R.id.examscedule);
        rv_timetable.setLayoutManager(new LinearLayoutManager(this));
        homeworkAdapter = new ExamScheduleAdapter(ExamScheduleActivity.this, tableModels, R.layout.timetable_row, new OnClickListener() {
            @Override
            public void onclick(int position) {

            }
        });
        rv_timetable.setAdapter(homeworkAdapter);


        /** end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        /** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .textSize(14f, 24f, 14f)
                .showDayName(true)
                .showMonthName(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(date);
             //   Toast.makeText(ExamScheduleActivity.this, formattedDate + " is selected!", Toast.LENGTH_SHORT).show();
                callExamScedule(formattedDate);
            }

        });

        horizontalCalendar.goToday(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

        private void callExamScedule(String s) {
            Utilz.showDailog(ExamScheduleActivity.this, getResources().getString(R.string.please_wait));
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            Download_web web = new Download_web(ExamScheduleActivity.this, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String response) {
                    Utilz.closeDialog();
                    if (response!=null && response.length()>0){
                        tableModels.clear();

                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optString("status").equals("true"))
                            {
                                JSONArray jsonArray = object.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jo = jsonArray.getJSONObject(i);
                                    tableModels.add(new ExamScheduleModel(jo.optString("id"),jo.optString("school_id"),
                                            jo.optString("class"),jo.optString("sec"),jo.optString("date"),
                                            jo.optString("from_time"),jo.optString("to_time"),jo.optString("invigilator"),jo.optString("subject"),jo.optString("status"),jo.optString("created_date")));
                                }
                                homeworkAdapter.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(ExamScheduleActivity.this, ""+object.optString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            nameValuePairs.add(new BasicNameValuePair("class","7"));
            nameValuePairs.add(new BasicNameValuePair("sec","a"));
            nameValuePairs.add(new BasicNameValuePair("date",s));
            web.setData(nameValuePairs);
            web.setReqType(false);
            web.execute(WebServices.EXAMTIMETABLE);
        }
    }


