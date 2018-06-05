package org.seckill.enums;

/**
 * 使用枚举表述常量字段
 * Created by ZhouZhe on 2018/6/5.
 */
public enum SeckillStatusEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀失败"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"内部错误"),
    DATA_REWRITE(-3,"数据篡改");

    private int status;

    private String statusInfo;


    SeckillStatusEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public static SeckillStatusEnum statusOf(int index){
        for(SeckillStatusEnum seckillStatusEnum:values()){
            if(seckillStatusEnum.getStatus()==index){
                return seckillStatusEnum;
            }
        }
        return null;
    }
}
