package a17lyb.com.mygooglepaly.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.utils.BitmapHelper;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/10.
 */

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>>{

    private ViewPager mViewPager;
    private ArrayList<String> data;
    private LinearLayout llContainer;
    private int mPreviousPos=0;//上一次选中的位置
    @Override
    public View getView() {
        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
        rlRoot.setLayoutParams(params);

        //viewPager
        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParmas = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlRoot.addView(mViewPager,vpParmas);

        //指示器
        llContainer = new LinearLayout(UIUtils.getContext());
        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padding =UIUtils.dip2px(10);
        llContainer.setPadding(padding,padding,padding,padding);//设置内边距
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        rlRoot.addView(llContainer,llParams);

        return rlRoot;
    }

    @Override
    public void refreshView(ArrayList<String> data) {
        this.data = data;

        for (int i = 0; i < data.size(); i++) {
            ImageView point = new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin=UIUtils.dip2px(4);//左边距
            point.setLayoutParams(layoutParams);
            if(i==0){
                point.setImageResource(R.mipmap.indicator_selected);
            }else {
                point.setImageResource(R.mipmap.indicator_normal);
            }
            llContainer.addView(point);
        }
        mViewPager.setAdapter(new HomeHeaderAdapter());
        mViewPager.setCurrentItem(data.size()*10000);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position=position%HomeHeaderHolder.this.data.size();

                ImageView pointImage = (ImageView) llContainer.getChildAt(position);
                pointImage.setImageResource(R.mipmap.indicator_selected);

                ImageView preImage = (ImageView) llContainer.getChildAt(mPreviousPos);
                preImage.setImageResource(R.mipmap.indicator_normal);
                mPreviousPos=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //启动轮播条自动播放
        HomeHeaderTask task = new HomeHeaderTask();
        task.start();
    }

    private class HomeHeaderTask implements Runnable{
        public void start(){
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this,2000);
        }

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(++currentItem);

            UIUtils.getHandler().postDelayed(this,2000);

        }
    }

    class HomeHeaderAdapter extends PagerAdapter{

        private BitmapUtils mBitmapUtils;

        public HomeHeaderAdapter() {
            mBitmapUtils = BitmapHelper.getBitmapUtils();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%data.size();
            String url = data.get(position);
            ImageView view= new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(view, HttpHelper.URL+ "image?name=" + url);

            container.addView(view);
            return view;
        }

    }
}
