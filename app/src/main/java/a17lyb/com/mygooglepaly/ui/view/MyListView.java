package a17lyb.com.mygooglepaly.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 10400 on 2016/12/9.
 */

public class MyListView  extends ListView{
    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setCacheColorHint(Color.TRANSPARENT);//去掉缓冲背景色
        this.setDivider(null);//去掉分割线
        this.setSelector(new ColorDrawable());//状态选择器设置为透明
    }
}
