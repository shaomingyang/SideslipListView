package com.ming.shao.sidesliplistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ming.shao.sidesliplistview.activity.AnumationActivity;
import com.ming.shao.sidesliplistview.activity.BezierActivity;
import com.ming.shao.sidesliplistview.activity.CurveViewActivity;
import com.ming.shao.sidesliplistview.activity.DynamicTextViewActivity;
import com.ming.shao.sidesliplistview.activity.HuaWeiActivity;
import com.ming.shao.sidesliplistview.activity.RippleAnimationActivity;
import com.ming.shao.sidesliplistview.activity.SimpleLineActivity;
import com.ming.shao.sidesliplistview.activity.SwipeLayoutActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    Button line_but;
    Button dynamic_but;
    Button swipe_layout_but;
    Button hua_wei_but;
    Button cure_view_but;
    Button bezier_view_but;
    Button animstion_view_but;

    Button animstion_ripple_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        line_but = (Button) this.findViewById(R.id.line_but);
        dynamic_but = (Button) this.findViewById(R.id.dynamic_but);
        swipe_layout_but = (Button) this.findViewById(R.id.swipe_layout_but);
        hua_wei_but = (Button) this.findViewById(R.id.hua_wei_but);
        cure_view_but = (Button) this.findViewById(R.id.cure_view_but);
        bezier_view_but = (Button) this.findViewById(R.id.bezier_view_but);
        animstion_view_but = (Button) this.findViewById(R.id.animstion_view_but);
        animstion_ripple_but = (Button) this.findViewById(R.id.animstion_ripple_but);
    }

    private void initListener() {
        line_but.setOnClickListener(this);
        dynamic_but.setOnClickListener(this);
        swipe_layout_but.setOnClickListener(this);
        hua_wei_but.setOnClickListener(this);
        cure_view_but.setOnClickListener(this);
        bezier_view_but.setOnClickListener(this);
        animstion_view_but.setOnClickListener(this);
        animstion_ripple_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.line_but:
                startActivity(new Intent(MainActivity.this, SimpleLineActivity.class));
                break;
            case R.id.dynamic_but:
                startActivity(new Intent(MainActivity.this, DynamicTextViewActivity.class));
                break;
            case R.id.swipe_layout_but:
                startActivity(new Intent(MainActivity.this, SwipeLayoutActivity.class));
                break;
            case R.id.hua_wei_but:
                startActivity(new Intent(MainActivity.this, HuaWeiActivity.class));
                break;
            case R.id.cure_view_but:
                startActivity(new Intent(MainActivity.this, CurveViewActivity.class));
                break;
            case R.id.bezier_view_but:
                startActivity(new Intent(MainActivity.this, BezierActivity.class));
                break;
            case R.id.animstion_view_but:
                startActivity(new Intent(MainActivity.this, AnumationActivity.class));
                break;
            case R.id.animstion_ripple_but:
                startActivity(new Intent(MainActivity.this, RippleAnimationActivity.class));
                break;
        }
    }
}
