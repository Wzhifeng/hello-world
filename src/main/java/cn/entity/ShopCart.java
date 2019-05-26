package cn.entity;

import java.util.Date;

public class ShopCart {
    private Integer shopCartId;
    private Integer productId;
    private Integer number;
    private Integer userId;
    private Date createTime;
    private Date updateTime;

    public ShopCart() {
    }

    public ShopCart(Integer shopCartId, Integer productId, Integer number, Integer userId, Date createTime, Date updateTime) {
        this.shopCartId = shopCartId;
        this.productId = productId;
        this.number = number;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(Integer shopCartId) {
        this.shopCartId = shopCartId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
