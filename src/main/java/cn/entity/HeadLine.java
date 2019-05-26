package cn.entity;

import java.util.Date;

public class HeadLine {
    private Integer headLineId;
    private Integer priority;
    private Integer shopId;
    private Date createTime;
    private Date updateTime;

    public HeadLine() {
    }

    public HeadLine(Integer headLineId, Integer priority, Integer shopId, Date createTime, Date updateTime) {
        this.headLineId = headLineId;
        this.priority = priority;
        this.shopId = shopId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getHeadLineId() {
        return headLineId;
    }

    public void setHeadLineId(Integer headLineId) {
        this.headLineId = headLineId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    @Override
    public String toString() {
        return "HeadLine{" +
                "headLineId=" + headLineId +
                ", priority=" + priority +
                ", shopId=" + shopId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
