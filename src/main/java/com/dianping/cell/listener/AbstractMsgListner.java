package com.dianping.cell.listener;

import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.MessageListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-28
 * Time: PM2:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMsgListner implements MessageListener {

    private Logger logger = Logger.getLogger(this.getClass());

    protected Map<String,Object> parseMessage(Message message){
        Map<String,Object> msg = null;
        if (message!=null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                msg = mapper.readValue(message.getContent().toString(),Map.class);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return msg;
    }


}
