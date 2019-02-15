package com.fcredit.ui.housecoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fcredit.R;
import com.fcredit.widget.textview.AlwaysCenterTextView;

public class HouseCoastListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_house_coast_list);

        View view = getWindow().getDecorView();

        // 设置标题
        AlwaysCenterTextView backToolTitle = view.findViewById(R.id.item_title);
        backToolTitle.setText(R.string.house_coast_list);

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
