package cn.service;

import cn.BaseTest;
import cn.dto.ProductExecution;
import cn.entity.Product;
import cn.enums.ProductStateEnum;
import cn.util.ImageHolder;
import com.sun.org.apache.xerces.internal.util.PropertyState;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * ProductService测试类
 */
public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    /**
     * 测试添加商品
     * @throws FileNotFoundException
     */
    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        product.setProductName("name");
        product.setProductDesc("desc");
        product.setPrice("100.00");
        product.setPriority(100);
        product.setShopId(8);
        product.setProductCategoryId(5);

        File file = new File("C:\\Users\\wuzhifeng\\Desktop\\test-image\\7.jpg");
        InputStream is = new FileInputStream(file);

        ImageHolder productImg = new ImageHolder(file.getName(), is);
        ProductExecution productExecution = productService.addProduct(product, productImg);
        System.out.println(productExecution.getProduct());
        System.out.println(productExecution.getState() == ProductStateEnum.SUCCESS.getState());

    }
}
