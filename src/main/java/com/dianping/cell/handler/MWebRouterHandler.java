package com.dianping.cell.handler;

import com.dianping.cell.policy.MWebRouterPolicy;
import com.dianping.cell.policy.Type;
import com.dianping.cell.service.MWebRouterUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/24
 * Time: PM8:48
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MWebRouterHandler extends Handler {

    @Autowired
    private MWebRouterPolicy mWebRouterPolicy;
    @Autowired
    private MWebRouterUpdateService mWebRouterUpdateService;

    @Override
    public void handle(int shopId) {
        Type type = mWebRouterPolicy.judge(shopId);
        if ( type==null ) type = Type.MAIN;
        mWebRouterUpdateService.update(shopId, type);
    }
}
