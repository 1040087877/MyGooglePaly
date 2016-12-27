package a17lyb.com.mygooglepaly.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.SubjectInfo;
import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.utils.BitmapHelper;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/9.
 */

public class SubjectHolder extends BaseHolder<SubjectInfo>{

    private ImageView ivPic;
    private TextView tvTitle;
    private BitmapUtils mBitmapUtils;

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.list_item_subject);
        ivPic = (ImageView) view.findViewById(R.id.iv_pic);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        mBitmapUtils.display(ivPic, HttpHelper.URL+"image?name="+data.url);
        tvTitle.setText(data.des);
    }
}
