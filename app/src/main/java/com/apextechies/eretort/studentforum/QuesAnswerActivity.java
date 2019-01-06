package com.apextechies.eretort.studentforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.ReplyAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.common.CircleImageView;
import com.apextechies.eretort.model.AskQuestionModel;
import com.apextechies.eretort.model.ReplyModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/19/2017.
 */

public class QuesAnswerActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<AskQuestionModel> askquestionList = new ArrayList<>();
    private ArrayList<ReplyModel> replyModels = new ArrayList<>();
    private CircleImageView profile_image;
    private TextView quesText, date, time;
    private ImageView quesImage;
    private ReplyAdapter replyAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quesanswer_activity);
        getIntentData();
        initWidgit();
        setdata();
        callReplyApi();
    }

    private void callReplyApi() {
        Utilz.showDailog(QuesAnswerActivity.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(QuesAnswerActivity.this, new OnTaskCompleted() {
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
                                String reply_id = jo.optString("id");
                                String registrationNumber = jo.optString("registrationNumber");
                                String replyText = jo.optString("replyText");
                                String replyImage = jo.optString("replyImage");
                                String schoolName = jo.optString("schoolName");
                                String replyStatus = jo.optString("replyStatus");
                                String forum_id = jo.optString("forum_id");
                                String date = jo.optString("date");
                                String time = jo.optString("time");

                                replyModels.add(new ReplyModel(reply_id, registrationNumber, replyText, replyImage, schoolName, replyStatus, forum_id, date, time));
                            }
                            replyAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(QuesAnswerActivity.this, "" + obj.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("forum_id", askquestionList.get(0).getForum_id()));
        web.setReqType(false);
        web.setData(nameValuePairs);
        web.execute(WebServices.ALLREPLY);

    }

    private void setdata() {
        if (askquestionList.get(0).getQuestionImage().length() > 0) {
            Picasso.with(QuesAnswerActivity.this).load(askquestionList.get(0).getQuestionImage()).into(quesImage);
        }
        else
        {
            quesImage.setVisibility(View.GONE);
        }
        quesText.setText(askquestionList.get(0).getQuestionText());
        date.setText(askquestionList.get(0).getDate());
        time.setText(askquestionList.get(0).getTime());
    }


    private void getIntentData() {
        try {
            askquestionList = getIntent().getParcelableArrayListExtra("list");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar);
        TextView addAnswer = (TextView) findViewById(R.id.addAnswer);

        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        quesText = (TextView) findViewById(R.id.quesText);
        quesImage = (ImageView) findViewById(R.id.quesImage);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        RecyclerView rv_reply = (RecyclerView) findViewById(R.id.rv_reply);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuesAnswerActivity.this);
        rv_reply.setLayoutManager(linearLayoutManager);
       // rv_reply.setLayoutManager(new LinearLayoutManager(QuesAnswerActivity.this, LinearLayout.VERTICAL, false).setReverseLayout(true));
        rv_reply.setHasFixedSize(true);
        setadapter(rv_reply);


        addAnswer.setOnClickListener(this);
    }

    private void setadapter(RecyclerView rv_reply) {

        replyAdapter = new ReplyAdapter(QuesAnswerActivity.this, replyModels, R.layout.questionlist_row, new OnClickListener() {
            @Override
            public void onclick(int position) {

            }
        });
        rv_reply.setAdapter(replyAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAnswer:
//                startActivity(new Intent(QuesAnswerActivity.this,AskQuestion.class).putExtra("type","ans")
//                .putExtra("forumId",askquestionList.get(0).getForum_id()));
                Intent intent = new Intent(QuesAnswerActivity.this, AskQuestion.class);
                intent.putExtra("type", "ans");
                intent.putExtra("forumId", askquestionList.get(0).getForum_id());
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            ArrayList<ReplyModel> dummylist = new ArrayList<>();
            dummylist = data.getParcelableArrayListExtra("MESSAGE");
            replyModels.addAll(dummylist
            );
            replyAdapter.notifyDataSetChanged();
        }
    }
}
