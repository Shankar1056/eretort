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
import com.apextechies.eretort.model.TopperListDateModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/1/2018.
 */

public class TopperListAdapter extends RecyclerView.Adapter<TopperListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TopperListDateModel> imageList;
    private OnClickListener clickPosition;

    public TopperListAdapter(Context context, ArrayList<TopperListDateModel> imageList, OnClickListener clickPosition) {
        this.context = context;
        this.imageList = imageList;
        this.clickPosition = clickPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topperlist_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText("Name: "+imageList.get(position).getName());
        holder.passyear.setText("Passing year: "+imageList.get(position).getPassing_year());
        holder.marks.setText("marks: "+imageList.get(position).getPassing_number());
        try {
            Picasso.with(context).load(imageList.get(position).getUser_image()).into(holder.userImage);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, passyear, marks;
        private ImageView userImage;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            passyear = (TextView) itemView.findViewById(R.id.passyear);
            marks = (TextView) itemView.findViewById(R.id.marks);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);

        }
    }
}

