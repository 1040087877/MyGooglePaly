package a17lyb.com.mygooglepaly.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.utils.BitmapHelper;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/10.
 */

public class DetailSafeHolder extends BaseHolder<AppInfo>{
        private ImageView[] mSafeIcons;
        private ImageView[] mDesIcons;
        private TextView[] mSafeDes;
    private LinearLayout[] mSafeDesBar;// 安全描述条目(图片+文字)
    private AppInfo data;
    private BitmapUtils mBitmapUtils;
    private RelativeLayout rlDesRoot;
    private LinearLayout llDesRoot;
    private int measuredHeight;
    private LinearLayout.LayoutParams layoutParams;
    private ImageView arraw;

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);
        mSafeIcons=new ImageView[4];
        mSafeIcons[0]= (ImageView) view.findViewById(R.id.iv_safe1);
        mSafeIcons[1]= (ImageView) view.findViewById(R.id.iv_safe2);
        mSafeIcons[2]= (ImageView) view.findViewById(R.id.iv_safe3);
        mSafeIcons[3]= (ImageView) view.findViewById(R.id.iv_safe4);

        mDesIcons = new ImageView[4];
        mDesIcons[0] = (ImageView) view.findViewById(R.id.iv_des1);
        mDesIcons[1] = (ImageView) view.findViewById(R.id.iv_des2);
        mDesIcons[2] = (ImageView) view.findViewById(R.id.iv_des3);
        mDesIcons[3] = (ImageView) view.findViewById(R.id.iv_des4);

        mSafeDes = new TextView[4];
        mSafeDes[0] = (TextView) view.findViewById(R.id.tv_des1);
        mSafeDes[1] = (TextView) view.findViewById(R.id.tv_des2);
        mSafeDes[2] = (TextView) view.findViewById(R.id.tv_des3);
        mSafeDes[3] = (TextView) view.findViewById(R.id.tv_des4);

        mSafeDesBar = new LinearLayout[4];
        mSafeDesBar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        mSafeDesBar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        mSafeDesBar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        mSafeDesBar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

        rlDesRoot = (RelativeLayout) view.findViewById(R.id.rl_des_root);
        llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);

        //箭头图片
        arraw = (ImageView) view.findViewById(R.id.iv_arrow);

        rlDesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });


        mBitmapUtils = BitmapHelper.getBitmapUtils();

        return view;
    }
    private boolean isOpen=false;
    private void toggle() {
        ValueAnimator valueAnimator=null;
        if(isOpen){
            isOpen=false;
            valueAnimator = ValueAnimator.ofInt(measuredHeight, 0);
        }else {
            isOpen=true;
            valueAnimator=ValueAnimator.ofInt(0,measuredHeight);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer height = (Integer) animation.getAnimatedValue();

                layoutParams.height=height;
                llDesRoot.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(isOpen){
                    arraw.setImageResource(R.mipmap.arrow_up);
                }else {
                    arraw.setImageResource(R.mipmap.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();

    }

    @Override
    public void refreshView(AppInfo data) {
        this.data = data;
        ArrayList<AppInfo.SafeInfo> safe = data.safe;
        for (int i = 0; i < 4; i++) {
            if(i<safe.size()){
                AppInfo.SafeInfo safeInfo = safe.get(i);
                mBitmapUtils.display(mSafeIcons[i], HttpHelper.URL
                        + "image?name=" + safeInfo.safeUrl);
                mBitmapUtils.display(mDesIcons[i],HttpHelper.URL
                        + "image?name=" + safeInfo.safeDesUrl);
                mSafeDes[i].setText(safeInfo.safeDes);
            }else {
                mSafeIcons[i].setVisibility(View.GONE);
                mSafeDesBar[i].setVisibility(View.GONE);
            }

        }
        llDesRoot.measure(0,0);
        measuredHeight = llDesRoot.getMeasuredHeight();
        System.out.printf("底部高度 measuredHeight"+ measuredHeight);
        layoutParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
        layoutParams.height=0;
        llDesRoot.setLayoutParams(layoutParams);
    }
}
