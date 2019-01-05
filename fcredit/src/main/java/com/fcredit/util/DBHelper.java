package com.fcredit.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static final String CREATE_CREDIT_INFO_TABLE = "CREATE TABLE t_credit_info ("
            + " id integer primary key autoincrement"
            + " ,credit_name text"
            + " ,credit_no text"
            + " ,bank_name"
            + " ,credit_limit"
            + " ,statement_date"
            + " ,repayment_date"
            + " ,credit_comment)";
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
        db.execSQL(CREATE_CREDIT_INFO_TABLE);

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
        // 在这里面可以把旧的表 drop掉 从新创建新表，
        // 但如果数据比较重要更好的做法还是把旧表的数据迁移到新表上
        Toast.makeText(mContext, "onUpgrade oldVersion：" + oldVersion + " newVersion:" + newVersion, Toast.LENGTH_SHORT).show();
    }
}
