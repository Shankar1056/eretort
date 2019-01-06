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
import com.apextechies.eretort.adapter.NewsAdaptert;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.NewsModel;
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
public class NewsFragment extends Fragment {
    private RecyclerView news_recycler;
    private ArrayList<NewsModel> newsList = new ArrayList<>();
    public NewsFragment(String s) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment,container,false);
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
                                String news_id = jo.optString("id");
                                String newsTitle = jo.optString("newsTitle");
                                String newsShortDesc = jo.optString("newsShortDesc");
                                String newsLonDesc = jo.optString("newsLonDesc");
                                String newsImage = jo.optString("newsImage");
                                String news_date = jo.optString("news_date");
                                String schoolName = jo.optString("schoolName");

                                newsList.add(new NewsModel(news_id,newsTitle,newsShortDesc,newsLonDesc,newsImage,news_date,schoolName));
                            }
                            NewsAdaptert newsAdaptert = new NewsAdaptert(getActivity(), newsList,  new OnClickListener() {
                                @Override
                                public void onclick(int position) {

                                }
                            });
                            news_recycler.setAdapter(newsAdaptert);
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
        web.execute(WebServices.NEWS);
    }

    private void initWidgit(View view) {
        news_recycler = (RecyclerView)view.findViewById(R.id.news_recycler);
        news_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        news_recycler.setHasFixedSize(true);
    }
}

