package com.fcredit.ui.creditmang;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fcredit.R;
import com.fcredit.ui.creditinfo.AddCreditInfoActivity;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.AppUtil;
import com.fcredit.util.DBHelper;
import com.fcredit.widget.table.TableCellTextView;

import java.math.BigDecimal;

/**
 * 贷款管理
 */
public class CreditMangFragment extends Fragment {

    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName = {"操作", "序号", "贷款名称", "银行", "还款日", "每月应还(元)", "利率", "贷款总额(万)"
            , "贷款期限(月)", "提款日期", "本期截止日", "贷款有效期(开始)", "贷款有效期(结束)", "备注"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credit_mang, container, false);

        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.creditMangTable);
        // 添加按钮
        TextView addCreditTv = view.findViewById(R.id.credit_mang_add);

        // 添加按钮事件
        addCreditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), CreditMangAddActivity.class);
                startActivity(intent);
            }

        });

        // 初始化数据
        initData();

        return view;
    }

    /**
     * 初始化数据
     */
    private void initData()
    {
        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //初始化标题,
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_credit_mang_list, null);
        // 操作
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_opt);
        title.setText(ColName[0]);

        // 序号
        title =  cellLineLayout.findViewById(R.id.list_no);
        title.setText(ColName[1]);

        // 贷款名称
        title = cellLineLayout.findViewById(R.id.list_credit_name);
        title.setText(ColName[2]);

        // 银行
        title =  cellLineLayout.findViewById(R.id.list_bank);
        title.setText(ColName[3]);

        // 还款日
        title =  cellLineLayout.findViewById(R.id.list_repayment_date);
        title.setText(ColName[4]);

        // 每月应还
        title =  cellLineLayout.findViewById(R.id.list_repay_month);
        title.setText(ColName[5]);

        // 利率
        title =  cellLineLayout.findViewById(R.id.list_interest_rate);
        title.setText(ColName[6]);

        // 贷款总额
        title =  cellLineLayout.findViewById(R.id.list_credit_limit);
        title.setText(ColName[7]);

        // 贷款期限(月)
        title =  cellLineLayout.findViewById(R.id.list_credit_length);
        title.setText(ColName[8]);

        // 提款日期
        title =  cellLineLayout.findViewById(R.id.list_draw_date);
        title.setText(ColName[9]);

        // 本期截止日
        title =  cellLineLayout.findViewById(R.id.list_current_end_date);
        title.setText(ColName[10]);

        // 贷款有效期(开始)
        title =  cellLineLayout.findViewById(R.id.list_credit_length_start_date);
        title.setText(ColName[11]);

        // 贷款有效期(结束)
        title =  cellLineLayout.findViewById(R.id.list_credit_length_end_date);
        title.setText(ColName[12]);

        // 备注
        title =  cellLineLayout.findViewById(R.id.list_comment);
        title.setText(ColName[13]);


        cellLayout.addView(cellLineLayout);

        BigDecimal totalRepayMonth = new BigDecimal(0);
        BigDecimal totalCreditLimit = new BigDecimal(0);
        int number = 1;
        //数据库检索
        String strSQL = "SELECT *"
                + " FROM t_credit_mang"
                + " ORDER BY repayment_date ASC";

        Cursor cursor = db.rawQuery(strSQL, null);
        while (cursor.moveToNext())
        {
            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_credit_mang_list, null);
            if (number % 2 == 1)
            {
                cellLineLayout.setBackgroundColor(getResources().getColor(R.color.white_table_line));
            }
            // 操作
            TableCellTextView optTxt = cellLineLayout.findViewById(R.id.list_opt);
            String tmpVal = String.valueOf("编辑");
            optTxt.setText(tmpVal);
            optTxt.setTextColor(getResources().getColor(R.color.opt_item_color));
            // 序号
            TableCellTextView  txt = cellLineLayout.findViewById(R.id.list_no);
            tmpVal = String.valueOf(number);
            txt.setText(tmpVal);
            // id
            txt =  cellLineLayout.findViewById(R.id.list_id);
            tmpVal = cursor.getString(cursor.getColumnIndex("id"));
            txt.setText(tmpVal);
            // 贷款名称
            txt =  cellLineLayout.findViewById(R.id.list_credit_name);
            tmpVal = cursor.getString(cursor.getColumnIndex("credit_name"));
            txt.setText(tmpVal);
            // 银行
            txt =  cellLineLayout.findViewById(R.id.list_bank);
            tmpVal = cursor.getString(cursor.getColumnIndex("bank_name"));
            txt.setText(tmpVal);
            // 还款日
            txt =  cellLineLayout.findViewById(R.id.list_repayment_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("repayment_date")));
            // 每月应还
            txt =  cellLineLayout.findViewById(R.id.list_repay_month);
            String strVal = cursor.getString(cursor.getColumnIndex("repay_month"));
            txt.setText(strVal);
            totalRepayMonth = totalRepayMonth.add(AppUtil.string2BigDecimal(strVal));
            // 保留2位小数
            totalRepayMonth = totalRepayMonth.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);
            // 利率
            txt =  cellLineLayout.findViewById(R.id.list_interest_rate);
            txt.setText(cursor.getString(cursor.getColumnIndex("interest_rate")));
            // 贷款总额
            txt =  cellLineLayout.findViewById(R.id.list_credit_limit);
            strVal = cursor.getString(cursor.getColumnIndex("credit_limit"));
            txt.setText(strVal);
            totalCreditLimit = totalCreditLimit.add(AppUtil.string2BigDecimal(strVal));
            // 保留2位小数
            totalCreditLimit = totalCreditLimit.divide(new BigDecimal(1),2, BigDecimal.ROUND_HALF_UP);
            // 贷款期限(月)
            txt =  cellLineLayout.findViewById(R.id.list_credit_length);
            txt.setText(cursor.getString(cursor.getColumnIndex("credit_length")));
            // 提款日期
            txt =  cellLineLayout.findViewById(R.id.list_draw_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("draw_date")));
            // 本期截止日
            txt =  cellLineLayout.findViewById(R.id.list_current_end_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("current_end_date")));
            // 贷款有效期(开始)
            txt =  cellLineLayout.findViewById(R.id.list_credit_length_start_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("credit_length_start_date")));
            // 贷款有效期(结束)
            txt =  cellLineLayout.findViewById(R.id.list_credit_length_end_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("credit_length_end_date")));
            // 备注
            txt =  cellLineLayout.findViewById(R.id.list_comment);
            txt.setText(cursor.getString(cursor.getColumnIndex("comment")));

            cellLayout.addView(cellLineLayout);
            number++;

            // 编辑点击事件
            optTxt.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    RelativeLayout line = (RelativeLayout) view.getParent();
                    TextView idTxtV = line.findViewById(R.id.list_id);
                    String strId = idTxtV.getText().toString();

                    Intent intent = new Intent(getActivity(), CreditMangAddActivity.class);

                    // 传参
                    Bundle bundle = new Bundle();
                    bundle.putString("id", strId);
                    intent.putExtra("bundle",bundle);

                    startActivity(intent);

                }
            });

        }// end while

        // 合计行
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_credit_mang_list, null);
        // 合计
        TableCellTextView optTxtTotal = cellLineLayout.findViewById(R.id.list_opt);
        String tmpVal = String.valueOf("合计");
        optTxtTotal.setText(tmpVal);
        // 序号
        TableCellTextView txt = cellLineLayout.findViewById(R.id.list_no);
        tmpVal = String.valueOf("");
        txt.setText(tmpVal);
        // 贷款名称
        txt =  cellLineLayout.findViewById(R.id.list_credit_name);
        tmpVal = "";
        txt.setText(tmpVal);
        // 银行
        txt =  cellLineLayout.findViewById(R.id.list_bank);
        tmpVal = "";
        txt.setText(tmpVal);
        // 还款日
        txt =  cellLineLayout.findViewById(R.id.list_repayment_date);
        txt.setText("");
        // 每月应还
        txt =  cellLineLayout.findViewById(R.id.list_repay_month);
        txt.setText(String.valueOf(totalRepayMonth));
        // 利率
        txt =  cellLineLayout.findViewById(R.id.list_interest_rate);
        txt.setText("");
        // 贷款总额
        txt =  cellLineLayout.findViewById(R.id.list_credit_limit);
        txt.setText(String.valueOf(totalCreditLimit));
        // 贷款期限(月)
        txt =  cellLineLayout.findViewById(R.id.list_credit_length);
        txt.setText("");
        // 提款日期
        txt =  cellLineLayout.findViewById(R.id.list_draw_date);
        txt.setText("");
        // 本期截止日
        txt =  cellLineLayout.findViewById(R.id.list_current_end_date);
        txt.setText("");
        // 贷款有效期(开始)
        txt =  cellLineLayout.findViewById(R.id.list_credit_length_start_date);
        txt.setText("");
        // 贷款有效期(结束)
        txt =  cellLineLayout.findViewById(R.id.list_credit_length_end_date);
        txt.setText("");
        // 备注
        txt =  cellLineLayout.findViewById(R.id.list_comment);
        txt.setText("");

        cellLayout.addView(cellLineLayout);
    }

}
