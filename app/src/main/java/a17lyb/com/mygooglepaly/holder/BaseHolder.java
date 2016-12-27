package a17lyb.com.mygooglepaly.holder;

import android.view.View;

/**
 * Created by 10400 on 2016/12/7.
 */

public abstract class BaseHolder<T>{

    private T data;
    private View mRootView;

    public BaseHolder() {
        mRootView = getView();
        mRootView.setTag(this);
    }

    public View getRootView() {
        return mRootView;
    }

    public void setData(T data){
        this.data=data;
        refreshView(data);
    }

    public abstract View getView();

    public abstract void refreshView(T data);
    public T getData(){
        return data;
    }

}
