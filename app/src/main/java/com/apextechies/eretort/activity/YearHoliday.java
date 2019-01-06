package com.apextechies.eretort.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.model.YearHolidayModel;
import com.apextechies.eretort.utilz.CalendarView;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Shankar on 1/13/2018.
 */

public class YearHoliday extends AppCompatActivity {
    private CalendarView cv;
    private ArrayList<YearHolidayModel> yearHolidayModels = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);
        datemapping();
        getData();
    }

    private void getData() {
        Utilz.showDailog(YearHoliday.this,getResources().getString(R.string.please_wait));
        Download_web web = new Download_web(YearHoliday.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utilz.closeDialog();

                Log.e("Response",response);
                if (response.length()>0)
                {

                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true"))
                        {
                            JSONArray jsonArray = object.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                yearHolidayModels.add(new YearHolidayModel(jo.optString("id"),jo.optString("name"),
                                        jo.optString("date"),jo.optString("image"),jo.optString("schoolName")));
                            }
                            HashSet<Date> events = new HashSet<>();
                            events.add(new Date());
                            cv.updateCalendar(events,yearHolidayModels);
                        }
                        else
                        {
                            Toast.makeText(YearHoliday.this, ""+object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        web.setReqType(true);
        web.execute(WebServices.YEARHOLIDAY);



    }



    private void datemapping() {
        cv = ((CalendarView)findViewById(R.id.calendar_view));
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {

                TextView description = (TextView)findViewById(R.id.description);


                SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
                String folderName = formatter.format(date);
                for (int i=0; i<yearHolidayModels.size();i++) {
                    if (yearHolidayModels.get(i).getDate().equalsIgnoreCase(folderName))
                    {
                        description.setText(yearHolidayModels.get(i).getName());
                    }
                }

            }
        });
    }
}
