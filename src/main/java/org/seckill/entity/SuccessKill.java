package org.seckill.entity;

import java.util.Date;

/**
 * Created by ZhouZhe on 2018/6/2.
 */
public class SuccessKill {

    private long secKillId;


    private String userPhone;

    private short status;


    private Date createTime;


    private SecKill secKill;

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuccessKill{" +
                "secKillId=" + secKillId +
                ", userPhone='" + userPhone + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
