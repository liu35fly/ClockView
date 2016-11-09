package com.example.administrator.clockview.filechoose;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.clockview.R;

/**
 * 文件选择视图
 */
public class FileChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choose);
        init();
    }
    private void init(){
        FileChooseView view=(FileChooseView)findViewById(R.id.file_choose);
        view.setFileList(Environment.getExternalStorageDirectory().getPath());
    }
}
