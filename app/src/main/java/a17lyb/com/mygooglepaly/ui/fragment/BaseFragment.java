package a17lyb.com.mygooglepaly.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/6.
 */

public abstract class BaseFragment extends Fragment{

    private LoadingPage mLodingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView tv=new TextView(UIUtils.getContext());
//        tv.setText(getClass().getSimpleName());
//        tv.setTextColor(Color.BLACK);
        mLodingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                // 注意:此处一定要调用BaseFragment的onCreateSuccessView, 否则栈溢出
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                ResultState resultState = BaseFragment.this.onLoad();
                return resultState;
            }
        };
        return mLodingPage;
    }

    public void loadData(){
        if(mLodingPage!=null){
            mLodingPage.LoadData();
        }
    }
    //检测解析出来的数据的状态（空的，还是有数据）
    public LoadingPage.ResultState checkResultStatus(Object obj){
        if(obj!=null){
            ArrayList list= (ArrayList) obj;
            if(list instanceof ArrayList){
                if(list.isEmpty()){//不能用 data.size 否则会空指针
                    return LoadingPage.ResultState.STATE_EMPTY;
                }else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }

    public abstract View onCreateSuccessView();
    public abstract LoadingPage.ResultState onLoad();
}
