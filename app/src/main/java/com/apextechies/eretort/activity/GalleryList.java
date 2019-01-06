package com.apextechies.eretort.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.GalleryListAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.GalleryDataModel;
import com.apextechies.eretort.model.GalleryModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Shankar on 4/6/2018.
 */

public class GalleryList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<GalleryDataModel> galleryModelsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
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
        if (Utilz.isInternetConnected(GalleryList.this)) {
            callCatApi();
        } else {
            Toast.makeText(GalleryList.this, "" + getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }
    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (getIntent().getStringExtra("operation").equalsIgnoreCase("alu")){
            getSupportActionBar().setTitle(getResources().getString(R.string.alumnilist));
        }
        else {
            getSupportActionBar().setTitle(getResources().getString(R.string.gallery));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callCatApi() {
        Utilz.showDailog(GalleryList.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(GalleryList.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Utilz.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        Gson gson = new Gson();
                        final GalleryModel datanodel = gson.fromJson(response, GalleryModel.class);

                        if (datanodel.getStatus().equals("true")) {

                            galleryModelsList = datanodel.getData();
                            GalleryListAdapter adapter = new GalleryListAdapter(GalleryList.this, galleryModelsList, new OnClickListener() {
                                @Override
                                public void onclick(int position) {
                                    if (galleryModelsList.size() > 0) {
                                        //galleryModelsList = datanodel.getData();
                                        Intent intent = new Intent(GalleryList.this,Gallery.class);
                                        Bundle bundle = new Bundle();
                                        intent.putParcelableArrayListExtra("images", galleryModelsList.get(position).getImages());
                                        intent.putExtra("position", position);
                                        startActivity(intent);


                                    } else {
                                        Toast.makeText(GalleryList.this, "No Questions found", Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(GalleryList.this, "" + object.opt("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setData(nameValuePairs);
        web.setReqType(true);
       /* if (getIntent().getStringExtra("operation").equalsIgnoreCase("alu")) {
            web.execute(WebServices.ALUMNI);
        } else {*/
            web.execute(WebServices.GALLERY);
        //}

    }
    }
