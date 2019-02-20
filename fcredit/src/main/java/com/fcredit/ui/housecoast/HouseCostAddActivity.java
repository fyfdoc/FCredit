package com.fcredit.ui.housecoast;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.fcredit.R;
import com.fcredit.ui.swiperecord.SwipeRecordAddFragment;
import com.fcredit.widget.textview.AlwaysCenterTextView;

public class HouseCostAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_cost_add);


        Intent intent = getIntent();
        // 获取参数
        Bundle bundle = intent.getBundleExtra("bundle");

        View view = getWindow().getDecorView();

        // 设置标题
        AlwaysCenterTextView backToolTitle = view.findViewById(R.id.item_title);
        backToolTitle.setText(R.string.house_cost_add_title);

        // 返回按钮
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 当前Fragment
        HouseCostAddFragment fragment = (HouseCostAddFragment) getSupportFragmentManager().findFragmentById(R.id.addHouseCostAddFragment);
        if (bundle != null)
        {
            // 传递参数
            fragment.setArguments(bundle);
            fragment.initData();
        }
    }

}
