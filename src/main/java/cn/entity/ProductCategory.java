package cn.entity;

import java.util.Date;

/**
 * 商品类别实体
 */
public class ProductCategory {
    private Integer productCategoryId;
    private String productCategoryName;
    private Integer priority;
    private Integer shopId;
    private Date createTime;
    private Date updateTime;

    public ProductCategory(Integer productCategoryId, String productCategoryName, Integer priority, Integer shopId, Date createTime, Date updateTime) {
        this.productCategoryId = productCategoryId;
        this.productCategoryName = productCategoryName;
        this.priority = priority;
        this.shopId = shopId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ProductCategory() {

    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
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
        return "ProductCategory{" +
                "productCategoryId=" + productCategoryId +
                ", productCategoryName='" + productCategoryName + '\'' +
                ", priority=" + priority +
                ", shopId=" + shopId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
