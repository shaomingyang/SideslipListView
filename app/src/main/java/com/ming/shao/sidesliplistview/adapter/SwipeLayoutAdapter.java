package com.ming.shao.sidesliplistview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ming.shao.sidesliplistview.ProjectAdapter;
import com.ming.shao.sidesliplistview.R;

/**
 * Created by smy on 2017/6/15 0015.
 */

public class SwipeLayoutAdapter extends ProjectAdapter {

    public SwipeLayoutAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_swipe_layout, null);
        }
        return convertView;
    }
}
