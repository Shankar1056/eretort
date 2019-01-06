package com.apextechies.eretort.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shankar on 2/10/2018.
 */

public class ContactUs extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener{
    private GoogleMap mMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initWidgit();
        callScoolDetailsApi();
    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.contact_us));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callScoolDetailsApi() {
        Utilz.showDailog(ContactUs.this, getResources().getString(R.string.please_wait));
        Download_web web = new Download_web(ContactUs.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utilz.closeDialog();
                if (response != null && response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.optString("status").equalsIgnoreCase("true")) {
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jo = array.getJSONObject(i);
                                setData(jo);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setReqType(true);
        web.execute(WebServices.SCHOOLDETAILS);
    }

    private void setData(JSONObject jo) {

        TextView tv_schoolname = (TextView)findViewById(R.id.tv_schoolname);
        TextView tv_address = (TextView)findViewById(R.id.tv_address);
        TextView tv_email = (TextView)findViewById(R.id.tv_email);
        TextView mobile = (TextView)findViewById(R.id.mobile);

        tv_email.setOnClickListener(this);
        mobile.setOnClickListener(this);

        tv_schoolname.setText("School Name:"+jo.optString("schoolName"));
        tv_address.setText("School Address:"+jo.optString("schoolAddress"));
        tv_email.setText("Principal Email:"+jo.optString("schoolPrincipalEmail"));
        mobile.setText("Principal Mobile:"+jo.optString("schoolPrincipalPhone"));
        loasMap(mMap,Double.parseDouble(jo.optString("latitute")),Double.parseDouble(jo.optString("longitute")));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        loasMap(mMap,23.7867885,86.4185349);
    }

    private void loasMap(GoogleMap mMap, double lat, double lon) {
        LatLng latLng = new LatLng(lat, lon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("eWebbazar Prv. Ltd");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mobile:
                try {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    // intent.setPackage("com.android.server.telecom");
                    intent.setData(Uri.parse("tel:8002877277"));
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(ContactUs.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(AboutUcc.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","dayashankargupta86@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;

            default:
                break;


        }

    }
}
