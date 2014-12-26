package com.kyon.klib.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import com.kyon.klib.stable.KConstants;
import com.kyon.klib.ui.KMessager;
import com.kyon.klib.utils.KResourceUtil;
import com.kyon.klib.utils.KUIHelper;

/**
 * Created by irrienberith on 14-12-25, 上午11:54.
 * All rights reserved.
 */
public class WelcomeActivity extends Activity {
    private LinearLayout container;
    private FrameLayout root;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initPage();
    }

    private void initUI() {
        container = new LinearLayout(this);
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        container.setGravity(Gravity.BOTTOM);

        root = new FrameLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        image = new ImageView(this);
        image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setImageResource(KResourceUtil.getDrawableId(this, "sea"));
        Animation scaleAnimation = new ScaleAnimation(1.0f, 1.15f, 1.0f, 1.15f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        scaleAnimation.setDuration(3800);
        image.startAnimation(scaleAnimation);
        root.addView(image);

        TextView text = new TextView(this);
        text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                KUIHelper.dip2px(this, KConstants.MESSAGE_HEIGHT)));
        text.setTextColor(KConstants.TEXT_COLOR_LIGHT);
        text.setTextSize(KConstants.TEXT_SIZE_NORMAL);
        text.setGravity(Gravity.CENTER);
        text.setText("Picture Magic~");
        text.setClickable(false);
        container.addView(text);

        setContentView(root);
    }


    /**
     * 准备载入主界面
     */
    private void initPage() {

        /**
         * 欢迎界面停留3500毫秒后进入主界面
         */
        final StartPageTimer pageTimer = new StartPageTimer(3500, 1000);
        pageTimer.start();


        /**
         * 点击欢迎界面立即进入主界面
         */
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageTimer.cancel();
                loadPage();
            }
        });

    }

    /**
     * 进入主界面
     */
    private void loadPage() {

        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        //container.removeAllViews();
    }


    public class StartPageTimer extends CountDownTimer {
        public StartPageTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            loadPage();
        }
    }
}
