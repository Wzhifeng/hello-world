package cn.dao;

import cn.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * OrderDao 接口
 */
public interface OrderDao {
    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    int insertOrder(@Param("order") Order order);

    /**
     * 根据商铺查询订单
     *
     * @param shopId
     * @return
     */
    List<Order> queryOrderByShopId(Integer shopId);


    /**
     * 更新订单
     *
     * @param order
     * @return
     */
    int updateOrder(@Param("order") Order order);

    /**
     * 根据用户查询订单
     *
     * @param userId
     * @return
     */
    List<Order> queryOrderByUserId(Integer userId);

    /**
     * 根据年 查询商铺的订单
     *
     * @param shopId
     * @param date
     * @return
     */
    List<Order> queryOrderByYear(@Param("shopId") Integer shopId, @Param("date") Date date);

    List<Order> queryOrderByMonth(@Param("shopId") Integer shopId, @Param("date") Date date);

    List<Order> queryOrderByDay(@Param("shopId") Integer shopId, @Param("date") Date date);

    List<Order> queryAllOrders(@Param("shopId") Integer shopId);

    List<Order> queryAllSales(Integer shopId);

    List<Order> querySalesByYear(@Param("shopId") Integer shopId, @Param("date") Date date);

    List<Order> querySalesByMonth(@Param("shopId") Integer shopId, @Param("date") Date date);

    List<Order> querySalesByDay(@Param("shopId") Integer shopId, @Param("date") Date date);
}
