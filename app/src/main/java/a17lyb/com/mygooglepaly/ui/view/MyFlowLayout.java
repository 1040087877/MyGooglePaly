package a17lyb.com.mygooglepaly.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.utils.LogUtils;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/11.
 */

public class MyFlowLayout extends ViewGroup{
    private static final int MAX_LINE = 1000;
    private Line mLine;
    private int mUsedWidth;
    private int mHorizontalSpacing = UIUtils.dip2px(6);
    private ArrayList<Line> mLineList = new ArrayList<>();
    private int mVerticalSpacing = UIUtils.dip2px(8);
    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left=l+getPaddingLeft();
        int top=t+getPaddingTop();

        for (int i = 0; i < mLineList.size(); i++) {
            Line line = mLineList.get(i);
            line.layout(left,top);
            top+=line.mMaxHeight+mVerticalSpacing;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec)-getPaddingLeft()-getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec)-getPaddingTop()-getPaddingBottom();
        LogUtils.e("width+height"+width+"   "+height);
        int widthMode= MeasureSpec.getMode(widthMeasureSpec);
        int heightMode= MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAtView = getChildAt(i);
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode);

            childAtView.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            if(mLine==null){
                mLine=new Line();
            }

            int childWidth = childAtView.getMeasuredWidth();
            mUsedWidth += childWidth;
            if(mUsedWidth<width){
                mLine.addView(childAtView);

                mUsedWidth+=mHorizontalSpacing;

                if(mUsedWidth>width){
                    boolean success = newLine();
                    if(!success){
                        break;
                    }
                }
            }else {
                // 已超出边界
                // 1.当前没有任何控件, 一旦添加当前子控件, 就超出边界(子控件很长)
                if(mLine.getChildCount()==0){
                    mLine.addView(childAtView);//强行添加到当前行
                    if(!newLine())//换行
                    {
                        break;
                    }
                }else {// 2.当前有控件, 一旦添加, 超出边界
                    if(!newLine())//换行
                    {
                        break;
                    }
                    mLine.addView(childAtView);
                    mUsedWidth+=childWidth+mHorizontalSpacing;
                }
            }
        }


        if(mLine!=null && mLine.getChildCount()!=0 && !mLineList.contains(mLine)){
            mLineList.add(mLine);
        }

        int totalWidth=MeasureSpec.getSize(widthMeasureSpec);
        int totalHeight=0;

        for (int i = 0; i < mLineList.size(); i++) {
            Line line = mLineList.get(i);
            totalHeight+=line.mMaxHeight;
        }
        totalHeight+= (mLineList.size() - 1) * mVerticalSpacing;
        totalHeight+= getPaddingTop()+getPaddingBottom();

        setMeasuredDimension(totalWidth,totalHeight);

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private boolean newLine() {
        mLineList.add(mLine);
        if(mLineList.size()<MAX_LINE){
            mLine=new Line();
            mUsedWidth=0;
            return true;
        }
        return  false;

    }

    public class Line{
        private int mTotalWidth;
        public int mMaxHeight;
        private ArrayList<View> mChildViewList=new ArrayList<>();
        public void addView(View view){
            mChildViewList.add(view);
            mTotalWidth+=view.getMeasuredWidth();
            int height=view.getMeasuredHeight();
            mMaxHeight=mMaxHeight<height?height:mMaxHeight;
        }

        public int getChildCount(){
            return mChildViewList.size();
        }
        public void layout(int left,int top){
            int childCount = getChildCount();
            int validWidth = getMeasuredWidth() - getPaddingLeft()-getPaddingRight();
            int surplusWidth = validWidth - mTotalWidth - (childCount - 1) * mHorizontalSpacing;
            if(surplusWidth>=0){
                int space = (int) (surplusWidth / childCount + 0.5f);

                for (int i = 0; i < childCount; i++) {
                    View childView = mChildViewList.get(i);
                    int measuredWidth = childView.getMeasuredWidth();
                    int measuredHeight = childView.getMeasuredHeight();

                    measuredWidth+=space;//宽度增加

                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);

                    childView.measure(widthMeasureSpec,heightMeasureSpec);

                    int topOffset = (mMaxHeight - measuredHeight) / 2;
                    if(topOffset<0){
                        topOffset=0;
                    }

                    childView.layout(left,top+topOffset,left+measuredWidth,top+topOffset+measuredHeight);
                    left+=measuredWidth+mHorizontalSpacing;
                }
            }else {
                View childView = mChildViewList.get(0);
                childView.layout(left,top,left+childView.getMeasuredWidth(),
                        top+childView.getMeasuredHeight());
            }


        }
    }
}
