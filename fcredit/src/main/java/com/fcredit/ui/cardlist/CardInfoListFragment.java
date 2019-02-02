package com.fcredit.ui.cardlist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fcredit.R;
import com.fcredit.ui.creditinfo.AddCreditInfoActivity;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.DBHelper;
import com.fcredit.widget.table.TableCellTextView;

/**
 * 卡片选择页面
 */
public class CardInfoListFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"操作", "序号","卡名","卡号","银行", "备注"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_info_list, container, false);

        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.cardInfoTable);

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
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_card_info_list, null);
        // 操作
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_opt);
        title.setText(ColName[0]);
        title.setTextColor(Color.BLUE);
        // 序号
        title =  cellLineLayout.findViewById(R.id.list_num);
        title.setText(ColName[1]);
        title.setTextColor(Color.BLUE);
        // 卡名
        title =  cellLineLayout.findViewById(R.id.list_card_name);
        title.setText(ColName[2]);
        title.setTextColor(Color.BLUE);
        // 卡号
        title = cellLineLayout.findViewById(R.id.list_card_no);
        title.setText(ColName[3]);
        title.setTextColor(Color.BLUE);
        // 银行
        title =  cellLineLayout.findViewById(R.id.list_bank);
        title.setText(ColName[4]);
        title.setTextColor(Color.BLUE);
        // 备注
        title =  cellLineLayout.findViewById(R.id.list_comment);
        title.setText(ColName[5]);
        title.setTextColor(Color.BLUE);

        cellLayout.addView(cellLineLayout);

        int number = 1;
        //数据库检索
        Cursor cursor = db.query("t_credit_info"
                ,new String[]{"id", "credit_name","credit_no","bank_name","credit_limit", "statement_date","repayment_date", "credit_comment"}
                ,null,null, null,null, null,null);
        while (cursor.moveToNext())
        {
            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_card_info_list, null);
            // 操作
            TableCellTextView  optTxt = cellLineLayout.findViewById(R.id.list_opt);
            String tmpVal = "选择";
            optTxt.setText(tmpVal);
            // 序号
            TableCellTextView txt = cellLineLayout.findViewById(R.id.list_num);
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
                    TextView cardName = line.findViewById(R.id.list_card_name);
                    String strCardName = cardName.getText().toString();

//                    Intent intent = new Intent(getActivity(), AddCreditInfoActivity.class);
                    Intent intent = new Intent();

                    // 传参
                    Bundle bundle = new Bundle();
                    bundle.putString("id", strId);
                    bundle.putString("cardName", strCardName);
                    intent.putExtra("bundle",bundle);

                    getActivity().setResult(0, intent);
                    getActivity().finish();
                    //startActivity(intent);

                }
            });

        }// end while
    }

    private void myToast(String strMsg)
    {
        Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
    }
}
