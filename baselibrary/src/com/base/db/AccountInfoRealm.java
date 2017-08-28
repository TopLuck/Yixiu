package com.base.db;

import com.base.db.domain.AccountInfo;
import com.base.frame.log.LogUtil;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.annotations.RealmModule;

/**
 * Created by liuchengran on 2017/8/21.
 */

public class AccountInfoRealm {
    private static final String LOGTAG = LogUtil.makeLogTag(AccountInfoRealm.class);
    private final RealmConfiguration realmConfig;
    private Realm realm;

    @RealmModule(library = true, classes = AccountInfo.class)
    public class AccountInfoModule {
    }

    public AccountInfoRealm() {
        realmConfig = new RealmConfiguration.Builder() // The app is responsible for calling `Realm.init(Context)`
                .name("accountinfo.realm")// So always use a unique name
                .schemaVersion(0)
                .modules(new AccountInfoModule())           // Always use explicit modules in library projects
                .build();

    }

    private RealmMigration realmMigration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof RealmMigration);
        }
    };


    public void deleteRealm() {
        Realm.deleteRealm(realmConfig);
    }

    public void open() {
        // Don't use Realm.setDefaultInstance() in library projects. It is unsafe as app developers can override the
        // default configuration. So always use explicit configurations in library projects.
        realm = Realm.getInstance(realmConfig);
    }


    public Realm getRealm() {
        return realm;
    }

    /**
     * 添加登录信息
     *
     * @param
     */
    public void addAccountInfo(final AccountInfo accountInfo) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(accountInfo);
            }
        });
    }

    /**
     * exit login
     *
     * @param
     */
    public void exitLogin() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();

            }
        });
    }

    /**
     * 获得登录信息
     *
     * @return
     */
    public AccountInfo getLoginInfo() {
        AccountInfo loginInfo = realm.where(AccountInfo.class).findFirst();
        if (loginInfo == null)
            return null;
        AccountInfo tmpLogin = realm.copyFromRealm(loginInfo);
        return tmpLogin;
    }

    public void close() {
        realm.close();
    }
}
