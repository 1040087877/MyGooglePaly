package a17lyb.com.mygooglepaly.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 10400 on 2016/12/12.
 */

public class ThreadManager {
    private static ThreadPool mThreadPool;
    public static ThreadPool getThreadPoll(){
        if(mThreadPool==null){
            synchronized (ThreadPool.class){
                if(mThreadPool==null){
                    int threadCount=10;
                    mThreadPool=new ThreadPool(threadCount,threadCount,1L);
                }
            }
        }
        return mThreadPool;
    }

    public static class ThreadPool{
        private int corePoolSize;// 核心线程数
        private int maximumPoolSize;// 最大线程数
        private long keepAliveTime;// 休息时间
        private ThreadPoolExecutor executor;

        private ThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime) {
            this.corePoolSize=corePoolSize;
            this.maximumPoolSize=maximumPoolSize;
            this.keepAliveTime=keepAliveTime;

        }
        public void execute(Runnable r){
            if(executor==null){
                executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }
            executor.execute(r);
        }
        public void cancel(Runnable r){
            if(executor!=null){
                executor.getQueue().remove(r);
            }
        }
    }
}
