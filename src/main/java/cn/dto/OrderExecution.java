package cn.dto;

import cn.entity.Order;
import cn.enums.OrderStateEnum;

import java.util.List;

public class OrderExecution {
    //状态码
    private int state;

    //状态标识
    private String stateInfo;

    private Order order;

    private List<Order> orderList;

    public OrderExecution() {
    }

    public OrderExecution(OrderStateEnum orderStateEnum) {
        this.state = orderStateEnum.getState();
        this.stateInfo = orderStateEnum.getStateInfo();
    }

    public OrderExecution(OrderStateEnum orderStateEnum, Order order) {
        this.state = orderStateEnum.getState();
        this.stateInfo = orderStateEnum.getStateInfo();
        this.order = order;
    }

    public OrderExecution(OrderStateEnum orderStateEnum, List<Order> orderList) {
        this.state = orderStateEnum.getState();
        this.stateInfo = orderStateEnum.getStateInfo();
        this.orderList = orderList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
