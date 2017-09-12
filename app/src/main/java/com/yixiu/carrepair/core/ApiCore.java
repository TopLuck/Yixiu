package com.yixiu.carrepair.core;

import com.base.frame.net.ApiResponse;
import com.yixiu.carrepair.bean.WXPayRequesParam;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by liuchengran on 2017/9/12.
 */

public interface ApiCore {
    /**
     *  获取微信订单预支付号
     * @return
     */
    @GET("/wxpay/wxpay")
    Call<ApiResponse<WXPayRequesParam>> getWXPay();
}
