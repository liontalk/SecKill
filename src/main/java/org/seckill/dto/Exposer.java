package org.seckill.dto;

import java.util.Date;

/**
 * 暴露秒杀地址接口
 * Created by ZhouZhe on 2018/6/4.
 */
public class Exposer extends Throwable {

    /**
     * 标志秒杀地址是否开启
     */
    private boolean exposed;


    /**
     * 一种加密措施
     */
    private String md5;


    private long seckillId;


    /**
     * 系统当前时间
     */
    private long now;

    /**
     * 秒杀开始时间
     */
    private long start;

    /**
     * 秒杀结束时间
     */
    private long end;


    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed,long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.now = now;
        this.start = start;
        this.end = end;
        this.seckillId =seckillId;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }


    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
