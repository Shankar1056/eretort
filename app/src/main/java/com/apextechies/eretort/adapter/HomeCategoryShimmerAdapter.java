package com.apextechies.eretort.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.apextechies.eretort.allinterface.OnClickListener;
import com.apextechies.eretort.model.HomeCategoryModel;
import com.apextechies.eretort.utilz.viewholders.ItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shankar on 19/2/18.
 */

public class HomeCategoryShimmerAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<HomeCategoryModel> mCards = new ArrayList<>();
    private int mType = 3;
    private OnClickListener onClickListener ;


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemHolder.newInstance(parent, mType);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        holder.bind(mCards.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onClickListener.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public void setCards(List<HomeCategoryModel> cards, OnClickListener onClickListener) {
        if (cards == null) {
            return;
        }

        mCards = cards;
        this.onClickListener = onClickListener;
    }


}
