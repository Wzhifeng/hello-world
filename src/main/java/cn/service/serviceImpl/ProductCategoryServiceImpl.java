package cn.service.serviceImpl;

import cn.dao.ProductCategoryDao;
import cn.dto.ProductCategoryExecution;
import cn.entity.Product;
import cn.entity.ProductCategory;
import cn.enums.ProductCategoryStateEnum;
import cn.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ProductCategoryService 实现类
 */

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    /**
     * 增加商品分类
     *
     * @param productCategory
     * @return
     */
    @Override
    public ProductCategoryExecution addProductCategory(ProductCategory productCategory) {
        if (productCategory == null) {
            return new ProductCategoryExecution(ProductCategoryStateEnum.PRODUCT_CATEGORY_EMPTY);
        }

        //插入商品分类
        int effectedNum = productCategoryDao.insertProductCategory(productCategory);

        if (effectedNum <= 0) {
            return new ProductCategoryExecution(ProductCategoryStateEnum.ADD_PRODUCT_CATEGORY_FAIL);
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
        }
    }

    /**
     * 取出该商铺下的商品分类列表
     *
     * @param shopId
     * @return
     */
    @Override
    public ProductCategoryExecution getProductCategoryList(Integer shopId) {
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        if (productCategoryList.size() > 0) {
            return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList);
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.PRODUCT_CATEGORY_EMPTY);
        }
    }

    /**
     * 获取指定的商品分类信息
     *
     * @param productCategoryId
     * @return
     */
    @Override
    public ProductCategoryExecution getProductCategory(int productCategoryId) {
        ProductCategory productCategory = productCategoryDao.queryProductCategoryById(productCategoryId);
        if (productCategory != null) {
            return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategory);
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.PRODUCT_CATEGORY_EMPTY);
        }
    }

    /**
     * 更新商品分类
     *
     * @param productCategory
     * @return
     */
    @Override
    public ProductCategoryExecution modifyProductCategory(ProductCategory productCategory) {

        //更新时间
        productCategory.setUpdateTime(new Date());

        int effectedNum = productCategoryDao.updateProductCategory(productCategory);

        if (effectedNum <= 0) {
            return new ProductCategoryExecution(ProductCategoryStateEnum.UPDATE_PRODUCT_CATEGORY_FAIL);
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
        }
    }

    /**
     * 删除商品分类
     *
     * @param productCategoryId
     * @return
     */
    @Override
    public boolean deleteProductCategory(int productCategoryId) {
        int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId);
        if (effectedNum > 0) {
            return true;
        } else {
            return false;
        }
    }


}
