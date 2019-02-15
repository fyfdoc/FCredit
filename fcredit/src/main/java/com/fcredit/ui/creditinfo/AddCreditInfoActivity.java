package com.fcredit.ui.creditinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.fcredit.R;
import com.fcredit.widget.textview.AlwaysCenterTextView;

public class AddCreditInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        // 获取参数
        Bundle bundle = intent.getBundleExtra("bundle");

        setContentView(R.layout.activity_add_credit_info);

        View view = getWindow().getDecorView();

        // 设置标题
        AlwaysCenterTextView backToolTitle = view.findViewById(R.id.item_title);
        backToolTitle.setText(R.string.card_add_title);

        // 返回按钮
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // 当前Fragment
        AddCreditInfoFragment fragment = (AddCreditInfoFragment) getSupportFragmentManager().findFragmentById(R.id.addCreditFragment);
        if (bundle != null)
        {
            // 传递参数
            fragment.setArguments(bundle);
            fragment.initData();
        }


    }

}
