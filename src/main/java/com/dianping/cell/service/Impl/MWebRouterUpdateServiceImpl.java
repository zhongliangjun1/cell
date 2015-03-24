package com.dianping.cell.service.Impl;

import com.dianping.cell.policy.Type;
import com.dianping.cell.service.MWebRouterUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/24
 * Time: PM9:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MWebRouterUpdateServiceImpl implements MWebRouterUpdateService {

    @Override
    public void update(int shopId, Type type) {

        if ( shopId<0 || type==null ) return;

        ShardedJedis client = null;
        try {
            client = getResource();
            client.set("mobile:wap:m:web:shop:"+shopId, type.value); // mobile:wap:m:web:shop:50000
        } finally {
            returnResource(client);
        }
    }

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    private ShardedJedis getResource(){
        return shardedJedisPool.getResource();
    }

    private void returnResource(ShardedJedis resource){
        if (resource!=null) {
            shardedJedisPool.returnResource(resource);
        }
    }

}
