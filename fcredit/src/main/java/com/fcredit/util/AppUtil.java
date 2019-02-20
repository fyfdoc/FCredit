package com.fcredit.util;

import java.math.BigDecimal;
import java.util.UUID;

public class AppUtil {

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 将字符串格式的数据转换为BigDecimal数据类型，如果参数为""或null转换为0
     * @param strVal
     * @return
     */
    public static BigDecimal string2BigDecimal(String strVal)
    {
        String valTmp = strVal;
        if ("".equals(valTmp) || null == strVal)
        {
            valTmp = "0";
        }

        return  new BigDecimal(valTmp);
    }

    /**
     * 格式化小数点
     * @param val
     * @param round
     * @return
     */
    public static BigDecimal bigDecimalRound(BigDecimal val , int round)
    {
        BigDecimal rs = val;
        rs = rs.divide(new BigDecimal(1), BigDecimal.ROUND_UP, round);

        return rs;
    }
}
