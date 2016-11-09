package com.example.administrator.clockview.recycle_item_animator;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Purpose     :
 * Description :
 * Author      : FLY
 * Date        : 2016.10.08 11:45
 */

public class MyAnimator extends BaseItemAnimator {
    @Override
    protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, -holder.itemView.getRootView().getWidth());
    }

    @Override
    protected void animateAddImpl(RecyclerView.ViewHolder holder) {
        View view = holder.itemView;
        view.setBackgroundColor(Color.CYAN);
        ViewCompat.animate(holder.itemView)
                .translationX(holder.itemView.getRootView().getWidth())
                .setDuration(4000)
                .setStartDelay(300)
                .setListener(new DefaultAddVpaListener(holder))
                .start();
    }
}
