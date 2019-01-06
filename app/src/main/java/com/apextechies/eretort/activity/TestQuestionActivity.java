package com.apextechies.eretort.activity;

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
import com.apextechies.eretort.adapter.CategoryAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.CategoryModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Shankar on 3/11/2018.
 */

public class TestQuestionActivity extends AppCompatActivity {
    private RecyclerView rv_category;
    private ArrayList<CategoryModel> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testquestion);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidgit();
        if (Utilz.isInternetConnected(TestQuestionActivity.this)) {
            callApi();
        }
        else
        {
            Toast.makeText(TestQuestionActivity.this, ""+getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    private void callApi() {
        Utilz.showDailog(TestQuestionActivity.this, getResources().getString(R.string.pleasewait));
        Download_web web = new Download_web(TestQuestionActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utilz.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true")) {
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jo = array.getJSONObject(i);
                                String cat_id = jo.optString("id");
                                String catName = jo.optString("catName");
                                String catIcon = jo.optString("catIcon");
                                list.add(new CategoryModel(cat_id, catName, catIcon));
                            }
                            CategoryAdapter adapter = new CategoryAdapter(TestQuestionActivity.this, list, new OnClickListener() {
                                @Override
                                public void onclick(int position) {
                                    startActivity(new Intent(TestQuestionActivity.this, CategoryList.class).putExtra("id", list.get(position).getCat_id()).
                                            putExtra("name", list.get(position).getCatName()));
                                }


                            });
                            rv_category.setAdapter(adapter);
                        } else {
                            Toast.makeText(TestQuestionActivity.this, "" + object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        web.setReqType(true);
        web.execute(WebServices.GETCATEGORY);
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Online Test");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rv_category = (RecyclerView) findViewById(R.id.rv_category);
        rv_category.setLayoutManager(new LinearLayoutManager(TestQuestionActivity.this));
    }
}
