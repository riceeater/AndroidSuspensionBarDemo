package com.xylitolz.androidsuspensionbardemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc 首页RecyclerView适配器
 * @date 2018-05-15 13:47
 */
public class RvContainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int TYPE_IMAGE = 0;
    public static int TYPE_TAB = 1;
    public static int TYPE_TEXT = 2;

    private List<RvEntity> data;
    private Context context;
    private TabTouchListener listener;

    public RvContainerAdapter(Context context,TabTouchListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<RvEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_image,parent,false);
            return new ImageViewHolder(view);
        } else if(viewType == TYPE_TAB) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_tab,parent,false);
            return new TabViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_text,parent,false);
            return new TextViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == TYPE_IMAGE) {

        } else if(viewType == TYPE_TAB) {
            ((TabViewHolder)holder).bind(listener);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class TabViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tl_item)
        BindTabLayout tlItem;
        @BindView(R.id.tv_notice)
        TextView tvNotice;

        private TabLayout.OnTabSelectedListener onTabSelectedListener;

        public TabViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tvNotice.setText("选中Tab:"+tab.getText());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            };
            tlItem.addOnTabSelectedListener(onTabSelectedListener);
        }

        public void bind(TabTouchListener listener) {
            tlItem.setListener(listener);
            listener.setListener(tlItem);

            tlItem.setTabMode(TabLayout.MODE_SCROLLABLE);
            for(int i = 0;i < 10;i++) {
                TabLayout.Tab tab = tlItem.newTab().setText("Tab"+i);
                tlItem.addTab(tab);
            }
            tlItem.setTabWidth(PhoneUtil.dipToPixels(50,tlItem.getContext()) * 10,14,0,0);

        }
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        public TextViewHolder(View itemView) {
            super(itemView);
        }
    }
}
