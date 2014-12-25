package com.kyon.klib.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.kyon.klib.ui.KMessager;

/**
 * Created by irrienberith on 14-12-25, 上午11:54.
 * All rights reserved.
 */
public class KActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI(){
        LinearLayout root = new LinearLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.setBackgroundColor(Color.LTGRAY);
        root.setGravity(Gravity.CENTER);
        root.setOrientation(LinearLayout.VERTICAL);
        setContentView(root);

        Button bt0 = new Button(this);
        bt0.setLayoutParams(new ViewGroup.LayoutParams(200,80));
        bt0.setBackgroundColor(Color.DKGRAY);
        bt0.setText("INFO");
        bt0.setTextColor(Color.WHITE);
        bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KMessager.info(KActivity.this, "信息提示");
            }
        });

        root.addView(bt0);

        Button bt1 = new Button(this);
        bt1.setLayoutParams(new ViewGroup.LayoutParams(200,80));
        bt1.setBackgroundColor(Color.DKGRAY);
        bt1.setText("ERROR");
        bt1.setTextColor(Color.WHITE);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KMessager.error(KActivity.this,"错误","错误提示1\n错误提示2\n错误提示3");
            }
        });

        root.addView(bt1);

        Button bt2 = new Button(this);
        bt2.setLayoutParams(new ViewGroup.LayoutParams(200,80));
        bt2.setBackgroundColor(Color.DKGRAY);
        bt2.setText("TOAST");
        bt2.setTextColor(Color.WHITE);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KMessager.toast(KActivity.this,"Toooooooooast!",1000);
            }
        });

        root.addView(bt2);


    }
}
