/*
 * *****************************************************************************
 *    Copyright (c) 2014 irrirnberith.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *   *****************************************************************************
 */

package com.kyon.klib.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.kyon.klib.base.KResourceUtil;

import java.util.ArrayList;

/**
 * Created by irrienberith on 14-12-29.
 */
public class KImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Bitmap> imgList = new ArrayList<Bitmap>();

    public class ViewHolder {
        public FrameLayout container;
        public LinearLayout root;
        public ImageView image;
        public TextView text;
    }

    public KImageAdapter(Context context) {
        this.context = context;
    }


    public void setImgList(ArrayList<Bitmap> list) {
        imgList = list;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int i) {
        if (i >= imgList.size()) {
            return null;
        }
        return imgList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            holder.container = new FrameLayout(context);
            holder.root = new LinearLayout(context);
            holder.image = new ImageView(context);
            holder.text = new TextView(context);

            holder.container.setBackground(KResourceUtil.getDrawableById(context, "bg"));

            holder.image.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.image.setPadding(15, 15, 15, 15);
            holder.image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.root.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            holder.root.setPadding(15, 15, 15, 15);

            holder.root.setGravity(Gravity.BOTTOM);
            holder.text.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    120));
            holder.text.setBackgroundColor(Color.parseColor("#88000000"));

            holder.container.addView(holder.image);
            holder.root.addView(holder.text);
            holder.container.addView(holder.root);

            view = holder.container;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.image.setImageBitmap(imgList.get(i));

        return view;
    }


}
