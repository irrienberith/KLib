package com.kyon.klib.effects;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by slynero on 14-12-16.
 * Copyright (c) 2014 FineSoft. All rights reserved.
 */
public class FadeOut extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(mDuration)

        );
    }
}
