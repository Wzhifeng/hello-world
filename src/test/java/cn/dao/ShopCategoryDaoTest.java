package cn.dao;

import cn.BaseTest;
import cn.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * ShopCategoryDao 测试
 */
public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    /**
     * 测试ShopCategoryDao插入商铺类别
     */
    @Test
    public void testInsertShopCategory(){
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryName("shopCategoryName1");
        shopCategory.setShopCategoryDesc("shopCategoryDesc1");
        shopCategory.setShopCategoryImg("shopCategoryImg1");
        shopCategory.setPriority(100);
        shopCategory.setParentId(0);
        int effectedNum = shopCategoryDao.insertShopCategory(shopCategory);
        System.out.println(effectedNum);
        System.out.println(shopCategory.toString());
    }

    /**
     * 测试ShopCategoryDao更新商铺类别
     */
    @Test
    public void testUpdateShopCategory(){
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1);
        shopCategory.setShopCategoryName("asss");
        shopCategory.setUpdateTime(new Date());

        int num = shopCategoryDao.updateShopCategory(shopCategory);
        System.out.println(num);
    }

    /**
     * 测试ShopCategoryDao查询商铺分类列表
     */
    @Test
    public void testQueryShopCategoryList(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategoryList();
        System.out.println(shopCategoryList.size());
    }

}
