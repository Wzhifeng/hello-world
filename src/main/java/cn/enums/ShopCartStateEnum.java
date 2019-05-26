package cn.enums;

public enum ShopCartStateEnum {
    SUCCESS(1, "操作成功"),
    ADD_SHOP_CART_FAIL(-1001, "添加购物车失败"),
    MODIFY_SHOP_CART_FAIL(-1002, "更新购物车失败"),;

    //状态码
    private int state;

    //状态信息
    private String stateInfo;

    private ShopCartStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
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
}
