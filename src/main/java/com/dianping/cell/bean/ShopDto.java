package com.dianping.cell.bean;

import lombok.Data;

/**
 * Created by withnate on 15-3-25.
 */
@Data
public class ShopDto extends ShopCategory{

    private Integer shopType;

    @Override
    public String toString(){

        StringBuffer sb = new StringBuffer();
        sb.append("shopid:"+getShopId());
        sb.append(";shopType"+shopType);
        sb.append(";shopCategory:"+getMainCategoryId());

        return sb.toString();
    }

}
