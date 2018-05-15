package com.xylitolz.androidsuspensionbardemo;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xylitolz.androidsuspensionbardemo.RvContainerAdapter.TYPE_IMAGE;
import static com.xylitolz.androidsuspensionbardemo.RvContainerAdapter.TYPE_TAB;
import static com.xylitolz.androidsuspensionbardemo.RvContainerAdapter.TYPE_TEXT;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_container)
    RecyclerView rvContainer;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sub_title)
    TextView tvSubTitle;
    @BindView(R.id.tv_sub_title2)
    TextView tvSubTitle2;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.tl_suspension)
    BindTabLayout tlSuspension;


    private RvContainerAdapter adapter;
    private float scrollRatio = 0;//滑动系数,初始设置为0
    private LinearLayoutManager linearLayoutManager;
    private int startColor = 0x600873d2;
    private int endColor = 0xff0873d2;
    private final int animationHeight = 60;

    private int subTitleStatus = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        processLogic();
    }

    private void initView() {
        if(Build.VERSION.SDK_INT >= 21) {
            //Android5.0以上，设置状态栏透明并且全屏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvContainer.setLayoutManager(linearLayoutManager);
        adapter = new RvContainerAdapter(this,tlSuspension);
        rvContainer.setAdapter(adapter);

        tlSuspension.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(int i = 0;i < 10;i++) {
            TabLayout.Tab tab = tlSuspension.newTab().setText("Tab"+i);
            tlSuspension.addTab(tab);
        }

        initTitleBar();
        initListener();
        setUpRatio();
    }

    /**
     * 给RecyclerView增加滑动监听
     */
    private void initListener() {
        rvContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisible = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if(firstVisible > 0) {
                    //滑动到第二个item及之后item，将滑动系数设置为1
                    scrollRatio = 1;
                } else {
                    View view = linearLayoutManager.findViewByPosition(0);//获取第一item
                    int verticalOffset = recyclerView.computeVerticalScrollOffset();//获取第一个item纵向滑动距离
                    float viewHeight = view.getMeasuredHeight() - rlTitleBar.getMeasuredHeight();//获取滑动距离
                    scrollRatio = verticalOffset / viewHeight;

                    if(verticalOffset > animationHeight) {
                        startAnimator(1);
                    } else {
                        startAnimator(0);
                    }
                }
                setUpRatio();
                updateSuspension();
            }
        });
    }

    /**
     * 更新悬浮条
     */
    private void updateSuspension() {
        if(scrollRatio >= 1) {
            //显示悬浮条
            tlSuspension.setVisibility(View.VISIBLE);
        } else {
            //隐藏悬浮条
            tlSuspension.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 开始副标题动画
     */
    private void startAnimator(int subTitleStatus) {
        if(this.subTitleStatus == subTitleStatus) {
            return;
        }
        this.subTitleStatus = subTitleStatus;
        if(subTitleStatus == 0) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvSubTitle, "y", (float) -tvSubTitle.getMeasuredHeight(),0f);
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(tvSubTitle2, "y",0f , (float) tvSubTitle2.getMeasuredHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.play(objectAnimator).with(objectAnimator2);
            animatorSet.start();

        } else {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvSubTitle, "y", 0f, (float) -tvSubTitle.getMeasuredHeight());
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(tvSubTitle2, "y", (float) tvSubTitle2.getMeasuredHeight(),0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.play(objectAnimator).with(objectAnimator2);
            animatorSet.start();
        }
    }

    /**
     * 为rlTitleBar设置滑动系数
     */
    private void setUpRatio() {
        if(scrollRatio < 0 || scrollRatio >= 1) {
            rlTitleBar.setBackgroundColor(endColor);
        } else {
            ArgbEvaluator argbEvaluator = new ArgbEvaluator();
            int color = (int) argbEvaluator.evaluate(scrollRatio,startColor,endColor);
            rlTitleBar.setBackgroundColor(color);
        }
    }

    /**
     * 设置TitleBar状态
     */
    private void initTitleBar() {
        if(Build.VERSION.SDK_INT >= 21) {
            //Android5.0以上，由于状态栏透明了，所以给TitleBar设置一个topPadding，高度为statusBar高度
            ViewGroup.LayoutParams params = rlTitleBar.getLayoutParams();
            params.height = params.height + getStatusBarHeight(this);
            rlTitleBar.setPadding(0,getStatusBarHeight(this),0,0);
        }
    }

    private void processLogic() {
        List<RvEntity> data = new ArrayList<>();
        data.add(new RvEntity(TYPE_IMAGE));
        data.add(new RvEntity(TYPE_TAB));
        for(int i = 0;i < 20;i++) {
            data.add(new RvEntity(TYPE_TEXT));
        }

        adapter.setData(data);
    }

    /**
     * 根据反射获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
