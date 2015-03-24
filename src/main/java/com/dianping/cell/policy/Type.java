package com.dianping.cell.policy;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/24
 * Time: PM8:01
 * To change this template use File | Settings | File Templates.
 */
public enum Type {

    MAIN("main"),
    SHOPPING("shopping"),
    WEDDING("wedding"),
    HOTEL("hotel"),
    BACKUP("backup");

    public String value;

    private Type(String value) {
        this.value = value;
    }

}
