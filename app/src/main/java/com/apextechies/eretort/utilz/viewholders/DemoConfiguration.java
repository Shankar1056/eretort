package com.apextechies.eretort.utilz.viewholders;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apextechies.eretort.R;

/**
 * Created by shankar on 19/2/18.
 */

public class DemoConfiguration {
    @StyleRes
    private int styleResource;

    @LayoutRes
    private int layoutResource;

    @StringRes
    private int titleResource;

    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView.ItemDecoration itemDecoration;

    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
    }

    public int getStyleResource() {
        return styleResource;
    }

    public void setStyleResource(int styleResource) {
        this.styleResource = styleResource;
    }

    public int getLayoutResource() {
        return layoutResource;
    }

    public void setLayoutResource(int layoutResource) {
        this.layoutResource = layoutResource;
    }

    public int getTitleResource() {
        return titleResource;
    }

    public void setTitleResource(int titleResource) {
        this.titleResource = titleResource;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public static DemoConfiguration getDemoConfiguration(Context context) {
        DemoConfiguration demoConfiguration;


                demoConfiguration = new DemoConfiguration();
                demoConfiguration.setStyleResource(R.style.AppThemeGrid);
                demoConfiguration.setLayoutResource(R.layout.activity_main);
                demoConfiguration.setLayoutManager(new GridLayoutManager(context, 4));
                demoConfiguration.setTitleResource(R.string.ab_grid_title);



        return demoConfiguration;
    }
}

