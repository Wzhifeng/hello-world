package cn.service;

import cn.dto.ShopCartExecution;
import cn.entity.ShopCart;

import java.util.List;

/**
 * ShopCartService 接口
 */
public interface ShopCartService {
    /**
     * 根据条件获取shopCary
     * @param shopCartCondition
     * @return
     */
    List<ShopCart> getShopCartByCondition(ShopCart shopCartCondition);

    /**
     * 增加shopCart
     * @param shopCartCondition
     * @return
     */
    ShopCartExecution addShopCart(ShopCart shopCartCondition);

    /**
     * 修改shopCart
     * @param shopCartCondition
     * @return
     */
    ShopCartExecution modifyShopCart(ShopCart shopCartCondition);

    /**
     * 根据productId与userId删除购物车
     * @param userId
     * @param productId
     * @return
     */
    boolean deleteShopCart(Integer userId, Integer productId);
}
