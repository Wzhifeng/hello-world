package cn.dto;

public class CommentVO {
    private String productName;
    private String commentContent;
    private String commentUserName;

    public CommentVO() {
    }

    public CommentVO(String productName, String commentContent, String commentUserName) {
        this.productName = productName;
        this.commentContent = commentContent;
        this.commentUserName = commentUserName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }
}
