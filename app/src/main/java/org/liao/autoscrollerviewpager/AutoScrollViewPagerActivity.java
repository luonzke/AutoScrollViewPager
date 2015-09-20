package org.liao.autoscrollerviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.liao.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Liao on 2015/9/19 23:52
 * 邮箱：583125288@qq.com
 * TODO : 主要演示在ScrollView使用方法
 */
public class AutoScrollViewPagerActivity extends Activity implements ViewPager.OnPageChangeListener {
    private AutoScrollViewPager viewpager;
    private TextView tv_intro;
    private LinearLayout dot_layout;

    private int[] ids = {
            R.mipmap.a
            , R.mipmap.b
            , R.mipmap.c
            , R.mipmap.d
            , R.mipmap.e
    };

    private List<View> mViewList;
    private List<Ad> mAdList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll_view_pager);

        viewpager = (AutoScrollViewPager) findViewById(R.id.viewpager);
        dot_layout = (LinearLayout) findViewById(R.id.dot_layout);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        initData();

        AutoScrollAdapter adapter = new AutoScrollAdapter(mViewList, mAdList, viewpager);
        viewpager.setAdapter(adapter);

        adapter.addOnItemClickListener(new AutoScrollAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AutoScrollViewPagerActivity.this, mAdList.get(position).title, Toast.LENGTH_SHORT).show();
            }
        });

        //设置这个屏蔽掉在scrollerView里面的事件冲突
        viewpager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);

        viewpager.addOnPageChangeListener(this);

        int beginPager = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mAdList.size();
        viewpager.setCurrentItem(beginPager);
        updateViews(beginPager);


    }

    /**
     * 更新下面指示器与文本
     * @param position
     */
    private void updateViews(int position) {
        position %= mAdList.size();

        int childCount = dot_layout.getChildCount();
        for (int index = 0; index < childCount; index++) {
            dot_layout.getChildAt(index).setEnabled(index == position);
        }
        tv_intro.setText(mAdList.get(position).title);
    }

    private void initData() {
        mViewList = new ArrayList<>();
        mAdList = new ArrayList<>();

        for (int index = 0; index < ids.length; index++) {
            Ad ad = new Ad();
            ad.title = "第一张图片:" + index;
            ad.id = ids[index];
            mAdList.add(ad);
        }

        for (int i = 0; i < mAdList.size(); i++) {

            View view = View.inflate(this, R.layout.auto_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            iv.setImageResource(mAdList.get(i).id);
            mViewList.add(view);


        }
        initDots();
    }

    /**
     * 初始化dot
     */
    private void initDots() {
        for (int i = 0; i < mAdList.size(); i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i != 0) {
                params.leftMargin = (0 != i) ? 20 : 0;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selector_dot);
            dot_layout.addView(view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewpager.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewpager.stopAutoScroll();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateViews(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
