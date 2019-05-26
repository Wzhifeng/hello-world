package cn.dao;

import cn.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ShopDao 接口
 */
public interface ShopDao {
    /**
     * 根据id查询商铺
     * @param userId
     * @return
     */
    Shop queryShopByUserId(int userId);

    /**
     * 插入shop
     * @param shop
     * @return
     */
    int insertShop(@Param("shop") Shop shop);

    /**
     * 更新shop
     * @param shop
     * @return
     */
    int updateShop(@Param("shop") Shop shop);

    /**
     * 查询shop列表
      * @return
     */
    List<Shop> queryShopList();


    /**
     * 根据商品id查询商品
     * @param shopId
     * @return
     */
    Shop queryShopById(Integer shopId);

    /**
     * 根据条件查询商铺
     * @param shopCondition
     * @return
     */
    List<Shop> queryShopByShopCondition(@Param("shopCondition") Shop shopCondition);

}
