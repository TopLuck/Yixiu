package com.yixiu.carrepair.pay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.base.frame.net.ActionCallbackListener;
import com.base.library.core.AbstractBaseToolbarCoreActivity;
import com.base.library.widget.CustomToast;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yixiu.carrepair.R;
import com.yixiu.carrepair.bean.WXPayRequesParam;
import com.yixiu.carrepair.core.HttpRequest;
import com.yixiu.carrepair.util.WXPayUtil;

public class WXPayActivity extends AbstractBaseToolbarCoreActivity implements IWXAPIEventHandler {
    private float account;
    private String payDetail;

    private final IWXAPI api =  WXAPIFactory.createWXAPI(this, WXPayUtil.APP_ID, false);

    private TextView txtOrder;//订单号
    private TextView txtPay;//订单金额
    private String TAG = "WXPayActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        api.registerApp(WXPayUtil.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected String getToolbarTitle() {
        return "订单支付";
    }

    @Override
    protected boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void dealloc() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initView() {

        findViewById(R.id.ll_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest.getInstance().startWXPay(new ActionCallbackListener<WXPayRequesParam>() {
                    @Override
                    public void onSuccess(WXPayRequesParam data) {
                        PayReq req = new PayReq();

                        req.appId = data.getAppid();
                        req.nonceStr = data.getNoncestr();
                        req.packageValue = "Sign=WXPay";
                        req.partnerId = data.getPartnerid();
                        req.prepayId = data.getPrepayid();
                        req.sign = data.getSign();
                        req.timeStamp = data.getTimestamp();


                        boolean isSucess = api.sendReq(req);
                        Log.d("WXPayActivity", "send sucess:" + isSucess + "data=" + data.toString() + " checkArgs=" + req.checkArgs());
                    }

                    @Override
                    public void onFailure(int errorEvent, String message) {
                        CustomToast.showCustomToast(WXPayActivity.this, message);
                    }
                });
            }
        });
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG, baseReq.toString());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e(TAG, baseResp.toString());
    }
}
