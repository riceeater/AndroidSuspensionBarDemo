package com.xylitolz.androidsuspensionbardemo;

import android.view.MotionEvent;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc 绑定两个TabLayout的触摸事件
 * @date 2018-05-15 15:27
 */
public interface TabTouchListener {
    void dispatchBindEvent(MotionEvent event);

    void setListener(TabTouchListener listener);
}
