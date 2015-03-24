package com.dianping.cell.listener;

import com.dianping.cell.handler.MWebRouterHandler;
import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.BackoutMessageException;
import com.google.common.primitives.Ints;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 15/3/20
 * Time: PM9:02
 * To change this template use File | Settings | File Templates.
 */
public class PoiChangeShopMsgListener extends AbstractMsgListner {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void onMessage(Message message) throws BackoutMessageException {

        Map<String, Object> msg = parseMessage(message);

        if ( msg==null ) return;

        Integer type = Ints.tryParse((String) msg.get("messageType"));

        if ( type==null ) return;

        Integer shopId = null;

        switch (type) {
            case 2 : // POI 合并商户
                shopId = Ints.tryParse((String) msg.get("ShopID"));
                break;
            case 4 : // POI 添加商户
                shopId = Ints.tryParse((String) msg.get("shopId"));
                break;
            case 5 : // POI 修改商户
                shopId = Ints.tryParse((String) msg.get("shopId"));
                break;
            default:
                break;
        }

        if ( shopId==null ) return;

        mWebRouterHandler.execute(shopId);


    }

    @Autowired
    private MWebRouterHandler mWebRouterHandler;


}
