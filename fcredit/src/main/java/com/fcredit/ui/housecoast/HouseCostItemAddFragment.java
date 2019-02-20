package com.fcredit.ui.housecoast;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
 * 成本详细
 */
public class HouseCostItemAddFragment extends Fragment {
    String strHouseId = "";
    String strId = "";
    String strHouseName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_cost_item_add, container, false);

        // 记录id
        final EditText edtId = view.findViewById(R.id.hidId);
        // 房屋id
        final EditText edtHouseId = view.findViewById(R.id.hidHouseId);
        // 费用名称
        final  EditText edtCostName = view.findViewById(R.id.edtCostName);
        // 费用金额(元)
        final  EditText edtCostAmount = view.findViewById(R.id.edtCostAmount);
        // 发生日期
        final  EditText edtIncurredDate = view.findViewById(R.id.edtIncurredDate);
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
                    strSQL = "UPDATE t_house_item_cost SET house_id=?, cost_name=?";
                    strSQL += ", cost_amount=?, incurred_date=?, comment=?";
                    strSQL += " WHERE id=?";

                    // Values
                    String[] values = new String[]{
                            edtHouseId.getText().toString()
                            ,edtCostName.getText().toString()
                            ,edtCostAmount.getText().toString()
                            ,edtIncurredDate.getText().toString()
                            ,edtComment.getText().toString()
                            ,edtId.getText().toString()};

                    // 更新
                    db.execSQL(strSQL, values);

                }
                else // 插入
                {
                    strSQL = "INSERT INTO t_house_item_cost";
                    strSQL += " (id, house_id, cost_name, cost_amount, incurred_date, comment)";
                    strSQL += " VALUES (?,?,?,?,?,?)";
                    // Values
                    String[] values = new String[]{
                            AppUtil.getUUID()
                            ,edtHouseId.getText().toString()
                            ,edtCostName.getText().toString()
                            ,edtCostAmount.getText().toString()
                            ,edtIncurredDate.getText().toString()
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
                    strSQL = "DELETE FROM t_house_item_cost WHERE ID=?";

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
     * 初始化页面
     */
    public void initData()
    {
        View view = getView();

        // 记录id
        final EditText edtId = view.findViewById(R.id.hidId);
        // 房屋id
        final EditText edtHouseId = view.findViewById(R.id.hidHouseId);
        // 房屋名称
        final EditText edtHouseName = view.findViewById(R.id.edtHouseName);
        // 费用名称
        final  EditText edtCostName = view.findViewById(R.id.edtCostName);
        // 费用金额(元)
        final  EditText edtCostAmount = view.findViewById(R.id.edtCostAmount);
        // 发生日期
        final  EditText edtIncurredDate = view.findViewById(R.id.edtIncurredDate);
        // 备注
        final  EditText edtComment = view.findViewById(R.id.edtComment);

        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 获取参数
        if (getArguments() != null)
        {
            strHouseId = getArguments().get("house_id").toString();
            strHouseName = getArguments().get("house_name").toString();
            if (getArguments().get("id") != null)
            {
                strId = getArguments().get("id").toString();
            }
            if (strHouseId == "" || strHouseId == null)
            {
                myToast("房屋记录ID为空!");
            }
            edtHouseId.setText(strHouseId);
            edtHouseName.setText(strHouseName);
            edtHouseName.setInputType(InputType.TYPE_NULL);
            edtHouseName.setTextColor(Color.GRAY);

            // 传递了参数
            if ( strId != null)
            {
                //数据库检索
                String strSQL = "SELECT *"
                        + " FROM t_house_item_cost"
                        + " WHERE id=?";

                Cursor cursor = db.rawQuery(strSQL, new String[]{strId});
                while (cursor.moveToNext())
                {
                    // 给页面赋值
                    edtId.setText(cursor.getString(cursor.getColumnIndex("id")));
                    edtHouseId.setText(cursor.getString(cursor.getColumnIndex("house_id")));
                    edtCostName.setText(cursor.getString(cursor.getColumnIndex("cost_name")));
                    edtCostAmount.setText(cursor.getString(cursor.getColumnIndex("cost_amount")));
                    edtIncurredDate.setText(cursor.getString(cursor.getColumnIndex("incurred_date")));
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
