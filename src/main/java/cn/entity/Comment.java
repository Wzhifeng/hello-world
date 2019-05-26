package cn.entity;

import java.util.Date;

public class Comment {
    private Integer commentId;
    private String commentContent;
    private Integer productId;
    private Integer userId;
    private Date createTime;
    private Date updateTime;

    public Comment() {
    }

    public Comment(Integer commentId, String commentContent, Integer productId, Integer userId, Date createTime, Date updateTime) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.productId = productId;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
