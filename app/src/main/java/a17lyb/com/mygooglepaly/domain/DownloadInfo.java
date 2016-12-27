package a17lyb.com.mygooglepaly.domain;

import android.os.Environment;

import java.io.File;

import a17lyb.com.mygooglepaly.manager.DownloadManager;

/**
 * Created by 10400 on 2016/12/12.
 */

public class DownloadInfo {
    public String id;
    public String name;
    public String downLoadUrl;
    public long size;
    public String packageName;
    public String path;
    public int currentState;
    public float currentPos;//当前下载位置
    public static final String GOOGLE_MARKET = "GOOGLE_MARKET";// sdcard根目录文件夹名称
    public static final String DONWLOAD = "download";// 子文件夹名称, 存放下载的文件

    public float getProgress() {
        if(size==0){
            return 0;
        }
        float progress = currentPos / (float) size;
        return progress;
    }

    public String getDownFile() {
        StringBuffer sb = new StringBuffer();
        File file = Environment.getExternalStorageDirectory();
        sb.append(file);
        sb.append(File.separator);
        sb.append(GOOGLE_MARKET);
        sb.append(File.separator);
        sb.append(DONWLOAD);
        if (createFile(sb.toString())) {
            return sb.toString() + File.separator + name + ".apk";
        }
        return null;
    }

    public boolean createFile(String fileUrl) {
        File file = new File(fileUrl);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    public static  DownloadInfo copy(AppInfo appinfo){
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.name=appinfo.name;
        downloadInfo.id=appinfo.id;
        downloadInfo.downLoadUrl=appinfo.downloadUrl;
        downloadInfo.size=appinfo.size;
        downloadInfo.packageName=appinfo.packageName;

        downloadInfo.currentPos=0;
        downloadInfo.currentState= DownloadManager.STATE_UNDO;
        downloadInfo.path=downloadInfo.getDownFile();
        return downloadInfo;
    }
}
