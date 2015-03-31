package com.dianping.cell;

import com.dianping.cell.bean.ShopDto;

/**
 * Created by withnate on 15-3-31.
 */
public class Test {

    public static void main(String[] args){
        ShopDto shopDto = new ShopDto();
        shopDto.setShopId(1111);
        shopDto.setShopType(10);
        shopDto.setMainCategoryId(null);
        if(100==shopDto.getMainCategoryId()){
            System.out.println(123);
        }
    }
}
