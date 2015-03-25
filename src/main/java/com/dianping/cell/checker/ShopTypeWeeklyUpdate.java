package com.dianping.cell.checker;

import com.dianping.cell.service.ShopTypeUpdateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by withnate on 15-3-25.
 */
public class ShopTypeWeeklyUpdate {

    @Autowired
    ShopTypeUpdateService shopTypeUpdateService;


    public void process(){
        Date date = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(dataFormat.format(date).toString());


//        Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_WEEK);
//
//        if(day==1){
//            shopTypeUpdateService.updateAllShopType();
//        }else {
//            shopTypeUpdateService.dailyUpdateShopType();
//        }

    }

}
