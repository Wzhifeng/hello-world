package cn.entity;

import java.util.Date;

/**
 * Shop 实体类
 */
public class Shop {
    private Integer shopId;
    private Integer shopOwnerId;
    private Integer shopCategoryId;
    private String shopName;
    private String shopDesc;
    private String shopImg;
    private String shopAddr;
    private String shopPhone;
    private Integer businessStatus;
    private Integer enableStatus;
    private Date createTime;
    private Date updateTime;

    public Shop() {
    }

    public Shop(Integer shopId, Integer shopOwnerId, Integer shopCategoryId, String shopName, String shopDesc, String shopImg, String shopAddr, String shopPhone, Integer businessStatus, Integer enableStatus, Date createTime, Date updateTime) {
        this.shopId = shopId;
        this.shopOwnerId = shopOwnerId;
        this.shopCategoryId = shopCategoryId;
        this.shopName = shopName;
        this.shopDesc = shopDesc;
        this.shopImg = shopImg;
        this.shopAddr = shopAddr;
        this.shopPhone = shopPhone;
        this.businessStatus = businessStatus;
        this.enableStatus = enableStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getShopOwnerId() {
        return shopOwnerId;
    }

    public void setShopOwnerId(Integer shopOwnerId) {
        this.shopOwnerId = shopOwnerId;
    }

    public Integer getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Integer shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public Integer getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(Integer businessStatus) {
        this.businessStatus = businessStatus;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
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

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", shopOwnerId=" + shopOwnerId +
                ", shopCategoryId=" + shopCategoryId +
                ", shopName='" + shopName + '\'' +
                ", shopDesc='" + shopDesc + '\'' +
                ", shopImg='" + shopImg + '\'' +
                ", shopAddr='" + shopAddr + '\'' +
                ", shopPhone='" + shopPhone + '\'' +
                ", businessStatus=" + businessStatus +
                ", enableStatus=" + enableStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
