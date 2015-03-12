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

package com.kyon.klib.base;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by irrienberith on 14-7-18.
 */
public class KResourceUtil {
    public static int getLayoutId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "layout",
                paramContext.getPackageName());
    }

    public static Drawable getDrawableById(Context paramContext, String paramString) {
        return paramContext.getResources().getDrawable(getDrawableId(paramContext, paramString));
    }
    public static Drawable getDrawableByColorId(Context paramContext, String paramString) {
        return paramContext.getResources().getDrawable(getColorId(paramContext, paramString));
    }

    public static int getStringId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "string",
                paramContext.getPackageName());
    }

    public static int getDrawableId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "drawable", paramContext.getPackageName());
    }

    public static int getStyleId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "style", paramContext.getPackageName());
    }

    public static int getId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "id", paramContext.getPackageName());
    }

    public static int getColorId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "color", paramContext.getPackageName());
    }

    public static int getAnimId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "anim", paramContext.getPackageName());
    }

    public static int getRowId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "row", paramContext.getPackageName());
    }

    public static int getValuesId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "values", paramContext.getPackageName());
    }
}
