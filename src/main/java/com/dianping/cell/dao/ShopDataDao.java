package com.dianping.cell.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.cell.bean.ShopDto;

import java.util.List;


/**
 * Created by withnate on 14-10-17.
 */
public interface ShopDataDao extends GenericDao {

    @DAOAction(action = DAOActionType.QUERY)
    public List<ShopDto> loadShop(@DAOParam("lastShopId") int lastShopId);

    @DAOAction(action = DAOActionType.QUERY)
    public List<ShopDto> loadIncreaseShop(@DAOParam("beginTime") String beginTime,@DAOParam("endTime") String endTime);

}
