package com.fcredit.ui.housecoast;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fcredit.R;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.AppUtil;
import com.fcredit.util.DBHelper;
import com.fcredit.widget.table.TableCellTextView;

import java.math.BigDecimal;

/**
 * 房屋成本列表
 */
public class HouseCoastListFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"操作","成本操作", "序号","房屋名称","交易日期", "贷款总额(万)", "成本合计(元)", "备注"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_coast_list, container, false);

        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.houseCoastTable);
        // 添加按钮
        TextView recordAdd = view.findViewById(R.id.coast_add);

        // 添加按钮事件
        recordAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HouseCostAddActivity.class);
                startActivity(intent);
            }
        });

        initData();

        return view;
    }

    private void initData()
    {
        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //初始化标题,
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_house_cost_list, null);
        // 操作
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_opt);
        title.setText(ColName[0]);
        // 序号
        title =  cellLineLayout.findViewById(R.id.list_opt_item);
        title.setText(ColName[1]);
        // 序号
        title =  cellLineLayout.findViewById(R.id.list_no);
        title.setText(ColName[2]);
        // 房屋名称
        title = cellLineLayout.findViewById(R.id.list_house_name);
        title.setText(ColName[3]);
        // 交易日期
        title =  cellLineLayout.findViewById(R.id.list_trade_date);
        title.setText(ColName[4]);
        // 贷款总额
        title =  cellLineLayout.findViewById(R.id.list_credit_limit);
        title.setText(ColName[5]);
        // 成本合计(元)
        title =  cellLineLayout.findViewById(R.id.list_cost_sum);
        title.setText(ColName[6]);
        // 备注
        title =  cellLineLayout.findViewById(R.id.list_comment);
        title.setText(ColName[7]);

        cellLayout.addView(cellLineLayout);

        // 成本合计
        BigDecimal costSum = new BigDecimal(0);
        int number = 1;
        //数据库检索
        String strSQL = "SELECT * FROM t_house_cost"
                + " ORDER BY trade_date DESC";

        Cursor cursor = db.rawQuery(strSQL, null);
        while (cursor.moveToNext())
        {
            String houseId = cursor.getString(cursor.getColumnIndex("id"));
            // 计算成本合计
            strSQL = "SELECT SUM(cost_amount) AS cost_amount"
                    + " FROM t_house_item_cost"
                    + " WHERE house_id=?";
            Cursor itemCursor = db.rawQuery(strSQL, new String[] {houseId});
            // 合计
            while (itemCursor.moveToNext())
            {
                costSum = AppUtil.string2BigDecimal(itemCursor.getString(itemCursor.getColumnIndex("cost_amount")));
                costSum = AppUtil.bigDecimalRound(costSum, 2);
            }

            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_house_cost_list, null);
            if (number % 2 == 1)
            {
                cellLineLayout.setBackgroundColor(getResources().getColor(R.color.white_table_line));
            }
            // 操作
            TableCellTextView optTxt = cellLineLayout.findViewById(R.id.list_opt);
            String tmpVal = String.valueOf("编辑");
            optTxt.setText(tmpVal);
            optTxt.setTextColor(getResources().getColor(R.color.opt_item_color));

            // 成本操作
            TableCellTextView  itemOptTxt = cellLineLayout.findViewById(R.id.list_opt_item);
            tmpVal = "成本详细";
            itemOptTxt.setText(tmpVal);
            itemOptTxt.setTextColor(getResources().getColor(R.color.opt_item_color));
            // 序号
            TableCellTextView txt = cellLineLayout.findViewById(R.id.list_no);
            tmpVal = String.valueOf(number);
            txt.setText(tmpVal);
            // id
            txt =  cellLineLayout.findViewById(R.id.list_id);
            tmpVal = houseId;
            txt.setText(tmpVal);
            // 房屋名称
            txt =  cellLineLayout.findViewById(R.id.list_house_name);
            tmpVal = cursor.getString(cursor.getColumnIndex("house_name"));
            txt.setText(tmpVal);
            // 交易日期
            txt =  cellLineLayout.findViewById(R.id.list_trade_date);
            tmpVal = cursor.getString(cursor.getColumnIndex("trade_date"));
            txt.setText(tmpVal);
            // 贷款总额
            txt =  cellLineLayout.findViewById(R.id.list_credit_limit);
            txt.setText(cursor.getString(cursor.getColumnIndex("credit_limit")));
            // 成本合计(元)
            txt =  cellLineLayout.findViewById(R.id.list_cost_sum);
            txt.setText(costSum.toString());
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

                    Intent intent = new Intent(getActivity(), HouseCostAddActivity.class);

                    // 传参
                    Bundle bundle = new Bundle();
                    bundle.putString("id", strId);
                    intent.putExtra("bundle",bundle);

                    startActivity(intent);
                }
            });

            // 成本编辑
            itemOptTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout line = (RelativeLayout) view.getParent();
                    TextView idTxtV = line.findViewById(R.id.list_id);
                    String strId = idTxtV.getText().toString();
                    TextView edtHouseName = line.findViewById(R.id.list_house_name);

                    Intent intent = new Intent(getActivity(), HouseCostItemListActivity.class);

                    // 传参
                    Bundle bundle = new Bundle();
                    bundle.putString("house_id", strId);
                    bundle.putString("house_name", edtHouseName.getText().toString());
                    intent.putExtra("bundle", bundle);

                    startActivity(intent);

                }
            });

        }// end while

    }

}
