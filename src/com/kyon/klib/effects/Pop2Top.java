package com.kyon.klib.effects;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by slynero on 14-12-16.
 * Copyright (c) 2014 FineSoft. All rights reserved.
 */
public class Pop2Top extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 1,0.8f,0.6f,0.4f,0.2f,0,0,0,1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "translationY", 0,-30,-60,-85,-100,-105,0,0,0 ).setDuration(mDuration)

        );

    }
}