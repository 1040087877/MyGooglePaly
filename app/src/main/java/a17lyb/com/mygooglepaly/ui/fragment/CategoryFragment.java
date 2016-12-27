package a17lyb.com.mygooglepaly.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.CategoryInfo;
import a17lyb.com.mygooglepaly.holder.BaseHolder;
import a17lyb.com.mygooglepaly.holder.CategoryHolder;
import a17lyb.com.mygooglepaly.holder.TitleHolder;
import a17lyb.com.mygooglepaly.http.protocol.CategoryProtocol;
import a17lyb.com.mygooglepaly.ui.adapter.MyBaseAdapter;
import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.ui.view.MyListView;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * 分类
 * Created by 10400 on 2016/12/6.
 */

public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView lv=new MyListView(UIUtils.getContext());
        lv.setAdapter(new CategoryAdapter(data));
        return lv;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        data = categoryProtocol.getData(0);
        return checkResultStatus(data);
    }

    class CategoryAdapter extends MyBaseAdapter{

        public CategoryAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public ArrayList onLoadMore() {
            return null;
        }

        @Override
        public BaseHolder getHolder(int position) {
            if(data.get(position).isTitle){
                return new TitleHolder();
            }else {
                return new CategoryHolder();
            }
        }

        @Override
        public int getInnerType(int position) {
            if(data.get(position).isTitle){
                return super.getInnerType(position);
            }else {
                return super.getInnerType(position)+1;
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }

        @Override
        public boolean isHasMore() {
            return false;
        }
    }
}
