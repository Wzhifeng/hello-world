package cn.service.serviceImpl;

import cn.dao.ShopCategoryDao;
import cn.dto.ShopCategoryExecution;
import cn.entity.ShopCategory;
import cn.enums.ShopCategoryEnum;
import cn.exceptions.ShopCategoryException;
import cn.service.ShopCategoryService;
import cn.util.ImageHolder;
import cn.util.ImageUtil;
import cn.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ShopCategoryService 实现类
 */

@Service
@Transactional
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    /**
     * 增加商铺分类信息
     *
     * @param shopCategory
     * @param shopCategoryImage
     * @return
     */
    @Override
    public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder shopCategoryImage) {
        if (shopCategory == null) {
            return new ShopCategoryExecution(ShopCategoryEnum.SHOP_CATEGORY_NULL);
        }

        //商铺分类信息初始化
        shopCategory.setParentId(0);

        try {
            //向数据库插入商铺分类
            int effectedNum = shopCategoryDao.insertShopCategory(shopCategory);
            if (effectedNum <= 0) {
                return new ShopCategoryExecution(ShopCategoryEnum.ADD_SHOP_CATEGORY_FAIL);
            } else {
                //添加商铺分类图片地址
                addShopCategoryImg(shopCategory, shopCategoryImage);

                //更新商铺分类图片
                effectedNum = shopCategoryDao.updateShopCategory(shopCategory);

                if (effectedNum <= 0) {
                    return new ShopCategoryExecution(ShopCategoryEnum.ADD_SHOP_CATEGORY_FAIL);
                } else {
                    return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS);
                }
            }
        } catch (Exception e) {
            throw new ShopCategoryException("添加商铺时发生异常");
        }
    }

    /**
     * 获取商铺列表
     *
     * @return
     */
    @Override
    public ShopCategoryExecution getShopCategoryList() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategoryList();
        if (shopCategoryList.size() > 0) {
            return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS, shopCategoryList);
        } else {
            return new ShopCategoryExecution(ShopCategoryEnum.SHOP_CATEGORY_LIST_NULL, shopCategoryList);
        }
    }


    /**
     * 根据商铺分类id删除商铺分类
     *
     * @param shopCategoryId
     * @return
     */
    @Override
    public boolean deleteShopCategory(int shopCategoryId) {
        //检查商铺分类是否有图片 有则删除
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(shopCategoryId);
        shopCategory = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());

        if (shopCategory.getShopCategoryImg() != null) {
            ImageUtil.deteFileOrPath(shopCategory.getShopCategoryImg());
        }

        int effectedNum = shopCategoryDao.deleteShopCategoryById(shopCategoryId);

        if (effectedNum > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据商铺分类id查询商铺分类
     *
     * @param shopCategoryId
     * @return
     */
    @Override
    public ShopCategoryExecution getShopCategory(int shopCategoryId) {

        //查询商铺分类
        ShopCategory shopCategory = shopCategoryDao.queryShopCategoryById(shopCategoryId);

        if (shopCategory != null) {
            return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS, shopCategory);
        } else {
            return new ShopCategoryExecution(ShopCategoryEnum.SHOP_CATEGORY_NULL);
        }
    }

    /**
     * 更新商铺分类信息
     *
     * @param shopCategory
     * @return
     */
    @Override
    public ShopCategoryExecution updateShopCategory(ShopCategory shopCategory, ImageHolder shopCategoryImg) {
        shopCategory.setUpdateTime(new Date());

        //如果有新图片则删除现有图片
        if (shopCategoryImg != null) {
            ShopCategory tempShopCategory = new ShopCategory();
            tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());
            if (tempShopCategory.getShopCategoryImg()!= null) {
                ImageUtil.deteFileOrPath(tempShopCategory.getShopCategoryImg());
            }
            //添加新的图片
            addShopCategoryImg(shopCategory, shopCategoryImg);
        }

        //更新商品信息
        int effectedNum = shopCategoryDao.updateShopCategory(shopCategory);
        if (effectedNum > 0) {
            return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS);
        } else {
            return new ShopCategoryExecution(ShopCategoryEnum.UPDATE_PRODUCT_FAIL);
        }
    }

    /**
     * 处理商铺分类图片
     *
     * @param shopCategory
     * @param shopCategoryImage
     */
    private void addShopCategoryImg(ShopCategory shopCategory, ImageHolder shopCategoryImage) {
        //获取ShopCategory的相对路径
        String targetAddr = PathUtil.getShopCategoryImagePath(shopCategory.getShopCategoryId());
        String relativeAddr = ImageUtil.generateThumbnail(shopCategoryImage, targetAddr);
        shopCategory.setShopCategoryImg(relativeAddr);
    }
}
