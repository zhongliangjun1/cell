package com.dianping.cell.bean;

import lombok.Data;

/**
 * Created by withnate on 15-3-30.
 */
@Data
public class ShopCategory implements  Comparable<ShopCategory>{
    private int shopId;
    private Integer mainCategoryId;

    public ShopCategory(){}

    public ShopCategory(int shopId){this.shopId = shopId;}

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }

        if(obj==null){
            return  false;
        }

        if(obj!=null&&obj instanceof ShopCategory ){
            if(this.shopId==((ShopCategory) obj).getShopId()){
                return true;
            }
        }
        return false;
    }


    @Override
    public int hashCode(){
        return shopId;
    }

    @Override
    public int compareTo(ShopCategory o) {
        return this.getShopId()-o.getShopId();
    }
}
