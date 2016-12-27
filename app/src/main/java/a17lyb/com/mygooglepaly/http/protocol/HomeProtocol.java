package a17lyb.com.mygooglepaly.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.AppInfo;

/**
 * Created by 10400 on 2016/12/8.
 */

public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>>{

    private ArrayList<String> pictureList;

    @Override
    public String getKey() {
        return "home";
    }

    @Override
    //不能传null
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        try {
            //App应用数据
            ArrayList<AppInfo> AppInfoList = new ArrayList<>();
            //图片链接数据（轮播条）
            pictureList = new ArrayList<>();
            JSONObject jo = new JSONObject(result);
            JSONArray joAppinfo = jo.getJSONArray("list");
            JSONArray joPicture = jo.getJSONArray("picture");
            for (int i = 0; i < joAppinfo.length(); i++) {
                /**
                 *   "des": "产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、",
                 "downloadUrl": "app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
                 "iconUrl": "app/com.youyuan.yyhl/icon.jpg",
                 "id": 1525490,
                 "name": "有缘网",
                 "packageName": "com.youyuan.yyhl",
                 "size": 3876203,
                 "stars": 4
                 */
                AppInfo appInfo = new AppInfo();
                JSONObject jo2 = joAppinfo.getJSONObject(i);
                appInfo.des= (String) jo2.getString("des");
                appInfo.downloadUrl= (String) jo2.getString("downloadUrl");
                appInfo.iconUrl= (String) jo2.getString("iconUrl");
                appInfo.id= jo2.getString("id");
                appInfo.name= (String) jo2.getString("name");
                appInfo.packageName= (String) jo2.getString("packageName");
                appInfo.size= (long) jo2.getLong("size");
                appInfo.stars= (float) jo2.getDouble("stars");
                AppInfoList.add(appInfo);
            }
            for (int i = 0; i <joPicture.length(); i++) {
                pictureList.add(joPicture.getString(i));
            }
            return AppInfoList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getPictureList(){
        return pictureList;
    }
}
