package com.apextechies.eretort.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.CustomPagerAdapter;
import com.apextechies.eretort.adapter.DotsAdapter;
import com.apextechies.eretort.adapter.HomeCategoryAdapter;
import com.apextechies.eretort.adapter.SkipPagerAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.login.LoginActivity;
import com.apextechies.eretort.model.AskQuestionModel;
import com.apextechies.eretort.model.BannerImageModel;
import com.apextechies.eretort.model.HomeCategoryModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Shankar on 3/18/2018.
 */

public class SkipActivity extends AppCompatActivity {
    private AdView mAdView;
    private RecyclerView home_RecyclerView;
    HomeCategoryAdapter adapter;
    private ImageView toolbar;
    //view pager
    private ViewPager mViewPager;
    private SkipPagerAdapter mCustomPagerAdapter;
    private RecyclerView dots;
    private DotsAdapter dotsAdapter;
    private int previousPage;
    private ArrayList<HomeCategoryModel> modellist = new ArrayList<>();
    private ArrayList<AskQuestionModel> dummyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_skip);
        toolbar = (ImageView) findViewById(R.id.toolbar);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Pangram-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        initWidget();
        callHomeSliderApi();
    }


    private void callHomeCategoryApi() {
        Utilz.showProgress(SkipActivity.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(SkipActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utilz.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equals("true")) {
                            JSONArray jsonArray = object.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                modellist.add(new HomeCategoryModel(jo.optString("cat_id"), jo.optString("catName"), jo.optString("catColor"),
                                        jo.optString("catIcon"), jo.optString("scholName")));
                            }
                            adapter = new HomeCategoryAdapter(SkipActivity.this, modellist, new OnClickListener() {
                                @Override
                                public void onclick(int position) {

                                    chkNsendPos(position, modellist.get(position).getCatName());
                                }
                            });
                            home_RecyclerView.setAdapter(adapter);

                        } else {
                            Toast.makeText(SkipActivity.this, "" + object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        nameValuePairs.add(new BasicNameValuePair("type", ClsGeneral.getPreferences(SkipActivity.this, PreferenceName.LOgINTYPE)));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.HOMECATEGORY);
    }

    private void chkNsendPos(int position, String catName) {

        switch (position) {
            case 0:
                startActivity(new Intent(SkipActivity.this, NoticeBoard.class));
                break;
            case 1:
                startActivity(new Intent(SkipActivity.this, AboutUs.class));
                break;

            case 2:
                startActivity(new Intent(SkipActivity.this, DirectorMessage.class));
                break;
            case 3:
                startActivity(new Intent(SkipActivity.this, NewsNEventsActivity.class));
                break;

            case 4:
                startActivity(new Intent(SkipActivity.this, YearHoliday.class));
                break;

            case 5:
                startActivity(new Intent(SkipActivity.this, GalleryList.class).putExtra("operation", "gal"));
                break;

            case 6:
                startActivity(new Intent(SkipActivity.this, LoginActivity.class));
                break;

            case 7:
                startActivity(new Intent(SkipActivity.this, LoginActivity.class));
                break;

            case 8:
                startActivity(new Intent(SkipActivity.this, ContactUs.class));
                break;
        }
    }


    private void initWidget() {
        home_RecyclerView = (RecyclerView) findViewById(R.id.home_RecyclerView);
        home_RecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        dots = (RecyclerView) findViewById(R.id.dots);
        mViewPager = (ViewPager) findViewById(R.id.pager_zoom);
        home_RecyclerView.setNestedScrollingEnabled(false);

        TextView recomended = (TextView)findViewById(R.id.recomended);
        recomended.setVisibility(View.GONE);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected", "position " + position);
                try {
                    if (previousPage != position) {
                        dotsAdapter.setSelected(position);
                        dotsAdapter.notifyDataSetChanged();
                    }
                    previousPage = position;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("onScrollStateChanged", "state " + state);
            }
        });


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }

    private void callHomeSliderApi() {
        try {
            Download_web web = new Download_web(SkipActivity.this, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String response) {

                    try {
                        String banner = null, id = null, status = null;
                        ArrayList<BannerImageModel> models = new ArrayList<>();
                        JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.has("id")) {
                                id = jsonObject1.getString("id");
                            }
                            if (jsonObject1.has("banner")) {
                                banner = jsonObject1.getString("banner");

                            }if (jsonObject1.has("status")) {
                                status = jsonObject1.getString("status");
                            }

                            if (status.equalsIgnoreCase("1")) {
                                models.add(new BannerImageModel(id, banner));
                            }
                        }
                        mCustomPagerAdapter = new SkipPagerAdapter();
                        mCustomPagerAdapter.setData(models, SkipActivity.this, SkipActivity.this);
                        mCustomPagerAdapter.isDynamic(true);
                        mCustomPagerAdapter.setVP(mViewPager);
                        mViewPager.setAdapter(mCustomPagerAdapter);
                        if (models.size() > 1) {
                            dots.setHasFixedSize(true);
                            dots.setLayoutManager(new LinearLayoutManager(SkipActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            dotsAdapter = new DotsAdapter(models.size());
                            dots.setAdapter(dotsAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            web.setReqType(true);
            web.execute(WebServices.BANNERSLIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }
        callHomeCategoryApi();
    }



    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }


}


