package com.fcredit.ui.swiperecord;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fcredit.R;
import com.fcredit.ui.cardlist.CardInfoListActivity;
import com.fcredit.ui.creditinfo.AddCreditInfoActivity;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.DBHelper;

/**
 * 添加/编辑页面
 */
public class SwipeRecordAddFragment extends Fragment {
    private TextView edtCardName;
    private TextView edtCardId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_swipe_record_add, container, false);

        // 卡片名称
        edtCardName = view.findViewById(R.id.edtCreditName);
        edtCardName.setInputType(InputType.TYPE_NULL);
        edtCardName.setTextColor(Color.GRAY);
        // 卡片记录id
        edtCardId = view.findViewById(R.id.hidCardId);
        // 记录id
        final EditText edtId = view.findViewById(R.id.hidId);
        // 刷卡日期
        final  EditText edtSwipeDate = view.findViewById(R.id.edtSwipeDate);
        // 金额
        final  EditText edtAmounts = view.findViewById(R.id.edtAmounts);
        // 商户
        final  EditText edtVendorName = view.findViewById(R.id.edtVendorName);
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

        // 卡片名称点击事件
        edtCardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 启动卡片选择页面
                Intent intent = new Intent(getActivity(), CardInfoListActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 0);

            }
        });

        // 保存按钮
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SQL
                String strSQL = "";
                // 更新
                if(edtId.getText() != null && !"".equals(edtId.getText().toString()))
                {
                    strSQL = "UPDATE t_swipe_record_info SET card_id=?, swipe_date=?";
                    strSQL += ", amounts=?, vendor_name=?, comment=?";
                    strSQL += " WHERE id=?";

                    // Values
                    String[] values = new String[]{edtCardId.getText().toString()
                            ,edtSwipeDate.getText().toString()
                            ,edtAmounts.getText().toString()
                            , edtVendorName.getText().toString()
                            ,edtComment.getText().toString()
                            ,edtId.getText().toString()};

                    // 更新
                    db.execSQL(strSQL, values);

                }
                else // 插入
                {
                    strSQL = "INSERT INTO t_swipe_record_info";
                    strSQL += " (card_id, swipe_date, amounts, vendor_name, comment)";
                    strSQL += " VALUES (?,?,?,?,?)";
                    // Values
                    String[] values = new String[]{edtCardId.getText().toString()
                            ,edtSwipeDate.getText().toString()
                            ,edtAmounts.getText().toString()
                            , edtVendorName.getText().toString()
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
                    strSQL = "DELETE FROM t_swipe_record_info WHERE ID=?";

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

        // 记录id
        final EditText edtId = view.findViewById(R.id.hidId);
        // 卡片名称
        final EditText edtCardName = view.findViewById(R.id.edtCreditName);
        // 卡片记录id
        final EditText edtCardId = view.findViewById(R.id.hidCardId);
        // 刷卡日期
        final  EditText edtSwipeDate = view.findViewById(R.id.edtSwipeDate);
        // 金额
        final  EditText edtAmounts = view.findViewById(R.id.edtAmounts);
        // 商户
        final  EditText edtVendorName = view.findViewById(R.id.edtVendorName);
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
                // 到数据库检索
                //数据库检索
                String strSQL = "SELECT A.id AS id, A.card_id , A.swipe_date , A.amounts, A.vendor_name, A.comment,"
                        + " B.credit_name, B.credit_no, B.bank_name"
                        + " FROM t_swipe_record_info A left join t_credit_info B"
                        + " ON A.card_id=B.id"
                        + " WHERE A.id=?";

                Cursor cursor = db.rawQuery(strSQL, new String[]{strId});
                while (cursor.moveToNext())
                {
                    // 给页面赋值
                    edtId.setText(cursor.getString(cursor.getColumnIndex("id")));
                    edtCardName.setText(cursor.getString(cursor.getColumnIndex("credit_name")));
                    edtCardId.setText(cursor.getString(cursor.getColumnIndex("card_id")));
                    edtSwipeDate.setText(cursor.getString(cursor.getColumnIndex("swipe_date")));
                    edtAmounts.setText(cursor.getString(cursor.getColumnIndex("amounts")));
                    edtVendorName.setText(cursor.getString(cursor.getColumnIndex("vendor_name")));
                    edtComment.setText(cursor.getString(cursor.getColumnIndex("comment")));
                }
            }

        }

    }

    /**
     * 卡片信息选择回调方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null)
        {
            Bundle bundle = data.getBundleExtra("bundle");

            String strId = bundle.get("id").toString();
            String strCardName = bundle.get("cardName").toString();

            edtCardId.setText(strId);
            edtCardName.setText(strCardName);
        }
    }


    private void myToast(String strMsg)
    {
        Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
    }
}
