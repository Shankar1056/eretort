package com.apextechies.eretort.studentforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.QuestionListAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.model.AskQuestionModel;
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
 * Created by Shankar on 12/31/2017.
 */

public class QuestionList extends AppCompatActivity {
    ArrayList<AskQuestionModel> askquestionList = new ArrayList<>();
    ArrayList<AskQuestionModel> dummyList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionlist_activity);
        initWidgit();
        callApi();
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_question));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuestionList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callApi() {
        Utilz.showDailog(QuestionList.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(QuestionList.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utilz.closeDialog();
                if (response.length() > 0) {
                    Utilz.closeDialog();
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.optString("status").equals("true")) {
                            JSONArray array = obj.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jo = array.getJSONObject(i);
                                String forum_id = jo.optString("forum_id");
                                String registratinId = jo.optString("registratinId");
                                String questionText = jo.optString("questionText");
                                String questionImage = jo.optString("questionImage");
                                String forumStatus = jo.optString("forumStatus");
                                String schoolName = jo.optString("schoolName");
                                String date = jo.optString("date");
                                String time = jo.optString("time");
                                askquestionList.add(new AskQuestionModel(forum_id, registratinId, questionText, questionImage, forumStatus,
                                        schoolName, date, time));
                            }
                         //   Collections.sort(askquestionList, Collections.reverseOrder());

                            QuestionListAdapter adapter = new QuestionListAdapter(QuestionList.this, askquestionList, new OnClickListener() {
                                @Override
                                public void onclick(int position) {
                                    dummyList.clear();
                                    AskQuestionModel am = askquestionList.get(position);
                                    dummyList.add(new AskQuestionModel(am.getForum_id(),am.getRegistratinId(),am.getQuestionText(),
                                            am.getQuestionImage(),am.getForumStatus(),am.getSchoolName(),am.getDate(), am.getTime()));
                                    startActivity(new Intent(QuestionList.this,QuesAnswerActivity.class).putParcelableArrayListExtra("list",dummyList));
                                }
                            });
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(QuestionList.this, "" + obj.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setData(nameValuePairs);
        web.setReqType(true);
        web.execute(WebServices.ALLQUESTIONLIST);
    }

}
