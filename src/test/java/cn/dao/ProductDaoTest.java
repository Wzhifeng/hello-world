package cn.dao;

import cn.BaseTest;
import cn.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ProductDao 测试类
 */
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    /**
     * 测试插入商品
     */
    @Test
    public void testInsertProduct() {
        Product product = new Product();
        product.setProductName("name");
        product.setProductDesc("desc");
        product.setProductImg("img");
        product.setPrice("100.00");
        product.setPriority(100);
        product.setShopId(8);
        product.setProductCategoryId(4);
        product.setPraise(0);
        product.setSales(0);
        product.setProductStatus(1);

        int i = productDao.insertProduct(product);
        System.out.println(product);
        System.out.println(i);
    }

    /**
     * 测试更新商品
     */
    @Test
    public void testUpdateProduct(){
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("updatename");
        product.setProductDesc("updatedesc");
        product.setProductImg("updateimg");
        product.setPrice("111");
        product.setPriority(99);
        product.setShopId(8);
        product.setProductCategoryId(5);
        product.setPraise(1);
        product.setSales(1);
        product.setProductStatus(0);

        int i = productDao.updateProduct(product);
        System.out.println(product);
        System.out.println(i);
    }

    /**
     * 测试根据条件查询商品
     */

    @Test
    public void testQueryProductByCondition(){
        Product productCondition = new Product();
        productCondition.setShopId(8);

        List<Product> productList = productDao.queryProductByCondition(productCondition);
        System.out.println(productList);
    }
}
