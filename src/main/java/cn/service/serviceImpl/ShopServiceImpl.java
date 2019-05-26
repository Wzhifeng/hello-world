package cn.service.serviceImpl;

import cn.dao.ShopDao;
import cn.dto.ShopExecution;
import cn.entity.Shop;
import cn.enums.ShopStateEnum;
import cn.exceptions.ShopException;
import cn.service.ShopService;
import cn.util.ImageHolder;
import cn.util.ImageUtil;
import cn.util.PathUtil;
import com.sun.org.apache.xerces.internal.xs.ShortList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ShopService 实现类
 */

@Service
@Transactional
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    /**
     * 根据userId选择店铺
     *
     * @param userId
     * @return
     */
    @Override
    public Shop getShopByUserId(Integer userId) {
        return shopDao.queryShopByUserId(userId);
    }

    /**
     * 添加商铺
     *
     * @param shop
     * @param shopImage
     * @return
     */
    @Override
    public ShopExecution addShop(Shop shop, ImageHolder shopImage) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.ADD_SHOP_FAIL);
        }

        //初始化商铺信息
        shop.setBusinessStatus(ShopStateEnum.SHOP_ON_BUSINESS.getState());
        shop.setEnableStatus(ShopStateEnum.SHOP_PASS_THE_AUDIT.getState());

        try {
            //将基本信息插入数据库并且返回shopId
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                return new ShopExecution(ShopStateEnum.ADD_SHOP_FAIL);
            } else {
                //添加商铺图片
                addShopImg(shop, shopImage);

                //更新商铺信息
                effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.ADD_SHOP_FAIL);
                } else {
                    return new ShopExecution(ShopStateEnum.SUCCESS);
                }
            }
        } catch (Exception e) {
            throw new ShopException("添加商铺时发生异常:" + e.toString());
        }
    }

    /**
     * 更新商铺
     *
     * @param shop
     * @param shopImage
     * @return
     */
    @Override
    public ShopExecution updateShop(Shop shop, ImageHolder shopImage) {
        //设置更新时间
        shop.setUpdateTime(new Date());

        //如果有新图片则将旧图片删除
        if (shopImage != null) {
            Shop tempShop = new Shop();
            tempShop = shopDao.queryShopByUserId(shop.getShopOwnerId());
            if (tempShop.getShopImg() != null) {
                ImageUtil.deteFileOrPath(tempShop.getShopImg());
            }

            //添加新图片
            addShopImg(shop, shopImage);
        }

        //更新商铺
        int effectedNum = shopDao.updateShop(shop);
        if (effectedNum > 0) {
            return new ShopExecution(ShopStateEnum.SUCCESS);
        } else {
            return new ShopExecution(ShopStateEnum.UPDATE_PRODUCT_FAIL);
        }

    }

    /**
     * 查询商铺列表
     *
     * @return
     */
    @Override
    public List<Shop> getShopList() {
        return shopDao.queryShopList();
    }

    @Override
    public Shop getShopById(Integer shopId) {
        return shopDao.queryShopById(shopId);
    }


    /**
     * 根据条件获取商铺列表
     *
     * @param shopCondition
     * @return
     */
    @Override
    public ShopExecution getShopListByShopCondition(Shop shopCondition) {
        //设置商铺状态为营业中并且通过审核
        shopCondition.setEnableStatus(ShopStateEnum.SHOP_PASS_THE_AUDIT.getState());
        shopCondition.setBusinessStatus(ShopStateEnum.SHOP_ON_BUSINESS.getState());

        //根据条件查询商铺列表
        List<Shop> shopList = shopDao.queryShopByShopCondition(shopCondition);
        if (shopList.size() > 0) {
            return new ShopExecution(ShopStateEnum.SUCCESS, shopList);
        } else {
            return new ShopExecution(ShopStateEnum.QUERY_SHOP_FAIL);
        }

    }

    /**
     * 更改商品上下架状态
     *
     * @param shopId
     * @return
     */
    @Override
    public boolean changeShopStatus(Integer shopId) {
        Shop shop = shopDao.queryShopById(shopId);

        //查询商铺状态并且设置状态
        if (shop.getEnableStatus() == ShopStateEnum.SHOP_PASS_THE_AUDIT.getState()) {
            shop.setEnableStatus(ShopStateEnum.SHOP_NOT_PASS.getState());
        } else {
            shop.setEnableStatus(ShopStateEnum.SHOP_PASS_THE_AUDIT.getState());
        }

        int effectedNum = shopDao.updateShop(shop);
        if (effectedNum > 0) {
            return true;
        } else {
            return false;
        }
    }


    private void addShopImg(Shop shop, ImageHolder shopImage) {
        //获取Shop的相对路径
        String targetAddr = PathUtil.getShopImagePath(shop.getShopId());
        String relativeAddr = ImageUtil.generateThumbnail(shopImage, targetAddr);
        shop.setShopImg(relativeAddr);
    }

}
