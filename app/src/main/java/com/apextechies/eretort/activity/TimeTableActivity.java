package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.TimeTableAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.model.TimeTableModel;
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
import java.util.List;

/**
 * Created by Shankar on 2/10/2018.
 */

public class TimeTableActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rv_timetable;
    ArrayList<TimeTableModel> tableModels = new ArrayList<>();
    private  TimeTableAdapter timeTableAdapter;
    private TextView tv_monday,tv_tuesday,tv_wednesday,tv_thursday,tv_friday,tv_saturday;
    private String m_date,t_date, w_date, th_date, f_date, s_date;
    private String todaysdate;
    private  List<String> dateList;
    private int datePos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        todaysdate = Utilz.getCurrentDateInDigit(TimeTableActivity.this);
        initWidgit();
        getTodayday();
        callTimeTableApi(dateList.get(datePos));
    }

    private void getTodayday() {
        String dayOfTheWeek = Utilz.todaysday(TimeTableActivity.this);
        if (dayOfTheWeek.equalsIgnoreCase("Monday")){
            tv_monday.setBackgroundResource(R.drawable.rounded_corner_colored);
            tv_monday.setTextColor(getResources().getColor(R.color.colorAccent));
            datePos = 0;
        }
        else if (dayOfTheWeek.equalsIgnoreCase("Tuesday")){
            tv_tuesday.setBackgroundResource(R.drawable.rounded_corner_colored);
            tv_tuesday.setTextColor(getResources().getColor(R.color.colorAccent));
            datePos = 1;
        }
        else if (dayOfTheWeek.equalsIgnoreCase("Wednesday")){
            tv_wednesday.setBackgroundResource(R.drawable.rounded_corner_colored);
            tv_wednesday.setTextColor(getResources().getColor(R.color.colorAccent));
            datePos = 2;
        }
        else if (dayOfTheWeek.equalsIgnoreCase("Thursday")){
            tv_thursday.setBackgroundResource(R.drawable.rounded_corner_colored);
            tv_thursday.setTextColor(getResources().getColor(R.color.colorAccent));
            datePos = 3;
        }
        else if (dayOfTheWeek.equalsIgnoreCase("Friday")){
            tv_friday.setBackgroundResource(R.drawable.rounded_corner_colored);
            tv_friday.setTextColor(getResources().getColor(R.color.colorAccent));
            datePos = 4;
        }
        else if (dayOfTheWeek.equalsIgnoreCase("Saturday")){
            tv_saturday.setBackgroundResource(R.drawable.rounded_corner_colored);
            tv_saturday.setTextColor(getResources().getColor(R.color.colorAccent));
            datePos = 5;
            //f_date = todaysdate
        }

    }

    private void callTimeTableApi(String s) {
        Utilz.showDailog(TimeTableActivity.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(TimeTableActivity.this, new OnTaskCompleted() {
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
                                tableModels.add(new TimeTableModel(jo.optString("id"),jo.optString("schoolName"),
                                        jo.optString("class"),jo.optString("sec"),jo.optString("date"),
                                        jo.optString("from_time"),jo.optString("to_time"),jo.optString("subject"),jo.optString("teacher_name")));
                            }
                            timeTableAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(TimeTableActivity.this, ""+object.optString("msg"), Toast.LENGTH_SHORT).show();
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
        web.execute(WebServices.TIMETABLE);
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.timetable));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        tv_monday = (TextView)findViewById(R.id.tv_monday);
        tv_tuesday = (TextView)findViewById(R.id.tv_tuesday);
        tv_wednesday = (TextView)findViewById(R.id.tv_wednesday);
        tv_thursday = (TextView)findViewById(R.id.tv_thursday);
        tv_friday = (TextView)findViewById(R.id.tv_friday);
        tv_saturday = (TextView)findViewById(R.id.tv_saturday);

        tv_monday.setOnClickListener(this);
        tv_tuesday.setOnClickListener(this);
        tv_wednesday.setOnClickListener(this);
        tv_thursday.setOnClickListener(this);
        tv_friday.setOnClickListener(this);
        tv_saturday.setOnClickListener(this);

        rv_timetable = (RecyclerView)findViewById(R.id.rv_timetable);
        rv_timetable.setLayoutManager(new LinearLayoutManager(this));
        timeTableAdapter = new TimeTableAdapter(TimeTableActivity.this, tableModels, R.layout.timetable_row, new OnClickListener() {
            @Override
            public void onclick(int position) {

            }
        });
        rv_timetable.setAdapter(timeTableAdapter);

        dateList  = Utilz.getCalendar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_monday:
                changeBackNText(tv_monday);
                callTimeTableApi(dateList.get(0));
                break;
                case R.id.tv_tuesday:
                changeBackNText(tv_tuesday);
                callTimeTableApi(dateList.get(1));
                break;
            case R.id.tv_wednesday:
                changeBackNText(tv_wednesday);
                callTimeTableApi(dateList.get(2));
                break;
            case R.id.tv_thursday:
                changeBackNText(tv_thursday);
                callTimeTableApi(dateList.get(3));
                break;
            case R.id.tv_friday:
                changeBackNText(tv_friday);
                callTimeTableApi(dateList.get(4));
                break;
            case R.id.tv_saturday:
                changeBackNText(tv_saturday);
                callTimeTableApi(dateList.get(5));
                break;
        }
    }

    private void changeBackNText(TextView tv_day) {
        tv_monday.setBackgroundResource(R.drawable.rounded_corner_gray);
        tv_monday.setTextColor(getResources().getColor(R.color.secondaryText_new));
        tv_tuesday.setBackgroundResource(R.drawable.rounded_corner_gray);
        tv_tuesday.setTextColor(getResources().getColor(R.color.secondaryText_new));
        tv_wednesday.setBackgroundResource(R.drawable.rounded_corner_gray);
        tv_wednesday.setTextColor(getResources().getColor(R.color.secondaryText_new));
        tv_thursday.setBackgroundResource(R.drawable.rounded_corner_gray);
        tv_thursday.setTextColor(getResources().getColor(R.color.secondaryText_new));
        tv_friday.setBackgroundResource(R.drawable.rounded_corner_gray);
        tv_friday.setTextColor(getResources().getColor(R.color.secondaryText_new));
        tv_saturday.setBackgroundResource(R.drawable.rounded_corner_gray);
        tv_saturday.setTextColor(getResources().getColor(R.color.secondaryText_new));

        tv_day.setBackgroundResource(R.drawable.rounded_corner_colored);
        tv_day.setTextColor(getResources().getColor(R.color.colorAccent));

    }
}
