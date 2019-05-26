package cn.enums;

public enum ShopCategoryEnum {
    SUCCESS(1, "操作成功"),
    SHOP_CATEGORY_NULL(-1001, "商铺分类信息为空！"),
    ADD_SHOP_CATEGORY_FAIL(-1002, "插入商铺分类失败！"),
    SHOP_CATEGORY_LIST_NULL(-1003, "商铺分类列表为空！"),
    UPDATE_PRODUCT_FAIL(-1004, "更新商铺信息失败"),;
    //状态码
    private int state;

    //状态信息
    private String stateInfo;

    private ShopCategoryEnum(int state, String stateInfo) {
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
