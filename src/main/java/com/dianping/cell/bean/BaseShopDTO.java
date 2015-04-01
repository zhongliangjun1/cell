package com.dianping.cell.bean;

import lombok.Data;

/**
 * Created by withnate on 15-3-25.
 */
@Data
public class BaseShopDTO {

    private int shopId;
    private int shopType;

    @Override
    public String toString() {
        return "BaseShopDTO{" +
                "shopId=" + shopId +
                ", shopType=" + shopType +
                '}';
    }
}
