package com.xylitolz.androidsuspensionbardemo;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc
 * @date 2018-05-15 15:13
 */
public class BindTabLayout extends TabLayout implements TabTouchListener{

    private TabTouchListener bindLisnter;

    public BindTabLayout(Context context) {
        super(context);
    }

    public BindTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BindTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(bindLisnter != null) {
            bindLisnter.dispatchBindEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void dispatchBindEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
    }

    @Override
    public void setListener(TabTouchListener listener) {
        this.bindLisnter = listener;
    }

    public void setTabWidth(int tabLayoutWidth, int textSize, int leftRemain,
                              int rightRemain) {
        setTabWidth(this,this.getClass().getSuperclass(),tabLayoutWidth,textSize,leftRemain,rightRemain,getContext());
    }

    private void setTabWidth(TabLayout tabs,Class<?> tabLayout, int tabLayoutWidth, int textSize, int leftRemain,
                                   int rightRemain,Context context) {
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Paint paint = new Paint();
        paint.setTextSize(PhoneUtil.sp2px(textSize,context));
        float tabWidth = tabLayoutWidth / tabs.getTabCount();

        for (int i = 0; i < llTab.getChildCount(); i++) {

            TabLayout.Tab tab = tabs.getTabAt(i);
            float textWidth = paint.measureText(tab.getText().toString());
            int left = (int) ((tabWidth - textWidth) / 2) - PhoneUtil.dipToPixels(leftRemain,context);
            int right = (int) ((tabWidth - textWidth) / 2) - PhoneUtil.dipToPixels(rightRemain,context);
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(textWidth + PhoneUtil.dipToPixels(leftRemain + rightRemain,context)),
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
