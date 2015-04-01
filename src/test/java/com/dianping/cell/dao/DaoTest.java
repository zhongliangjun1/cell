package com.dianping.cell.dao;

import com.dianping.cat.Cat;
import com.dianping.cell.AbstractTest;
import com.dianping.cell.bean.ShopCategory;
import com.dianping.cell.bean.BaseShopDTO;
import com.dianping.cell.handler.MWebRouterHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class DaoTest extends AbstractTest {



    @Autowired
    private ShopDataDao shopDataDao;

    @Autowired
    private MWebRouterHandler mWebRouterHandler;


    @Test
    public void testLoadUserByUserId() {

        List<BaseShopDTO> shopDtoList = shopDataDao.loadShop(510000);
        List<Integer> shopIds = new ArrayList<Integer>();
        for(BaseShopDTO shopDto : shopDtoList){
            shopIds.add(shopDto.getShopId());
        }
        shopDtoList = (List<BaseShopDTO>) assembleShop(shopDtoList,shopIds);
        for(BaseShopDTO shopDto : shopDtoList){
            shopIds.add(shopDto.getShopId());
            System.out.println(shopDto.toString());
        }

//
//        System.out.println("read result : "+shopDtoList.size());
//
//
//        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        Date date2 = new Date(date.getTime()-1000*3600*24);
//        String beginTime = dataFormat.format(date2);
//        String endTime = dataFormat.format(date);
//        List<ShopDto> shopDtoList2 = shopDataDao.loadIncreaseShop(0,beginTime,endTime);
//
//        System.out.println("read result : "+shopDtoList2.size());


//        int lastShopId = 0;
//        int failTimes = 0;
//        int i=0;
//        while(failTimes<5&&i<5){
//            try{
//                List<ShopDto> shopDtoList = shopDataDao.loadShop(lastShopId);
//                if(shopDtoList==null){
//                    failTimes++;
//                    continue;
//                }
//                if(CollectionUtils.isNotEmpty(shopDtoList)){
//
////                    shopDtoList = (List<ShopDto>) assembleShop(shopDtoList);
//
//                    for(ShopDto shopDto : shopDtoList){
//                        mWebRouterHandler.execute(shopDto.getShopId());
//                    }
//
//                    if(shopDtoList.size()<500){
//                        break;
//                    }
//                }
//            }catch (Exception e){
//                failTimes++;
//                Cat.logError("loadShop", e);
//            }
//        }

    }

    public List<? extends ShopCategory> assembleShop(List<? extends  ShopCategory> shopList,List<Integer> shopIds) {
        try {
            List<ShopCategory> shopCategoryList = shopDataDao.loadShopCategory(shopIds);
            Collections.sort(shopCategoryList, new Comparator<ShopCategory>() {
                @Override
                public int compare(ShopCategory o1, ShopCategory o2) {
                    return o1.getShopId() - o2.getShopId();
                }
            });
            for (ShopCategory shop : shopList) {
                int index = Collections.binarySearch(shopCategoryList, new ShopCategory(shop.getShopId()));
                if (index > -1 && index < shopList.size()) {
                    shop.setMainCategoryId(shopCategoryList.get(index).getMainCategoryId());
                }
            }

        }catch (Exception e){
            System.out.println("assembleShop error "+e.getMessage());
            Cat.logError("assembleShop error", e);
        }
        return shopList;
    }


}
