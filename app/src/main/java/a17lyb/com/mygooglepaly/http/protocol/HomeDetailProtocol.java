package a17lyb.com.mygooglepaly.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.AppInfo;

/**
 * Created by 10400 on 2016/12/10.
 */

public class HomeDetailProtocol extends BaseProtocol <AppInfo>{


    private String packageName;

    public HomeDetailProtocol(String packageName) {
        super();
        this.packageName = packageName;
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    public String getParams() {
        return "&packageName=" + packageName;
    }

    @Override
    public AppInfo parseData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            AppInfo appInfo = new AppInfo();
            appInfo.des = (String) jsonObject.getString("des");
            appInfo.downloadUrl = (String) jsonObject.getString("downloadUrl");
            appInfo.iconUrl = (String) jsonObject.getString("iconUrl");
            appInfo.id = jsonObject.getString("id");
            appInfo.name = (String) jsonObject.getString("name");
            appInfo.packageName = (String) jsonObject.getString("packageName");
            appInfo.size = (long) jsonObject.getLong("size");
            appInfo.stars = (float) jsonObject.getDouble("stars");

            appInfo.author = jsonObject.getString("author");
            appInfo.date = jsonObject.getString("date");
            appInfo.downloadNum = jsonObject.getString("downloadNum");
            appInfo.version = jsonObject.getString("version");

            JSONArray safe = jsonObject.getJSONArray("safe");
            ArrayList<AppInfo.SafeInfo> safeInfoList = new ArrayList<>();
            for (int i = 0; i < safe.length(); i++) {
                AppInfo.SafeInfo safeInfo = new AppInfo.SafeInfo();
                JSONObject jo = safe.getJSONObject(i);
                safeInfo.safeDes = jo.getString("safeDes");
                safeInfo.safeDesUrl = jo.getString("safeDesUrl");
                safeInfo.safeUrl = jo.getString("safeUrl");
                safeInfoList.add(safeInfo);
            }
            appInfo.safe=safeInfoList;
            // 解析截图信息
            ArrayList<String> screenList = new ArrayList<>();
            JSONArray screenJo = jsonObject.getJSONArray("screen");
            for (int i = 0; i < screenJo.length(); i++) {
                String screenUrl = screenJo.getString(i);
                screenList.add(screenUrl);
            }
            appInfo.screen=screenList;
            return appInfo;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
