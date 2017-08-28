package com.base.db.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by liuchengran on 2017/8/21.
 */

public class AccountInfo extends RealmObject {
    @PrimaryKey
    private int id = 1;

    private String uid;
    private String token;
    private String nickName;
    private String name;
    //性别 1：男 2：女
    private int sex;
    private long birthday;
    private String logo;//用户头像地址
    /**
     * 手机号
     */
    private String telephone;
    /**
     * json data
     */
    private String userInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
