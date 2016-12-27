package a17lyb.com.mygooglepaly.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import a17lyb.com.mygooglepaly.http.protocol.HotProtocol;
import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.ui.view.MyFlowLayout;
import a17lyb.com.mygooglepaly.utils.DrawableUtils;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * 排行
 * Created by 10400 on 2016/12/6.
 */

public class HotFragment extends BaseFragment {

    private ArrayList<String> data;

    @Override
    public View onCreateSuccessView() {
        MyFlowLayout flowLayout = new MyFlowLayout(UIUtils.getContext());
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        scrollView.addView(flowLayout);
        int padding=UIUtils.dip2px(10);
        flowLayout.setPadding(padding,padding,padding,padding);
//        flowLayout.setHorizontalSpacing(UIUtils.dip2px(6));
//        flowLayout.setVerticalSpacing(UIUtils.dip2px(8));
        for(int i = 0; i<data.size(); i++){
            final String keyWord=data.get(i);
            TextView tv = new TextView(UIUtils.getContext());
            tv.setTextColor(Color.WHITE);
            tv.setText(data.get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(padding,padding,padding,padding);
            //设置随机颜色
            Random random = new Random();
            int r=30+random.nextInt(200);
            int g=30+random.nextInt(200);
            int b=30+random.nextInt(200);

//            GradientDrawable shape = DrawableUtils.getGradientDrawable(Color.rgb(r, g, b), UIUtils.dip2px(6));

            int color = 0xffcecece;// 按下后偏白的背景色

            StateListDrawable selector = DrawableUtils.getSelector( Color.rgb(r, g, b),color, UIUtils.dip2px(6));
            tv.setBackgroundDrawable(selector);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),keyWord, Toast.LENGTH_SHORT).show();
                }
            });
            flowLayout.addView(tv);
        }

        return scrollView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HotProtocol hotProtocol = new HotProtocol();
        data = hotProtocol.getData(0);
        return checkResultStatus(data);
    }
}
