package com.apextechies.eretort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.SubCatListModel;

import java.util.ArrayList;


/**
 * Created by Shankar on 12/28/2017.
 */

public class CateoryListAdapter extends RecyclerView.Adapter<CateoryListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SubCatListModel> imageList;
    private OnClickListener clickPosition;

    public CateoryListAdapter(Context context, ArrayList<SubCatListModel> imageList, OnClickListener clickPosition) {
        this.context = context;
        this.imageList = imageList;
        this.clickPosition = clickPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.cat_text.setText(imageList.get(position).getSubCatNme());

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

        private TextView cat_text;

        public ViewHolder(View itemView) {
            super(itemView);
            cat_text = (TextView) itemView.findViewById(R.id.cat_text);

        }
    }
}
