package cn.service;

import cn.dto.ProductExecution;
import cn.entity.Product;
import cn.util.ImageHolder;

import java.util.List;
import java.util.Map;

/**
 * ProductService 接口
 */
public interface ProductService {
    ProductExecution addProduct(Product product, ImageHolder productImg);

    ProductExecution getProductList(Integer shopId);

    ProductExecution getProductByCondition(Product productCondition);

    ProductExecution modifyProduct(Product product, ImageHolder productImg);

    boolean deleteProduct(int productId);

    boolean changeProductStatus(int productId);

    Integer addPraise(Integer productId);

    Product getProductById(Integer productId);

    boolean addSales(Integer productId, Integer number);

    Map<String, Double> getIncomeByCondition(Integer shopId, Long date);

    List<Product> getSalesByCondition(Integer shopId, Long timestamp, String salesScope);
}
