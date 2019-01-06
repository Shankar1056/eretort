package com.apextechies.eretort.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.CateoryListAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.QuesAnsModel;
import com.apextechies.eretort.model.SubCatListModel;
import com.apextechies.eretort.model.SubCategoryModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Shankar on 12/28/2017.
 */

public class CategoryList extends AppCompatActivity {

    private String catid;
    private RecyclerView rv_list;
    private ArrayList<SubCatListModel> list = new ArrayList<>();
    private ArrayList<QuesAnsModel> qnlist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callist);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidgit();
        if (Utilz.isInternetConnected(CategoryList.this)) {
            callCatApi();
        } else {
            Toast.makeText(CategoryList.this, "" + getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void callCatApi() {
        Utilz.showDailog(CategoryList.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(CategoryList.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Utilz.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        Gson gson = new Gson();
                        final SubCategoryModel subCategoryModel = gson.fromJson(response, SubCategoryModel.class);

                        if (subCategoryModel.getStatus().equals("true")) {

                            list = subCategoryModel.getData();
                            CateoryListAdapter adapter = new CateoryListAdapter(CategoryList.this, list, new OnClickListener() {
                                @Override
                                public void onclick(int position) {
                                    if (subCategoryModel.getData().get(position).getAuesand().size() > 0) {
                                        qnlist = subCategoryModel.getData().get(position).getAuesand();
                                        startActivity(new Intent(CategoryList.this, QuesAnsActivity.class).putParcelableArrayListExtra("list", qnlist).
                                                putExtra("name", list.get(position).getSubCatNme()));
                                    } else {
                                        Toast.makeText(CategoryList.this, "No Questions found", Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });
                            rv_list.setAdapter(adapter);
                        } else {
                            Toast.makeText(CategoryList.this, "" + object.opt("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("id", catid));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.SUBCATEGORY);

    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(CategoryList.this));
        catid = getIntent().getStringExtra("id");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.menu_item:
                if (Utilz.isInternetConnected(CategoryList.this)) {
                    callCatApi();
                } else {
                    Toast.makeText(CategoryList.this, "" + getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
                }
                return true;


            default: return super.onOptionsItemSelected(item);
        }
    }
}
