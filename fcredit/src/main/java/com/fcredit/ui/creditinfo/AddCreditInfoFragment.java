package com.fcredit.ui.creditinfo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fcredit.R;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.DBHelper;

/**
 * 添加Credit
 */
public class AddCreditInfoFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_credit_info, container, false);

        final EditText edtCreditName = view.findViewById(R.id.edtCreditName);
        final EditText edtCreditNo = view.findViewById(R.id.edtCreditNo);
        final EditText edtBankName = view.findViewById(R.id.edtBankName);
        final EditText edtCreditLimit = view.findViewById(R.id.edtCreditLimit);
        final EditText edtStatementDate = view.findViewById(R.id.edtStatmentDate);
        final EditText edtRepaymentDate = view.findViewById(R.id.edtRepaymentDate);
        final EditText edtComment = view.findViewById(R.id.edtComment);


        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 保存按钮
        Button btnSave = view.findViewById(R.id.btnSave);

        // 保存按钮事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //创建存放数据的ContentValues对象
                ContentValues values = new ContentValues();
                values.put("credit_name", edtCreditName.getText().toString());
                values.put("credit_no", edtCreditNo.getText().toString());
                values.put("bank_name", edtBankName.getText().toString());
                values.put("credit_limit", edtCreditLimit.getText().toString());
                values.put("statement_date", edtStatementDate.getText().toString());
                values.put("repayment_date", edtRepaymentDate.getText().toString());
                values.put("credit_comment", edtComment.getText().toString());

                //数据库执行插入命令
                db.insert("t_credit_info", null, values);
            }
        });
        return view;
    }
}
