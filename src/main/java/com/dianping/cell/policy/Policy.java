package com.dianping.cell.policy;

import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/24
 * Time: PM7:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class Policy {

    public abstract Type judge(int shopId);

    public Map<Integer,Type> judge(List<Integer> shopIds) {
        Map<Integer,Type> result = new HashMap<Integer, Type>();
        if ( CollectionUtils.isNotEmpty(shopIds) ) {
            for (Integer shopId: shopIds) {
                result.put(shopId, judge(shopId));
            }
        }
        return result;
    }

}

