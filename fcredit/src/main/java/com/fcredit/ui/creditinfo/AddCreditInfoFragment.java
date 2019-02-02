package com.fcredit.ui.creditinfo;

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


        final EditText txtId = view.findViewById(R.id.txtId);
        final EditText edtCreditName = view.findViewById(R.id.edtCreditName);
        final EditText edtCreditNo = view.findViewById(R.id.edtCreditNo);
        final EditText edtBankName = view.findViewById(R.id.edtBankName);
        final EditText edtCreditLimit = view.findViewById(R.id.edtCreditLimit);
        final EditText edtStatementDate = view.findViewById(R.id.edtStatmentDate);
        final EditText edtRepaymentDate = view.findViewById(R.id.edtRepaymentDate);
        final EditText edtComment = view.findViewById(R.id.edtComment);

        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 保存按钮
        Button btnSave = view.findViewById(R.id.btnSave);
        // 删除按钮
        Button btnDelete = view.findViewById(R.id.btnDelete);

        // 更新或删除
        if (txtId.getText() != null && !"".equals(txtId.getText().toString()))
        {
            btnDelete.setEnabled(false);
        }

        // 保存按钮事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SQL
                String strSQL = "";
                if (txtId.getText() != null && !"".equals(txtId.getText().toString()))// 更新
                {
                    strSQL = "UPDATE t_credit_info SET credit_name=?, credit_no=?";
                    strSQL += ", bank_name=?, credit_limit=?, statement_date=?, repayment_date=?, credit_comment=?";
                    strSQL += " WHERE id=?";

                    // Values
                    String[] values = new String[]{edtCreditName.getText().toString()
                            ,edtCreditNo.getText().toString()
                            ,edtBankName.getText().toString()
                            , edtCreditLimit.getText().toString()
                            ,edtStatementDate.getText().toString()
                            ,edtRepaymentDate.getText().toString()
                            ,edtComment.getText().toString()
                            ,txtId.getText().toString()};

                    // 更新
                    db.execSQL(strSQL, values);
                }
                else // 插入
                {
                    strSQL = "INSERT INTO t_credit_info";
                    strSQL += " (credit_name, credit_no, bank_name, credit_limit, statement_date, repayment_date, credit_comment)";
                    strSQL += " VALUES (?,?,?,?,?,?,?)";
                    // Values
                    String[] values = new String[]{edtCreditName.getText().toString()
                            ,edtCreditNo.getText().toString()
                            ,edtBankName.getText().toString()
                            , edtCreditLimit.getText().toString()
                            ,edtStatementDate.getText().toString()
                            ,edtRepaymentDate.getText().toString()
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
                if (txtId.getText() != null && !"".equals(txtId.getText().toString()))// 删除
                {
                    strSQL = "DELETE FROM t_credit_info WHERE ID=?";

                    String[] values = new String[]{
                            txtId.getText().toString()
                    };

                    db.execSQL(strSQL, values);

                    myToast("删除成功!");
                }
            }
        });

        return view;
    }

    public void initData()
    {
        View view = getView();
        final EditText txtId = view.findViewById(R.id.txtId);
        final EditText edtCreditName = view.findViewById(R.id.edtCreditName);
        final EditText edtCreditNo = view.findViewById(R.id.edtCreditNo);
        final EditText edtBankName = view.findViewById(R.id.edtBankName);
        final EditText edtCreditLimit = view.findViewById(R.id.edtCreditLimit);
        final EditText edtStatementDate = view.findViewById(R.id.edtStatmentDate);
        final EditText edtRepaymentDate = view.findViewById(R.id.edtRepaymentDate);
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
                String strSQL = "SELECT * FROM t_credit_info "
                        + " WHERE ID=?";
                Cursor cursor = db.rawQuery(strSQL, new String[]{strId});
                while (cursor.moveToNext())
                {
                    // 给页面赋值
                    txtId.setText(cursor.getString(cursor.getColumnIndex("id")));
                    edtCreditName.setText(cursor.getString(cursor.getColumnIndex("credit_name")));
                    edtCreditNo.setText(cursor.getString(cursor.getColumnIndex("credit_no")));
                    edtBankName.setText(cursor.getString(cursor.getColumnIndex("bank_name")));
                    edtCreditLimit.setText(cursor.getString(cursor.getColumnIndex("credit_limit")));
                    edtStatementDate.setText(cursor.getString(cursor.getColumnIndex("statement_date")));
                    edtRepaymentDate.setText(cursor.getString(cursor.getColumnIndex("repayment_date")));
                    edtComment.setText(cursor.getString(cursor.getColumnIndex("credit_comment")));
                }
            }

        }

    }

    private void myToast(String strMsg)
    {
        Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
    }
}
