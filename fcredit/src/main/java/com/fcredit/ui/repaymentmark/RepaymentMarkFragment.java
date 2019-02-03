package com.fcredit.ui.repaymentmark;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fcredit.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 还款标记
 */
public class RepaymentMarkFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"标记", "序号","卡名/贷款名", "还款日", "本期应还", "卡号", "银行"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repayment_mark, container, false);


        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.repaymentMarkTable);

        // 今日
        TextView tvToday = view.findViewById(R.id.today);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar toDayCal = Calendar.getInstance();
        String strToday = df.format(toDayCal.getTime());
        tvToday.setText("今天:" + strToday);

        // 初始化数据
        initData();

        return view;
    }

    private void initData()
    {

    }

}
