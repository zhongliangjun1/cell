package com.dianping.cell.web;

import com.dianping.cell.policy.MWebRouterPolicy;
import com.dianping.cell.policy.Type;
import com.dianping.cell.service.MWebRouterUpdateService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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



    public void read(){



    }

    public void update(){


    }

}
