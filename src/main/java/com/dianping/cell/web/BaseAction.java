package com.dianping.cell.web;

import com.dianping.combiz.web.action.AvatarAction;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by withnate on 15-3-26.
 */
public class BaseAction extends AvatarAction implements Preparable {

    @Override
    public void prepare() throws Exception {

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control",
                "must-revalidate, no-cache, private");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");

    }
}
