package a17lyb.com.mygooglepaly.holder;

import android.view.View;
import android.widget.TextView;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.domain.CategoryInfo;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/9.
 */

public class TitleHolder extends BaseHolder<CategoryInfo>{

    private TextView title;

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        title = (TextView) view.findViewById(R.id.tv_title);

        return view;
    }

    public void refreshView(CategoryInfo data) {
        title.setText(data.title);

    }
}
