package a17lyb.com.mygooglepaly.manager;

import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.domain.DownloadInfo;
import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.utils.IOUtils;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/12.
 */

public class DownloadManager {


    //饿汉模式
    private static DownloadManager mDm = new DownloadManager();
    private ArrayList<DownloadObserver> mObservers = new ArrayList<>();
    public static final int STATE_UNDO = 1;
    public static final int STATE_WAITING = 2;
    public static final int STATE_DOWNLOADING = 3;
    public static final int STATE_PAUSE = 4;
    public static final int STATE_ERROR = 5;
    public static final int STATE_SUCCESS = 6;

    private ConcurrentHashMap<String, DownloadInfo> mDownloadInfoMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, DownloadTask> mDownloadTaskMap = new ConcurrentHashMap<>();

    public DownloadManager() {
    }

    public static DownloadManager getInstance() {
        return mDm;
    }

    public synchronized void registerObserver(DownloadObserver observer) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public synchronized void unregisterObserver(DownloadObserver observer) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    public DownloadInfo getDownLoadInfo(AppInfo info) {

        return mDownloadInfoMap.get(info.id);
    }

    public synchronized void notifyDownloadStateChanged(DownloadInfo downInfo) {
//        System.out.println("notifyDownloadStateChanged = [" + downInfo + "]");
        for (DownloadObserver observer : mObservers) {
            observer.onDownloadStateChanged(downInfo);
        }
    }

    public synchronized void notifyDownloadProgressChanged(DownloadInfo downInfo) {
        for (DownloadObserver observer : mObservers) {
            observer.onDownloadProgressChanged(downInfo);
        }
    }

    public interface DownloadObserver {
        public void onDownloadStateChanged(DownloadInfo downInfo);

        public void onDownloadProgressChanged(DownloadInfo downInfo);
    }

    public synchronized void download(AppInfo appinfo) {
        DownloadInfo downLoadInfo = mDownloadInfoMap.get(appinfo.id);
        if (downLoadInfo == null) {
            downLoadInfo = DownloadInfo.copy(appinfo);
        }
        downLoadInfo.currentState = STATE_WAITING;
        notifyDownloadStateChanged(downLoadInfo);
        mDownloadInfoMap.put(downLoadInfo.id, downLoadInfo);

        DownloadTask downloadTask = new DownloadTask(downLoadInfo);
        ThreadManager.getThreadPoll().execute(downloadTask);
        mDownloadTaskMap.put(downLoadInfo.id, downloadTask);
    }

    class DownloadTask implements Runnable {
        private DownloadInfo downloadInfo;
        private HttpHelper.HttpResult httpResult;
        private FileOutputStream out;

        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {

            downloadInfo.currentState = STATE_DOWNLOADING;
//            System.out.println(downloadInfo.name + "开始下载啦  状态为"+downloadInfo.currentState);
            UIUtils.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    notifyDownloadStateChanged(downloadInfo);

                }
            });

            File file = new File(downloadInfo.path);
            if (!file.exists() || file.length() != downloadInfo.currentPos ||
                    downloadInfo.currentPos == 0) {
                //从头开始下载
                //删除无效文件
                file.delete();//文件不存在也可以删除
                downloadInfo.currentPos = 0;//当前下载位置为0

                httpResult = HttpHelper.download(HttpHelper.URL + "download?name=" +
                        downloadInfo.downLoadUrl);
            } else {
                //断点续传
                //range 表示请求服务器从哪个位置开始返回数据
                httpResult = HttpHelper.download(HttpHelper.URL + "download?name=" +
                        downloadInfo.downLoadUrl + "&range=" + file.length());

            }
//            System.out.println("httpResult.getInputStream()" + HttpHelper.URL + "download?name=" +
//                    downloadInfo.downLoadUrl);
            if (httpResult != null && httpResult.getInputStream() != null) {

                InputStream in = httpResult.getInputStream();
                try {
                    //在原有文件上追加
                    out = new FileOutputStream(file, true);
                    int len = 0;
                    byte[] buffer = new byte[3024];
                    //只有状态是正在下载，才继续轮循，解决下载过程中途暂停BUG
                    while ((len = in.read(buffer)) != -1 && downloadInfo.currentState == STATE_DOWNLOADING) {
                        out.write(buffer, 0, len);
                        out.flush();
                        //更新下载进度
                        downloadInfo.currentPos += len;
                        UIUtils.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                notifyDownloadStateChanged(downloadInfo);

                            }
                        });
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(in);
                    IOUtils.close(out);

                }
                //文件下载结束
                if (file.length() == downloadInfo.size) {
                    //文件完整，表示下载成功
                    System.out.println(downloadInfo.name + "文件完整，表示下载成功");
                    downloadInfo.currentState=STATE_SUCCESS;
                    notifyDownloadStateChanged(downloadInfo);
                } else if (downloadInfo.currentState == STATE_PAUSE) {
                    //中途暂停
                    System.out.println(downloadInfo.name + "中途暂停");
                    notifyDownloadStateChanged(downloadInfo);
                } else {
                    //下载失败
                    file.delete();//删除无效文件
                    System.out.println(downloadInfo.name + "下载失败");
                    downloadInfo.currentState = STATE_ERROR;
                    downloadInfo.currentPos = 0;
                    notifyDownloadStateChanged(downloadInfo);

                }

            } else {
                //网络异常
                file.delete();//删除无效文件
                downloadInfo.currentState = STATE_ERROR;
                downloadInfo.currentPos = 0;
                notifyDownloadStateChanged(downloadInfo);
            }

            //从集合中移除下载任务
            mDownloadTaskMap.remove(downloadInfo.id);

        }
    }

    public synchronized void pause(AppInfo appinfo) {
        DownloadInfo downLoadInfo = mDownloadInfoMap.get(appinfo.id);
        if (downLoadInfo != null) {
            if (downLoadInfo.currentState == STATE_DOWNLOADING || downLoadInfo.currentState == STATE_WAITING) {
                DownloadTask downloadTask = mDownloadTaskMap.get(downLoadInfo.id);
                if (downloadTask != null) {
                    ThreadManager.getThreadPoll().cancel(downloadTask);
                }
                downLoadInfo.currentState = STATE_PAUSE;
                notifyDownloadStateChanged(downLoadInfo);
            }
        }

    }

    public synchronized void install(AppInfo appinfo) {
        DownloadInfo downloadInfo = mDownloadInfoMap.get(appinfo.id);
        if (downloadInfo != null) {
            // 跳到系统的安装页面进行安装
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + downloadInfo.path),
                    "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(intent);
        }
    }


}
