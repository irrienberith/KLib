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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kyon.klib.stable.KConstants;
import com.kyon.klib.ui.KToolBar;
import com.kyon.klib.utils.KResourceUtil;

/**
 * Created by irrienberith on 14-12-30.
 */
public class AboutActivity extends Activity {

    private int color;
    private KToolBar  toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPara();
        initUI();

    }

    private void initPara(){
        Intent intent = getIntent();
        color = intent.getIntExtra("color", KConstants.THEME_COLOR_DARK);
    }

    private void initUI(){
        LinearLayout root = new LinearLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.setOrientation(LinearLayout.VERTICAL);

        toolBar = new KToolBar(this);
        toolBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        toolBar.setColor(color);
        toolBar.setIconImage(0);
        toolBar.setTitle("关于PicMagik");

        LinearLayout sub = new LinearLayout(this);
        sub.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        sub.setOrientation(LinearLayout.VERTICAL);
        sub.setGravity(Gravity.CENTER);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView icon = new ImageView(this);
        icon.setLayoutParams(new ViewGroup.LayoutParams(200,200));
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        icon.setImageResource(KResourceUtil.getDrawableId(this,"ic_launcher"));

        TextView text = new TextView(this);
        text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        text.setTextColor(KConstants.TEXT_COLOR_DARK);
        text.setGravity(Gravity.CENTER);
        text.setText("PicMagik 1.0\nCopyright (c) 2014 Kyon. All rights reserved.");

        root.addView(toolBar);
        sub.addView(icon);
        sub.addView(text);
        root.addView(sub);
        setContentView(root);

    }
}
