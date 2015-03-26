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
            String key = getRedisKey(shopId);
            client.set(key, type.value);
        } finally {
            returnResource(client);
        }
    }

    @Override
    public String read(int shopId) {
        if ( shopId<0  ) return null;

        ShardedJedis client = null;
        String value = null;
        try {
            client = getResource();
            String key = getRedisKey(shopId);
            value = client.get(key);
        } finally {
            returnResource(client);
        }

        return value;
    }

    private String getRedisKey(int shopId) {
        return "mobile:wap:m:web:shop:" + shopId;  // mobile:wap:m:web:shop:50000
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
