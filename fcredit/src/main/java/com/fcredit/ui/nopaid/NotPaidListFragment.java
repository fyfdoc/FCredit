package com.fcredit.ui.nopaid;

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

import com.fcredit.R;
import com.fcredit.ui.swiperecord.SwipeRecordAddActivity;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.DBHelper;
import com.fcredit.widget.table.TableCellTextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 本月应还统计
 */
public class NotPaidListFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"序号","卡名", "还款日", "本期应还", "刷卡总额", "卡号","银行"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_not_paid_list, container, false);

        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.notPaidTable);

        // 今日
        TextView tvToday = view.findViewById(R.id.today);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar toDayCal = Calendar.getInstance();
        String strToday = df.format(toDayCal.getTime());
        tvToday.setText("今天:" + strToday);

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
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_not_paid_list, null);
        // 序号
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_no);
        title.setText(ColName[0]);
        title.setTextColor(Color.BLUE);
        // 卡名
        title =  cellLineLayout.findViewById(R.id.list_card_name);
        title.setText(ColName[1]);
        title.setTextColor(Color.BLUE);
        // 还款日
        title = cellLineLayout.findViewById(R.id.list_payment_date);
        title.setText(ColName[2]);
        title.setTextColor(Color.BLUE);
        // 还款额
        title =  cellLineLayout.findViewById(R.id.list_not_paid_sum);
        title.setText(ColName[3]);
        title.setTextColor(Color.BLUE);
        // 刷卡总额
        title =  cellLineLayout.findViewById(R.id.list_swipe_total);
        title.setText(ColName[4]);
        title.setTextColor(Color.BLUE);
        // 卡号
        title =  cellLineLayout.findViewById(R.id.list_card_no);
        title.setText(ColName[5]);
        title.setTextColor(Color.BLUE);
        // 银行
        title =  cellLineLayout.findViewById(R.id.list_bank);
        title.setText(ColName[6]);
        title.setTextColor(Color.BLUE);

        cellLayout.addView(cellLineLayout);

        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 年
        int year = calendar.get(Calendar.YEAR);
        // 当前月
        int month = calendar.get(Calendar.MONTH) + 1;
        // 当前日
        int today = calendar.get(Calendar.DAY_OF_MONTH);

        // 本期应还合计
        BigDecimal notPaidTotal = new BigDecimal(0);
        // 本期刷卡合计
        BigDecimal swipeTotal = new BigDecimal(0);
        int number = 1;
        // 循环卡片信息表中的每张卡，然后再根据刷卡记录表进行统计
        String strSql = "SELECT * FROM t_credit_info ORDER BY repayment_date ASC";
        Cursor cursorCardInfo = db.rawQuery(strSql, null);
        while (cursorCardInfo.moveToNext())
        {
            // 卡片记录id
            String cardId = cursorCardInfo.getString(cursorCardInfo.getColumnIndex("id"));
            // 卡片名称
            String cardName = cursorCardInfo.getString(cursorCardInfo.getColumnIndex("credit_name"));
            String cardNo = cursorCardInfo.getString(cursorCardInfo.getColumnIndex("credit_no"));
            String bank = cursorCardInfo.getString(cursorCardInfo.getColumnIndex("bank_name"));
            // 卡片账单日
            int statementDate = cursorCardInfo.getInt(cursorCardInfo.getColumnIndex("statement_date"));
            // 卡片还款日
            int repaymentDate = cursorCardInfo.getInt(cursorCardInfo.getColumnIndex("repayment_date"));

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            // 有效账单开始日,用于计算当期的刷卡总额，计算方法如下：
            // 如果今天在还款日之前：刷卡总额=前2个月的账单日到今天的刷卡额总和
            // 如果今天在还款日之后: 刷卡总额=前1个月的账单日到今天的刷卡额总和
            Calendar startCalendar = Calendar.getInstance();
            // 前1个月
            startCalendar.add(Calendar.MONTH, -1);
            // 前1个月的账单日
            startCalendar.set(Calendar.DAY_OF_MONTH, statementDate);
            String str1MonthStatementDate = df.format(startCalendar.getTime());
            // 前1个月账单日的后一天
            startCalendar.set(Calendar.DAY_OF_MONTH, statementDate + 1);
            // 前1个月的账单日的后一天
            String str1MonthDate = df.format(startCalendar.getTime());
            // 前2个月
            startCalendar.add(Calendar.MONTH, -1);
            // 前两个月的账单日的后一天
            String str2MonthDate = df.format(startCalendar.getTime());

            // 计算未还款的刷卡总额
            strSql = "SELECT card_id, sum(amounts) AS amounts_sum"
                    + " FROM t_swipe_record_info"
                    + " WHERE card_id=?";

            // 检索日期
            String queryStartDate = "";
            if (today <= statementDate)
            {
                // 前2月
                queryStartDate = str2MonthDate;

                strSql += " AND swipe_date>='" + str2MonthDate + "'";
                strSql += " AND swipe_date<='" + str1MonthStatementDate + "'";
            }
            else
            {
                // 前1月
                queryStartDate = str1MonthDate;;
                strSql += " AND swipe_date>='" + str1MonthDate + "'";
            }
            strSql += " GROUP BY card_id";

            Cursor notPaidSumCursor = db.rawQuery(strSql, new String[]{cardId});

            // 本期应还总额
            BigDecimal notPaidSum = new BigDecimal(0);
            while (notPaidSumCursor.moveToNext())// 只有一条记录
            {

                notPaidSum = new BigDecimal(notPaidSumCursor.getString(notPaidSumCursor.getColumnIndex("amounts_sum")));
            }
            // 保留2位小数
            notPaidSum = notPaidSum.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);
            notPaidTotal = notPaidTotal.add(notPaidSum);

            // 计算刷卡总额
            strSql = "SELECT card_id, sum(amounts) AS amounts_sum"
                    + " FROM t_swipe_record_info"
                    + " WHERE card_id=?"
                    + " AND swipe_date>=?"
                    + " GROUP BY card_id";

            Cursor swipeSumCursor = db.rawQuery(strSql, new String[]{cardId, queryStartDate});

            // 刷卡总额
            BigDecimal swipeSum = new BigDecimal(0);
            while (swipeSumCursor.moveToNext())
            {
                swipeSum = new BigDecimal(swipeSumCursor.getString(swipeSumCursor.getColumnIndex("amounts_sum")));
            }
            // 保留2位小数
            swipeSum = swipeSum.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);
            swipeTotal = swipeTotal.add(swipeSum);


            //数据表,
            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_not_paid_list, null);
            // 序号
            TableCellTextView cell =  cellLineLayout.findViewById(R.id.list_no);
            String tmpVal = String.valueOf(number);
            cell.setText(tmpVal);
            // 卡名
            cell =  cellLineLayout.findViewById(R.id.list_card_name);
            tmpVal = cardName;
            cell.setText(tmpVal);
            // 还款日
            cell = cellLineLayout.findViewById(R.id.list_payment_date);
            tmpVal = String.valueOf(repaymentDate);
            cell.setText(tmpVal);
            // 还款额
            cell =  cellLineLayout.findViewById(R.id.list_not_paid_sum);
            tmpVal = String.valueOf(notPaidSum);
            cell.setText(tmpVal);
            // 刷卡总额
            cell =  cellLineLayout.findViewById(R.id.list_swipe_total);
            tmpVal = String.valueOf(swipeSum);
            cell.setText(tmpVal);
            // 卡号
            cell =  cellLineLayout.findViewById(R.id.list_card_no);
            cell.setText(cardNo);
            // 银行
            cell =  cellLineLayout.findViewById(R.id.list_bank);
            cell.setText(bank);
            number++;

            cellLayout.addView(cellLineLayout);


        } // end while

        // 合计
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_not_paid_list, null);
        // 序号
        TableCellTextView cell =  cellLineLayout.findViewById(R.id.list_no);
        String tmpVal = "合计";
        cell.setText(tmpVal);
        // 卡名
        cell =  cellLineLayout.findViewById(R.id.list_card_name);
        tmpVal = "";
        cell.setText(tmpVal);
        // 还款日
        cell = cellLineLayout.findViewById(R.id.list_payment_date);
        tmpVal = "";
        cell.setText(tmpVal);
        // 还款额
        cell =  cellLineLayout.findViewById(R.id.list_not_paid_sum);
        tmpVal = String.valueOf(notPaidTotal);
        cell.setText(tmpVal);
        // 刷卡总额
        cell =  cellLineLayout.findViewById(R.id.list_swipe_total);
        tmpVal = String.valueOf(swipeTotal);
        cell.setText(tmpVal);
        // 卡号
        cell =  cellLineLayout.findViewById(R.id.list_card_no);
        cell.setText("");
        // 银行
        cell =  cellLineLayout.findViewById(R.id.list_bank);
        cell.setText("");

        cellLayout.addView(cellLineLayout);
    }

}
