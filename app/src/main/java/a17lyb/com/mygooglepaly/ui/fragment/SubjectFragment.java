package a17lyb.com.mygooglepaly.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.SubjectInfo;
import a17lyb.com.mygooglepaly.holder.BaseHolder;
import a17lyb.com.mygooglepaly.holder.SubjectHolder;
import a17lyb.com.mygooglepaly.http.protocol.SubjectProtocol;
import a17lyb.com.mygooglepaly.ui.adapter.MyBaseAdapter;
import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.ui.view.MyListView;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * 专题
 * Created by 10400 on 2016/12/6.
 */

public class SubjectFragment extends BaseFragment {
    private ArrayList<SubjectInfo> data;
    private SubjectProtocol subjectProtocol;

    @Override
    public View onCreateSuccessView() {
        MyListView lv=new MyListView(UIUtils.getContext());
        lv.setAdapter(new SubjectAdapter(data));
        return lv;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        subjectProtocol = new SubjectProtocol();
        data =  subjectProtocol.getData(0);
        return checkResultStatus(data);
    }

    class SubjectAdapter extends MyBaseAdapter{

        public SubjectAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public ArrayList onLoadMore() {
            ArrayList<SubjectInfo> moreList = subjectProtocol.getData(getArrayListSize());
            return moreList;
        }

        @Override
        public BaseHolder getHolder(int position) {
            SubjectHolder subjectHolder =  new SubjectHolder();
            return subjectHolder;
        }
    }
}
