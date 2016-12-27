package a17lyb.com.mygooglepaly.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * 推荐网络访问
 * Created by 10400 on 2016/12/8.
 */

public class RecommendProtocol extends BaseProtocol<ArrayList<String>>{
    @Override
    public String getKey() {
        return "recommend";
    }

    @Override
    //不能传null
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseData(String result) {
        try {
            ArrayList<String> dataList=new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                String keyword = jsonArray.getString(i);
                dataList.add(keyword);
            }
            return dataList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
