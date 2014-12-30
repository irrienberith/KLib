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
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kyon.klib.stable.KConstants;
import com.kyon.klib.ui.KToolBar;
import com.kyon.klib.utils.KColorPickerDialog;
import com.kyon.klib.utils.KUIHelper;

/**
 * Created by irrienberith on 14-12-30.
 */
public class ColorActivity extends Activity {

    private int width;
    private int height;
    private int color;

    private KToolBar toolBar;

    private TextView pink;
    private TextView green;
    private TextView dark;
    private TextView holo;

    private TextView custom;

    private TextView confirm;

    private KColorPickerDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        color = intent.getIntExtra("color", KConstants.THEME_COLOR_DARK);
        initSize();
        initUI();
    }

    private void initSize() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = (dm.widthPixels - KUIHelper.dip2px(this, KConstants.TOOL_HEIGHT)) / 3;
        height = (dm.heightPixels - KUIHelper.dip2px(this, KConstants.TOOL_HEIGHT)) / 4;
    }

    private void initUI() {
        LinearLayout root = new LinearLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.setOrientation(LinearLayout.VERTICAL);

        toolBar = new KToolBar(this);
        toolBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        toolBar.setIconImage(0);
        toolBar.setTitle("主题颜色选择");
        toolBar.setColor(color);

        root.addView(toolBar);

        LinearLayout sub = new LinearLayout(this);
        sub.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        sub.setGravity(Gravity.CENTER);
        sub.setOrientation(LinearLayout.VERTICAL);

        LinearLayout sub1 = new LinearLayout(this);
        sub1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height));
        sub1.setGravity(Gravity.CENTER);

        LinearLayout sub2 = new LinearLayout(this);
        sub2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height));
        sub2.setGravity(Gravity.CENTER);

        LinearLayout sub3 = new LinearLayout(this);
        sub3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height));
        sub3.setGravity(Gravity.CENTER);

        LinearLayout sub4 = new LinearLayout(this);
        sub4.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height));
        sub4.setGravity(Gravity.CENTER);

        pink = new TextView(this);
        pink.setLayoutParams(new ViewGroup.LayoutParams(width, height / 3));
        pink.setText("Pink");
        pink.setTextColor(Color.WHITE);
        pink.setBackgroundColor(KConstants.THEME_COLOR_PINK);
        pink.setGravity(Gravity.CENTER);
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = KConstants.THEME_COLOR_PINK;
                confirm.setBackgroundColor(color);
                toolBar.setColor(color);

            }
        });

        green = new TextView(this);
        green.setLayoutParams(new ViewGroup.LayoutParams(width, height / 3));
        green.setText("Green");
        green.setTextColor(Color.WHITE);
        green.setBackgroundColor(KConstants.THEME_COLOR_GREEN);
        green.setGravity(Gravity.CENTER);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = KConstants.THEME_COLOR_GREEN;
                confirm.setBackgroundColor(color);
                toolBar.setColor(color);

            }
        });

        holo = new TextView(this);
        holo.setLayoutParams(new ViewGroup.LayoutParams(width, height / 3));
        holo.setText("Holo");
        holo.setTextColor(Color.WHITE);
        holo.setBackgroundColor(KConstants.THEME_COLOR_HOLO);
        holo.setGravity(Gravity.CENTER);
        holo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = KConstants.THEME_COLOR_HOLO;
                confirm.setBackgroundColor(color);
                toolBar.setColor(color);

            }
        });

        dark = new TextView(this);
        dark.setLayoutParams(new ViewGroup.LayoutParams(width, height / 3));
        dark.setText("Dark");
        dark.setTextColor(Color.WHITE);
        dark.setBackgroundColor(KConstants.THEME_COLOR_DARK);
        dark.setGravity(Gravity.CENTER);
        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = KConstants.THEME_COLOR_DARK;
                confirm.setBackgroundColor(color);
                toolBar.setColor(color);

            }
        });

        custom = new TextView(this);
        custom.setLayoutParams(new ViewGroup.LayoutParams(width * 3 / 2, height / 3));
        custom.setText("Custom");
        custom.setTextColor(Color.WHITE);
        custom.setBackgroundColor(Color.BLACK);
        custom.setGravity(Gravity.CENTER);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new KColorPickerDialog(ColorActivity.this, color, "自定义颜色选择", new KColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        ColorActivity.this.color = color;
                        confirm.setBackgroundColor(color);
                        toolBar.setColor(color);
                    }
                });
                dialog.show();
            }
        });

        confirm = new TextView(this);
        confirm.setLayoutParams(new ViewGroup.LayoutParams(width * 3 / 2, height / 3));
        confirm.setText("确认");
        confirm.setTextColor(Color.WHITE);
        confirm.setBackgroundColor(color);
        confirm.setGravity(Gravity.CENTER);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackIntent();
            }
        });


        sub1.addView(pink);
        sub1.addView(green);

        sub.addView(sub1);

        sub2.addView(holo);
        sub2.addView(dark);

        sub.addView(sub2);

        sub3.addView(custom);
        sub.addView(sub3);

        sub4.addView(confirm);
        sub.addView(sub4);

        root.addView(sub);
        setContentView(root);

    }

    private void setBackIntent() {
        Intent intent = new Intent();
        intent.putExtra("color", color);
        setResult(RESULT_OK, intent);
        finish();

    }

}
