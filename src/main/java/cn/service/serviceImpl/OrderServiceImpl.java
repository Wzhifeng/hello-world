package cn.service.serviceImpl;

import cn.dao.OrderDao;
import cn.dao.PersonDao;
import cn.dao.ProductDao;
import cn.dao.ShopDao;
import cn.dto.OrderExecution;
import cn.dto.OrderVO;
import cn.dto.PurchaseHistoryVO;
import cn.entity.Order;
import cn.entity.Person;
import cn.entity.Product;
import cn.entity.Shop;
import cn.enums.OrderStateEnum;
import cn.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * OrderService 实现类
 */

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ShopDao shopDao;

    @Override
    public OrderExecution addOrder(Order order) {
        order.setOrderNo(System.currentTimeMillis() + new Random().nextInt(100));
        int effectedNum = orderDao.insertOrder(order);
        if (effectedNum > 0) {
            return new OrderExecution(OrderStateEnum.SUCCESS);
        } else {
            return new OrderExecution(OrderStateEnum.CREATE_ORDER_FAIL);
        }
    }

    @Override
    public List<OrderVO> getOrderListByShop(Integer shopId) {
        List<OrderVO> orderVOList = new ArrayList<>();

        List<Order> orderList = orderDao.queryOrderByShopId(shopId);


        if (orderList != null && orderList.size() > 0) {
            for (Order order :
                    orderList) {
                Product product = productDao.queryProductById(order.getProductId());
                Person person = personDao.selectPersonById(order.getUserId());

                //组装OrderVO
                OrderVO orderVO = new OrderVO();
                orderVO.setUsername(person.getUsername());
                orderVO.setProductName(product.getProductName());
                orderVO.setProductImg(product.getProductImg());
                orderVO.setRemark(order.getRemark());
                orderVO.setNumber(order.getNumber());
                orderVO.setCreateTime(order.getCreateTime());
                orderVO.setOrderNo(order.getOrderNo());
                orderVO.setRepastWay(order.getRepastWay());
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    @Override
    public boolean finishOrder(Long orderNo) {
        Order order = new Order();

        order.setOrderNo(orderNo);
        order.setUpdateTime(new Date());
        order.setOrderStatus(OrderStateEnum.ALREADY_FINISHED.getState());

        int effectedNum = orderDao.updateOrder(order);
        if (effectedNum > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<PurchaseHistoryVO> getOrderListByUserId(Integer userId) {
        //查询订单
        List<Order> orderList = orderDao.queryOrderByUserId(userId);

        if (orderList != null ) {
            List<PurchaseHistoryVO> purchaseHistoryVOList = new ArrayList<>();
            for (Order order :
                    orderList) {
                //查询该订单的商品信息
                Product product = productDao.queryProductById(order.getProductId());
                //查询该订单所属的商铺信息
                Shop shop = shopDao.queryShopById(product.getShopId());
                //组装PurchaseHistoryVO
                PurchaseHistoryVO purchaseHistoryVO = new PurchaseHistoryVO();
                purchaseHistoryVO.setShopName(shop.getShopName());
                purchaseHistoryVO.setProductName(product.getProductName());
                purchaseHistoryVO.setProductDesc(product.getProductDesc());
                purchaseHistoryVO.setProductImg(product.getProductImg());
                purchaseHistoryVO.setProductId(product.getProductId());
                purchaseHistoryVO.setPrice(product.getPrice());
                purchaseHistoryVO.setNumber(order.getNumber());
                purchaseHistoryVO.setCreateTime(order.getCreateTime());
                //添加进list
                purchaseHistoryVOList.add(purchaseHistoryVO);
            }
            return purchaseHistoryVOList;
        }
        return null;
    }
}
