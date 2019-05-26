package cn.enums;

public enum Role {
    ADMIN(0, "管理员"),
    USER(1, "普通用户"),
    SHOPKEEPER(2, "商家");

    //状态码
    private int state;

    //状态码信息
    private String stateInfo;

    Role(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
