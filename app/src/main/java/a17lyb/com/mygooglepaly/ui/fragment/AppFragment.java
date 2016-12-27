package a17lyb.com.mygooglepaly.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.holder.AppHolder;
import a17lyb.com.mygooglepaly.holder.BaseHolder;
import a17lyb.com.mygooglepaly.http.protocol.AppProtocol;
import a17lyb.com.mygooglepaly.ui.adapter.MyBaseAdapter;
import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.ui.view.MyListView;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * APP界面
 * Created by 10400 on 2016/12/6.
 */

public class AppFragment extends BaseFragment {
    private ArrayList<AppInfo> data;
    private AppProtocol appProtocol;

    @Override
    public View onCreateSuccessView() {
        MyListView lv = new MyListView(UIUtils.getContext());
        AppAdapter appAdapter=  new AppAdapter(data);
        lv.setAdapter(appAdapter);
        return lv;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        appProtocol = new AppProtocol();
        data = appProtocol.getData(0);
        return checkResultStatus(data);
    }

    class AppAdapter extends MyBaseAdapter{

        public AppAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder(int position) {
            return new AppHolder();
        }

        @Override
        public ArrayList onLoadMore() {
            data = appProtocol.getData(getArrayListSize());
            return data;
        }
    }
}
