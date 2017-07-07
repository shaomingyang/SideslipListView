package com.ming.shao.sidesliplistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.ming.shao.sidesliplistview.R;
import com.ming.shao.sidesliplistview.adapter.SwipeLayoutAdapter;

/**
 * Created by smy on 2017/6/14 0014.
 */

public class SwipeLayoutActivity extends Activity {
    ListView listview;
    SwipeLayoutAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_layout);
        initView();
    }

    private void initView() {
        adapter = new SwipeLayoutAdapter(this);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }

}
