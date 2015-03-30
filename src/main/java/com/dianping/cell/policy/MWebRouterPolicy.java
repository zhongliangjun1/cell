package com.dianping.cell.policy;

import com.dianping.shopremote.remote.ShopService;
import com.dianping.shopremote.remote.ShoppingMallService;
import com.dianping.shopremote.remote.dto.ShopDTO;
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

        if (isMallShop(shopId))
            return Type.BACKUP; //Type.SHOPPING; 暂未迁出

        if (isWeddingShop(shopId)) {
            return Type.BACKUP;
        }

        if (isMovieShop(shopId)) {
            return Type.BACKUP;
        }


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

    private boolean isWeddingShop(int shopId) {
        boolean result = false;

        try {
            ShopDTO shopDto = shopService.loadShop(shopId);
            if (shopDto != null) {

                int shopType = shopDto.getShopType() != null ? shopDto.getShopType().intValue() : 0;

                //wedding
                if (shopType == 55 || shopType == 70 || shopType == 90) {
                    result = true;
                }

                if (shopType == 50 && 6700 == shopDto.getMainCategoryId()) {
                    result = true;
                }

            }
        } catch (Exception e) {
            logger.error("shopService error", e);
        }

        return result;
    }

    private boolean isMovieShop(int shopId) {
        boolean result = false;

        try {
            ShopDTO shopDto = shopService.loadShop(shopId);
            if (shopDto != null && 136 == shopDto.getMainCategoryId()) {
                result = true;
            }
        } catch (Exception e) {
            logger.error("shopService error", e);
        }

        return result;
    }


    @Autowired
    private ShoppingMallService shoppingMallService;

    @Autowired
    private ShopService shopService;

}
