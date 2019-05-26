package cn.service;

import cn.dto.ProductCategoryExecution;
import cn.entity.Product;
import cn.entity.ProductCategory;
import cn.entity.Shop;

/**
 * ProductCategoryService 接口
 */
public interface ProductCategoryService {
    ProductCategoryExecution addProductCategory(ProductCategory productCategory);

    ProductCategoryExecution getProductCategoryList(Integer shopId);

    ProductCategoryExecution getProductCategory(int productCategoryId);

    ProductCategoryExecution modifyProductCategory(ProductCategory productCategory);

    boolean deleteProductCategory(int productCategoryId);

}
