package a17lyb.com.mygooglepaly.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by 10400 on 2016/12/9.
 */

public class DrawableUtils {
    public static GradientDrawable getGradientDrawable(int color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);//矩形
        shape.setCornerRadius(radius);
        shape.setColor(color);
        return shape;
    }

    public static StateListDrawable getSelector(Drawable press,Drawable normal){
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed},press);
        selector.addState(new int[]{},normal);
        return  selector;
    }

    public static StateListDrawable getSelector(int normal,int press, int radius){
        GradientDrawable bgNormal = getGradientDrawable(normal, radius);
        GradientDrawable bgPress = getGradientDrawable(press, radius);
        StateListDrawable selector = getSelector(bgPress, bgNormal);
        return  selector;
    }

}
