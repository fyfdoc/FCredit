package com.fcredit.model.bean.credit;

import java.math.BigDecimal;

/**
 * 还款记录
 */
public class RepaymentMark implements Comparable<RepaymentMark>{
    // 信用卡/贷款记录id
    private String creditId;
    // 标记
    private int mark;
    // 卡名/贷款名
    private String creditName;
    // 还款日
    private String paymentDate;
    // 还款额
    private BigDecimal notPaidSum;
    // 卡号
    private String cardNo;
    // 银行
    private String bankName;

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getNotPaidSum() {
        return notPaidSum;
    }

    public void setNotPaidSum(BigDecimal notPaidSum) {
        this.notPaidSum = notPaidSum;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    @Override
    public int compareTo(RepaymentMark repaymentMark)
    {
        if (Integer.parseInt(this.paymentDate) > Integer.parseInt(repaymentMark.getPaymentDate()))
        {
            return 1;
        }

        return -1;
    }
}
