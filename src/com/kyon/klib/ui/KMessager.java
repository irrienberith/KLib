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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyon.klib.effects.BaseEffects;
import com.kyon.klib.effects.Effectstype;
import com.kyon.klib.stable.KConstants;
import com.kyon.klib.utils.KResourceUtil;
import com.kyon.klib.utils.KStringUtils;
import com.kyon.klib.utils.KUIHelper;

public class KMessager {

    public static final int OK_HEIGHT = 37;
    public static final int MSG_HEIGHT = 64;
    private static AlertDialog uiMessager;
    private static TextView message;
    private static ImageView line;
    private static TextView button;
    private static LinearLayout sub;
    private static LinearLayout root;
    private static Toast uiToast;

    private static void init(Context context, String title, String msg) {

        root = new LinearLayout(context);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        root.setGravity(Gravity.CENTER);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        sub = new LinearLayout(context);

        sub.setBackgroundColor(Color.WHITE);
        sub.setOrientation(LinearLayout.VERTICAL);
        sub.setPadding(KUIHelper.dip2px(context, 5), 0,
                KUIHelper.dip2px(context, 5), 0);
        sub.setGravity(Gravity.CENTER_HORIZONTAL);

        message = new TextView(context);
        message.setTextColor(Color.BLACK);
        message.setText(KUIHelper.trimNewLine(msg));
        message.setTextSize(KConstants.TEXT_SIZE_NORMAL);
        message.setPadding(KUIHelper.dip2px(context, 30), 0,
                KUIHelper.dip2px(context, 30), 0);
        message.setGravity(Gravity.CENTER);
        message.setClickable(true);
        if (KStringUtils.isNotBlank(title)) {
            sub.setLayoutParams(new LinearLayout.LayoutParams(KUIHelper.dip2px(context, 300),
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            message.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView titleText = new TextView(context);
            titleText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            titleText.setTextColor(Color.BLACK);
            titleText.setTextSize(18);
            titleText.setPadding(0, KUIHelper.dip2px(context, 10), 0, 0);
            titleText.setText(title);
            titleText.setClickable(true);
            sub.addView(titleText);
        } else {
            sub.setLayoutParams(new LinearLayout.LayoutParams(KUIHelper.dip2px(context, 300),
                    KUIHelper.dip2px(context, 100)));
            message.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    KUIHelper.dip2px(context, MSG_HEIGHT)));
        }
        sub.addView(message);

        line = new ImageView(context);
        line.setLayoutParams(new LinearLayout.LayoutParams(KUIHelper.dip2px(context, 290), 1));
        line.setBackgroundColor(Color.DKGRAY);
        sub.addView(line);

        button = new TextView(context);
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                KUIHelper.dip2px(context, OK_HEIGHT)));
        button.setTextColor(KConstants.TEXT_COLOR_BLUE);
        button.setText("确定");
        button.setGravity(Gravity.CENTER);
        button.setTextSize(KConstants.TEXT_SIZE_NORMAL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiMessager.dismiss();
            }
        });
        setButtonBackGround(button);

        sub.addView(button);
        root.addView(sub);

        WindowManager.LayoutParams params = uiMessager.getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        uiMessager.getWindow().setAttributes(params);
    }


    private static void initToast(Context context, String msg) {
        sub = new LinearLayout(context);
        message = new TextView(context);
        message.setTextColor(Color.WHITE);
        message.setText(msg);
        message.setTextSize(KConstants.TEXT_SIZE_NORMAL);
        message.setPadding(KUIHelper.dip2px(context, 5), 0,
                KUIHelper.dip2px(context, 5), 0);
        message.setGravity(Gravity.CENTER);
        sub.setBackgroundResource(KResourceUtil.getDrawableId(context, "toast"));
        message.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sub.addView(message);
    }

    /**
     * 操作对话框
     *
     * @param context  上下文
     * @param message  提示信息
     * @param listener 点击确认后的操作
     */
    public static void operation(final Context context, String message, View.OnClickListener listener) {

        uiMessager = new AlertDialog.Builder(context).create();
        uiMessager.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                start(Effectstype.Slidetop);
            }
        });
        uiMessager.show();
        init(context, KStringUtils.EMPTY, message);
        button.setOnClickListener(listener);
        Window window = uiMessager.getWindow();
        window.setContentView(root);
        makeBlurBg(context);

    }

    /**
     * 信息对话框
     *
     * @param context 上下文
     * @param message 提示信息
     */
    public static void info(Context context, String message) {
        uiMessager = new AlertDialog.Builder(context).create();
        uiMessager.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                start(Effectstype.Fadein);
            }
        });
        uiMessager.show();
        init(context, KStringUtils.EMPTY, message);
        Window window = uiMessager.getWindow();
        window.setContentView(root);
        makeBlurBg(context);
        Log.i("KMessager",message);
    }

    /**
     * 警告对话框
     *
     * @param context 上下文
     * @param message 警告信息
     */
    public static void warning(Context context, String message) {
        uiMessager = new AlertDialog.Builder(context).create();
        uiMessager.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                start(Effectstype.RotateLeft);
            }
        });
        uiMessager.show();
        init(context, KStringUtils.EMPTY, message);
        Window window = uiMessager.getWindow();
        window.setContentView(root);
        makeBlurBg(context);

    }

    /**
     * 错误对话框
     *
     * @param context 上下文
     * @param message 错误信息
     */
    public static void error(Context context, String message) {
        uiMessager = new AlertDialog.Builder(context).create();
        uiMessager.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                start(Effectstype.Shake);
            }
        });
        uiMessager.show();
        init(context, KStringUtils.EMPTY, message);
        KMessager.message.setTextColor(KConstants.TEXT_COLOR_RED);
        Window window = uiMessager.getWindow();
        window.setContentView(root);
        Log.e("KMessager", message);
        makeBlurBg(context);

    }

    public static void error(Context context, String title, String message) {
        uiMessager = new AlertDialog.Builder(context).create();
        uiMessager.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                start(Effectstype.Shake);
            }
        });
        uiMessager.show();
        init(context, title, message);
        KMessager.message.setTextColor(KConstants.TEXT_COLOR_RED);
        Window window = uiMessager.getWindow();
        window.setContentView(root);
        makeBlurBg(context);
        Log.e("KMessager", message);
    }


    public static void toast(Context context, String msg, int time) {
        uiToast = new Toast(context);
        initToast(context, msg);
        uiToast.setView(sub);
        uiToast.show();
        CountTimer countTimer = new CountTimer(time, time);
        countTimer.start();
    }


    private static void setButtonBackGround(final TextView textButton) {
        textButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        textButton.setBackgroundColor(KConstants.TEXT_COLOR_BLUE);
                        textButton.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:
                        textButton.setTextColor(KConstants.TEXT_COLOR_BLUE);
                        textButton.setBackgroundColor(Color.WHITE);

                }
                return false;
            }
        });

    }

    static class CountTimer extends CountDownTimer {
        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (uiMessager != null) {
                uiMessager.dismiss();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

    }

    /**
     * 隐藏对话框
     */
    public static void dismiss() {
        uiMessager.dismiss();

    }

    private static void makeBlurBg(Context context){
        root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        View view = ((Activity) context).getWindow().getDecorView();
        sub.setBackground(KUIHelper.generateBlurAreaCenter(KUIHelper.getBitmapFromView(view),
                context,
                sub.getMeasuredWidth(),
                sub.getMeasuredHeight(),
                KUIHelper.dip2px(context,KConstants.CORNER_RAD),   //圆角半径
                KConstants.BLUR_RAD));    //模糊半径
    }

    private static void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        animator.setDuration(500);
        animator.start(sub);
    }

}

