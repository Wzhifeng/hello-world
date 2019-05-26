package cn.enums;

public enum ShopStateEnum {
    SUCCESS(1, "操作成功"),
    SHOP_ON_BUSINESS(1, "营业中"),
    SHOP_ON_REST(0, "休息中"),
    SHOP_PASS_THE_AUDIT(1, "审核通过"),
    SHOP_NOT_PASS(0, "下架"),
    ADD_SHOP_FAIL(-1, "添加商铺失败"),
    UPDATE_PRODUCT_FAIL(-1001, "更新商铺失败"),
    QUERY_SHOP_FAIL(-1002, "查询商铺失败"),;
    //状态码
    private int state;

    //状态信息
    private String stateInfo;

    private ShopStateEnum(int state, String stateInfo) {
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
