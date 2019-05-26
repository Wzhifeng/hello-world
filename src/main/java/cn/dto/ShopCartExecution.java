package cn.dto;

import cn.entity.ShopCart;
import cn.enums.ShopCartStateEnum;

import java.util.List;

public class ShopCartExecution {
    //状态码
    private int state;

    //状态标识
    private String stateInfo;

    private ShopCart shopCart;

    private List<ShopCart> shopCartList;

    public ShopCartExecution() {
    }

    public ShopCartExecution(ShopCartStateEnum shopCartStateEnum) {
        this.state = shopCartStateEnum.getState();
        this.stateInfo = shopCartStateEnum.getStateInfo();
    }

    public ShopCartExecution(ShopCartStateEnum shopCartStateEnum, ShopCart shopCart) {
        this.state = shopCartStateEnum.getState();
        this.stateInfo = shopCartStateEnum.getStateInfo();
        this.shopCart = shopCart;
    }

    public ShopCartExecution(ShopCartStateEnum shopCartStateEnum, List<ShopCart> shopCartList) {
        this.state = shopCartStateEnum.getState();
        this.stateInfo = shopCartStateEnum.getStateInfo();
        this.shopCartList = shopCartList;
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

    public ShopCart getShopCart() {
        return shopCart;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }

    public List<ShopCart> getShopCartList() {
        return shopCartList;
    }

    public void setShopCartList(List<ShopCart> shopCartList) {
        this.shopCartList = shopCartList;
    }
}
