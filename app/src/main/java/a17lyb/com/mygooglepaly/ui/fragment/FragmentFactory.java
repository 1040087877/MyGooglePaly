package a17lyb.com.mygooglepaly.ui.fragment;

import java.util.HashMap;

/**
 * Created by 10400 on 2016/12/6.
 */

public class FragmentFactory {
    private static HashMap<Integer,BaseFragment> mFragmentMap=new HashMap<Integer,BaseFragment>();
    public static BaseFragment createFragment(int pos){
//        BaseFragment fragment=null;
        BaseFragment fragment=mFragmentMap.get(pos);

        if(fragment==null){
            switch (pos){
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new RecommendFragment();
                    break;
                case 5:
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    fragment = new HotFragment();
                    break;

            }
            mFragmentMap.put(pos,fragment);
        }
        return fragment;
    }
}
