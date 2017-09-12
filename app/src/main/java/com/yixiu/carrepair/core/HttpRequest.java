package com.yixiu.carrepair.core;

import android.util.Log;

import com.base.frame.net.ActionCallbackListener;
import com.base.frame.net.ApiResponse;
import com.base.frame.net.retrofit.RetrofitHelper;
import com.yixiu.carrepair.bean.WXPayRequesParam;
import com.yixiu.carrepair.util.ErrorEvent;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by liuchengran on 2017/9/12.
 */

public class HttpRequest {
    private static HttpRequest instance;
    private ApiCore apiCore;
    private HttpRequest() {
        apiCore = RetrofitHelper.instance().getRetrofit().create(ApiCore.class);
    }

    public static HttpRequest getInstance() {
        if (instance == null) {
            instance = new HttpRequest();
        }

        return instance;
    }

    public Call<?> startWXPay(ActionCallbackListener<WXPayRequesParam> listener) {
        Call<ApiResponse<WXPayRequesParam>> call = apiCore.getWXPay();
        enqueue(call, listener);
        return call;
    }


    private <T> void enqueue(Call<ApiResponse<T>> call, final ActionCallbackListener<T> listener) {
        call.enqueue(new Callback<ApiResponse<T>>() {

            public void onResponse(Call<ApiResponse<T>> arg0, Response<ApiResponse<T>> arg1) {
                if (arg1.isSuccessful()) {
                    if (arg1.body().isSuccess()) {
                        listener.onSuccess(arg1.body().getData());
                    } else {
                        reportError(listener, arg1.body().getCode(), arg1.body().getMessage());
                    }
                } else {
                    try {
                        Log.d(TAG, "report Error:" + arg1.errorBody().string());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    reportError(listener, ErrorEvent.CODE_CONNECT_TIMEOUT, "服务器连接错误");
                }
            }

            public void onFailure(Call<ApiResponse<T>> arg0, Throwable arg1) {
                listener.onFailure(ErrorEvent.CODE_CONNECT_TIMEOUT, ErrorEvent.MSG_CONNECT_TIMEOUT);
            }
        });

    }

    private void reportError(ActionCallbackListener<?> listener, int errorCode, String errorMsg) {
        listener.onFailure(errorCode, errorMsg);
    }
}
