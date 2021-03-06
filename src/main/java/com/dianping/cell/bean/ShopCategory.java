package com.dianping.cell.bean;

import lombok.Data;

/**
 * Created by withnate on 15-3-30.
 */
@Data
public class ShopCategory {

    private int shopId;
    private int categoryId;

    @Override
    public String toString() {
        return "ShopCategory{" +
                "shopId=" + shopId +
                ", categoryId=" + categoryId +
                '}';
    }
}
