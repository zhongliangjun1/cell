package com.dianping.cell.policy;

import com.dianping.shopremote.remote.ShoppingMallService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/24
 * Time: PM8:13
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MWebRouterPolicy implements Policy {

    private Logger logger = Logger.getLogger(MWebRouterPolicy.class);

    @Override
    public Type judge(int shopId) {

        if ( isMallShop(shopId) )
            return Type.SHOPPING;


        return Type.MAIN;
    }


    private boolean isMallShop(int shopId) {
        boolean result = false;
        try {
            result = shoppingMallService.hasMallShops(shopId);
        } catch (Exception e) {
            logger.error("shoppingMallService error", e);
        }
        return result;
    }




    @Autowired
    private ShoppingMallService shoppingMallService;

}
