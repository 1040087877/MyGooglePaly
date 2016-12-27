package a17lyb.com.mygooglepaly.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.domain.DownloadInfo;
import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.manager.DownloadManager;
import a17lyb.com.mygooglepaly.ui.view.ProgressArc;
import a17lyb.com.mygooglepaly.utils.BitmapHelper;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/7.
 */

public class HomeHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObserver, View.OnClickListener {

    @ViewInject(R.id.iv_icon)
    private ImageView ivIcon;
    @ViewInject(R.id.tv_name)
    private TextView tvName;
    @ViewInject(R.id.tv_size)
    private TextView tvSize;
    @ViewInject(R.id.tv_des)
    private TextView tvDes;
    @ViewInject(R.id.rb_start)
    private RatingBar rbStart;
    private BitmapUtils mBitmapUtils;
    private DownloadManager mDm;
    private int mCurrentState;
    private float mProgress;
    private ProgressArc progressArc;
    private ImageView ivDownload;
    private TextView tvDownLoad;
    private FrameLayout flProgress;

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.list_item_home);
        ViewUtils.inject(this, view);
        mBitmapUtils = BitmapHelper.getBitmapUtils();

        flProgress = (FrameLayout) view.findViewById(R.id.fl_progress);

        tvDownLoad = (TextView) view.findViewById(R.id.tv_download);

        progressArc = new ProgressArc(UIUtils.getContext());
        progressArc.setArcDiameter(UIUtils.dip2px(26));
        progressArc.setProgressColor(UIUtils.getColor(R.color.progress));
        progressArc.setArcDiameter(UIUtils.dip2px(26));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(UIUtils.dip2px(27), UIUtils.dip2px(27));
        flProgress.addView(progressArc, params);

        flProgress.setOnClickListener(this);

        mDm = DownloadManager.getInstance();
        mDm.registerObserver(this);
        return view;
    }

    @Override
    public void refreshView(AppInfo appInfo) {
        tvName.setText(appInfo.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), appInfo.size));
        tvDes.setText(appInfo.des);
        rbStart.setRating(appInfo.stars);
        mBitmapUtils.display(ivIcon, HttpHelper.URL + "image?name=" + appInfo.iconUrl);

        DownloadInfo downLoadInfo = mDm.getDownLoadInfo(appInfo);
        if (downLoadInfo != null) {
            //之前下载过
            mCurrentState = downLoadInfo.currentState;
            mProgress = downLoadInfo.getProgress();
        } else {
            mCurrentState = DownloadManager.STATE_UNDO;
            mProgress = 0;
        }
        refreshUI(mCurrentState, mProgress,appInfo.id);
    }

    private void refreshUI(int mCurrentState, float mProgress,String id) {
        //由于listview的重用机制，确保刷新之前，确实是同一个应用
        if(getData().id.equals(id)){
            return;
        }
        this.mCurrentState = mCurrentState;
        this.mProgress = mProgress;
//        System.out.println("refreshU方法里mCurrentState "+mCurrentState);
        switch (mCurrentState) {
            case DownloadManager.STATE_UNDO:
                // 自定义进度条背景
                progressArc.setBackgroundResource(R.mipmap.ic_download);
                // 没有进度
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownLoad.setText("下载");
                break;
            case DownloadManager.STATE_WAITING:
                progressArc.setBackgroundResource(R.mipmap.ic_download);
                // 等待模式
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                tvDownLoad.setText("等待");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                progressArc.setBackgroundResource(R.mipmap.ic_pause);
                // 下载中模式
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                progressArc.setProgress(mProgress, true);
                tvDownLoad.setText((int) (mProgress * 100) + "%");
                break;
            case DownloadManager.STATE_PAUSE:
                progressArc.setBackgroundResource(R.mipmap.ic_resume);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                break;
            case DownloadManager.STATE_ERROR:
                progressArc.setBackgroundResource(R.mipmap.ic_redownload);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownLoad.setText("下载失败");
                break;
            case DownloadManager.STATE_SUCCESS:
                progressArc.setBackgroundResource(R.mipmap.ic_install);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownLoad.setText("安装");
                break;
            default:

                break;
        }
    }

    private void refreshUIOnMainThread(final DownloadInfo info) {
        UIUtils.RunUIThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(info.currentState, info.getProgress(),info.id);
            }
        });
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo downInfo) {
        //判断下载对象是否是当前的应用
        AppInfo appInfo = getData();
        if (appInfo.id.equals(downInfo.id)) {
            refreshUIOnMainThread(downInfo);
        }
    }

    @Override
    public void onDownloadProgressChanged(DownloadInfo downInfo) {
        AppInfo appInfo = getData();
        if (appInfo.id.equals(downInfo.id)) {
            refreshUIOnMainThread(downInfo);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
