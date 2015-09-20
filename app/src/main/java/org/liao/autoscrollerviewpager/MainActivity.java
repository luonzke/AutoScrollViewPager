package org.liao.autoscrollerviewpager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final int AUTO_SCROLLER = 1;


    private ViewPager viewPager;
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
    private List<String> descList;


    private int mAutoScrollerDelayMillis = 3000;

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AUTO_SCROLLER:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                    mHandler.sendEmptyMessageDelayed(AUTO_SCROLLER,mAutoScrollerDelayMillis);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            MainActivity.this.startActivity(new Intent(MainActivity.this,AutoScrollViewPagerActivity.class));
            }
        });



        tv_intro = (TextView) findViewById(R.id.tv_intro);
        dot_layout = (LinearLayout) findViewById(R.id.dot_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        initData();


        viewPager.setAdapter(new MyAdapter());
        viewPager.addOnPageChangeListener(this);

        int beginPager = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % ids.length;
        viewPager.setCurrentItem(beginPager);

        updateViews(beginPager);

        mHandler.sendEmptyMessageDelayed(AUTO_SCROLLER,mAutoScrollerDelayMillis);

    }

    /**
     * 初始化dot
     */
    private void initDots() {
        for (int i = 0; i < ids.length; i++) {
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

    private void initData() {
        mViewList = new ArrayList<>();
        descList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {

            View view = View.inflate(this, R.layout.auto_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            iv.setImageResource(ids[i]);
            mViewList.add(view);

            descList.add("第一张图片:" + i);
        }

        initDots();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mHandler.removeMessages(AUTO_SCROLLER);

                break;
            case MotionEvent.ACTION_UP:
                mHandler.sendEmptyMessageDelayed(AUTO_SCROLLER,mAutoScrollerDelayMillis);
                break;
        }
        return super.onTouchEvent(event);
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

    private void updateViews(int position) {
        position %= ids.length;

        int childCount = dot_layout.getChildCount();
        for (int index = 0; index < childCount; index++) {
            dot_layout.getChildAt(index).setEnabled(index == position);
        }
        tv_intro.setText(descList.get(position));
    }


    private class MyAdapter extends PagerAdapter {

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
            View view = mViewList.get(position % ids.length);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
