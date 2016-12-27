package a17lyb.com.mygooglepaly.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.domain.DownloadInfo;
import a17lyb.com.mygooglepaly.manager.DownloadManager;
import a17lyb.com.mygooglepaly.ui.view.ProgressHorizontal;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/12.
 */

public class DetailDownloadHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObserver,View.OnClickListener{

    private Button btnDownload;
    private DownloadManager mDm;
    private int mCurrentState;
    private float mProgress;
    private FrameLayout flProgress;
    private ProgressHorizontal progressHorizontal;

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);
        flProgress = (FrameLayout) view.findViewById(R.id.fl_progress);
        btnDownload = (Button) view.findViewById(R.id.btn_download);
        progressHorizontal = new ProgressHorizontal(UIUtils.getContext());
        progressHorizontal.setProgressTextSize(UIUtils.dip2px(16));
        progressHorizontal.setProgressBackgroundResource(R.drawable.progress_bg);
        progressHorizontal.setProgressResource(R.drawable.progress_normal);
        progressHorizontal.setProgressTextColor(Color.WHITE);
        btnDownload.setOnClickListener(this);
        flProgress.setOnClickListener(this);
        mDm = DownloadManager.getInstance();
        mDm.registerObserver(this);

        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        flProgress.addView(progressHorizontal,params);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        //判断应用是否下载过
        DownloadInfo downLoadInfo = mDm.getDownLoadInfo(data);
        System.out.println("集合中的数据"+downLoadInfo);
        if (downLoadInfo!=null){
            //之前下载过
            mCurrentState = downLoadInfo.currentState;
            mProgress = downLoadInfo.getProgress();
        }else {
            mCurrentState=DownloadManager.STATE_UNDO;
            mProgress=0;
        }
        refreshUI(mCurrentState,mProgress);
    }

    private void refreshUI(int mCurrentState, float mProgress) {
        this.mCurrentState=mCurrentState;
        this.mProgress=mProgress;
//        System.out.println("refreshU方法里mCurrentState "+mCurrentState);
        switch(mCurrentState){
            case DownloadManager.STATE_UNDO:
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载");
            break;
            case DownloadManager.STATE_WAITING:
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("等待中");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                btnDownload.setText("正在下载");
                progressHorizontal.setCenterText("");
                progressHorizontal.setProgress(mProgress);
                break;
            case DownloadManager.STATE_PAUSE:
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                progressHorizontal.setCenterText("暂停");
                progressHorizontal.setProgress(mProgress);
                break;
            case DownloadManager.STATE_ERROR:
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("失败");
                break;
            case DownloadManager.STATE_SUCCESS:
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("安装");
                break;
            default:

            break;
        }
    }
    private void refreshUIOnMainThread(final DownloadInfo info){
        UIUtils.RunUIThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(info.currentState,info.getProgress());
            }
        });
    }

    //状态更新
    public void onDownloadStateChanged(DownloadInfo info) {
        //判断下载对象是否是当前的应用
//        System.out.println("onDownloadStateChanged 当前状态 "+info.currentState);
        AppInfo appInfo = getData();
        if(appInfo.id.equals(info.id)){
//            refreshUI(info.currentState,info.getProgress());
            refreshUIOnMainThread(info);
        }
    }


    @Override//进度更新，子线程执行
    public void onDownloadProgressChanged(DownloadInfo info) {
        AppInfo appInfo = getData();
        if(appInfo.id.equals(info.id)){
            refreshUIOnMainThread(info);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_download:
            case R.id.fl_progress:
                //根据当前状态觉得下一步操作
                if(mCurrentState==DownloadManager.STATE_UNDO || mCurrentState==DownloadManager.STATE_ERROR
                        || mCurrentState==DownloadManager.STATE_PAUSE ){
                    mDm.download(getData());//开始下载
                }else if(mCurrentState== DownloadManager.STATE_DOWNLOADING
                        || mCurrentState==DownloadManager.STATE_WAITING){
                    mDm.pause(getData());//暂停下载
                    System.out.println("暂停下载");
                }else if(mCurrentState==DownloadManager.STATE_SUCCESS){
                    mDm.install(getData());//开始安装
                }
                System.out.println("下载状态"+mCurrentState);
            break;
             default:

            break;
        }
    }
}
