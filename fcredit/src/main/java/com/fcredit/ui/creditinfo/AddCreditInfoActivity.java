package com.fcredit.ui.creditinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fcredit.R;

public class AddCreditInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        // 获取参数
        Bundle bundle = intent.getBundleExtra("bundle");

        setContentView(R.layout.activity_add_credit_info);

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
