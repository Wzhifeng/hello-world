package cn.dto;

import cn.entity.Comment;
import cn.enums.CommentStateEnum;

import java.util.List;

public class CommentExecution {
    //状态码
    private int state;

    //状态标识
    private String stateInfo;

    private Comment comment;
    private List<Comment> commentList;

    public CommentExecution() {
    }

    public CommentExecution(CommentStateEnum commentStateEnum) {
        this.state = commentStateEnum.getState();
        this.stateInfo = commentStateEnum.getStateInfo();
    }

    public CommentExecution(CommentStateEnum commentStateEnum, Comment comment) {
        this.state = commentStateEnum.getState();
        this.stateInfo = commentStateEnum.getStateInfo();
        this.comment = comment;
    }

    public CommentExecution(CommentStateEnum commentStateEnum, List<Comment> commentList) {
        this.state = commentStateEnum.getState();
        this.stateInfo = commentStateEnum.getStateInfo();
        this.commentList = commentList;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
