package com.blood.nativedemo.copy;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/12/7 16:03
 * @Description:
 */
public class CopyUtil {

    /**
     * 从assets目录中复制整个文件夹内容,考贝到 /data/data/包名/files/目录中
     *
     * @param activity activity 使用CopyFiles类的Activity
     * @param filePath String  文件路径,如：/assets/aa
     */
    public static void copyAssetsDir2Phone(Activity activity, String filePath) {
        try {
            String[] fileList = activity.getAssets().list(filePath);
            if (fileList.length > 0) {//如果是目录
                File file = new File(activity.getFilesDir()
                        .getAbsolutePath() + File.separator + filePath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileList) {
                    filePath = filePath + File.separator + fileName;

                    copyAssetsDir2Phone(activity, filePath);

                    filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
                    Log.e("oldPath", filePath);
                }
            } else {//如果是文件
                InputStream inputStream = activity.getAssets().open(filePath);
                File file = new File(activity.getFilesDir()
                        .getAbsolutePath() + File.separator + filePath);
                Log.i("copyAssets2Phone", "file:" + file);
                if (!file.exists() || file.length() == 0) {
                    FileOutputStream fos = new FileOutputStream(file);
                    int len = -1;
                    byte[] buffer = new byte[1024];
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    inputStream.close();
                    fos.close();
                    ToastUtils.showShort("模型文件复制完毕");
                } else {
                    ToastUtils.showShort("模型文件已存在，无需复制");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件从assets目录，考贝到 /data/data/包名/files/ 目录中。assets 目录中的文件，会不经压缩打包至APK包中，使用时还应从apk包中导出来
     *
     * @param fileName 文件名,如aaa.txt
     */
    public static void copyAssetsFile2Phone(Activity activity, String fileName) {
        try {
            InputStream inputStream = activity.getAssets().open(fileName);
            //getFilesDir() 获得当前APP的安装路径 /data/data/包名/files 目录
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName);
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fos = new FileOutputStream(file);//如果文件不存在，FileOutputStream会自动创建文件
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();//刷新缓存区
                inputStream.close();
                fos.close();
                ToastUtils.showShort("模型文件复制完毕");
            } else {
                ToastUtils.showShort("模型文件已存在，无需复制");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
