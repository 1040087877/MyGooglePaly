package a17lyb.com.mygooglepaly.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/11.
 */

public class DetailDesHolder extends BaseHolder<AppInfo>{

    private TextView tvDes;
    private RelativeLayout rlToggle;
    private TextView tvAuthor;
    private ImageView ivArrow;
    private LinearLayout.LayoutParams layoutParams;

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.layout_detail_desinfo);
        tvDes = (TextView) view.findViewById(R.id.tv_detail_des);
        rlToggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);
        tvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);

        rlToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return view;
    }
    private boolean isOpen=false;
    private void toggle() {
        ValueAnimator animator=null;
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();
        if(shortHeight<longHeight){
            if(isOpen){
                isOpen=false;
                animator=ValueAnimator.ofInt(longHeight,shortHeight);
            }else {
                isOpen=true;
                animator=ValueAnimator.ofInt(shortHeight,longHeight);
            }
        }
        if(animator!=null){
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int height = (int) animation.getAnimatedValue();
                    layoutParams.height=height;
                    tvDes.setLayoutParams(layoutParams);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    final ScrollView scrollView = getScrollView();
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);

                        }
                    });
                    if(isOpen){
                        ivArrow.setImageResource(R.mipmap.arrow_up);
                    }else{
                        ivArrow.setImageResource(R.mipmap.arrow_down);
                    }

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.setDuration(200);
            animator.start();
        }


    }

    private ScrollView getScrollView() {
        ViewParent view = tvDes.getParent();
        while (!(view instanceof ScrollView)){
            view=view.getParent();
        }
        return (ScrollView) view;

    }

    @Override
    public void refreshView(AppInfo data) {
        tvDes.setText(data.des);
        tvAuthor.setText(data.author);
        tvDes.post(new Runnable() {
            @Override
            public void run() {
                layoutParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
                layoutParams.height=getShortHeight();
                tvDes.setLayoutParams(layoutParams);
            }
        });

    }

    public int getShortHeight(){
        int width = tvDes.getMeasuredWidth();
        TextView tv=new TextView(UIUtils.getContext());
        tv.setText(getData().des);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        tv.setMaxLines(7);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);

        tv.measure(widthMeasureSpec,heightMeasureSpec);

        return tv.getMeasuredHeight();

    }
    public int getLongHeight(){
        int width = tvDes.getMeasuredWidth();
        TextView tv=new TextView(UIUtils.getContext());
        tv.setText(getData().des);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//        tv.setMaxLines(7);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);

        tv.measure(widthMeasureSpec,heightMeasureSpec);

        return tv.getMeasuredHeight();

    }
}
