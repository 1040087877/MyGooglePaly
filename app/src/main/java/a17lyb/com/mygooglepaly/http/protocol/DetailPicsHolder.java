package a17lyb.com.mygooglepaly.http.protocol;

import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.holder.BaseHolder;
import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.utils.BitmapHelper;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/11.
 */

public class DetailPicsHolder extends BaseHolder<AppInfo>{
    private ImageView iv[];
    private BitmapUtils mBitmapUtils;

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.layout_detail_picinfo);
        iv=new ImageView[5];
        iv[0]= (ImageView) view.findViewById(R.id.iv_pic1);
        iv[1]= (ImageView) view.findViewById(R.id.iv_pic2);
        iv[2]= (ImageView) view.findViewById(R.id.iv_pic3);
        iv[3]= (ImageView) view.findViewById(R.id.iv_pic4);
        iv[4]= (ImageView) view.findViewById(R.id.iv_pic5);

        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<String> screen = data.screen;
        for (int i = 0; i < 5; i++) {
            if(i<screen.size()){
                mBitmapUtils.display(iv[i], HttpHelper.URL+ "image?name="+screen.get(i));
            }else {
                iv[i].setVisibility(View.GONE);
            }
        }
    }
}
