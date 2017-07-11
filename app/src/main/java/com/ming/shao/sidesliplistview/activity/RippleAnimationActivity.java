package com.ming.shao.sidesliplistview.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ming.shao.sidesliplistview.R;
import com.ming.shao.sidesliplistview.widget.RippleCircleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/7/11 0011.
 * 波纹动画效果
 */

public class RippleAnimationActivity extends Activity {
    RippleCircleView circleView;
    RippleCircleView circleView2;
    RippleCircleView circleView3;

    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple_animation);
        circleView = (RippleCircleView) findViewById(R.id.circleView);
        circleView2 = (RippleCircleView) findViewById(R.id.circleView2);
        circleView3 = (RippleCircleView) findViewById(R.id.circleView3);


        animationButton();
    }

    private void animationButton() {
        List<RippleCircleView> circleViewList = new ArrayList<>();
        circleViewList.add(circleView);
        circleViewList.add(circleView2);
        circleViewList.add(circleView3);
        //分析该动画后将其拆分为缩放、渐变
        ArrayList<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < circleViewList.size(); i++) {
            //动画集合
            animatorSet = new AnimatorSet();
            //ScaleX缩放
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(circleViewList.get(i), "ScaleX", 1.0f, 6f);
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * 1000);
            scaleXAnimator.setDuration(2000);
            animatorList.add(scaleXAnimator);
            //ScaleY缩放
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(circleViewList.get(i), "ScaleY", 1.0f, 6f);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * 1000);
            scaleYAnimator.setDuration(2000);
            animatorList.add(scaleYAnimator);
            //Alpha渐变
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(circleViewList.get(i), "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * 1000);
            alphaAnimator.setDuration(2000);
            animatorList.add(alphaAnimator);


        }

        animatorSet.playTogether(animatorList);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }
}
