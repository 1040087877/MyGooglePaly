package a17lyb.com.mygooglepaly.domain;

import java.util.ArrayList;

/**
 *  "des": "产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、",
 "downloadUrl": "app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
 "iconUrl": "app/com.youyuan.yyhl/icon.jpg",
 "id": 1525490,
 "name": "有缘网",
 "packageName": "com.youyuan.yyhl",
 "size": 3876203,
 "stars": 4
 * Created by 10400 on 2016/12/8.
 */

public class AppInfo {
    public String des;
    public String downloadUrl;
    public String iconUrl;
    public String id;
    public String name;
    public String packageName;
    public long size;
    public float stars;


    //补充字段, 供应用详情页使用
    public String author;
    public String date;
    public String downloadNum;
    public String version;
    public ArrayList<SafeInfo> safe;
    public ArrayList<String> screen;

    //当一个内部类是public static的时候, 和外部类没有区别
    public static class SafeInfo {
        public String safeDes;
        public String safeDesUrl;
        public String safeUrl;
    }
}
