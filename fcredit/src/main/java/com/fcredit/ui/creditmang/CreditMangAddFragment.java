package com.fcredit.ui.creditmang;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fcredit.R;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.AppUtil;
import com.fcredit.util.DBHelper;

/**
 * 添加贷款
 */
public class CreditMangAddFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credit_mang_add, container, false);

        final EditText edtId = view.findViewById(R.id.edtId);
        final EditText edtCreditName = view.findViewById(R.id.edtCreditName);
        final EditText edtBankName = view.findViewById(R.id.edtBankName);
        final EditText edtRepaymentDate = view.findViewById(R.id.edtRepaymentDate);
        final EditText edtRepayMonth = view.findViewById(R.id.edtRepayMonth);
        final EditText edtInterestRate = view.findViewById(R.id.edtInterestRate);
        final EditText edtCreditItemLimit = view.findViewById(R.id.edtCreditItemLimit);
        final EditText edtCreditLength = view.findViewById(R.id.edtCreditLength);
        final EditText edtDrawDate = view.findViewById(R.id.edtDrawDate);
        final EditText edtCurrentEndDate = view.findViewById(R.id.edtCurrentEndDate);
        final EditText edtCreditLengthStartDate = view.findViewById(R.id.edtCreditLengthStartDate);
        final EditText edtCreditLengthEndDate = view.findViewById(R.id.edtCreditLengthEndDate);
        final EditText edtComment = view.findViewById(R.id.edtComment);

        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 保存按钮
        Button btnSave = view.findViewById(R.id.btnSave);
        // 删除按钮
        Button btnDelete = view.findViewById(R.id.btnDelete);

        // 更新或删除
        if (edtId.getText() != null && !"".equals(edtId.getText().toString()))
        {
            btnDelete.setEnabled(false);
        }

        // 保存按钮事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SQL
                String strSQL = "";
                if (edtId.getText() != null && !"".equals(edtId.getText().toString()))// 更新
                {
                    strSQL = "UPDATE t_credit_mang SET credit_name=?, bank_name=?";
                    strSQL += ", repayment_date=?, repay_month=?, interest_rate=?, credit_limit=?, credit_length=?";
                    strSQL += ", draw_date=?, current_end_date=?, credit_length_start_date=?, credit_length_end_date=?, comment=?";
                    strSQL += " WHERE id=?";

                    // Values
                    String[] values = new String[]{
                            edtCreditName.getText().toString()
                            ,edtBankName.getText().toString()
                            ,edtRepaymentDate.getText().toString()
                            , edtRepayMonth.getText().toString()
                            ,edtInterestRate.getText().toString()
                            ,edtCreditItemLimit.getText().toString()
                            ,edtCreditLength.getText().toString()
                            ,edtDrawDate.getText().toString()
                            ,edtCurrentEndDate.getText().toString()
                            ,edtCreditLengthStartDate.getText().toString()
                            ,edtCreditLengthEndDate.getText().toString()
                            ,edtComment.getText().toString()
                            ,edtId.getText().toString()};

                    // 更新
                    db.execSQL(strSQL, values);
                }
                else // 插入
                {
                    strSQL = "INSERT INTO t_credit_mang";
                    strSQL += " (id, credit_name, bank_name, repayment_date, repay_month, interest_rate, credit_limit";
                    strSQL += " ,credit_length, draw_date, current_end_date, credit_length_start_date, credit_length_end_date, comment)";
                    strSQL += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    // Values
                    String[] values = new String[]{
                            AppUtil.getUUID()
                            ,edtCreditName.getText().toString()
                            ,edtBankName.getText().toString()
                            ,edtRepaymentDate.getText().toString()
                            , edtRepayMonth.getText().toString()
                            ,edtInterestRate.getText().toString()
                            ,edtCreditItemLimit.getText().toString()
                            ,edtCreditLength.getText().toString()
                            ,edtDrawDate.getText().toString()
                            ,edtCurrentEndDate.getText().toString()
                            ,edtCreditLengthStartDate.getText().toString()
                            ,edtCreditLengthEndDate.getText().toString()
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
                    strSQL = "DELETE FROM t_credit_mang WHERE ID=?";

                    String[] values = new String[]{
                            edtId.getText().toString()
                    };

                    db.execSQL(strSQL, values);

                    myToast("删除成功!");
                }
            }
        });

        return view;
    }

    /**
     * 初始化数据
     */
    public void initData()
    {
        View view = getView();
        final EditText edtId = view.findViewById(R.id.edtId);
        final EditText edtCreditName = view.findViewById(R.id.edtCreditName);
        final EditText edtBankName = view.findViewById(R.id.edtBankName);
        final EditText edtRepaymentDate = view.findViewById(R.id.edtRepaymentDate);
        final EditText edtRepayMonth = view.findViewById(R.id.edtRepayMonth);
        final EditText edtInterestRate = view.findViewById(R.id.edtInterestRate);
        final EditText edtCreditItemLimit = view.findViewById(R.id.edtCreditItemLimit);
        final EditText edtCreditLength = view.findViewById(R.id.edtCreditLength);
        final EditText edtDrawDate = view.findViewById(R.id.edtDrawDate);
        final EditText edtCurrentEndDate = view.findViewById(R.id.edtCurrentEndDate);
        final EditText edtCreditLengthStartDate = view.findViewById(R.id.edtCreditLengthStartDate);
        final EditText edtCreditLengthEndDate = view.findViewById(R.id.edtCreditLengthEndDate);
        final EditText edtComment = view.findViewById(R.id.edtComment);


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
                // 到数据库检索
                String strSQL = "SELECT * FROM t_credit_mang "
                        + " WHERE ID=?";
                Cursor cursor = db.rawQuery(strSQL, new String[]{strId});
                while (cursor.moveToNext())
                {
                    // 给页面赋值
                    edtId.setText(cursor.getString(cursor.getColumnIndex("id")));
                    edtCreditName.setText(cursor.getString(cursor.getColumnIndex("credit_name")));
                    edtBankName.setText(cursor.getString(cursor.getColumnIndex("bank_name")));
                    edtRepaymentDate.setText(cursor.getString(cursor.getColumnIndex("repayment_date")));
                    edtRepayMonth.setText(cursor.getString(cursor.getColumnIndex("repay_month")));
                    edtInterestRate.setText(cursor.getString(cursor.getColumnIndex("interest_rate")));
                    edtCreditItemLimit.setText(cursor.getString(cursor.getColumnIndex("credit_limit")));
                    edtCreditLength.setText(cursor.getString(cursor.getColumnIndex("credit_length")));
                    edtDrawDate.setText(cursor.getString(cursor.getColumnIndex("draw_date")));
                    edtCurrentEndDate.setText(cursor.getString(cursor.getColumnIndex("current_end_date")));
                    edtCreditLengthStartDate.setText(cursor.getString(cursor.getColumnIndex("credit_length_start_date")));
                    edtCreditLengthEndDate.setText(cursor.getString(cursor.getColumnIndex("credit_length_end_date")));
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
