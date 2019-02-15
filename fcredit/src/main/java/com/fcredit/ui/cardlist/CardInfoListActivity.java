package com.fcredit.ui.cardlist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.fcredit.R;
import com.fcredit.widget.textview.AlwaysCenterTextView;

/**
 * 卡片信息选择
 */
public class CardInfoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info_list);

        View view = getWindow().getDecorView();

        // 设置标题
        AlwaysCenterTextView backToolTitle = view.findViewById(R.id.item_title);
        backToolTitle.setText(R.string.card_select_list);

        // 返回按钮
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
