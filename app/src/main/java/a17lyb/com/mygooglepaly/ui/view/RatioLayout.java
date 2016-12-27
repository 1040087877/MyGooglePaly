package a17lyb.com.mygooglepaly.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.utils.LogUtils;

/**
 * 根据图片调整宽高比例
 * Created by 10400 on 2016/12/9.
 */

public class RatioLayout extends FrameLayout{

    private float ratio;

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        ratio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
        typedArray.recycle();
        LogUtils.e("图片宽高比例为："+ ratio);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode==MeasureSpec.EXACTLY && heightMode==MeasureSpec.UNSPECIFIED && ratio>0){
            int imageWidth=width-getPaddingLeft()-getPaddingRight();
            int imageHeight= (int) (imageWidth/ratio+0.5f);

            int height=imageHeight+getPaddingTop()+getPaddingBottom();

            heightMeasureSpec=  MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
