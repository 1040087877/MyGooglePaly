package a17lyb.com.mygooglepaly.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.manager.ThreadManager;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/6.
 */

public abstract class LoadingPage extends FrameLayout {

    private static final int STAE_LOAD_UNDO = 1;//未加载
    private static final int STAE_LOAD_LOADING = 2;//加载中
    private static final int STAE_LOAD_ERROR = 3;//加载失败
    private static final int STAE_LOAD_EMPTY = 4;//加载为空
    private static final int STAE_LOAD_SUCCESS = 5;//加载成功

    private int mCurrentState = STAE_LOAD_UNDO;//当前状态,默认为未加载

    private View mPageLoading;
    private View mErrormPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (mPageLoading == null) {
            mPageLoading = UIUtils.inflate(R.layout.page_loading);
            addView(mPageLoading);
        }

        if (mErrormPage == null) {
            mErrormPage = UIUtils.inflate(R.layout.page_error);
            Button btRetry = (Button) mErrormPage.findViewById(R.id.bt_retry);
            btRetry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoadData();
                }
            });
            addView(mErrormPage);
        }

        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }
        //刷新界面
        showRightView();

    }

    private void showRightView() {
        mPageLoading.setVisibility(mCurrentState == STAE_LOAD_UNDO || mCurrentState == STAE_LOAD_LOADING ? VISIBLE : GONE);
        mErrormPage.setVisibility(mCurrentState == STAE_LOAD_ERROR ? VISIBLE : GONE);
        mEmptyPage.setVisibility(mCurrentState == STAE_LOAD_EMPTY ? VISIBLE : GONE);

        //判断 成功页面的状态
        if (mSuccessPage == null && mCurrentState == STAE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }
        if(mSuccessPage!=null){
            mSuccessPage.setVisibility(mCurrentState==STAE_LOAD_SUCCESS? VISIBLE:GONE);
        }
    }
    // 加载成功后显示的布局, 必须由调用者来实现
    public abstract View onCreateSuccessView();

    public void LoadData() {
        if (mCurrentState != STAE_LOAD_LOADING) {//如果没有加载就开始加载
            mCurrentState=STAE_LOAD_LOADING;
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    final ResultState resultState = onLoad();
//                    // 运行在主线程
//                    UIUtils.RunUIThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (resultState != null) {
//                                mCurrentState = resultState.getState();//网络加载结束后，更新网络状态
//                                showRightView();//根本最新动态刷新页面
//                            }
//                        }
//                    });
//                }
//            }.start();
            ThreadManager.getThreadPoll().execute(new Runnable() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    // 运行在主线程
                    UIUtils.RunUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                mCurrentState = resultState.getState();//网络加载结束后，更新网络状态
                                showRightView();//根本最新动态刷新页面
                            }
                        }
                    });
                }
            });
        }
    }

    // 加载网络数据, 返回值表示请求网络结束后的状态
    public abstract ResultState onLoad();

    public enum ResultState {
        STATE_SUCCESS(STAE_LOAD_SUCCESS), STATE_EMPTY(STAE_LOAD_EMPTY), STATE_ERROR(
                STAE_LOAD_ERROR);
        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
