package com.dianping.cell.checker;

import com.dianping.cat.Cat;
import com.dianping.cell.bean.ShopDto;
import com.dianping.cell.dao.ShopDataDao;
import com.dianping.cell.handler.MWebRouterHandler;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by withnate on 15-3-25.
 */
public class ShopTypeWeeklyUpdate {



    @Autowired
    ShopDataDao shopDataDao;

    @Autowired
    private MWebRouterHandler mWebRouterHandler;


    public void process(){
        Date date = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(dataFormat.format(date).toString());


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(day==1){
            //updateAllShopType();
        }else {
            //dailyUpdateShopType();
        }

    }



    public void updateAllShopType() {
        int lastShopId = 0;
        int failTimes = 0;
        while(failTimes<5){
            try{
                List<ShopDto> shopDtoList = shopDataDao.loadShop(lastShopId);
                if(shopDtoList==null){
                    failTimes++;
                    continue;
                }
                if(CollectionUtils.isNotEmpty(shopDtoList)){

                    for(ShopDto shopDto : shopDtoList){
                        mWebRouterHandler.equals(shopDto.getShopId());
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

    public void dailyUpdateShopType() {
        try{
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Date date2 = new Date(date.getTime()-1000*3600*24);
            String beginTime = dataFormat.format(date2);
            String endTime = dataFormat.format(date);
            int lastShopId = 0;
            int failTimes = 0;

            while(failTimes<5){
                try{
                    List<ShopDto> shopDtoList = shopDataDao.loadIncreaseShop(lastShopId,beginTime,endTime);
                    if(shopDtoList==null){
                        failTimes++;
                        continue;
                    }
                    if(CollectionUtils.isNotEmpty(shopDtoList)){
                        for(ShopDto shopDto : shopDtoList){
                            mWebRouterHandler.equals(shopDto.getShopId());
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
        }catch (Exception e){
            Cat.logError("dailyUpdateShopType",e);
        }
    }

}
