/**
 * Copyright 2017 Harish Sridharan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apextechies.eretort.utilz.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.model.HomeCategoryModel;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;


public class ItemHolder extends RecyclerView.ViewHolder {

    CircularImageView catImage;
    TextView catText;

    public static ItemHolder newInstance(ViewGroup container, int type) {
        View root = LayoutInflater.from(container.getContext()).inflate(getLayoutResourceId(type),
                container, false);

        return new ItemHolder(root);
    }

    private ItemHolder(View itemView) {
        super(itemView);
        catImage = (CircularImageView) itemView.findViewById(R.id.CatImageView);
        catText = (TextView) itemView.findViewById(R.id.CatTextView);


    }

    public void bind(HomeCategoryModel card) {


            catText.setText(card.getCatName());

            if (card.getCatIcon().length()>0) {
                Glide.with(itemView.getContext()).load(card.getCatIcon()).into(catImage);
            }
            else {
              //  Glide.with(itemView.getContext()).load("https://s3.ap-south-1.amazonaws.com/pujanpujari/development/PoojanPoojariIcons.png").into(catImage);

            }

    }

    private static int getLayoutResourceId(int type) {
        int selectedLayoutResource;
        switch (type) {

            case 3:
                selectedLayoutResource = R.layout.homecategory_item;
                break;
            default:
                selectedLayoutResource = 0;
        }

        return selectedLayoutResource;
    }
}
