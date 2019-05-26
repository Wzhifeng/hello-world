package cn.entity;

import java.util.Date;

/**
 * 商品实体类
 */
public class Product {
    private Integer productId;
    private String productName;
    private String productImg;
    private String productDesc;
    private String price;
    private Integer priority;
    private Integer shopId;
    private Integer productCategoryId;
    private Integer praise;
    private Integer sales;
    private Integer productStatus;
    private Date createTime;
    private Date updateTime;


    public Product() {

    }

    public Product(Integer productId, String productName, String productImg, String productDesc, String price, Integer priority, Integer shopId, Integer productCategoryId, Integer praise, Integer sales, Integer productStatus, Date createTime, Date updateTime) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.productDesc = productDesc;
        this.price = price;
        this.priority = priority;
        this.shopId = shopId;
        this.productCategoryId = productCategoryId;
        this.praise = praise;
        this.sales = sales;
        this.productStatus = productStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
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
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productImg='" + productImg + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", price='" + price + '\'' +
                ", priority=" + priority +
                ", shopId=" + shopId +
                ", productCategoryId=" + productCategoryId +
                ", praise=" + praise +
                ", sales=" + sales +
                ", productStatus=" + productStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
