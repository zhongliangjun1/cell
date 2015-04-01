package com.dianping.cell.policy;

import com.dianping.cell.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/4/1
 * Time: PM2:54
 * To change this template use File | Settings | File Templates.
 */
public class MWebRouterPolicyTest extends AbstractTest {

    @Autowired
    private MWebRouterPolicy mWebRouterPolicy;

    @Test
    public void test() {

        List<Integer> shopIds = new ArrayList<Integer>();
        shopIds.add(500000);   // 美食
        shopIds.add(4728270);  // 购物
        shopIds.add(1575769); // 结婚
        shopIds.add(1796965);  // 电影

        Map<Integer,Type> result = mWebRouterPolicy.judge(shopIds);

        System.out.println("500000 type is : " + result.get(500000));
        System.out.println("4728270 type is : " + result.get(4728270));
        System.out.println("1575769 type is : " + result.get(1575769));
        System.out.println("1796965 type is : " + result.get(1796965));

    }


}
