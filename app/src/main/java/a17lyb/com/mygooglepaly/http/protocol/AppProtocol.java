package a17lyb.com.mygooglepaly.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.AppInfo;

/**
 * Created by 10400 on 2016/12/9.
 */

public class AppProtocol extends BaseProtocol<ArrayList<AppInfo>>{
    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        try {
            ArrayList<AppInfo> AppInfoList = new ArrayList<>();
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                AppInfo appInfo = new AppInfo();
                JSONObject jo = ja.getJSONObject(i);
                appInfo.des= (String) jo.getString("des");
                appInfo.downloadUrl= (String) jo.getString("downloadUrl");
                appInfo.iconUrl= (String) jo.getString("iconUrl");
                appInfo.id= jo.getString("id");
                appInfo.name= (String) jo.getString("name");
                appInfo.packageName= (String) jo.getString("packageName");
                appInfo.size= (long) jo.getLong("size");
                appInfo.stars= (float)jo.getDouble("stars");
                AppInfoList.add(appInfo);
            }
            return AppInfoList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
