package cn.enums;

public enum PersonStateEnum {
    SUCCESS(1, "操作成功"),
    FAIL(0, "操作失败"),
    ADD_PERSON_FAIL(-1001, "添加用户失败"),
    LOGIN_FAIL(-1002, "登陆失败");

    //状态码
    private int state;

    //状态信息
    private String stateInfo;

    private PersonStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PersonStateEnum stateOf(int state) {
        for (PersonStateEnum personStateEnum :
                values()) {
            if (personStateEnum.getState() == state) {
                return personStateEnum;
            }
        }
        return null;
    }
}
