package com.apextechies.eretort.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.apextechies.eretort.adapter.HomeCategoryShimmerAdapter;
import com.apextechies.eretort.adapter.QuestionListAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.model.AskQuestionModel;
import com.apextechies.eretort.model.BannerImageModel;
import com.apextechies.eretort.model.HomeCategoryModel;
import com.apextechies.eretort.studentforum.AskQuestion;
import com.apextechies.eretort.studentforum.QuesAnswerActivity;
import com.apextechies.eretort.studentforum.QuestionList;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.utilz.viewholders.DemoConfiguration;
import com.apextechies.eretort.webservices.WebServices;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private AdView mAdView;
    private RecyclerView home_RecyclerView, rv_topques;
    HomeCategoryAdapter adapter;
    private ImageView toolbar;
    //view pager
    private ViewPager mViewPager;
    private CustomPagerAdapter mCustomPagerAdapter;
    private RecyclerView dots;
    private RecyclerView rv_drawer;
    private DotsAdapter dotsAdapter;
    private int previousPage;
    private ArrayList<HomeCategoryModel> modellist = new ArrayList<>();
    private ArrayList<AskQuestionModel> askquestionList = new ArrayList<>();
    private ArrayList<AskQuestionModel> dummyList = new ArrayList<>();
    private DrawerLayout drawer;
    private TextView tv_attendance, tv_timetable, tv_test, tv_leaves, tv_forum, userName, userClass, userSec;
    private ShimmerRecyclerView shimmerRecycler;
    private HomeCategoryShimmerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        toolbar = (ImageView) findViewById(R.id.toolbar);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Pangram-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        initShimmerWidgit();
        initWidget();
        callHomeSliderApi();
        callTopQues();
        initAds();
    }

    private void initShimmerWidgit() {
        shimmerRecycler = (ShimmerRecyclerView) findViewById(R.id.shimmer_recycler_view);
        shimmerRecycler.setNestedScrollingEnabled(false);
        shimmerRecycler.setVisibility(View.VISIBLE);
        final DemoConfiguration demoConfiguration = DemoConfiguration.getDemoConfiguration(MainActivity.this);
        if (demoConfiguration.getItemDecoration() != null) {
            shimmerRecycler.addItemDecoration(demoConfiguration.getItemDecoration());
        }

        mAdapter = new HomeCategoryShimmerAdapter();
        shimmerRecycler.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        shimmerRecycler.setAdapter(mAdapter);
        shimmerRecycler.showShimmerAdapter();


    }

    private void callTopQues() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                if (response.length() > 0) {
                    Utilz.closeDialog();
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.optString("status").equals("true")) {
                            JSONArray array = obj.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                if (i <= 10) {
                                    JSONObject jo = array.getJSONObject(i);
                                    String forum_id = jo.optString("id");
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
                            }

                            QuestionListAdapter adapter = new QuestionListAdapter(MainActivity.this, askquestionList, new OnClickListener() {
                                @Override
                                public void onclick(int position) {
                                    dummyList.clear();
                                    AskQuestionModel am = askquestionList.get(position);
                                    dummyList.add(new AskQuestionModel(am.getForum_id(), am.getRegistratinId(), am.getQuestionText(),
                                            am.getQuestionImage(), am.getForumStatus(), am.getSchoolName(), am.getDate(), am.getTime()));
                                    startActivity(new Intent(MainActivity.this, QuesAnswerActivity.class).putParcelableArrayListExtra("list", dummyList));
                                }
                            });
                            rv_topques.setAdapter(adapter);
                        } else {
                            Toast.makeText(MainActivity.this, "" + obj.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        web.setReqType(true);
        web.execute(WebServices.ALLQUESTIONLIST);

    }

    private void callHomeCategoryApi() {
        // Utilz.showProgress(MainActivity.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                // Utilz.closeDialog();
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


                            shimmerRecycler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.setCards(modellist, new OnClickListener() {
                                        @Override
                                        public void onclick(int position) {
                                            if (ClsGeneral.getPreferences(MainActivity.this, PreferenceName.LOgINTYPE).equalsIgnoreCase("teacher")) {
                                                chkNsendPos(position);
                                            } else {
                                                chkNsendstudentPos(position);
                                            }
                                        }


                                    });
                                    shimmerRecycler.hideShimmerAdapter();
                                    // home_RecyclerView.setAdapter(adapter);
                                }
                            }, 100);

                        } else {
                            Toast.makeText(MainActivity.this, "" + object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        nameValuePairs.add(new BasicNameValuePair("type", ClsGeneral.getPreferences(MainActivity.this, PreferenceName.LOgINTYPE)));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.HOMECATEGORY);
    }

    private void chkNsendPos(int position) {

        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, NoticeBoard.class));
                break;

            case 1:
                startActivity(new Intent(MainActivity.this, DirectorMessage.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, NewsNEventsActivity.class));
                break;

            case 3:
                startActivity(new Intent(MainActivity.this, YearHoliday.class));
                break;

            case 4:
                startActivity(new Intent(MainActivity.this, GalleryList.class).putExtra("operation", "gal"));
                break;

            case 5:
                startActivity(new Intent(MainActivity.this, Attendance.class));
                break;

            case 6:
                startActivity(new Intent(MainActivity.this, DialyHomework.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, TimeTableActivity.class));
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, QuestionList.class));
                break;
            case 9:
                startActivity(new Intent(MainActivity.this, LeaveRequestList.class));
                break;
            case 10:
                startActivity(new Intent(MainActivity.this, TestQuestionActivity.class));
                break;
            case 13:
                startActivity(new Intent(MainActivity.this, TopperList.class));
                break;
            case 14:
                startActivity(new Intent(MainActivity.this, GalleryList.class).putExtra("operation", "alu"));
                break;

        }
    }


    private void initWidget() {
        home_RecyclerView = (RecyclerView) findViewById(R.id.home_RecyclerView);
        home_RecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        dots = (RecyclerView) findViewById(R.id.dots);
        mViewPager = (ViewPager) findViewById(R.id.pager_zoom);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        userName = (TextView) findViewById(R.id.userName);
        userClass = (TextView) findViewById(R.id.userClass);
        userSec = (TextView) findViewById(R.id.userSec);
        userName.setOnClickListener(this);
        findViewById(R.id.ll_drawer).setOnClickListener(this);
        findViewById(R.id.tv_rateus).setOnClickListener(this);
        findViewById(R.id.tv_feedback).setOnClickListener(this);
        findViewById(R.id.tv_share).setOnClickListener(this);
        findViewById(R.id.tv_contactus).setOnClickListener(this);
        findViewById(R.id.principalSection).setOnClickListener(this);

        userName.setText(ClsGeneral.getPreferences(MainActivity.this, PreferenceName.NAME));
        userClass.setText(ClsGeneral.getPreferences(MainActivity.this, PreferenceName.CLASS));
        userSec.setText(ClsGeneral.getPreferences(MainActivity.this, PreferenceName.SECTION));
        rv_topques = (RecyclerView) findViewById(R.id.rv_topques);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rv_topques.setLayoutManager(layoutManager);

        // home_RecyclerView.setNestedScrollingEnabled(false);
        rv_topques.setNestedScrollingEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AskQuestion.class);
                intent.putExtra("type", "ask");
                startActivity(intent);
            }
        });
        fab.setImageResource(R.mipmap.ask);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });


    }

    private void callHomeSliderApi() {
        try {
            Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
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

                            }
                            if (jsonObject1.has("status")) {
                                status = jsonObject1.getString("status");
                            }

                            if (status.equalsIgnoreCase("1")) {
                                models.add(new BannerImageModel(id, banner));
                            }
                        }
                        mCustomPagerAdapter = new CustomPagerAdapter();
                        mCustomPagerAdapter.setData(models, MainActivity.this, MainActivity.this);
                        mCustomPagerAdapter.isDynamic(true);
                        mCustomPagerAdapter.setVP(mViewPager);
                        mViewPager.setAdapter(mCustomPagerAdapter);
                        if (models.size() > 1) {
                            dots.setHasFixedSize(true);
                            dots.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
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

   /* private void callHomeCategoryStudentApi() {
        Utilz.showProgress(MainActivity.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(MainActivity.this, new OnTaskCompleted() {
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
                            adapter = new HomeCategoryAdapter(MainActivity.this, modellist, new OnClickListener() {
                                @Override
                                public void onclick(int position) {

                                    chkNsendstudentPos(position);
                                }
                            });
                            home_RecyclerView.setAdapter(adapter);

                        } else {
                            Toast.makeText(MainActivity.this, "" + object.optString("msg"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        web.setReqType(true);
        web.execute(WebServices.HOMECATEGORYSTUDENT);
    }*/

    private void chkNsendstudentPos(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, NoticeBoard.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, AboutUs.class));
                break;

            case 2:
                startActivity(new Intent(MainActivity.this, DirectorMessage.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, NewsNEventsActivity.class));
                break;

            case 4:
                startActivity(new Intent(MainActivity.this, YearHoliday.class));
                break;

            case 5:
                startActivity(new Intent(MainActivity.this, GalleryList.class).putExtra("operation", "gal"));
                break;

            case 6:
                startActivity(new Intent(MainActivity.this, TakeAttendenceActivity.class));
                break;

            case 7:
                startActivity(new Intent(MainActivity.this, DialyHomework.class));
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, TimeTableActivity.class));
                break;
            case 9:
                startActivity(new Intent(MainActivity.this, QuestionList.class));
                break;
            case 10:
                startActivity(new Intent(MainActivity.this, LeaveApplicationActivity.class));
                break;
            case 11:
                startActivity(new Intent(MainActivity.this, TestQuestionActivity.class));
                break;
            case 12:
//                startActivity(new Intent(MainActivity.this, TestQuestionActivity.class));
                break;
            case 13:
                startActivity(new Intent(MainActivity.this, TopperList.class));
                break;
            case 14:
                startActivity(new Intent(MainActivity.this, GalleryList.class).putExtra("operation", "alu"));
                break;

        }
    }

    private void initAds() {
        MobileAds.initialize(this, getResources().getString(R.string.ADMOB_APP_ID));
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.ADUNIT_ID));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userName:
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.tv_rateus:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.tv_feedback:
                startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.tv_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Click below link to Download Your school App \n https://play.google.com/store/apps/details?id=com.apextechies.eretort");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.tv_contactus:
                startActivity(new Intent(MainActivity.this, ContactUs.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.principalSection:
                startActivity(new Intent(MainActivity.this, PrincipalZone.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }
}

