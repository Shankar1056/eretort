package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.HomeCategoryModel;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shankar on 11/7/2017.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.MyViewHolder> {

    private List<HomeCategoryModel> myCartModels;
    private Context context;
    private int mycart_item;
    private OnClickListener onClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView CatTextView;
        public LinearLayout layout;
        private CircularImageView circularImageView;

        public MyViewHolder(View view) {
            super(view);
            circularImageView = (CircularImageView)view.findViewById(R.id.CatImageView);
            CatTextView = (TextView) view.findViewById(R.id.CatTextView);
//            layout = (LinearLayout) view.findViewById(R.id.layout);
        }
    }


    public HomeCategoryAdapter(Context context, List<HomeCategoryModel> myCartModels, OnClickListener onClickListener) {
        this.context = context;
        this.myCartModels = myCartModels;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homecategory_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final HomeCategoryModel sa = myCartModels.get(position);
        // holder.CatTextView.setTypeface(Utilz.font(context, "bold"));

        holder.CatTextView.setText(sa.getCatName());
        Picasso.with(context).load(sa.getCatIcon()).into(holder.circularImageView);
       // holder.layout.setBackgroundColor(Color.parseColor(sa.getCatColor()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onclick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCartModels.size();
    }

}

