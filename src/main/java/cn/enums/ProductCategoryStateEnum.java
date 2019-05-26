package cn.enums;

public enum ProductCategoryStateEnum {
    SUCCESS(1, "操作成功"),
    PRODUCT_CATEGORY_EMPTY(-1002, "商品分类信息为空！"),
    ADD_PRODUCT_CATEGORY_FAIL(-1003, "添加商品分类信息失败！"),
    UPDATE_PRODUCT_CATEGORY_FAIL(-1004,"更新商品分类信息失败！"),
    ;

    //状态码
    private int state;

    //状态信息
    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo) {
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
