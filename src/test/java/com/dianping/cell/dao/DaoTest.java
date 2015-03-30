package com.dianping.cell.dao;

import com.dianping.cat.Cat;
import com.dianping.cell.AbstractTest;
import com.dianping.cell.bean.ShopDto;
import com.dianping.cell.handler.MWebRouterHandler;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DaoTest extends AbstractTest {



    @Autowired
    private ShopDataDao shopDataDao;

    @Autowired
    private MWebRouterHandler mWebRouterHandler;


    @Test
    public void testLoadUserByUserId() {

//        List<ShopDto> shopDtoList = shopDataDao.loadShop(0);
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


        int lastShopId = 0;
        int failTimes = 0;
        int i=0;
        while(failTimes<5&&i<5){
            try{
                List<ShopDto> shopDtoList = shopDataDao.loadShop(lastShopId);
                if(shopDtoList==null){
                    failTimes++;
                    continue;
                }
                if(CollectionUtils.isNotEmpty(shopDtoList)){

//                    shopDtoList = (List<ShopDto>) assembleShop(shopDtoList);

                    for(ShopDto shopDto : shopDtoList){
                        mWebRouterHandler.execute(shopDto.getShopId());
                    }

                    if(shopDtoList.size()<500){
                        break;
                    }
                }
            }catch (Exception e){
                failTimes++;
                Cat.logError("loadShop", e);
            }
        }

    }


}
