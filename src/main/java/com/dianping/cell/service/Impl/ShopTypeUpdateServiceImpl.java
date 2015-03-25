package com.dianping.cell.service.Impl;

import com.dianping.cat.Cat;
import com.dianping.cell.bean.ShopDto;
import com.dianping.cell.dao.ShopDataDao;
import com.dianping.cell.policy.Type;
import com.dianping.cell.service.MWebRouterUpdateService;
import com.dianping.cell.service.ShopTypeUpdateService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by withnate on 15-3-25.
 */
@Service
public class ShopTypeUpdateServiceImpl implements ShopTypeUpdateService {

    @Autowired
    ShopDataDao shopDataDao;

    @Autowired
    private MWebRouterUpdateService mWebRouterUpdateService;

    @Override
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
                        parseShopType(shopDto);
                        mWebRouterUpdateService.update(shopDto.getShopId(),shopDto.getType());
                    }

                    if(shopDtoList.size()<500){
                        break;
                    }
                }
            }catch (Exception e){
                failTimes++;
                Cat.logError("loadShop",e);
            }
        }

    }

    @Override
    public void dailyUpdateShopType() {
        try{
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Date date2 = new Date(date.getTime()-1000*3600*24);
            String beginTime = dataFormat.format(date2);
            String endTime = dataFormat.format(date);
            List<ShopDto> shopDtoList = shopDataDao.loadIncreaseShop(beginTime,endTime);
            if(CollectionUtils.isNotEmpty(shopDtoList)){
                for(ShopDto shopDto : shopDtoList){
                    parseShopType(shopDto);
                    mWebRouterUpdateService.update(shopDto.getShopId(),shopDto.getType());
                }
            }
        }catch (Exception e){
            Cat.logError("dailyUpdateShopType",e);
        }
    }

    public void parseShopType(ShopDto shopDto){
        if(shopDto.getShopType()==null){
            shopDto.setType(Type.MAIN);
        }else {
            switch (shopDto.getShopType()){
                case 25:
                    shopDto.setType(Type.MOVIE);
                    break;
                default:
                    shopDto.setType(Type.MAIN);
            }
        }
    }
}
