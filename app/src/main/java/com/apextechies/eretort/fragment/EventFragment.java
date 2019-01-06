package com.apextechies.eretort.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.EventsAdapter;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.EventsModel;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.webservices.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 12/17/2017.
 */

@SuppressLint("ValidFragment")
public class EventFragment extends Fragment {
    private RecyclerView event_recycler;
    ArrayList<EventsModel> eventsList = new ArrayList<>();
    public EventFragment(String recharge) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.events_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgit(view);
        callApi();
    }

    private void callApi() {
        Download_web web = new Download_web(getActivity(), new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                if (response.length()>0)
                {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true"))
                        {
                            JSONArray array = object.getJSONArray("data");
                            for (int i=0 ;i<array.length(); i++)
                            {
                                JSONObject jo = array.getJSONObject(i);
                                String events_id = jo.optString("id");
                                String eventsTitle = jo.optString("eventsTitle");
                                String eventsShortDesc = jo.optString("eventsShortDesc");
                                String eventsLonDesc = jo.optString("eventsLonDesc");
                                String newsImage = jo.optString("newsImage");
                                String events_date = jo.optString("events_date");
                                String schoolName = jo.optString("schoolName");

                                eventsList.add(new EventsModel(events_id,eventsTitle,eventsShortDesc,eventsLonDesc,newsImage,events_date,schoolName));
                            }
                            EventsAdapter eventsAdapter = new EventsAdapter(getActivity(), eventsList,  new OnClickListener() {
                                @Override
                                public void onclick(int position) {

                                }
                            });
                            event_recycler.setAdapter(eventsAdapter);
                        }
                        else
                        {
                            Toast.makeText(getActivity(), ""+object.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        web.setReqType(true);
        web.execute(WebServices.EVENTS);
    }

    private void initWidgit(View view) {
        event_recycler = (RecyclerView)view.findViewById(R.id.event_recycler);
        event_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        event_recycler.setHasFixedSize(true);
    }
}
