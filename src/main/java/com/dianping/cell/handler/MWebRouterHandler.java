package com.dianping.cell.handler;

import com.dianping.cell.policy.MWebRouterPolicy;
import com.dianping.cell.policy.Type;
import com.dianping.cell.service.MWebRouterUpdateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
        logger.info("handle shop : "+ shopId +" success");
    }

    @Override
    protected void handle(List<Integer> shopIds) {
        Map<Integer,Type> result =  mWebRouterPolicy.judge(shopIds);
        if ( result==null || result.isEmpty()  )
            return;

        for (Integer shopId : shopIds) {
            Type type = result.get(shopId);
            if ( type==null ) type = Type.MAIN;
            mWebRouterUpdateService.update(shopId, type);
        }

        logger.info("handle shops : " + StringUtils.join(shopIds, ",") + " success");
    }
}
