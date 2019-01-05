package com.fcredit.ui.creditinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fcredit.R;
import com.fcredit.widget.table.TableCellTextView;

import butterknife.OnClick;

/**
 * Credit基本信息
 */
public class CreditInfoFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"序号","卡明","卡号","银行","额度", "账单日", "还款日"};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_info, container, false);
        final int containerId = container.getId();
        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.creditInfoTable);
       // 添加按钮
        TextView addCreditTv = view.findViewById(R.id.credit_add);

        addCreditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                AddCreditInfoFragment addCreditInfoFragment = new AddCreditInfoFragment();
                transaction.replace(containerId, addCreditInfoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
*/
                Intent intent = new Intent(getActivity(), AddCreditInfoActivity.class);
                startActivity(intent);
            }

        });


        // 初始化数据
        initData();

        return view;
    }

    private void initData()
    {
        //初始化标题,
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_credit_info, null);
        // "序号"
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_1_1);
        title.setText(ColName[0]);
        title.setTextColor(Color.BLUE);
        // "卡名"
        title = cellLineLayout.findViewById(R.id.list_1_2);
        title.setText(ColName[1]);
        title.setTextColor(Color.BLUE);
        // "卡号"
        title =  cellLineLayout.findViewById(R.id.list_1_3);
        title.setText(ColName[2]);
        title.setTextColor(Color.BLUE);
        // "银行"
        title =  cellLineLayout.findViewById(R.id.list_1_4);
        title.setText(ColName[3]);
        title.setTextColor(Color.BLUE);
        // "额度"
        title =  cellLineLayout.findViewById(R.id.list_1_5);
        title.setText(ColName[4]);
        title.setTextColor(Color.BLUE);
        // "账单日"
        title =  cellLineLayout.findViewById(R.id.list_1_6);
        title.setText(ColName[5]);
        title.setTextColor(Color.BLUE);
        // "还款日"
        title =  cellLineLayout.findViewById(R.id.list_1_7);
        title.setText(ColName[6]);
        title.setTextColor(Color.BLUE);

        cellLayout.addView(cellLineLayout);

        //初始化内容
        int number = 1;
        for (int i=0; i < 10; i++) {
            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_credit_info, null);
            // "序号"
            TableCellTextView txt = cellLineLayout.findViewById(R.id.list_1_1);
            String tmpVal = String.valueOf(number);
            txt.setText(tmpVal);
            // "卡名"
            txt =  cellLineLayout.findViewById(R.id.list_1_2);
            txt.setText("FYF建行" +  String.valueOf(number));
            // "卡号"
            txt =  cellLineLayout.findViewById(R.id.list_1_3);
            int tmpInt = 10 + number;
            txt.setText("6666 6666 6666 6666" + String.valueOf(tmpInt));
            // "银行"
            txt =  cellLineLayout.findViewById(R.id.list_1_4);
            txt.setText("建行");
            // "额度"
            txt =  cellLineLayout.findViewById(R.id.list_1_5);
            txt.setText("50000");
            // "账单日"
            txt =  cellLineLayout.findViewById(R.id.list_1_6);
            txt.setText("07");
            // "还款日"
            txt =  cellLineLayout.findViewById(R.id.list_1_7);
            txt.setText("25");

            cellLayout.addView(cellLineLayout);
            number++;
        }

    }

    @OnClick(R.id.credit_add)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.credit_add:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                AddCreditInfoFragment addCreditInfoFragment = new AddCreditInfoFragment();
                transaction.replace(R.id.viewpager, addCreditInfoFragment);
                transaction.commit();

                break;
        }

    }



}
