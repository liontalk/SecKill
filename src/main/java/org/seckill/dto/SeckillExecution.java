package org.seckill.dto;

import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStatusEnum;

/**
 * 封装秒杀执行之后的结果
 * Created by ZhouZhe on 2018/6/4.
 */
public class SeckillExecution {

    private long seckillId;


    /**
     * 秒杀执行 之后的状态
     */
    private int status;

    /**
     * 秒杀执行之后的提示信息
     */
    private String statusInfo;

    /**
     * 秒杀执行成功之后的对象
     */
    private SuccessKill successKill;

    /**
     * 成功之后的返回数据
     * @param seckillId
     * @param successKill
     */
    public SeckillExecution(long seckillId, SeckillStatusEnum seckillStatusEnum, SuccessKill successKill) {
        this.seckillId = seckillId;
        this.status = seckillStatusEnum.getStatus();
        this.statusInfo = seckillStatusEnum.getStatusInfo();
        this.successKill = successKill;
    }

    /**
     * 秒杀失败之后的状态
     * @param seckillId
     */
    public SeckillExecution(long seckillId, SeckillStatusEnum seckillStatusEnum) {
        this.seckillId = seckillId;
        this.status = seckillStatusEnum.getStatus();
        this.statusInfo = seckillStatusEnum.getStatusInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public SuccessKill getSuccessKill() {
        return successKill;
    }

    public void setSuccessKill(SuccessKill successKill) {
        this.successKill = successKill;
    }
}
