package cn.enums;

public enum HeadLineStateEnum {
    HEAD_LINE_1(1, "头条图1"),
    HEAD_LINE_2(2, "头条图2"),
    HEAD_LINE_3(3, "头条图3"),;
    //状态码

    private int state;

    //状态信息
    private String stateInfo;

    HeadLineStateEnum() {
    }

    private HeadLineStateEnum(int state, String stateInfo) {
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
