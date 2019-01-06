package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.GalleryDataModel;
import com.apextechies.eretort.webservices.WebServices;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Shankar on 4/6/2018.
 */

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.MyViewHolder> {

    private List<GalleryDataModel> images;
    private Context mContext;
    private OnClickListener onClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView folder_name;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            folder_name = (TextView) view.findViewById(R.id.folder_name);
        }
    }


    public GalleryListAdapter(Context context, List<GalleryDataModel> images, OnClickListener onClickListener ) {
        mContext = context;
        this.images = images;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallerylist_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        GalleryDataModel image = images.get(position);

        if (image.getStatus().equalsIgnoreCase("1")) {
            Glide.with(mContext).load(WebServices.IMAGE_BASE_URL_ERETORT+image.getImages().get(0).getImage())
                    .thumbnail(0.5f)
                    .into(holder.thumbnail);
            holder.folder_name.setText(image.getHeading());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }



}
