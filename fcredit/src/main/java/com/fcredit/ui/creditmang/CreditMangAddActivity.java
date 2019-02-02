package com.fcredit.ui.creditmang;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fcredit.R;
import com.fcredit.ui.creditinfo.AddCreditInfoFragment;

/**
 * 贷款管理
 */
public class CreditMangAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        // 获取参数
        Bundle bundle = intent.getBundleExtra("bundle");

        setContentView(R.layout.activity_credit_mang_add);

        // 当前Fragment
        CreditMangAddFragment fragment = (CreditMangAddFragment) getSupportFragmentManager().findFragmentById(R.id.addCreditMangFragment);
        if (bundle != null)
        {
            // 传递参数
            fragment.setArguments(bundle);
            fragment.initData();
        }

    }

}
