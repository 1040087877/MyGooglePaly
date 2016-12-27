package a17lyb.com.mygooglepaly.ui.fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import a17lyb.com.mygooglepaly.http.protocol.RecommendProtocol;
import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.ui.view.fly.ShakeListener;
import a17lyb.com.mygooglepaly.ui.view.fly.StellarMap;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * 推荐
 * Created by 10400 on 2016/12/6.
 */

public class RecommendFragment extends BaseFragment {
    private ArrayList<String> keywordList;
    private RecommendProtocol recommendProtocol;

    @Override
    public View onCreateSuccessView() {
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        stellarMap.setAdapter(new RecommendAdapter());
        stellarMap.setGroup(0,true);
        stellarMap.setRegularity(2,2);
        ShakeListener shakeListener = new ShakeListener(UIUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellarMap.zoomIn();
            }
        });
        return stellarMap;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        recommendProtocol = new RecommendProtocol();
        keywordList = recommendProtocol.getData(0);
        return checkResultStatus(keywordList);
    }

    class RecommendAdapter implements StellarMap.Adapter {

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getCount(int group) {
            int count=keywordList.size()/getGroupCount();
            if(group==getGroupCount()-1){
                count+=keywordList.size()%getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            position=getCount(group-1)*group+position;
            TextView tv=new TextView(UIUtils.getContext());
            final String keyword = keywordList.get(position);
            tv.setText(keyword);
            tv.setTextColor(Color.BLACK);
            Random random = new Random();
            //16-25
            int size= 16+random.nextInt(10);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);

            int r=30+random.nextInt(200);
            int g=30+random.nextInt(200);
            int b=30+random.nextInt(200);
            tv.setTextColor(Color.rgb(r,g,b));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),keyword, Toast.LENGTH_SHORT).show();
                }
            });

            return tv;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if(isZoomIn){//向下滑加载上一页
                if(group>0){
                    group--;
                }else {
                    group=getGroupCount()-1;
                }
            }else {//向上滑加载下一页
                if(group<getGroupCount()-1){
                    group++;
                }else {
                    group=0;
                }
            }
            System.out.println("group"+group);
            return group;
        }
    }

}
