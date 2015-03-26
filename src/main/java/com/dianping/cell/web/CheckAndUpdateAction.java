package com.dianping.cell.web;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cell.policy.MWebRouterPolicy;
import com.dianping.cell.policy.Type;
import com.dianping.cell.service.MWebRouterUpdateService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by withnate on 15-3-26.
 */
public class CheckAndUpdateAction extends BaseAction{


    @Autowired
    private MWebRouterUpdateService mWebRouterUpdateService;

    @Autowired
    private MWebRouterPolicy mWebRouterPolicy;

    @Setter
    @Getter
    private String shopId;

    @Setter
    @Getter
    String type;

    @Getter
    List<String> list = new ArrayList<String>();


    public String execute(){

        for(Type ty : Type.values()){
            list.add(ty.value);
        }


        return SUCCESS;
    }



    public void read() throws IOException {

        JSONObject jsonObject = new JSONObject();


        if(StringUtils.isNumeric(shopId)){
            String result = mWebRouterUpdateService.read(Integer.valueOf(shopId));
            jsonObject.put("code","200");
            jsonObject.put("value",result);
        }else {
            jsonObject.put("code","201");
            jsonObject.put("errmsg","wrong shopid");
        }

        ServletActionContext.getResponse().getWriter().write(jsonObject.toString());



    }

    public void update() throws IOException {

        JSONObject jsonObject = new JSONObject();

        if(StringUtils.isNumeric(shopId)&&StringUtils.isNotBlank(type)){
            mWebRouterUpdateService.update(Integer.valueOf(shopId),Type.getType(type));
            jsonObject.put("code","200");
            jsonObject.put("msg","success");
        }else {
            jsonObject.put("code","201");
            jsonObject.put("errmsg","wrong shopid");
        }

        ServletActionContext.getResponse().getWriter().write(jsonObject.toString());

    }

}
