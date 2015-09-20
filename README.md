### AutoScrollViewPager自动滚动广告
#### 使用方式
* 步骤一：

只需将下面的控件替换v4原声的viewpager即可：

	 <org.liao.AutoScrollViewPager
            android:id="@+id/viewpager"
            android:layout_width="match_parent"
            android:layout_height="200dp"/>
原来的v4：
	
	 <android.support.v4.view.ViewPager
            android:id="@+id/viewpager"
            android:layout_width="match_parent"
            android:layout_height="200dp"/>

* 步骤2：

在Activity里面开启自动滚动：
	
	viewpager.startAutoScroll(); //开启自动滚动
	viewpager.stopAutoScroll(); //关闭自动滚动

经过上面两个步骤了之后，刚刚创建的viewPager就具备自动滚动的功能了。

* 额外说明：

如果需要在ScrollView或是ListView中使用时，会有事件的冲突，只需加上这么一句代码即可：

	viewpager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);

#### 其他方法说明

	setCycle(boolean isCycle)：是否循环滚动，默认为true
	setStopScrollWhenTouch(boolean stopScrollWhenTouch):当手指触摸时，是否停止滚动，默认true
	setDirection(int direction)：设置自动滚动的方向，有LEFT与 RIGHT, 默认是向右
	