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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fcredit.R;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.AppUtil;
import com.fcredit.util.DBHelper;
import com.fcredit.widget.table.TableCellTextView;

import java.math.BigDecimal;

/**
 * 成本项列表
 */
public class HouseCostItemListFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"操作", "序号","费用名称", "费用金额(元)", "发生日期", "备注"};

    // 房屋记录id
    String strHouseId = "";
    String strHouseName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_cost_item_list, container, false);

        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.houseCoastItemTable);
        // 添加按钮
        TextView recordAdd = view.findViewById(R.id.coast_item_add);

        // 添加按钮事件
        recordAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HouseCostItemAddActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("house_id", strHouseId);
                bundle.putString("house_name", strHouseName);
                intent.putExtra("bundle", bundle);

                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * 初始化页面
     */
    public void initData()
    {
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            strHouseId = bundle.get("house_id").toString();
            strHouseName = bundle.get("house_name").toString();
        }
        if (strHouseId == "" || strHouseId == null)
        {
            myToast("房屋记录ID为空!");
        }

        View view = getView();
        // 房屋名称
        TextView txtHouseName = view.findViewById(R.id.coast_house_name_title);
        txtHouseName.setText("房屋:" + strHouseName);

        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //初始化标题,
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_house_cost_item_list, null);
        // 操作
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_opt);
        title.setText(ColName[0]);
        // 序号
        title =  cellLineLayout.findViewById(R.id.list_no);
        title.setText(ColName[1]);
        // 费用名称
        title =  cellLineLayout.findViewById(R.id.list_cost_name);
        title.setText(ColName[2]);
        // 费用金额(元)
        title = cellLineLayout.findViewById(R.id.list_cost_amount);
        title.setText(ColName[3]);
        // 发生日期
        title =  cellLineLayout.findViewById(R.id.list_incurred_date);
        title.setText(ColName[4]);
        // 备注
        title =  cellLineLayout.findViewById(R.id.list_comment);
        title.setText(ColName[5]);

        cellLayout.addView(cellLineLayout);

        // 成本合计
        BigDecimal costSum = new BigDecimal(0);
        int number = 1;
        //数据库检索
        String strSQL = "SELECT * FROM t_house_item_cost"
                + " WHERE house_id=?"
                + " ORDER BY incurred_date DESC";

        Cursor cursor = db.rawQuery(strSQL, new String[]{strHouseId});
        while (cursor.moveToNext())
        {
            String strId = cursor.getString(cursor.getColumnIndex("id"));

            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_house_cost_item_list, null);
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
            TableCellTextView txt = cellLineLayout.findViewById(R.id.list_no);
            tmpVal = String.valueOf(number);
            txt.setText(tmpVal);
            // id
            txt =  cellLineLayout.findViewById(R.id.list_id);
            tmpVal = strId;
            txt.setText(tmpVal);

            // 费用名称
            txt =  cellLineLayout.findViewById(R.id.list_cost_name);
            tmpVal = cursor.getString(cursor.getColumnIndex("cost_name"));
            txt.setText(tmpVal);
            // 费用金额(元)
            txt =  cellLineLayout.findViewById(R.id.list_cost_amount);
            tmpVal = cursor.getString(cursor.getColumnIndex("cost_amount"));
            txt.setText(tmpVal);
            // 金额合计
            costSum = costSum.add(AppUtil.string2BigDecimal(tmpVal));

            // 发生日期
            txt =  cellLineLayout.findViewById(R.id.list_incurred_date);
            txt.setText(cursor.getString(cursor.getColumnIndex("incurred_date")));
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

                    Intent intent = new Intent(getActivity(), HouseCostItemAddActivity.class);

                    // 传参
                    Bundle bundle = new Bundle();
                    bundle.putString("id", strId);
                    bundle.putString("house_id", strHouseId);
                    bundle.putString("house_name", strHouseName);
                    intent.putExtra("bundle",bundle);

                    startActivity(intent);
                }
            });

        }// end while

        // 合计行
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_house_cost_item_list, null);
        // 合计
        TableCellTextView optTxt = cellLineLayout.findViewById(R.id.list_opt);
        String tmpVal = String.valueOf("合计");
        optTxt.setText(tmpVal);

        // 序号
        TableCellTextView txt = cellLineLayout.findViewById(R.id.list_no);
        txt.setText("");
        // 费用名称
        txt =  cellLineLayout.findViewById(R.id.list_cost_name);
        txt.setText("");
        // 费用金额(元)
        txt =  cellLineLayout.findViewById(R.id.list_cost_amount);
        txt.setText(AppUtil.bigDecimalRound(costSum, 2).toString());
        // 发生日期
        txt =  cellLineLayout.findViewById(R.id.list_incurred_date);
        txt.setText("");
        // 备注
        txt =  cellLineLayout.findViewById(R.id.list_comment);
        txt.setText("");

        cellLayout.addView(cellLineLayout);

    }

    private void myToast(String strMsg)
    {
        Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
    }
}
