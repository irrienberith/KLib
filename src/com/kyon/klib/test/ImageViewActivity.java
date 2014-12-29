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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by irrienberith on 14-12-30.
 */
public class ImageViewActivity extends Activity {
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRes();
        initUI();
    }

    private void initRes(){
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            return;
        }
        float scale = 1.0f;
        if (bitmap.getWidth() >= 1800 || bitmap.getHeight() >= 2400) {
            scale = Math.min((1800.0f / (float) bitmap.getWidth()), (2400.0f / (float) bitmap.getHeight()));
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void initUI(){
        LinearLayout root = new LinearLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.setBackgroundColor(Color.WHITE);
        root.setGravity(Gravity.CENTER);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
        horizontalScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView pic = new ImageView(this);
        pic.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pic.setScaleType(ImageView.ScaleType.FIT_CENTER);
        pic.setImageBitmap(bmp);

        scrollView.addView(pic);
        horizontalScrollView.addView(scrollView);
        root.addView(horizontalScrollView);
        setContentView(root);




    }
}
