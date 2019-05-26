package cn.dao;

import cn.entity.ShopCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ShopCartDao 接口
 */
public interface ShopCartDao {

    /**
     * 根据条件插入购物车
     * @param shopCart
     * @return
     */
    int insertShopCart(@Param("shopCart") ShopCart shopCart);

    /**
     * 根据条件查询shopCart
     * @param shopCartCondition
     * @return
     */
    List<ShopCart> queryShopCaryByCondition(@Param("shopCartCondition")ShopCart shopCartCondition);

    /**
     * 根据条件更新购物车
     * @param shopCartCondition
     * @return
     */
    int updateShopCart(@Param("shopCartCondition")ShopCart shopCartCondition);

    /**
     * 根据条件删除购物车
     * @param userId
     * @param productId
     * @return
     */
    int deleteShopCart(@Param("userId")Integer userId, @Param("productId")Integer productId);
}
