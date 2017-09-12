package com.yixiu.carrepair.util;

/**
 * Created by liuchengran on 2017/9/12.
 */

public class ErrorEvent {
    static final public int PARAM_ILLEGAL = 1002; // 参数不合法
    static final public int USER_UNLOGIN = 1003;// 未登录

    static final public int CODE_CONNECT_TIMEOUT = 1003;//连接超时
    static final public int CODE_NET_UNCONNECTED = 1004;//网络未连接

    static final public int CODE_NO_DATA = 4;//没有数据

    public static String MSG_CONNECT_TIMEOUT = "连接超时";
    public static String MSG_NET_UNCONNECTED = "网络未连接";
}
