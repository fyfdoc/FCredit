package com.fcredit.ui.repaymentmark;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fcredit.R;
import com.fcredit.model.bean.credit.RepaymentMark;
import com.fcredit.util.AppConstaint;
import com.fcredit.util.DBHelper;
import com.fcredit.util.AppUtil;
import com.fcredit.widget.table.TableCellTextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 还款标记
 */
public class RepaymentMarkFragment extends Fragment {
    // 显示数据表的Layout
    private LinearLayout cellLayout;
    private RelativeLayout cellLineLayout;
    private String[] ColName={"标记", "序号","卡名/贷款名", "还款日", "本期应还", "卡号", "银行"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repayment_mark, container, false);


        // 获取显示表格的Layout
        cellLayout = view.findViewById(R.id.repaymentMarkTable);

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

    private void initData()
    {
        // 数据库
        DBHelper dbHelper = new DBHelper(getContext(), AppConstaint.DB_NAME, null, DBHelper.dbVer);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        //初始化标题,
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_repayment_mark_list, null);

        // 标记
        TableCellTextView title =  cellLineLayout.findViewById(R.id.list_mark);
        title.setText(ColName[0]);

        // 序号
        title =  cellLineLayout.findViewById(R.id.list_no);
        title.setText(ColName[1]);

        // 卡名/贷款名
        title =  cellLineLayout.findViewById(R.id.list_credit_name);
        title.setText(ColName[2]);

        // 还款日
        title = cellLineLayout.findViewById(R.id.list_payment_date);
        title.setText(ColName[3]);

        // 还款额
        title =  cellLineLayout.findViewById(R.id.list_not_paid_sum);
        title.setText(ColName[4]);

        // 卡号
        title =  cellLineLayout.findViewById(R.id.list_card_no);
        title.setText(ColName[5]);

        // 银行
        title =  cellLineLayout.findViewById(R.id.list_bank);
        title.setText(ColName[6]);


        cellLayout.addView(cellLineLayout);


        // 还款标记表需要合并“信用卡信息表”和“贷款信息表”的信息，并且信用卡还要统计还款额度

        // 记录list
        List<RepaymentMark> markList = new ArrayList<>();

        // 信用卡信息表并统计还款额度
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
        // 循环卡片信息表中的每张卡，然后再根据刷卡记录表进行统计
        String strSql = "SELECT * FROM t_credit_info ORDER BY repayment_date ASC";
        Cursor cursorCardInfo = db.rawQuery(strSql, null);
        while (cursorCardInfo.moveToNext())
        {
            // 卡片记录id
            String id = cursorCardInfo.getString(cursorCardInfo.getColumnIndex("id"));
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
            if (today <= statementDate)
            {
                // 前2月
                strSql += " AND swipe_date>='" + str2MonthDate + "'";
                strSql += " AND swipe_date<='" + str1MonthStatementDate + "'";
            }
            else
            {
                // 前1月
                strSql += " AND swipe_date>='" + str1MonthDate + "'";
            }
            strSql += " GROUP BY card_id";

            Cursor notPaidSumCursor = db.rawQuery(strSql, new String[]{id});

            // 本期应还总额
            BigDecimal notPaidSum = new BigDecimal(0);
            while (notPaidSumCursor.moveToNext())// 只有一条记录
            {

                notPaidSum = new BigDecimal(notPaidSumCursor.getString(notPaidSumCursor.getColumnIndex("amounts_sum")));
            }
            // 保留2位小数
            notPaidSum = notPaidSum.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);
            notPaidTotal = notPaidTotal.add(notPaidSum);

            // 组装记录
            RepaymentMark repaymentMark = new RepaymentMark();
            repaymentMark.setCreditId(id);
            repaymentMark.setCreditName(cardName);
            repaymentMark.setPaymentDate(String.valueOf(repaymentDate));
            repaymentMark.setNotPaidSum(notPaidTotal);
            repaymentMark.setCardNo(cardNo);
            repaymentMark.setMark(2);// 未还
            repaymentMark.setBankName(bank);

