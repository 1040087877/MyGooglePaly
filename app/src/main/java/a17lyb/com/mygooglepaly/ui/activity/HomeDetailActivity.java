package a17lyb.com.mygooglepaly.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.holder.DetailAppInfoHolder;
import a17lyb.com.mygooglepaly.holder.DetailDesHolder;
import a17lyb.com.mygooglepaly.holder.DetailDownloadHolder;
import a17lyb.com.mygooglepaly.holder.DetailSafeHolder;
import a17lyb.com.mygooglepaly.http.protocol.DetailPicsHolder;
import a17lyb.com.mygooglepaly.http.protocol.HomeDetailProtocol;
import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/10.
 */

public class HomeDetailActivity extends BaseActivity{

    private String packageName;
    private AppInfo data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        packageName = getIntent().getStringExtra("packageName");
        LoadingPage loadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };

        setContentView(loadingPage);
        loadingPage.LoadData();
        initDrawer();
    }


    private void initDrawer(){
        ActionBar supportActionBar =  getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public View onCreateSuccessView(){
        View view =  UIUtils.inflate(R.layout.page_home_detail);
        FrameLayout flAppInfoLayout = (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);
        //应用信息界面
        DetailAppInfoHolder detailAppInfoHolder = new DetailAppInfoHolder();
        flAppInfoLayout.addView(detailAppInfoHolder.getRootView());
        detailAppInfoHolder.setData(data);


        //安全数据界面
        FrameLayout flSafeLayout = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        flSafeLayout.addView(detailSafeHolder.getRootView());
        detailSafeHolder.setData(data);

        //截屏图片界面
        HorizontalScrollView horScr = (HorizontalScrollView) view.findViewById(R.id.horScr);
        DetailPicsHolder detailPicsHolder = new DetailPicsHolder();
        horScr.addView(detailPicsHolder.getRootView());
        detailPicsHolder.setData(data);

        //详情信息界面
        FrameLayout flDesLayout = (FrameLayout) view.findViewById(R.id.fl_detail_des);
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        flDesLayout.addView(detailDesHolder.getRootView());
        detailDesHolder.setData(data);

        FrameLayout flDownLoadLayout = (FrameLayout) view.findViewById(R.id.fl_detail_download);
        DetailDownloadHolder detailDownloadHolder = new DetailDownloadHolder();
        flDownLoadLayout.addView(detailDownloadHolder.getRootView());
        detailDownloadHolder.setData(data);
        return view;
    }

    public LoadingPage.ResultState onLoad(){
        HomeDetailProtocol homeDetailProtocol = new HomeDetailProtocol(packageName);
        data = homeDetailProtocol.getData(0);
        if(data !=null){
            return LoadingPage.ResultState.STATE_SUCCESS;
        }else {
            return LoadingPage.ResultState.STATE_ERROR;
        }
    }
}
