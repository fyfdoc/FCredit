package com.fcredit.ui.creditinfo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.fcredit.util.AppConstaint;
import com.fcredit.util.DBHelper;
import com.fcredit.widget.table.TableCellTextView;

import butterknife.OnClick;

/**
 * Credit基本信息
 */
public class CreditInfoFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"操作","序号","卡名","额度", "账单日", "还款日","卡号","银行", "备注"};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_info, container, false);
        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.creditInfoTable);
       // 添加按钮
        TextView addCreditTv = view.findViewById(R.id.credit_add);

        // 添加按钮事件
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
        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //初始化标题,
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_credit_info, null);
        // 操作
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_opt);
        title.setText(ColName[0]);

        // 序号
        title =  cellLineLayout.findViewById(R.id.list_no);
        title.setText(ColName[1]);

        // 卡名
        title = cellLineLayout.findViewById(R.id.list_card_name);
        title.setText(ColName[2]);

        // 额度
        title =  cellLineLayout.findViewById(R.id.list_limit);
        title.setText(ColName[3]);

        // 账单日
        title =  cellLineLayout.findViewById(R.id.list_statement_date);
        title.setText(ColName[4]);

        // 还款日
        title =  cellLineLayout.findViewById(R.id.list_repayment_date);
        title.setText(ColName[5]);

        // 卡号
        title =  cellLineLayout.findViewById(R.id.list_card_no);
        title.setText(ColName[6]);

        // 银行
        title =  cellLineLayout.findViewById(R.id.list_bank);
        title.setText(ColName[7]);

        // 备注
        title =  cellLineLayout.findViewById(R.id.list_comment);
        title.setText(ColName[8]);


        cellLayout.addView(cellLineLayout);

        int totalLimit = 0;
        int number = 1;
        //数据库检索
        Cursor cursor = db.query("t_credit_info"
                ,new String[]{"id", "credit_name","credit_no","bank_name","credit_limit", "statement_date","repayment_date", "credit_comment"}
                ,null,null, null,null, new String("statement_date ASC"),null);
        while (cursor.moveToNext())
        {
            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_credit_info, null);
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
            // 卡名
            txt =  cellLineLayout.findViewById(R.id.list_card_name);
            tmpVal = cursor.getString(cursor.getColumnIndex("credit_name"));
            txt.setText(tmpVal);
            // 卡号
            txt =  cellLineLayout.findViewById(R.id.list_card_no);
            tmpVal = cursor.getString(cursor.getColumnIndex("credit_no"));
            txt.setText(tmpVal);
            // 银行
            txt =  cellLineLayout.findViewById(R.id.list_bank);
            txt.setText(cursor.getString(cursor.getColumnIndex("bank_name")));
            // 额度
            txt =  cellLineLayout.findViewById(R.id.list_limit);
            txt.setText(cursor.getString(cursor.getColumnIndex("credit_limit")));
            totalLimit += Integer.parseInt(cursor.getString(cursor.getColumnIndex("credit_limit")));
            // 账单日
            txt =  cellLineLayout.findViewById(R.id.list_statement_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("statement_date")));
            // 还款日
            txt =  cellLineLayout.findViewById(R.id.list_repayment_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("repayment_date")));
            // 备注
            txt =  cellLineLayout.findViewById(R.id.list_comment);
            txt.setText(cursor.getString(cursor.getColumnIndex("credit_comment")));

            cellLayout.addView(cellLineLayout);
            number++;

            // 编辑点击事件
            optTxt.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    RelativeLayout line = (RelativeLayout) view.getParent();
                    TextView idTxtV = line.findViewById(R.id.list_id);
                    String strId = idTxtV.getText().toString();

                    Intent intent = new Intent(getActivity(), AddCreditInfoActivity.class);

                    // 传参
                    Bundle bundle = new Bundle();
                    bundle.putString("id", strId);
                    intent.putExtra("bundle",bundle);

                    startActivity(intent);

                }
            });

        }// end while

        // 合计行
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_credit_info, null);
        // 合计
        TableCellTextView optTxtTotal = cellLineLayout.findViewById(R.id.list_opt);
        String tmpVal = String.valueOf("合计");
        optTxtTotal.setText(tmpVal);
        // 序号
        TableCellTextView txt = cellLineLayout.findViewById(R.id.list_no);
        tmpVal = String.valueOf("");
        txt.setText(tmpVal);
        // 卡名
        txt =  cellLineLayout.findViewById(R.id.list_card_name);
        tmpVal = "";
        txt.setText(tmpVal);
        // 卡号
        txt =  cellLineLayout.findViewById(R.id.list_card_no);
        tmpVal = "";
        txt.setText(tmpVal);
        // 银行
        txt =  cellLineLayout.findViewById(R.id.list_bank);
        txt.setText("");
        // 额度
        txt =  cellLineLayout.findViewById(R.id.list_limit);
        txt.setText(String.valueOf(totalLimit));
        // 账单日
        txt =  cellLineLayout.findViewById(R.id.list_statement_date);
        txt.setText("");
        // 还款日
        txt =  cellLineLayout.findViewById(R.id.list_repayment_date);
        txt.setText("");
        // 备注
        txt =  cellLineLayout.findViewById(R.id.list_comment);
        txt.setText("");

        cellLayout.addView(cellLineLayout);

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