            markList.add(repaymentMark);

        } // end while

        // 贷款记录表
        strSql = "SELECT * FROM t_credit_mang ORDER BY repayment_date ASC";
        Cursor cursorCreditInfo = db.rawQuery(strSql, null);
        while (cursorCreditInfo.moveToNext()) {
            // 卡片记录id
            String id = cursorCreditInfo.getString(cursorCreditInfo.getColumnIndex("id"));
            // 贷款名称
            String creditName = cursorCreditInfo.getString(cursorCreditInfo.getColumnIndex("credit_name"));
            String bank = cursorCreditInfo.getString(cursorCreditInfo.getColumnIndex("bank_name"));
            // 卡片还款日
            int repaymentDate = cursorCreditInfo.getInt(cursorCreditInfo.getColumnIndex("repayment_date"));
            String strRepayMonth = cursorCreditInfo.getString(cursorCreditInfo.getColumnIndex("repay_month"));

            // 组装记录
            RepaymentMark repaymentMark = new RepaymentMark();
            repaymentMark.setCreditId(id);
            repaymentMark.setCreditName(creditName);
            repaymentMark.setPaymentDate(String.valueOf(repaymentDate));
            repaymentMark.setNotPaidSum(new BigDecimal(strRepayMonth));
            repaymentMark.setCardNo("");
            repaymentMark.setMark(2);// 未还
            repaymentMark.setBankName(bank);

            markList.add(repaymentMark);

        } // end while

        // 根据“还款标记表”设置还款标志
        // 是否为垃圾记录
        boolean isDirt = false;
        strSql = "SELECT * FROM t_repayment_mark ";
        Cursor cursorMark = db.rawQuery(strSql, null);
        while (cursorMark.moveToNext()) {
            String id = cursorMark.getString(cursorMark.getColumnIndex("id"));
            String strCreditId = cursorMark.getString(cursorMark.getColumnIndex("credit_id"));

            isDirt = true;
            // 设置还款标志
            for (RepaymentMark item : markList) {
                if (item.getCreditId().equals(strCreditId))
                {
                    // 已还
                    item.setMark(1);
                    isDirt = false;
                }
            }

            // 清除垃圾数据
            if (isDirt == true)
            {
                strSql = "DELETE FROM t_repayment_mark WHERE id=? ";
                String[] values = new String[] {id};

                db.execSQL(strSql, values);
            }

        } // end while

        // 按还款日排序
        Collections.sort(markList);

        int intIndex = 1;
        BigDecimal paymentTotal = new BigDecimal(0);
        // 显示数据
        for (RepaymentMark item: markList) {
            //行数据,
            cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_body_repayment_mark_list, null);
            if (intIndex % 2 == 1)
            {
                cellLineLayout.setBackgroundColor(getResources().getColor(R.color.white_table_line));
            }

            // 标记
            Switch markSwitch = cellLineLayout.findViewById(R.id.list_mark);
            markSwitch.setId(intIndex+100);// 类似按钮等有状态的控件一定要设置上id，否则系统会给设置上重复的id，页面切换设置状态时会崩溃
            if (item.getMark() == 1) {
                markSwitch.setChecked(true);
            } else
            {
                markSwitch.setChecked(false);
            }
            // 按钮切换事件
            markSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    String strSql;
                    RelativeLayout line = (RelativeLayout)buttonView.getParent();
                    TextView txvCreditId = line.findViewById(R.id.list_credit_id);
                    String creditId = txvCreditId.getText().toString();

                    if (isChecked)
                    {
                        // 已还，添加记录
                        strSql = "INSERT INTO t_repayment_mark (id, credit_id) VALUES (?,?)";
                        db.execSQL(strSql, new String[] {AppUtil.getUUID(), creditId});

                        myToast("保存成功");
                    }
                    else
                    {
                        // 未还，删除记录
                        strSql = "DELETE FROM t_repayment_mark WHERE credit_id=?";
                        db.execSQL(strSql, new String[] {creditId});

                        myToast("保存成功");
                    }
                }
            });

            // 序号
            TableCellTextView cell =  cellLineLayout.findViewById(R.id.list_no);
            // 因为“标记”项设置了动态id，xml无法设置对齐方式，为了对齐才加了这么多空格
            cell.setText("                 " + String.valueOf(intIndex));

            // 卡名/贷款名id
            cell =  cellLineLayout.findViewById(R.id.list_credit_id);
            cell.setText(item.getCreditId());
            // 卡名/贷款名
            cell =  cellLineLayout.findViewById(R.id.list_credit_name);
            cell.setText(item.getCreditName());
            // 还款日
            cell = cellLineLayout.findViewById(R.id.list_payment_date);
            cell.setText(item.getPaymentDate());
            // 还款额
            cell =  cellLineLayout.findViewById(R.id.list_not_paid_sum);
            cell.setText(item.getNotPaidSum().toString());
            paymentTotal = paymentTotal.add(item.getNotPaidSum());
            // 保留2位小数
            paymentTotal = paymentTotal.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP);

            // 卡号
            cell =  cellLineLayout.findViewById(R.id.list_card_no);
            if (item.getCardNo() == null)
            {
                cell.setText("");
            }
            else
            {
                cell.setText(String.valueOf(item.getCardNo()));
            }
            // 银行
            cell =  cellLineLayout.findViewById(R.id.list_bank);
            cell.setText(item.getBankName());

            cellLayout.addView(cellLineLayout);
            intIndex++;
        }

        // 合计
        cellLineLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.table_header_repayment_mark_list, null);

        // 标记
        title =  cellLineLayout.findViewById(R.id.list_mark);
        title.setText("合计");
        // 序号
        title =  cellLineLayout.findViewById(R.id.list_no);
        title.setText("");
        // 卡名/贷款名
        title =  cellLineLayout.findViewById(R.id.list_credit_name);
        title.setText("");
        // 还款日
        title = cellLineLayout.findViewById(R.id.list_payment_date);
        title.setText("");
        // 还款额
        title =  cellLineLayout.findViewById(R.id.list_not_paid_sum);
        title.setText(paymentTotal.toString());
        // 卡号
        title =  cellLineLayout.findViewById(R.id.list_card_no);
        title.setText("");
        // 银行
        title =  cellLineLayout.findViewById(R.id.list_bank);
        title.setText("");
        //
        title = cellLineLayout.findViewById(R.id.list_credit_id);
        title.setText("");

        cellLayout.addView(cellLineLayout);

    }

    private void myToast(String strMsg)
    {
        Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
    }
}
