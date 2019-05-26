package cn.service;

import cn.dto.ShopExecution;
import cn.entity.Shop;
import cn.util.ImageHolder;

import java.util.List;

/**
 * ShopService 接口
 */
public interface ShopService {
    Shop getShopByUserId(Integer userId);

    ShopExecution addShop(Shop shop, ImageHolder shopImage);

    ShopExecution updateShop(Shop shop, ImageHolder shopImage);

    List<Shop> getShopList();

    Shop getShopById(Integer shopId);

    ShopExecution getShopListByShopCondition(Shop shopCondition);

    boolean changeShopStatus(Integer shopId);
}
