package com.example.administrator.clockview.recycle_item_animator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose     :
 * Description :
 * Author      : FLY
 * Date        : 2016.10.08 10:58
 */

public class AnimatorAdapter extends RecyclerView.Adapter<AnimatorAdapter.MyHolder> {
    private List<String> list = new ArrayList<>();
    private Context context;

    public AnimatorAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(new TextView(context));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
