package com.dianping.dao;

import com.dianping.AbstractTest;
import com.dianping.cell.bean.ShopDto;
import com.dianping.cell.dao.ShopDataDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class DaoTest extends AbstractTest {



    @Autowired
    ShopDataDao shopDataDao;


    @Test
    public void testLoadUserByUserId() {

        List<ShopDto> shopDtoList = shopDataDao.loadShop(0);

        System.out.println("read result : "+shopDtoList.size());


    }

}
