package a17lyb.com.mygooglepaly.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.SubjectInfo;

/**
 * Created by 10400 on 2016/12/9.
 */

public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>>{
    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<SubjectInfo> parseData(String result) {
        ArrayList<SubjectInfo> subjectList=new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                SubjectInfo info=new SubjectInfo();
                JSONObject jo = ja.getJSONObject(i);
                info.des = jo.getString("des");
                info.url = jo.getString("url");
                subjectList.add(info);
            }
            return subjectList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
