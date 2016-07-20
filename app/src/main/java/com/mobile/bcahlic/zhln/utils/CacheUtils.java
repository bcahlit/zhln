package com.mobile.bcahlic.zhln.utils;

import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.mobile.bcahlic.zhln.utils.Md5utils.getMd5;

/**
 * Created by bcahlic on 16-7-20.
 */
public class CacheUtils {
    public static void setcache(String key,String value) throws IOException {
        String md5 = getMd5(key);
        File file = getPath(md5);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(value.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String readcache(String key) {
        String md5 = getMd5(key);
        File path = getPath(md5);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);



        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] array = new byte[1024];
        int len = -1;
        while( (len = fileInputStream.read(array)) != -1){
            bos.write(array,0,len);
        }
        bos.close();
        fileInputStream.close();

            Log.d("CacheUtils","Read文件中"+bos.toString());
        return bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static File getPath(String path) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File root = Environment.getExternalStorageDirectory();
            String filePath = root.toString() + "/zhly/" + path;
            String mkdir = root.toString() + "/zhly";
            File fDir = new File(mkdir);
            if (!fDir.exists()){fDir.mkdirs();}
            File fileDir = new File(filePath);
            if (!fileDir.exists()){
                try {
                    fileDir.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return fileDir;
        }
        return null;
    }
}
