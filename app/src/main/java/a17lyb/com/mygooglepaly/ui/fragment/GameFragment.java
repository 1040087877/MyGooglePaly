package a17lyb.com.mygooglepaly.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import a17lyb.com.mygooglepaly.ui.view.LoadingPage;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * 游戏
 * Created by 10400 on 2016/12/6.
 */

public class GameFragment extends BaseFragment {
    @Override
    public View onCreateSuccessView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(getClass().getSimpleName());
        tv.setTextColor(Color.BLACK);
        return tv;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
