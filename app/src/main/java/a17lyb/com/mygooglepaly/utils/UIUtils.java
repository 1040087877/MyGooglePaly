package a17lyb.com.mygooglepaly.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import a17lyb.com.mygooglepaly.global.GoolePlayApplication;

/**
 * Created by 10400 on 2016/12/6.
 */

public class UIUtils {

    public static Context getContext(){
        return GoolePlayApplication.getContext();
    }
    public static Handler getHandler(){
        return GoolePlayApplication.getHandler();
    }
    public static int getMainThreadId(){
        return GoolePlayApplication.getMyTid();
    }

    //////////////加载资源文件///////////////////
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }

    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }
    public static float getDimens(int id){
        return getContext().getResources().getDimension(id);
    }

    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }

    // /////////////////dip和px转换//////////////////////////
    public static int dip2px(float dip){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density+0.5f);
    }
    public static float px2dip(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (px /density);
    }

    // /////////////////加载布局文件//////////////////////////
    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }

    // /////////////////判断是否运行在主线程////////////////////
    public static boolean isRunUIThread(){
        int myTid = Process.myTid();
        if(myTid==getMainThreadId()){
            return true;
        }
        return false;
    }

    //运行在主线程
    public static void RunUIThread(Runnable r){
        if (isRunUIThread()) {
            //主线程，直接运行
            r.run();
        }else {
            getHandler().post(r);
        }
    }

    public static ColorStateList getColorStateList(int mTabTextColorResId) {

        return getContext().getResources().getColorStateList(mTabTextColorResId);
    }
}
