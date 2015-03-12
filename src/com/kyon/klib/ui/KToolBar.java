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

package com.kyon.klib.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kyon.klib.stable.KConstants;
import com.kyon.klib.base.KResourceUtil;
import com.kyon.klib.base.KUIHelper;

/**
 * Created by irrienberith on 14-12-29.
 */
public class KToolBar extends LinearLayout {
    private int padding;
    private int height;
    private FrameLayout root;
    private ImageView icon;
    private LinearLayout titleLayout;
    private TextView title;
    private LinearLayout menuLayout;
    private ImageView menu;


    public KToolBar(Context context) {
        super(context);
        setOrientation(VERTICAL);
        initUI(context);
    }

    private void initUI(Context context){
        height = KUIHelper.dip2px(context,KConstants.TOOL_HEIGHT);
        root = new FrameLayout(context);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height ));

        titleLayout = new LinearLayout(context);
        titleLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        titleLayout.setBackgroundColor(KConstants.THEME_COLOR_PINK);
        titleLayout.setClickable(true);
        titleLayout.setGravity(Gravity.CENTER);

        title = new TextView(context);
        title.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        title.setSingleLine();
        title.setEllipsize(TextUtils.TruncateAt.END);

        titleLayout.addView(title);
        root.addView(titleLayout);

        padding = KUIHelper.dip2px(context,KConstants.PADDING_NORMAL);

        icon = new ImageView(context);
        icon.setLayoutParams(new ViewGroup.LayoutParams(height,height));
        icon.setPadding(padding,padding,padding,padding);
        icon.setImageResource(KResourceUtil.getDrawableId(context,"ic_launcher"));

        root.addView(icon);

        ImageView shadow = new ImageView(context);
        shadow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                KUIHelper.dip2px(context,KConstants.LINE_HEIGHT)));
        shadow.setImageResource(KResourceUtil.getDrawableId(context,"line_shadow"));
        addView(root);
        addView(shadow);
    }

    public void setOnMenuClickListener(OnClickListener listener){
        menuLayout = new LinearLayout(getContext());
        menuLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        menuLayout.setGravity(Gravity.RIGHT);

        menu = new ImageView(getContext());
        menu.setLayoutParams(new ViewGroup.LayoutParams(height, height));
        menu.setPadding(padding, padding, padding, padding);
        menu.setImageResource(KResourceUtil.getDrawableId(getContext(), "ic_menu"));
        menu.setScaleType(ImageView.ScaleType.FIT_CENTER);

        menuLayout.addView(menu);
        root.addView(menuLayout);

        menu.setOnClickListener(listener);

    }

    public void setOnIconClickListener(OnClickListener listener){
        icon.setOnClickListener(listener);
    }

    public void setColor(int color){
        titleLayout.setBackgroundColor(color);
    }

    public void setIconImage(int resId){
        icon.setImageResource(resId);
    }

    public void setIconClickable(boolean clickable){
        icon.setClickable(clickable);
    }

    public void setTitle(String text){
        title.setText(text);
    }
}
