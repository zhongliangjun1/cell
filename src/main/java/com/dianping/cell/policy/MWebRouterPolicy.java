package com.dianping.cell.policy;

import com.dianping.cell.bean.DetailShopDTO;
import com.dianping.cell.bean.ShopCategory;
import com.dianping.cell.bean.BaseShopDTO;
import com.dianping.cell.dao.ShopDataDao;
import com.dianping.shopremote.remote.ShoppingMallService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
            return Type.BACKUP; // Type.SHOPPING; 暂未迁出
        }

        DetailShopDTO detailShopDTO = loadShop(shopId);

        if ( isWeddingShop(detailShopDTO) ) {
            return Type.BACKUP; // Type.WEDDING;
        }

        if ( isMovieShop(detailShopDTO) ) {
            return Type.BACKUP; // Type.MOVIE;
        }

        return Type.MAIN;
    }

    @Override
    public Map<Integer,Type> judge(List<Integer> shopIds) {

        Map<Integer,Type> result = new HashMap<Integer, Type>();

        try {

            List<DetailShopDTO> detailShopDTOs = loadShops(shopIds);

            if( CollectionUtils.isNotEmpty(detailShopDTOs) ){

                for( DetailShopDTO shopDTO : detailShopDTOs ){

                    int shopId = shopDTO.getShopId();

                    if ( isMallShop(shopId) ) {
                        result.put(shopId, Type.BACKUP);
                        continue;
                    }

                    if ( isWeddingShop(shopDTO) ) {
                        result.put(shopId, Type.BACKUP);
                        continue;
                    }

                    if ( isMovieShop(shopDTO) ) {
                        result.put(shopId, Type.BACKUP);
                        continue;
                    }

                    result.put(shopId, Type.MAIN);
                }

            }
        }catch (Exception e){
            logger.error("judge shopIds error",e);
        }

        return result;
    }

    private DetailShopDTO loadShop(int shopId) {
        DetailShopDTO detailShopDTO = null;
        try{
            BaseShopDTO baseShopDTO = shopDataDao.loadSingleShop(shopId);
            if ( baseShopDTO==null )
                return null;

            ShopCategory shopMainCategory = shopDataDao.loadShopMainCategoryByShopId(shopId);
            int mainCategoryId = baseShopDTO.getShopType();
            if ( shopMainCategory!=null )
                mainCategoryId = shopMainCategory.getCategoryId();

            detailShopDTO = new DetailShopDTO();
            detailShopDTO.setShopId(baseShopDTO.getShopId());
            detailShopDTO.setShopType(baseShopDTO.getShopType());
            detailShopDTO.setMainCategoryId(mainCategoryId);

        }catch (Exception e){
            logger.error("load shopDto error", e);
        }
        return detailShopDTO;
    }

    private List<DetailShopDTO> loadShops(List<Integer> shopIds) {

        List<DetailShopDTO> result = null;

        if ( CollectionUtils.isNotEmpty(shopIds) ) {

            List<BaseShopDTO> baseShopDTOs = shopDataDao.loadShopsByShopIds(shopIds);
            if ( CollectionUtils.isEmpty(baseShopDTOs) )
                return null;

            List<ShopCategory> shopMainCategories = shopDataDao.loadShopMainCategoriesByShopIds(shopIds);

            result = convert(baseShopDTOs, shopMainCategories);
        }

        return result;

    }

    private List<DetailShopDTO> convert(List<BaseShopDTO> baseShopDTOs, List<ShopCategory> shopMainCategories) {

        List<DetailShopDTO> result = new ArrayList<DetailShopDTO>();

        if ( CollectionUtils.isNotEmpty(baseShopDTOs) ) {

            Map<Integer, Integer> shopMainCategoryIdsRepo = toMap(shopMainCategories);

            for ( BaseShopDTO baseShopDTO : baseShopDTOs ) {

                Integer shopMainCategoryId = shopMainCategoryIdsRepo.get(baseShopDTO.getShopId());
                if ( shopMainCategoryId==null )
                    shopMainCategoryId = baseShopDTO.getShopType();

                DetailShopDTO detailShopDTO = new DetailShopDTO();
                detailShopDTO.setShopId(baseShopDTO.getShopId());
                detailShopDTO.setShopType(baseShopDTO.getShopType());
                detailShopDTO.setMainCategoryId(shopMainCategoryId);

                result.add(detailShopDTO);
            }
        }

        return result;

    }

    private Map<Integer, Integer> toMap(List<ShopCategory> shopMainCategories) {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        if ( CollectionUtils.isNotEmpty(shopMainCategories) ) {
            for ( ShopCategory shopCategory : shopMainCategories ) {
                map.put(shopCategory.getShopId(), shopCategory.getCategoryId());
            }
        }

        return map;
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

    private boolean isWeddingShop(DetailShopDTO detailShopDTO) {
        boolean result = false;
        try {
            if (detailShopDTO != null) {

                int shopType = detailShopDTO.getShopType();

                if (shopType == 55 || shopType == 70 || shopType == 90) {
                    result = true;
                }

                if (shopType == 50 && 6700 == detailShopDTO.getMainCategoryId()) {
                    result = true;
                }

            }
        } catch (Exception e) {
            logger.error("shopService error", e);
        }

        return result;
    }

    private boolean isMovieShop(DetailShopDTO detailShopDTO) {
        boolean result = false;
        try {
            if (detailShopDTO != null && 136 == detailShopDTO.getMainCategoryId()) {
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
