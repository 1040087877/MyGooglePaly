package a17lyb.com.mygooglepaly.http.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.utils.IOUtils;
import a17lyb.com.mygooglepaly.utils.LogUtils;
import a17lyb.com.mygooglepaly.utils.StringUtils;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * 127.0.0.1:8090/ home ?index=0 &name=zhangsang&age=8 假设
 * Created by 10400 on 2016/12/8.
 */

public abstract class BaseProtocol<T> {

    private String result;

    //获取网络并解析数据
    public T getData(int index) {
        String result = getCache(index);
        if(StringUtils.isEmpty(result)){
            result = getDataFromServer(index);
        }
        if(!StringUtils.isEmpty(result)){
            T data=parseData(result);
            return data;
        }
        return null;
    }

    private String getDataFromServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParams());
        LogUtils.e(HttpHelper.URL + getKey() + "?index=" + index + getParams());
        if (httpResult != null) {
            result = httpResult.getString();
            System.out.println("获取的数据" + result);
            setCache(index,result);
            return result;
        }
        return null;
    }

    public void setCache(int index,String json){
        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());
        long deadLine=System.currentTimeMillis()+30*60*1000;//半个小时后失效
        FileWriter fileWriter = null;
        try {
            fileWriter =  new FileWriter(cacheFile);
            fileWriter.write(deadLine+"\n");
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(fileWriter);
        }

    }

    public String getCache(int index){
        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());
        BufferedReader bufferedReader=null;
        if(cacheFile.exists()){
            try {
                bufferedReader = new BufferedReader(new FileReader(cacheFile));
                String deadLine =bufferedReader.readLine();//获取过期时间
                Long deadTime = Long.parseLong(deadLine);
                StringBuffer sb=new StringBuffer();
                String line=null;
                if(System.currentTimeMillis()<deadTime){
                    while ((line=bufferedReader.readLine())!=null){
                        sb.append(line);
                    }
                }
                LogUtils.e("调用保存的数据"+sb.toString());
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(bufferedReader);
            }
        }
        return null;
    }

    //网络链接关键词
    public abstract String getKey();

    public abstract String getParams();

    public abstract T parseData(String result);
}
