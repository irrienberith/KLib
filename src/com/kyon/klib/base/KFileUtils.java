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
import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by irrienberith on 14-12-30.
 */
public class KFileUtils {
    //写数据
    public static void writeFile(Context context, String fileName, String writeStr) throws IOException {
        try {
            FileOutputStream fOut = context.openFileOutput(fileName, 0);
            byte[] bytes = writeStr.getBytes();
            fOut.write(bytes);
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读数据
    public static String readFile(Context context, String fileName) throws IOException {
        String res = "";
        try {
            FileInputStream fIn = context.openFileInput(fileName);
            int length = fIn.available();
            byte[] buffer = new byte[length];
            fIn.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }
}
