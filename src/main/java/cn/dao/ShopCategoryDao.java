package cn.dao;

import cn.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ShopCategoryDao接口
 */
public interface ShopCategoryDao {
    /**
     * 插入商铺类别信息
     * @param shopCategory
     * @return
     */
    int insertShopCategory(@Param("shopCategory") ShopCategory shopCategory);


    /**
     * 更新商铺类别信息
     * @param shopCategory
     * @return
     */
    int updateShopCategory(@Param("shopCategory") ShopCategory shopCategory);


    /**
     * 获取商铺列表信息
     * @return
     */
    List<ShopCategory> queryShopCategoryList();

    /**
     * 根据id查询商铺列表
     * @param shopCategoryId
     * @return
     */
    ShopCategory queryShopCategoryById(Integer shopCategoryId);

    /**
     * 根据id删除商铺分类新信息
     * @param shopCategoryId
     * @return
     */
    int deleteShopCategoryById(int shopCategoryId);

}
