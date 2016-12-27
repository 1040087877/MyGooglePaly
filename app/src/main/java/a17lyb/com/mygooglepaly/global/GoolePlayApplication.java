package a17lyb.com.mygooglepaly.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.antfortune.freeline.FreelineCore;

/**
 * Created by 10400 on 2016/12/6.
 */

public class GoolePlayApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int myTid;

    @Override
    public void onCreate() {
        super.onCreate();
        FreelineCore.init(this);
        context = getApplicationContext();
        handler = new Handler();
        myTid = Process.myTid();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMyTid() {
        return myTid;
    }

}
