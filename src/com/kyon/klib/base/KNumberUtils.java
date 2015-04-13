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

/**
 * Created by irrienberith on 15-3-12.
 */
public class KNumberUtils {


    public static float[] makeNicePoint(float pX, float pY, int maxDis) {
        float randX = (float) (Math.random() * maxDis);
        float randY = (float) (Math.random() * maxDis);
        return new float[]{pX + randX, pY + randY};
    }


//    public static float[] makeNiceRing(int maxDis){
//        float randA
//
//
//    }
}
