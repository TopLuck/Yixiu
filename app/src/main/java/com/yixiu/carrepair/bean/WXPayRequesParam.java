package com.yixiu.carrepair.bean;

/**
 * Created by liuchengran on 2017/9/12.
 */

public class WXPayRequesParam {
    private String appid;
    private String noncestr;
    private String prepayid;
    private String sign;
    private String signType;
    private String timestamp;
    private String partnerid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    @Override
    public String toString() {
        return "WXPayRequesParam{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", sign='" + sign + '\'' +
                ", signType='" + signType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", partnerid='" + partnerid + '\'' +
                '}';
    }
}
