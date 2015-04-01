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
        shopIds.add(500000);

        Map<Integer,Type> result = mWebRouterPolicy.judge(shopIds);

        System.out.println(result.get(500000));

    }


}
