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
public class UserChangeShopMsgListener extends AbstractMsgListner {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void onMessage(Message message) throws BackoutMessageException {

        try {

            Map<String, Object> msg = parseMessage(message);

            if ( msg==null || msg.get("type")==null || msg.get("shopId")==null )
                return;

            Integer type = Ints.tryParse(msg.get("type").toString());
            if ( type==null || type!=201 )
                return;

            // 前台用户添加
            Integer shopId = Ints.tryParse(msg.get("shopId").toString());

            mWebRouterHandler.execute(shopId);

        } catch (Exception e) {
            logger.error("UserChangeShopMsgListener Error", e);
        }


    }

    @Autowired
    private MWebRouterHandler mWebRouterHandler;


}
