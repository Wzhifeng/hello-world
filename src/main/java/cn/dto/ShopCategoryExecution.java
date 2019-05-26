package cn.dto;

import cn.entity.ShopCategory;
import cn.enums.ShopCategoryEnum;

import java.util.List;

public class ShopCategoryExecution {
    //状态码
    private int state;

    //状态标识
    private String stateInfo;

    //操作的商铺分类
    private ShopCategory shopCategory;

    //操作的商铺分类列表
    private List<ShopCategory> shopCategoryList;

    public ShopCategoryExecution() {
    }

    public ShopCategoryExecution(ShopCategoryEnum shopCategoryEnum) {
        this.state = shopCategoryEnum.getState();
        this.stateInfo = shopCategoryEnum.getStateInfo();
    }

    public ShopCategoryExecution(ShopCategoryEnum shopCategoryEnum, ShopCategory shopCategory) {
        this.state = shopCategoryEnum.getState();
        this.stateInfo = shopCategoryEnum.getStateInfo();
        this.shopCategory = shopCategory;
    }


    public ShopCategoryExecution(ShopCategoryEnum shopCategoryEnum, List<ShopCategory> shopCategoryList) {
        this.state = shopCategoryEnum.getState();
        this.stateInfo = shopCategoryEnum.getStateInfo();
        this.shopCategoryList = shopCategoryList;
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

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public List<ShopCategory> getShopCategoryList() {
        return shopCategoryList;
    }

    public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
        this.shopCategoryList = shopCategoryList;
    }
}
