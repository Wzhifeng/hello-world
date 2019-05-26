package cn.service;

import cn.BaseTest;
import cn.dto.ShopExecution;
import cn.entity.Shop;
import cn.enums.ShopStateEnum;
import cn.util.ImageHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * ShopService 测试类
 */
public class ShopServiceTest extends BaseTest{
    @Autowired
    private ShopService shopService;


    /**
     * 测试ShopService添加商铺
     */
    @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopOwnerId(11);
        shop.setShopCategoryId(1);
        shop.setShopName("shopName");
        shop.setShopDesc("shopDesc");
        shop.setShopAddr("shopAddr");
        shop.setShopPhone("3308388");

        File file = new File("C:\\Users\\wuzhifeng\\Desktop\\images\\item\\shop\\18\\2017060609110899956.jpg");
        InputStream is = new FileInputStream(file);
        ImageHolder shopImage = new ImageHolder(file.getName(), is);

        ShopExecution shopExecution = shopService.addShop(shop, shopImage);
        System.out.println(shopExecution.getState() == ShopStateEnum.SUCCESS.getState());
    }
}
