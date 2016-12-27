package a17lyb.com.mygooglepaly.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.holder.BaseHolder;
import a17lyb.com.mygooglepaly.holder.HomeHeaderHolder;
import a17lyb.com.mygooglepaly.holder.HomeHolder;
import a17lyb.com.mygooglepaly.http.protocol.HomeProtocol;
import a17lyb.com.mygooglepaly.ui.activity.HomeDetailActivity;
import a17lyb.com.mygooglepaly.ui.adapter.MyBaseAdapter;
import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.ui.view.MyListView;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * 首页
 * Created by 10400 on 2016/12/6.
 */

public class HomeFragment extends BaseFragment {
    private ArrayList<AppInfo> data;
    private HomeAdapter homeAdapter;
    private HomeProtocol homeProtocol;
    private ArrayList<AppInfo> moreData;
    private ArrayList<String> pictureList;

    @Override
    public View onCreateSuccessView() {
//        TextView tv = new TextView(UIUtils.getContext());
//        tv.setText(getClass().getSimpleName());
//        tv.setTextColor(Color.BLACK);
        MyListView lv=new MyListView(UIUtils.getContext());
        homeAdapter = new HomeAdapter(data);
        HomeHeaderHolder homeHeaderHolder = new HomeHeaderHolder();
        lv.addHeaderView(homeHeaderHolder.getRootView());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = data.get(position - 1);//去掉头布局
                Intent intent = new Intent();
                intent.setClass(UIUtils.getContext(), HomeDetailActivity.class);
                intent.putExtra("packageName",appInfo.packageName);
                startActivity(intent);
            }
        });
        lv.setAdapter(homeAdapter);

        if(pictureList!=null){
            homeHeaderHolder.setData(pictureList);
        }
        return lv;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
//        data=new ArrayList<>();
//        for (int i=0;i<30;i++){
//            data.add("测试数据"+i);
//        }
        homeProtocol = new HomeProtocol();
        data = homeProtocol.getData(0);

        pictureList = homeProtocol.getPictureList();
        return checkResultStatus(data);
    }

    class HomeAdapter extends MyBaseAdapter<AppInfo>{

        public HomeAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder(int position) {
            return  new HomeHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
//            ArrayList<String> moreData=new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                moreData.add("测试更多数据"+i);
//            }
//            SystemClock.sleep(2000);
            moreData = homeProtocol.getData(getArrayListSize());
            return moreData;
        }
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if(convertView==null){
//                holder=new ViewHolder();
//                convertView = UIUtils.inflate(R.layout.list_item_home);
//                holder.tv= (TextView) convertView.findViewById(R.id.tv_content);
//                convertView.setTag(holder);
//            }else {
//                holder= (ViewHolder) convertView.getTag();
//                holder.tv.setText(data.get(position));
//            }
//            return convertView;
//        }

    }

//    static class ViewHolder{
//        public TextView tv;
//    }

}
