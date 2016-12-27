package a17lyb.com.mygooglepaly.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.holder.BaseHolder;
import a17lyb.com.mygooglepaly.holder.MoreHolder;
import a17lyb.com.mygooglepaly.manager.ThreadManager;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/7.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    //注意: 此处必须要从0开始写
    private static final int TYPE_NORMAL = 1;// 正常布局类型
    private static final int TYPE_MORE = 0;// 加载更多类型
    private ArrayList<T> data;
    private ArrayList<T> moreData;
    private boolean isOnload=false;
    public MyBaseAdapter(ArrayList<T> data) {
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size()+1;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==(getCount()-1)){
            return TYPE_MORE;
        }else{
            return getInnerType(position);
        }
    }

    // 子类可以重写此方法来更改返回的布局类型
    public int getInnerType(int position) {
        return TYPE_NORMAL;//默认普通类型
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder=null;
        if(convertView==null){
            if(getItemViewType(position)==TYPE_MORE){
                holder = new MoreHolder(isHasMore());
            }else{
                holder  = getHolder(position);//调用时已经初始化好布局
            }
        }else{
            holder = (BaseHolder) convertView.getTag();
        }

        if(getItemViewType(position)!=TYPE_MORE){
            holder.setData(getItem(position));
        }else {
            MoreHolder moreHolder= (MoreHolder) holder;
            if(moreHolder.getData()==MoreHolder.STATE_MORE_MORE){
                LoadMore((MoreHolder) holder);
            }
        }
        return holder.getRootView();
    }


    public void LoadMore(final MoreHolder holder){
        //加载 更多数据
        //正在更新则不处理
        if(!isOnload){
            isOnload=true;
//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                    isOnload=false;
//                    moreData = onLoadMore();
//                    //主线程更新数据
//                    UIUtils.RunUIThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(moreData!=null){
//                                //每一个页有20条数据，如果小于20条说明为最小一页
//                                if(moreData.size()<20){
//                                    holder.setData(MoreHolder.STATE_MORE_NONE);
//                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    //还有更多数据
//                                    holder.setData(MoreHolder.STATE_MORE_MORE);
//                                }
//                                data.addAll(moreData);
//                                MyBaseAdapter.this.notifyDataSetChanged();
//                            }else { //加载更多失败
//                                holder.setData(MoreHolder.STATE_MORE_ERROR);
//                            }
//
//                        }
//                    });
//                }
//            }.start();
            ThreadManager.getThreadPoll().execute(new Runnable() {
                @Override
                public void run() {
                    isOnload=false;
                    moreData = onLoadMore();
                    //主线程更新数据
                    UIUtils.RunUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if(moreData!=null){
                                //每一个页有20条数据，如果小于20条说明为最小一页
                                if(moreData.size()<20){
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                }else {
                                    //还有更多数据
                                    holder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                data.addAll(moreData);
                                MyBaseAdapter.this.notifyDataSetChanged();
                            }else { //加载更多失败
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }

                        }
                    });
                }
            });
        }

    }

    public int getArrayListSize(){
        return data.size();
    }

    public abstract ArrayList<T> onLoadMore();

    public  boolean isHasMore() {
        return true;
    }


    public abstract BaseHolder getHolder(int position);



}

