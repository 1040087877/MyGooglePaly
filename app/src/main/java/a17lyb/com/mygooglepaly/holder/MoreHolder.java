package a17lyb.com.mygooglepaly.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import a17lyb.com.mygooglepaly.R;
import a17lyb.com.mygooglepaly.utils.UIUtils;

/**
 * Created by 10400 on 2016/12/7.
 */

public class MoreHolder extends BaseHolder<Integer>{
    public static final int STATE_MORE_MORE=0;
    public static final int STATE_MORE_ERROR=1;
    public static final int STATE_MORE_NONE=2;
    private LinearLayout mllLoadMore;
    private TextView tvLoadError;
    private boolean hasMore;
    public MoreHolder(boolean hasMore) {
        this.hasMore=hasMore;
        setData(hasMore?STATE_MORE_MORE:STATE_MORE_NONE);
    }

    @Override
    public View getView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        mllLoadMore = (LinearLayout) view.findViewById(R.id.ll_load_more);
        tvLoadError =   (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }



    @Override
    public void refreshView(Integer data) {
        switch(data){
            case STATE_MORE_MORE:
                mllLoadMore.setVisibility(View.VISIBLE);
                tvLoadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                mllLoadMore.setVisibility(View.GONE);
                tvLoadError.setVisibility(View.VISIBLE);
                break;
            case STATE_MORE_NONE:
                mllLoadMore.setVisibility(View.GONE);
                tvLoadError.setVisibility(View.GONE);
                break;
            default:

            break;
        }
    }
}
