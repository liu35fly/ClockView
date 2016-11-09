package com.example.administrator.clockview.recycle_item_animator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.clockview.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleAnimatorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> list;
    private AnimatorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_animator);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new MyAnimator());
        initData();
        clik();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i + " 项");
        }
        adapter = new AnimatorAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
    private void clik(){
        Button button=(Button)findViewById(R.id.main_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(2,"codekk");
                adapter.notifyItemInserted(2);

            }
        });
    }
}
