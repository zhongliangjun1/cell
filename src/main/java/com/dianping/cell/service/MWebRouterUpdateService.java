package com.dianping.cell.service;

import com.dianping.cell.policy.Type;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/24
 * Time: PM9:41
 * To change this template use File | Settings | File Templates.
 */
public interface MWebRouterUpdateService {

    public void update(int shopId, Type type);

    public String read(int shopId);

}
