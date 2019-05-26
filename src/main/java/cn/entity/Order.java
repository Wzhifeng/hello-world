package cn.entity;

import java.util.Date;

public class Order {
    private Integer orderId;
    private Long orderNo;
    private Integer userId;
    private Integer productId;
    private Integer shopId;
    private Integer number;
    private String payment;
    private String remark;
    private Integer repastWay;
    private Integer orderStatus;
    private Integer paymentType;
    private Date createTime;
    private Date updateTime;

    public Order() {
    }

    public Order(Integer orderId, Long orderNo, Integer userId, Integer productId, Integer shopId, Integer number, String payment, String remark, Integer repastWay, Integer orderStatus, Integer paymentType, Date createTime, Date updateTime) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.userId = userId;
        this.productId = productId;
        this.shopId = shopId;
        this.number = number;
        this.payment = payment;
        this.remark = remark;
        this.repastWay = repastWay;
        this.orderStatus = orderStatus;
        this.paymentType = paymentType;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRepastWay() {
        return repastWay;
    }

    public void setRepastWay(Integer repastWay) {
        this.repastWay = repastWay;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
