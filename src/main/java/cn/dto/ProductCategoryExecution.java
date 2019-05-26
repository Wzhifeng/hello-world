package cn.dto;

import cn.entity.ProductCategory;
import cn.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {
    //状态码
    private int state;

    //状态标识
    private String stateInfo;

    private ProductCategory productCategory;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum, ProductCategory productCategory) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
        this.productCategory = productCategory;
    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum, List<ProductCategory> productCategoryList) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
