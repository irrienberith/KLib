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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.kyon.klib.R;
import com.kyon.klib.graphic.k2d.KDropView;
import com.kyon.klib.stable.KConstants;
import com.kyon.klib.ui.FloatingActionButton;
import com.kyon.klib.ui.KMessager;
import com.kyon.klib.ui.KToolBar;
import com.kyon.klib.base.KFileUtils;
import com.kyon.klib.base.KStringUtils;
import com.kyon.klib.base.KUIHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by irrienberith on 14-12-26.
 */
public class MainActivity extends Activity {
    private static final int RESULT_LOAD_IMAGE = 88;
    private static final int RESULT_GET_COLOR = 99;

    private int themeColor;
    private KToolBar toolBar;
    private LinearLayout root;
    private LinearLayout listLayout;
    private ListView imageViewList;
    private KImageAdapter imageAdapter;
    private FloatingActionButton fab;
    private ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();
    private ArrayList<String> pathList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initData();
//        initUI();
//        initFloat();
//        initList();
        initDropView();

    }

    private void initDropView(){
        KDropView dropView = new KDropView(this);
        dropView.setBackgroundColor(Color.WHITE);
        dropView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(dropView);

    }


    private void initData() {
        String data = "";
        try {
            data = KFileUtils.readFile(this, "config.tt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (KStringUtils.isNotEmpty(data)) {
            phraseFile(data);
        }

        if (themeColor == 0) {
            themeColor = KConstants.THEME_COLOR_DARK;
        }
    }

    private void initUI() {
        root = new LinearLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.WHITE);

        toolBar = new KToolBar(this);
        toolBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        toolBar.setColor(themeColor);
        toolBar.setTitle("PicMagik");
        toolBar.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onMenuOpened(0,null);
            }
        });
        toolBar.setOnIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("color", themeColor);
                intent.setClass(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        root.addView(toolBar);

        listLayout = new LinearLayout(this);
        listLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        listLayout.setOrientation(LinearLayout.VERTICAL);
        listLayout.setBackgroundColor(Color.LTGRAY);
        listLayout.setPadding(15, 0, 15, 0);

        root.addView(listLayout);

        setContentView(root);


    }

    private void initFloat() {
        fab = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_action_new))
                .withButtonColor(themeColor)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, KUIHelper.dip2px(this, KConstants.PADDING_NORMAL) / 2, KUIHelper.dip2px(this, KConstants.PADDING_NORMAL) / 2)
                .create();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    private void initList() {

        imageViewList = new ListView(this);
        imageViewList.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Drawable drawable = new ColorDrawable(Color.LTGRAY);
        imageViewList.setDivider(drawable);
        imageViewList.setDividerHeight(15);

        imageAdapter = new KImageAdapter(this);

        imageViewList.setAdapter(imageAdapter);
        imageAdapter.setImgList(imageList);
        listLayout.addView(imageViewList);

        imageViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent();
                intent.putExtra("path", pathList.get(i));
                intent.setClass(MainActivity.this, ImageViewActivity.class);
                startActivity(intent);
            }
        });

        imageViewList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                KMessager.operation(MainActivity.this, "从应用中删除这张照片吗？", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pathList.remove(i);
                        imageList.remove(i);
                        imageAdapter.notifyDataSetChanged();
                        write2File();
                        KMessager.dismiss();

                    }
                });

                return false;
            }
        });
    }

    private void addBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            return;
        }
        float scale = 1.0f;
        if (bitmap.getWidth() >= 660 || bitmap.getHeight() >= 900) {
            scale = Math.min((660.0f / (float) bitmap.getWidth()), (900.0f / (float) bitmap.getHeight()));
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap thumb = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        imageList.add(thumb);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            if (!pathList.contains(picturePath)) {
                pathList.add(picturePath);
                addBitmap(picturePath);
                imageAdapter.setImgList(imageList);
                imageAdapter.notifyDataSetChanged();
                write2File();
            }

            return;

        }

        if (requestCode == RESULT_GET_COLOR && resultCode == RESULT_OK && null != data) {
            themeColor = data.getIntExtra("color", KConstants.THEME_COLOR_DARK);
            initFloat();
            toolBar.setColor(themeColor);
            write2File();
        }

    }

    private void write2File() {
        String stream = "#" + themeColor + "#,DE,";
        for (String str : pathList) {
            stream += str + ",DE,";
        }
        try {
            KFileUtils.writeFile(this, "config.tt", stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void phraseFile(String data) {
        int index0 = data.indexOf("#");
        int index1 = data.indexOf("#", index0 + 1);
        String color = data.substring(index0 + 1, index1);
        themeColor = Integer.valueOf(color);

        data = data.substring(index1 + 1);
        String[] list = data.split(",DE,");
        if (list.length > 0) {
            for (String str : list) {
                if (!KStringUtils.contains(",DE,", str) && KStringUtils.isNotEmpty(str)) {
                    pathList.add(str);
                    addBitmap(str);
                }
            }
        }


    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Intent intent = new Intent();
        intent.putExtra("color", themeColor);
        intent.setClass(MainActivity.this, ColorActivity.class);
        startActivityForResult(intent, RESULT_GET_COLOR);
        return true;
    }

    @Override
    public void onBackPressed() {
        KMessager.operation(this, "确定要退出吗？", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KMessager.dismiss();
                MainActivity.this.finish();
            }
        });
    }
}
