package cn.dao;

import cn.BaseTest;
import cn.entity.Shop;
import cn.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ShopDao 接口测试
 */
public class ShopDaoTest extends BaseTest{
    @Autowired
    private ShopDao shopDao;

    /**
     * 测试ShopDao根据商家id查询商品
     */
    @Test
    public void testQueryShopByUserId(){
        int userId = 1;
        Shop shop = shopDao.queryShopByUserId(userId);
        System.out.println(shop);
    }

    /**
     * 测试ShopDao插入商铺
     */
    @Test
    public void testInsertShop(){
        Shop shop = new Shop();

        shop.setShopOwnerId(15);
        shop.setShopCategoryId(1);
        shop.setShopName("shopName");
        shop.setShopDesc("shopDesc");
        shop.setShopAddr("shopAddr");
        shop.setShopPhone("3308388");
        shop.setEnableStatus(ShopStateEnum.SHOP_PASS_THE_AUDIT.getState());
        shop.setBusinessStatus(ShopStateEnum.SHOP_ON_BUSINESS.getState());

        int effectedNum = shopDao.insertShop(shop);
        System.out.println(effectedNum);
        System.out.println(shop);
    }

    /**
     * 测试ShopDao更新商铺
     */
    @Test
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setShopId(3);
        shop.setShopCategoryId(11);
        shop.setShopName("updateName");
        shop.setShopDesc("updateDesc");
        shop.setShopImg("updateImg");
        shop.setShopAddr("updateAddr");
        shop.setShopPhone("3333333");
        shop.setEnableStatus(ShopStateEnum.SHOP_NOT_PASS.getState());
        shop.setBusinessStatus(ShopStateEnum.SHOP_ON_REST.getState());

        int num = shopDao.updateShop(shop);
        System.out.println(num);
        System.out.println(shop);
    }
}
