package a17lyb.com.mygooglepaly.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * 排行页网络访问
 * Created by 10400 on 2016/12/9.
 */

public class HotProtocol extends BaseProtocol<ArrayList<String>>{
    @Override
    public String getKey() {
        return "hot";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseData(String result) {
        ArrayList<String> hotList=new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                String keyword = jsonArray.getString(i);
                hotList.add(keyword);
            }
            return hotList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
