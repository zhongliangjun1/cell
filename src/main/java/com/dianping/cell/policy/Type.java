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
    MOVIE("movie"),
    BACKUP("backup");

    public String value;

    private Type(String value) {
        this.value = value;
    }

    public static Type getType(String value){

        for(Type type : Type.values()){
            if(type.value.equals(value)){
                return type;
            }
        }

        return Type.MAIN;
    }


}
