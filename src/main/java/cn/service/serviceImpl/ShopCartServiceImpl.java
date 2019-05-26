package cn.service.serviceImpl;

import cn.dao.ShopCartDao;
import cn.dto.ShopCartExecution;
import cn.entity.ShopCart;
import cn.enums.ShopCartStateEnum;
import cn.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ShopCartService 实现类
 */
@Service
@Transactional
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    private ShopCartDao shopCartDao;

    @Override
    public List<ShopCart> getShopCartByCondition(ShopCart shopCartCondition) {
        return shopCartDao.queryShopCaryByCondition(shopCartCondition);
    }

    @Override
    public ShopCartExecution addShopCart(ShopCart shopCartCondition) {
        int num = shopCartDao.insertShopCart(shopCartCondition);
        if (num > 0) {
            return new ShopCartExecution(ShopCartStateEnum.SUCCESS);
        } else {
            return new ShopCartExecution(ShopCartStateEnum.ADD_SHOP_CART_FAIL);
        }
    }

    @Override
    public ShopCartExecution modifyShopCart(ShopCart shopCartCondition) {
        //设置更新时间
        shopCartCondition.setUpdateTime(new Date());

        int num = shopCartDao.updateShopCart(shopCartCondition);
        if (num > 0) {
            return new ShopCartExecution(ShopCartStateEnum.SUCCESS);
        } else {
            return new ShopCartExecution(ShopCartStateEnum.MODIFY_SHOP_CART_FAIL);
        }
    }

    @Override
    public boolean deleteShopCart(Integer userId, Integer productId) {
        int effectedNum = 0;
        if (userId != null && productId != null) {
            effectedNum = shopCartDao.deleteShopCart(userId, productId);
        }
        if (effectedNum > 0) {
            return true;
        } else {
            return false;
        }
    }
}
