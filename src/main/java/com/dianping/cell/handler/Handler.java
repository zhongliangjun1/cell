package com.dianping.cell.handler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

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

    protected abstract void handle(List<Integer> shopIds);

    public boolean execute(int shopId) {
        try {
            handle(shopId);
            return true;
        } catch (Exception e) {
            logger.error("handle "+ shopId +" with some error", e);
            return false;
        }
    }

    public boolean execute(List<Integer> shopIds) {

        if (CollectionUtils.isEmpty(shopIds))
            return false;

        try {
            handle(shopIds);
            return true;
        } catch (Exception e) {
            logger.error("handle "+ StringUtils.join(shopIds, ",") +" with some error", e);
            return false;
        }

    }


}
