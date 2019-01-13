package com.fcredit.ui.swiperecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fcredit.R;
import com.fcredit.ui.creditinfo.AddCreditInfoFragment;

public class SwipeRecordAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        // 获取参数
        Bundle bundle = intent.getBundleExtra("bundle");

        setContentView(R.layout.activity_swipe_record_add);

        // 当前Fragment
        SwipeRecordAddFragment fragment = (SwipeRecordAddFragment) getSupportFragmentManager().findFragmentById(R.id.addCreditFragment);
        if (bundle != null)
        {
            // 传递参数
            fragment.setArguments(bundle);
            fragment.initData();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
