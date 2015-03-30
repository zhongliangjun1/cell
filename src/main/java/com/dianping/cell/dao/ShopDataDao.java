package com.dianping.cell.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.cell.bean.ShopCategory;
import com.dianping.cell.bean.ShopDto;

import java.util.List;


/**
 * Created by withnate on 14-10-17.
 */
public interface ShopDataDao extends GenericDao {

    @DAOAction(action = DAOActionType.QUERY)
    public List<ShopDto> loadShop(@DAOParam("lastShopId") int lastShopId);

    @DAOAction(action = DAOActionType.LOAD)
    public ShopDto loadSingleShop(@DAOParam("shopId") int shopId);

    @DAOAction(action = DAOActionType.QUERY)
    public List<ShopCategory> loadShopCategory(@DAOParam("shopids") List<Integer> shopids);

    @DAOAction(action = DAOActionType.QUERY)
    public ShopCategory loadSingleShopCategory(@DAOParam("shopId") int shopId);

    @DAOAction(action = DAOActionType.QUERY)
    public List<ShopDto> loadIncreaseShop(@DAOParam("lastShopId") int lastShopId,@DAOParam("beginTime") String beginTime,@DAOParam("endTime") String endTime);

}
