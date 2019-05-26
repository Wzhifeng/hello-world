package cn.enums;

public enum ProductStateEnum {
    SUCCESS(1, "操作成功"),
    PRODUCT_INFO_NULL(-1001, "商品信息为空"),
    PRODUCT_POYAWAY(1, "上架"),
    PRODUCT_REMOVE_OFF(0, "下架"),
    ADD_PRODUCT_FAIL(-1002, "添加商品失败"),
    PRODUCT_CONDITION_NULL(-1003, "商品查询条件为空"),
    UPDATE_PRODUCT_FAIL(-1004,"更新商品失败");

    //状态码
    private int state;

    //状态信息
    private String stateInfo;

    private ProductStateEnum(int state, String stateInfo) {
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
