package com.dianping.dao;

import com.dianping.AbstractTest;
import com.dianping.cell.bean.ShopDto;
import com.dianping.cell.dao.ShopDataDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DaoTest extends AbstractTest {



    @Autowired
    ShopDataDao shopDataDao;


    @Test
    public void testLoadUserByUserId() {

        List<ShopDto> shopDtoList = shopDataDao.loadShop(0);

        System.out.println("read result : "+shopDtoList.size());


        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date date2 = new Date(date.getTime()-1000*3600*24);
        String beginTime = dataFormat.format(date2);
        String endTime = dataFormat.format(date);
        List<ShopDto> shopDtoList2 = shopDataDao.loadIncreaseShop(0,beginTime,endTime);

        System.out.println("read result : "+shopDtoList2.size());


    }

}
