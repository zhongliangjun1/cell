package com.dianping.cell.checker;

import com.dianping.cat.Cat;
import com.dianping.cell.bean.ShopDto;
import com.dianping.cell.dao.ShopDataDao;
import com.dianping.cell.handler.MWebRouterHandler;
import com.dianping.combiz.spring.util.LionConfigUtils;
import com.google.common.primitives.Ints;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by withnate on 15-3-25.
 */
public class ShopUpdateChecker {

    private Logger logger = Logger.getLogger(this.getClass());

    private volatile static boolean flag = true;

    @Autowired
    private ShopDataDao shopDataDao;

    @Autowired
    private MWebRouterHandler mWebRouterHandler;


    public void process(){

        if ( "n".equals(LionConfigUtils.getProperty("cell.ShopUpdateChecker.switch", "n")) )
            return;

        if ( !should_I_process() )
            return;

        if( flag ) {

            flag = false;

            logger.info("ShopUpdateChecker Begin : " + getNowTime());

            if (isTimeToUpdateAll()) {
                updateAllShopType();
            } else {
                dailyUpdateShopType();
            }

            flag = true;

            logger.info("ShopUpdateChecker End : " + getNowTime());

        }

    }

    private boolean should_I_process() {
        boolean result = false;

        try {
            InetAddress ia = InetAddress.getLocalHost();
            String serverName = ia.getHostName(); //获取计算机主机名
            if( serverName.contains(LionConfigUtils.getProperty("cell.checker.process.machine.name")) ){
                result = true;
            }
        }catch (Exception e){
            logger.error("get serverName error ", e);
        }

        return result;
    }

    private String getNowTime() {
        Date date = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dataFormat.format(date).toString();
    }

    private boolean isTimeToUpdateAll() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
        int time = Ints.tryParse(LionConfigUtils.getProperty("cell.TimeToUpdateAll.week"));
        if ( day==time ) {
            return true;
        } else {
            return false;
        }
    }

    private void updateAllShopType() {
        int lastShopId = 0;
        int failTimes = 0;
        while(failTimes<5){
            if ( "n".equals(LionConfigUtils.getProperty("cell.ShopUpdateChecker.switch", "n")) )
                return;
            try{
                List<ShopDto> shopDtoList = shopDataDao.loadShop(lastShopId);
                if(shopDtoList==null){
                    failTimes++;
                    continue;
                }
                if(CollectionUtils.isNotEmpty(shopDtoList)){


                    List<Integer> shopIds = new ArrayList<Integer>();
                    for(ShopDto shopDto : shopDtoList){
                        shopIds.add(shopDto.getShopId());
                    }

                    mWebRouterHandler.execute(shopIds);

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

    private void dailyUpdateShopType() {
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
                    List<Integer> shopIds = new ArrayList<Integer>();
                    for(ShopDto shopDto : shopDtoList){
                        shopIds.add(shopDto.getShopId());
                    }
                    mWebRouterHandler.execute(shopIds);
                    if(shopDtoList.size()<500){
                        break;
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
