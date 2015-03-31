package com.dianping.cell.policy;

import com.dianping.cat.Cat;
import com.dianping.cell.bean.ShopCategory;
import com.dianping.cell.bean.ShopDto;
import com.dianping.cell.dao.ShopDataDao;
import com.dianping.shopremote.remote.ShoppingMallService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/24
 * Time: PM8:13
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MWebRouterPolicy extends Policy {

    private Logger logger = Logger.getLogger(MWebRouterPolicy.class);

    @Autowired
    private ShopDataDao shopDataDao;

    @Override
    public Type judge(int shopId) {


        if ( isMallShop(shopId) ) {
            return Type.BACKUP; //Type.SHOPPING; 暂未迁出
        }

        ShopDto shopDto = getShopDto(shopId);

        if ( isWeddingShop(shopDto) ) {
            return Type.BACKUP;
        }

        if ( isMovieShop(shopDto) ) {
            return Type.BACKUP;
        }


        return Type.MAIN;
    }

    @Override
    public Map<Integer,Type> judge(List<Integer> shopIds) {
        Map<Integer,Type> result = new HashMap<Integer, Type>();

        return result;
    }

    private ShopDto getShopDto(int shopId){
        ShopDto shopDto = null;
        try{
            shopDto = shopDataDao.loadSingleShop(shopId);
            ShopCategory shopCategory = shopDataDao.loadSingleShopCategory(shopId);
            if(shopCategory!=null&&shopDto!=null){
                shopDto.setMainCategoryId(shopCategory.getMainCategoryId());
            }
        }catch (Exception e){
            Cat.logError("load shopDto error",e);
            logger.error("load shopDto error", e);
        }
        return shopDto;
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

    private boolean isWeddingShop(ShopDto shopDto) {
        boolean result = false;
        try {
            if (shopDto != null) {

                int shopType = shopDto.getShopType() != null ? shopDto.getShopType().intValue() : 0;

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

    private boolean isMovieShop(ShopDto shopDto) {
        boolean result = false;
        try {
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

}
