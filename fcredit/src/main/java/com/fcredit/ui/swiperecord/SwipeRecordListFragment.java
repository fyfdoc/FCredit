package com.fcredit.ui.swiperecord;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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
import com.fcredit.util.DBHelper;
import com.fcredit.widget.table.TableCellTextView;

import java.math.BigDecimal;

/**
 * 刷卡记录
 */
public class SwipeRecordListFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"操作","序号","卡名","刷卡日期", "金额", "商户","卡号","银行", "备注"};

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

        initData();

        // Inflate the layout for this fragment
        return view;
    }


    /**
     * 初始化数据
     */
    private void initData()
    {
        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //初始化标题,
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_swipe_record, null);
        // 操作
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_opt);
        title.setText(ColName[0]);
        title.setTextColor(Color.BLUE);
        // 序号
        title =  cellLineLayout.findViewById(R.id.list_no);
        title.setText(ColName[1]);
        title.setTextColor(Color.BLUE);
        // 卡名
        title = cellLineLayout.findViewById(R.id.list_card_name);
        title.setText(ColName[2]);
        title.setTextColor(Color.BLUE);
        // 刷卡日期
        title =  cellLineLayout.findViewById(R.id.list_swipe_date);
        title.setText(ColName[3]);
        title.setTextColor(Color.BLUE);
        // 金额
        title =  cellLineLayout.findViewById(R.id.list_amounts);
        title.setText(ColName[4]);
        title.setTextColor(Color.BLUE);
        // 商户
        title =  cellLineLayout.findViewById(R.id.list_vendor_name);
        title.setText(ColName[5]);
        title.setTextColor(Color.BLUE);
        // 卡号
        title =  cellLineLayout.findViewById(R.id.list_card_no);
        title.setText(ColName[6]);
        title.setTextColor(Color.BLUE);
        // 银行
        title =  cellLineLayout.findViewById(R.id.list_bank);
        title.setText(ColName[7]);
        title.setTextColor(Color.BLUE);
        // 备注
        title =  cellLineLayout.findViewById(R.id.list_comment);
        title.setText(ColName[8]);
        title.setTextColor(Color.BLUE);

        cellLayout.addView(cellLineLayout);

        BigDecimal totalSwipe = new BigDecimal(0);
        int number = 1;
        //数据库检索
        String strSQL = "SELECT A.id AS id, A.card_id , A.swipe_date , A.amounts, A.vendor_name, A.comment,"
                + " B.credit_name, B.credit_no, B.bank_name"
                + " FROM t_swipe_record_info A left join t_credit_info B"
                + " ON A.card_id=B.id "
                + " ORDER BY A.swipe_date DESC";

        Cursor cursor = db.rawQuery(strSQL, null);
        while (cursor.moveToNext())
        {
            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_swipe_record, null);
            // 操作
            TableCellTextView optTxt = cellLineLayout.findViewById(R.id.list_opt);
            String tmpVal = String.valueOf("编辑");
            optTxt.setText(tmpVal);
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
            // 刷卡日期
            txt =  cellLineLayout.findViewById(R.id.list_swipe_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("swipe_date")));
            // 金额
            txt =  cellLineLayout.findViewById(R.id.list_amounts);
            txt.setText(cursor.getString(cursor.getColumnIndex("amounts")));
            //totalSwipe += Integer.parseInt(cursor.getString(cursor.getColumnIndex("amounts")));
            totalSwipe = totalSwipe.add(
                    new BigDecimal(cursor.getString(cursor.getColumnIndex("amounts"))));
            // 保留2位小数
            totalSwipe = totalSwipe.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);
            // 商户
            txt =  cellLineLayout.findViewById(R.id.list_vendor_name);
            txt.setText(cursor.getString(cursor.getColumnIndex("vendor_name")));
            // 备注
            txt =  cellLineLayout.findViewById(R.id.list_comment);
            txt.setText(cursor.getString(cursor.getColumnIndex("comment")));

            cellLayout.addView(cellLineLayout);
            number++;


            // 编辑点击事件
            optTxt.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //TextView idText = view.findViewById(view.getId());
                    RelativeLayout line = (RelativeLayout) view.getParent();
                    TextView idTxtV = line.findViewById(R.id.list_id);
                    String strId = idTxtV.getText().toString();

                    Intent intent = new Intent(getActivity(), SwipeRecordAddActivity.class);

                    // 传参
                    Bundle bundle = new Bundle();
                    bundle.putString("id", strId);
                    intent.putExtra("bundle",bundle);

                    startActivity(intent);
                }
            });

        }// end while

        // 合计行
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_swipe_record, null);
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
        // 刷卡日期
        txt =  cellLineLayout.findViewById(R.id.list_swipe_date);
        txt.setText("");
        // 金额
        txt =  cellLineLayout.findViewById(R.id.list_amounts);
        txt.setText(String.valueOf(totalSwipe));
        // 商户
        txt =  cellLineLayout.findViewById(R.id.list_vendor_name);
        txt.setText("");
        // 备注
        txt =  cellLineLayout.findViewById(R.id.list_comment);
        txt.setText("");

        cellLayout.addView(cellLineLayout);

    }

}
