package cn.enums;

public enum CommentStateEnum {
    SUCCESS(1, "操作成功"),
    COMMENT_INFO_NULL(-1001, "评论信息为空"),;

    private int state;

    //状态信息
    private String stateInfo;


    private CommentStateEnum(int state, String stateInfo) {
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
