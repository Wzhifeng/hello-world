package cn.dto;

import java.util.Date;

public class PurchaseHistoryVO {
    private String shopName;
    private String productName;
    private String productDesc;
    private String productImg;
    private String price;
    private Integer number;
    private Date createTime;
    private Integer productId;

    public PurchaseHistoryVO(String shopName, String productName, String productDesc, String productImg, String price, Integer number, Date createTime, Integer productId) {
        this.shopName = shopName;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productImg = productImg;
        this.price = price;
        this.number = number;
        this.createTime = createTime;
        this.productId = productId;
    }

    public PurchaseHistoryVO() {

    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
