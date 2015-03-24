package com.dianping.cell.handler;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/24
 * Time: PM8:49
 * To change this template use File | Settings | File Templates.
 */
public abstract class Handler {

    protected Logger logger = Logger.getLogger(this.getClass());

    protected abstract void handle(int shopId);

    public boolean execute(int shopId) {
        try {
            handle(shopId);
            return true;
        } catch (Exception e) {
            logger.error("handle "+ shopId +" with some error", e);
            return false;
        }
    }

}
