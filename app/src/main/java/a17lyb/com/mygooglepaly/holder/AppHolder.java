package a17lyb.com.mygooglepaly.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.AppInfo;
import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.utils.BitmapHelper;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/7.
 */

public class AppHolder extends BaseHolder<AppInfo>{

    @ViewInject(R.id.iv_icon)
    private ImageView ivIcon;
    @ViewInject(R.id.tv_name)
    private TextView tvName;
    @ViewInject(R.id.tv_size)
    private TextView tvSize;
    @ViewInject(R.id.tv_des)
    private TextView tvDes;
    @ViewInject(R.id.rb_start)
    private RatingBar rbStart;
    private BitmapUtils mBitmapUtils;

    @Override
    public View getView() {
        View view= UIUtils.inflate(R.layout.list_item_home);
        ViewUtils.inject(this,view);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo appInfo) {
        tvName.setText(appInfo.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),appInfo.size));
        tvDes.setText(appInfo.des);
        rbStart.setRating(appInfo.stars);
        mBitmapUtils.display(ivIcon, HttpHelper.URL+"image?name="+appInfo.iconUrl);

    }


}
