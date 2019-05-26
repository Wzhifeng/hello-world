package cn.dao;

import cn.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProductCategoryDao 接口
 */
public interface ProductCategoryDao {
    int insertProductCategory(@Param("productCategory") ProductCategory productCategory);

    List<ProductCategory> queryProductCategoryList(Integer shopId);

    ProductCategory queryProductCategoryById(int productCategoryId);

    int updateProductCategory(@Param("productCategory") ProductCategory productCategory);

    int deleteProductCategory(int productCategoryId);
}
