package org.liao.autoscrollerviewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.liao.AutoScrollViewPager;

import java.util.List;

/**
 * 作者：Liao on 2015/9/20 13:30
 * 邮箱：583125288@qq.com
 * TODO :
 */
public class AutoScrollAdapter extends PagerAdapter implements View.OnClickListener {
    private List<View> mViewList;
    private List<Ad> mAdList;
    private AutoScrollViewPager mViewPager;
    private OnItemClickListener mOnItemClickListener;

    /***
     * 当前正在展示的view的索引
     */
    private int mCurrentPos;

    public AutoScrollAdapter(List<View> viewList, List<Ad> adList, AutoScrollViewPager viewPager) {
        this.mViewList = viewList;
        this.mAdList = adList;
        this.mViewPager = viewPager;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position % mAdList.size());
        container.addView(view);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mCurrentPos = mViewPager.getCurrentItem() % mViewList.size();
            mOnItemClickListener.onItemClick(v, mCurrentPos);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
