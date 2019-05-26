package cn.dto;

import java.util.Date;

public class OrderVO {
    private String username;
    private String productName;
    private String productImg;
    private Integer number;
    private String remark;
    private Long orderNo;
    private Integer repastWay;
    private Date createTime;

    public OrderVO() {
    }


    public OrderVO(String username, String productName, String productImg, Integer number, String remark, Long orderNo, Integer repastWay, Date createTime) {
        this.username = username;
        this.productName = productName;
        this.productImg = productImg;
        this.number = number;
        this.remark = remark;
        this.orderNo = orderNo;
        this.repastWay = repastWay;
        this.createTime = createTime;
    }

    public Integer getRepastWay() {
        return repastWay;
    }

    public void setRepastWay(Integer repastWay) {
        this.repastWay = repastWay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
