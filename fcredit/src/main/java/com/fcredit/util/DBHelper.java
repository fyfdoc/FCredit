package com.fcredit.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;
    // 数据库版本
    public static int dbVer = 4;

    // 信用卡信息表
    public static final String CREATE_CREDIT_INFO_TABLE = "CREATE TABLE t_credit_info ("
            + " id text primary key"
            + " ,credit_name text" // 卡片名称
            + " ,credit_no text" // 卡号
            + " ,bank_name text" // 银行
            + " ,credit_limit integer" // 额度
            + " ,statement_date text" // 账单日
            + " ,repayment_date text" // 还款日
            + " ,credit_comment text)"; // 备注

    // 刷卡记录表
    public static final String CREATE_SWIPE_RECORD_INFO_TABLE="CREATE TABLE t_swipe_record_info "
            + " (id text primary key"
            + " ,card_id text" // 卡片记录id
            + " ,swipe_date date"
            + " ,amounts decimal(8, 2)"
            + " ,vendor_name text"
            + " ,comment text)";

    // 贷款管理表
    public static final String CREATE_CREDIT_MANG_TABLE = "CREATE TABLE t_credit_mang "
            + " (id text primary key"
            + " ,credit_name text" // 贷款名称
            + " ,bank_name text"
            + " ,repayment_date date" // 还款日
            + " ,repay_month decimal(8, 2)" // 每月应还
            + " ,interest_rate decimal(8, 3)" // 利率
            + " ,credit_limit decimal(8, 2)" // 贷款总额
            + " ,credit_length integer" // 贷款期限(月)
            + " ,draw_date date" // 提款日期
            + " ,current_end_date date" // 本期截止日
            + " ,credit_length_start_date date" // 贷款有效期(开始)
            + " ,credit_length_end_date date" // 贷款有效期(结束)
            + " ,comment text)";

    // 还款标记表
    public static final String CREATE_REPAYMENT_MARK_TABLE = "CREATE TABLE t_repayment_mark "
            + " (id text primary key"
            + " ,credit_id text)"; // 贷款记录id或信用卡记录id


    /**
     * 带全部参数的构造方法
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        mContext = context;
    }

    /**
     * 数据库已经创建过了， 则不会执行到，如果不存在数据库则会执行
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // 创建表
        db.execSQL(CREATE_CREDIT_INFO_TABLE);
        db.execSQL(CREATE_SWIPE_RECORD_INFO_TABLE);
        db.execSQL(CREATE_CREDIT_MANG_TABLE);
        db.execSQL(CREATE_REPAYMENT_MARK_TABLE);

        Toast.makeText(mContext, "create succeeded", Toast.LENGTH_SHORT).show();
    }

    /**
     * 创建数据库时不会执行，增大版本号升级时才会执行到
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //db.execSQL("delete from t_swipe_record_info");
        //db.execSQL(CREATE_SWIPE_RECORD_INFO_TABLE);
        // 删除表
        db.execSQL("DROP TABLE t_credit_info ");
        db.execSQL("DROP TABLE t_swipe_record_info ");
        db.execSQL("DROP TABLE t_credit_mang ");
        db.execSQL("DROP TABLE t_repayment_mark ");

        // 创建表
        db.execSQL(CREATE_CREDIT_INFO_TABLE);
        db.execSQL(CREATE_SWIPE_RECORD_INFO_TABLE);
        db.execSQL(CREATE_CREDIT_MANG_TABLE);
        db.execSQL(CREATE_REPAYMENT_MARK_TABLE);


        // 在这里面可以把旧的表 drop掉 从新创建新表，
        // 但如果数据比较重要更好的做法还是把旧表的数据迁移到新表上
        Toast.makeText(mContext, "onUpgrade oldVersion：" + oldVersion + " newVersion:" + newVersion, Toast.LENGTH_SHORT).show();
    }
}
