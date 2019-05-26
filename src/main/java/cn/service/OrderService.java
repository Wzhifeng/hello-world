package cn.service;

import cn.dto.OrderExecution;
import cn.dto.OrderVO;
import cn.dto.PurchaseHistoryVO;
import cn.entity.Order;

import java.util.List;

/**
 * OrderService 接口
 */
public interface OrderService {
    /**
     * 添加订单
     * @param order
     * @return
     */
    OrderExecution addOrder(Order order);

    /**
     * 获取商品的订单
     * @param shopId
     * @return
     */
    List<OrderVO> getOrderListByShop(Integer shopId);

    /**
     * 完成订单
     * @param orderNo
     * @return
     */
    boolean finishOrder(Long orderNo);

    /**
     * 获取用户的消费记录
     * @param userId
     * @return
     */
    List<PurchaseHistoryVO> getOrderListByUserId(Integer userId);
}
