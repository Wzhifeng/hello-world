package cn.dao;

import cn.BaseTest;
import cn.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ProductCategoryDao 测试类
 */
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    /**
     * 测试查询商品分类列表
     */
    @Test
    public void testQueryProductCategoryList(){
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(8);
        System.out.println(productCategoryList.size());
    }
}
