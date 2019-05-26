package cn.enums;

public enum OrderStateEnum {
    SUCCESS(1, "操作成功"),
    CREATE_ORDER_FAIL(-1, "创建订单失败"),
    EAT_INSIDE(0, "堂食"),
    TAKEOUT(1, "外带"),
    CASH_PAY(1, "现金支付"),
    ALREARDY_PAY(1000, "已经支付"),
    ALREADY_FINISHED(1001, "商家已完成"),;

    //状态码
    private int state;

    //状态信息
    private String stateInfo;

    private OrderStateEnum(int state, String stateInfo) {
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
