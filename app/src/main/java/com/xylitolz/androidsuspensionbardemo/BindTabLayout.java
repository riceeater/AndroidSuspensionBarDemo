package com.xylitolz.androidsuspensionbardemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

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
}
