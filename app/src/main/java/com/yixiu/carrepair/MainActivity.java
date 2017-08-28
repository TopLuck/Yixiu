package com.yixiu.carrepair;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;

import com.base.db.AccountInfoRealm;
import com.base.db.domain.AccountInfo;
import com.umeng.analytics.MobclickAgent;
import com.yixiu.carrepair.util.JPushUtil;

public class MainActivity extends AppCompatActivity {
    private JPushUtil jpush;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccountInfoRealm accountInfoRealm = new AccountInfoRealm();
        accountInfoRealm.open();
        AccountInfo accountInfo = accountInfoRealm.getLoginInfo();
        if (accountInfo == null) {
            Toast.makeText(this, "无人员信息", Toast.LENGTH_SHORT).show();
            accountInfo = new AccountInfo();
            accountInfo.setName("liuchengran");
            accountInfoRealm.addAccountInfo(accountInfo);
        } else {
            Toast.makeText(this, "人员名字：" + accountInfo.getName(), Toast.LENGTH_SHORT).show();
        }

        accountInfoRealm.close();
        initJpush();
        initLeftMenu();
    }

    private void initJpush() {
        jpush = new JPushUtil();
        jpush.setAlias("lcr");
    }

    private void initLeftMenu() {
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.dl_left);
        //mNavigationView = (NavigationView)findViewById(R.id.main_navigation);
        toolbar.setTitle("Toolbar");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                //将侧边栏顶部延伸至status bar
                mDrawerLayout.setFitsSystemWindows(true);
                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                mDrawerLayout.setClipToPadding(false);
            }
        }

     /*   mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
