package com.dianping.cell.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.cell.bean.ShopCategory;
import com.dianping.cell.bean.BaseShopDTO;

import java.util.List;


/**
 * Created by withnate on 14-10-17.
 */
public interface ShopDataDao extends GenericDao {


    //  base shop info

    @DAOAction(action = DAOActionType.QUERY)
    public List<BaseShopDTO> loadShopsByPreLastShopId(@DAOParam("lastShopId") int lastShopId);

    @DAOAction(action = DAOActionType.QUERY)
    public List<BaseShopDTO> loadShopsByShopIds(@DAOParam("shopIds") List<Integer> shopIds);

    @DAOAction(action = DAOActionType.LOAD)
    public BaseShopDTO loadSingleShop(@DAOParam("shopId") int shopId);

    @DAOAction(action = DAOActionType.QUERY)
    public List<BaseShopDTO> loadIncreaseShop(@DAOParam("lastShopId") int lastShopId,@DAOParam("beginTime") String beginTime,@DAOParam("endTime") String endTime);




    // shop category info

    @DAOAction(action = DAOActionType.LOAD)
    public ShopCategory loadShopMainCategoryByShopId(@DAOParam("shopId") int shopId);

    @DAOAction(action = DAOActionType.QUERY)
    public List<ShopCategory> loadShopMainCategoriesByShopIds(@DAOParam("shopIds") List<Integer> shopIds);


}
