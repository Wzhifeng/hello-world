package cn.service;

import cn.BaseTest;
import cn.dto.ShopCategoryExecution;
import cn.entity.ShopCategory;
import cn.enums.ShopCategoryEnum;
import cn.util.ImageHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * ShopCategoryService 测试类
 */
public class ShopCategoryServiceTest extends BaseTest {
    @Autowired
    private ShopCategoryService shopCategoryService;


    /**
     * 测试增加商铺分类
     */
    @Test
    public void testAddShopCategory() throws FileNotFoundException {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryName("name2");
        shopCategory.setShopCategoryDesc("desc2");
        shopCategory.setPriority(100);
        shopCategory.setParentId(0);

        File img = new File("C:\\Users\\wuzhifeng\\Desktop\\test-image\\6.png");
        InputStream is = new FileInputStream(img);


        ImageHolder shopCategoryImg = new ImageHolder(img.getName(), is);
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.addShopCategory(shopCategory, shopCategoryImg);
        System.out.println(shopCategoryExecution.getState() == ShopCategoryEnum.SUCCESS.getState());
    }

    /**
     * 测试查询商铺分类列表
     */
    @Test
    public void testGetShopCategory(){
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.getShopCategoryList();
        System.out.println(shopCategoryExecution.getStateInfo());
        System.out.println(shopCategoryExecution.getShopCategoryList().size());
    }
}
