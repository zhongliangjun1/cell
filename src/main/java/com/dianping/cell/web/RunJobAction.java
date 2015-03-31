package com.dianping.cell.web;

import com.dianping.cell.checker.ShopUpdateChecker;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by withnate on 15-3-31.
 */
public class RunJobAction extends BaseAction {

    @Autowired
    ShopUpdateChecker shopUpdateChecker;

    public void run(){
        shopUpdateChecker.process();
    }
}
