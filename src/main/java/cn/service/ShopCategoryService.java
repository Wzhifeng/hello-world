package cn.service;

import cn.dto.ShopCategoryExecution;
import cn.entity.ShopCategory;
import cn.util.ImageHolder; /**
 * ShopCategoryService 接口
 */
public interface ShopCategoryService {
    ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder shopCategoryImage);

    ShopCategoryExecution getShopCategoryList();

    boolean deleteShopCategory(int shopCategoryId);

    ShopCategoryExecution getShopCategory(int shopCategoryId);

    ShopCategoryExecution updateShopCategory(ShopCategory shopCategory ,ImageHolder shopCategoryImg);
}
