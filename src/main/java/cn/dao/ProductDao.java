package cn.dao;

import cn.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProductDao 接口
 */
public interface ProductDao {
    /**
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct(@Param("product") Product product);

    /**
     * 更新商品
     * @param product
     * @return
     */
    int updateProduct(@Param("product")Product product);

    /**
     * 查询商品列表
     * @return
     */
    List<Product> queryProductList(Integer shopId);

    /**
     * 根据条件查询商品
     * @param productCondition
     * @return
     */
    List<Product> queryProductByCondition(@Param("productCondition") Product productCondition);

    /**
     * 根据productId删除商品
     * @param productId
     * @return
     */
    int deleteProduct(int productId);

    Product queryProductById(Integer productId);
}
