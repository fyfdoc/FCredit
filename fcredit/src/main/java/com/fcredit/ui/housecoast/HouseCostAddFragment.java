package com.fcredit.ui.housecoast;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fcredit.R;
import com.fcredit.ui.cardlist.CardInfoListActivity;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.AppUtil;
import com.fcredit.util.DBHelper;

/**
 * 添加房屋成本
 */
public class HouseCostAddFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_cost_add, container, false);

        // 记录id
        final EditText edtId = view.findViewById(R.id.hidId);
        // 房屋名称
        final EditText edtHouseName = view.findViewById(R.id.edtHouseName);
        // 交易日期
        final  EditText edtTradeDate = view.findViewById(R.id.edtTradeDate);
        // 贷款总额(万)
        final  EditText edtCreditLimit = view.findViewById(R.id.edtCreditLimit);
        // 备注
        final  EditText edtComment = view.findViewById(R.id.edtComment);

        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        // 更新或删除
        if (edtId.getText() != null && !"".equals(edtId.getText().toString()))
        {
            btnDelete.setEnabled(false);
        }

        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 保存按钮
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SQL
                String strSQL = "";
                // 更新
                if(edtId.getText() != null && !"".equals(edtId.getText().toString()))
                {
                    strSQL = "UPDATE t_house_cost SET house_name=?, trade_date=?";
                    strSQL += ", credit_limit=?, comment=?";
                    strSQL += " WHERE id=?";

                    // Values
                    String[] values = new String[]{edtHouseName.getText().toString()
                            ,edtTradeDate.getText().toString()
                            ,edtCreditLimit.getText().toString()
                            ,edtComment.getText().toString()
                            ,edtId.getText().toString()};

                    // 更新
                    db.execSQL(strSQL, values);

                }
                else // 插入
                {
                    strSQL = "INSERT INTO t_house_cost";
                    strSQL += " (id, house_name, trade_date, credit_limit, comment)";
                    strSQL += " VALUES (?,?,?,?,?)";
                    // Values
                    String[] values = new String[]{
                            AppUtil.getUUID()
                            ,edtHouseName.getText().toString()
                            ,edtTradeDate.getText().toString()
                            ,edtCreditLimit.getText().toString()
                            ,edtComment.getText().toString()};

                    // 插入
                    db.execSQL(strSQL, values);
                }

                myToast("保存成功");
            }
        });

        // 删除按钮事件
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SQL
                String strSQL = "";
                if (edtId.getText() != null && !"".equals(edtId.getText().toString()))// 删除
                {
                    // 先删除子表
                    strSQL = "DELETE FROM t_house_item_cost WHERE house_id=?";

                    String[] values = new String[]{
                            edtId.getText().toString()
                    };
                    db.execSQL(strSQL, values);

                    // 删除主表
                    strSQL = "DELETE FROM t_house_cost WHERE id=?";
                    db.execSQL(strSQL, values);

                    myToast("删除成功!");
                }
            }
        });


        return view;
    }

    /**
     * 初始化页面
     */
    public void initData()
    {
        View view = getView();

        // 记录id
        final EditText edtId = view.findViewById(R.id.hidId);
        // 房屋名称
        final EditText edtHouseName = view.findViewById(R.id.edtHouseName);
        // 交易日期
        final EditText edtTradeDate = view.findViewById(R.id.edtTradeDate);
        // 贷款总额(万)
        final  EditText edtCreditLimit = view.findViewById(R.id.edtCreditLimit);
        // 备注
        final  EditText edtComment = view.findViewById(R.id.edtComment);

        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 获取参数
        if (getArguments() != null)
        {
            String strId = getArguments().getString("id");
            // 传递了参数
            if ( strId != null)
            {
                //数据库检索
                String strSQL = "SELECT *"
                        + " FROM t_house_cost"
                        + " WHERE id=?";

                Cursor cursor = db.rawQuery(strSQL, new String[]{strId});
                while (cursor.moveToNext())
                {
                    // 给页面赋值
                    edtId.setText(cursor.getString(cursor.getColumnIndex("id")));
                    edtHouseName.setText(cursor.getString(cursor.getColumnIndex("house_name")));
                    edtTradeDate.setText(cursor.getString(cursor.getColumnIndex("trade_date")));
                    edtCreditLimit.setText(cursor.getString(cursor.getColumnIndex("credit_limit")));
                    edtComment.setText(cursor.getString(cursor.getColumnIndex("comment")));
                }
            }

        }
    }

    private void myToast(String strMsg)
    {
        Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
    }

}
