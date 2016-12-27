package a17lyb.com.mygooglepaly.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.domain.CategoryInfo;

/**
 * Created by 10400 on 2016/12/10.
 */

public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>>{
    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategoryInfo> parseData(String result) {
        try {
            JSONArray CategoryJo = new JSONArray(result);
            ArrayList<CategoryInfo> cateList=new ArrayList<>();
            for (int i = 0; i < CategoryJo.length(); i++) {
                JSONObject jo = CategoryJo.getJSONObject(i);
                if(jo.has("title")){
                    CategoryInfo categoryInfo = new CategoryInfo();
                    String title = jo.getString("title");
                    categoryInfo.title=title;
                    categoryInfo.isTitle=true;
                    cateList.add(categoryInfo);
                }

                if (jo.has("infos")) {
                    JSONArray infosJo = jo.getJSONArray("infos");
                    for (int j = 0; j < infosJo.length(); j++) {
                        CategoryInfo categoryInfo = new CategoryInfo();
                        JSONObject jo2 = infosJo.getJSONObject(j);
                        categoryInfo.name1=jo2.getString("name1");
                        categoryInfo.name2=jo2.getString("name2");
                        categoryInfo.name3=jo2.getString("name3");
                        categoryInfo.url1=jo2.getString("url1");
                        categoryInfo.url2=jo2.getString("url2");
                        categoryInfo.url3=jo2.getString("url3");
                        categoryInfo.isTitle=false;
                        cateList.add(categoryInfo);
                    }
                }
            }
            return cateList;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
