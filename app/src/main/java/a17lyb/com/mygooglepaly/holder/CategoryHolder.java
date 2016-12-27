package a17lyb.com.mygooglepaly.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.CategoryInfo;
import a17lyb.com.mygooglepaly.http.HttpHelper;
import a17lyb.com.mygooglepaly.utils.BitmapHelper;
import a17lyb.com.mygooglepaly.utils.UIUtils;

import static a17lyb.com.mygooglepaly.R.id.ll_grid1;
import static a17lyb.com.mygooglepaly.R.id.ll_grid2;
import static a17lyb.com.mygooglepaly.R.id.ll_grid3;

/**
 * Created by 10400 on 2016/12/9.
 */

public class CategoryHolder extends BaseHolder<CategoryInfo> implements View.OnClickListener {
    private TextView tvName1, tvName2, tvName3;
    private ImageView ivIcon1, ivIcon2, ivIcon3;
    private LinearLayout llGrid1, llGrid2, llGrid3;
    private BitmapUtils mBitmapUtils;

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.list_item_category);
        tvName1 = (TextView) view.findViewById(R.id.tv_name1);
        tvName2 = (TextView) view.findViewById(R.id.tv_name2);
        tvName3 = (TextView) view.findViewById(R.id.tv_name3);

        ivIcon1 = (ImageView) view.findViewById(R.id.iv_icon1);
        ivIcon2 = (ImageView) view.findViewById(R.id.iv_icon2);
        ivIcon3 = (ImageView) view.findViewById(R.id.iv_icon3);

        llGrid1 = (LinearLayout) view.findViewById(ll_grid1);
        llGrid2 = (LinearLayout) view.findViewById(ll_grid2);
        llGrid3 = (LinearLayout) view.findViewById(ll_grid3);

        mBitmapUtils = BitmapHelper.getBitmapUtils();

        return view;
    }

    public void refreshView(CategoryInfo data) {
        tvName1.setText(data.name1);
        tvName2.setText(data.name2);
        tvName3.setText(data.name3);
        mBitmapUtils.display(ivIcon1, HttpHelper.URL + "image?name=" + data.url1);
        mBitmapUtils.display(ivIcon2, HttpHelper.URL + "image?name=" + data.url2);
        mBitmapUtils.display(ivIcon3, HttpHelper.URL + "image?name=" + data.url3);
        llGrid1.setOnClickListener(this);
        llGrid2.setOnClickListener(this);
        llGrid3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case ll_grid1:
                Toast.makeText(UIUtils.getContext(), getData().name1, Toast.LENGTH_SHORT).show();
                break;
            case ll_grid2:
                Toast.makeText(UIUtils.getContext(), getData().name2, Toast.LENGTH_SHORT).show();
                break;
            case ll_grid3:
                Toast.makeText(UIUtils.getContext(), getData().name3, Toast.LENGTH_SHORT).show();
                break;
            default:

                break;
        }
    }
}
