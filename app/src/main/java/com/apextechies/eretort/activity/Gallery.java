package com.apextechies.eretort.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import com.apextechies.eretort.adapter.GalleryAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.fragment.SlideshowDialogFragment;
import com.apextechies.eretort.model.GalleryImages;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Shankar on 3/18/2018.
 */

public class Gallery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<GalleryImages> galleryModelsList = new ArrayList<>();

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

    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        /*if (getIntent().getStringExtra("operation").equalsIgnoreCase("alu")){
            getSupportActionBar().setTitle(getResources().getString(R.string.alumnilist));
        }
        else {*/
            getSupportActionBar().setTitle(getResources().getString(R.string.gallery));
       // }

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

        Bundle bundle = getIntent().getExtras();


        galleryModelsList = bundle.getParcelableArrayList("images");
        GalleryAdapter adapter = new GalleryAdapter(Gallery.this, galleryModelsList, new OnClickListener() {
            @Override
            public void onclick(int position) {
                if (galleryModelsList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("images", galleryModelsList);
                    bundle.putInt("position", position);

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "slideshow");

                } else {
                    Toast.makeText(Gallery.this, "No Questions found", Toast.LENGTH_SHORT).show();
                }

            }


        });
        recyclerView.setAdapter(adapter);

    }

   /* private void callCatApi() {
        Utilz.showDailog(Gallery.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(Gallery.this, new OnTaskCompleted() {
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
                            GalleryAdapter adapter = new GalleryAdapter(Gallery.this, galleryModelsList, new OnClickListener() {
                                @Override
                                public void onclick(int position) {
                                    if (datanodel.getData().size() > 0) {
                                        galleryModelsList = datanodel.getData();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("images", galleryModelsList);
                                        bundle.putInt("position", position);

                                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                                        newFragment.setArguments(bundle);
                                        newFragment.show(ft, "slideshow");

                                    } else {
                                        Toast.makeText(Gallery.this, "No Questions found", Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(Gallery.this, "" + object.opt("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setData(nameValuePairs);
        web.setReqType(true);
        if (getIntent().getStringExtra("operation").equalsIgnoreCase("alu")){
            web.execute(WebServices.ALUMNI);
        }
        else {
            web.execute(WebServices.GALLERY);
        }


    }*/

}
