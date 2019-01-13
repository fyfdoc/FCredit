package com.fcredit.ui.swiperecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fcredit.R;

/**
 * 刷卡记录
 */
public class SwipeRecordListFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"操作","序号","卡名","卡号","银行","刷卡日期", "金额", "商户", "备注"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_record_list, container, false);

        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.swipeRecordTable);
        // 添加按钮
        TextView recordAdd = view.findViewById(R.id.record_add);

        // 添加按钮事件
        recordAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SwipeRecordAddActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
