package a17lyb.com.mygooglepaly.utils;

import com.lidroid.xutils.BitmapUtils;

import a17lyb.com.mygooglepaly.R;

/**
 * Created by 10400 on 2016/12/9.
 */

public class BitmapHelper {
    private static BitmapUtils mBitmapUtils=null;
    public static BitmapUtils getBitmapUtils(){
        if(mBitmapUtils==null){
            synchronized (BitmapUtils.class){
                if(mBitmapUtils==null){
                    mBitmapUtils =new BitmapUtils(UIUtils.getContext());
                    mBitmapUtils.configDefaultLoadingImage(R.mipmap.ic_default);
                }
            }
        }

        return mBitmapUtils;
    }
}
