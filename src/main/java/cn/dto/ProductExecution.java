package cn.dto;

import cn.entity.Product;
import cn.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {
    //状态码
    private int state;

    //状态标识
    private String stateInfo;

    private Product product;

    private List<Product> productList;

    public ProductExecution() {
    }

    public ProductExecution(ProductStateEnum productStateEnum) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
    }

    public ProductExecution(ProductStateEnum productStateEnum, Product product) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.product = product;
    }

    public ProductExecution(ProductStateEnum productStateEnum, List<Product> productList) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.productList = productList;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
