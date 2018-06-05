package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 站在接口使用人角度 开发接口
 * Created by ZhouZhe on 2018/6/4.
 */
public interface SeckillService {


    /**
     * 查询所有的秒杀列表
     * @return
     */
    List<SecKill> getSeckillList();


    /**
     * 查询单个的秒杀记录
     * @param seckillId
     * @return
     */
    SecKill getSecKillById(long seckillId);


    /**
     * 秒杀开始的输出秒杀接口地址
     * 没有开始的时候输出当前的时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer  exportSecKillUrl(long seckillId);


    /**
     * 用户执行秒杀操作
     * @param seckillId
     * @param userPhone
     */
    SeckillExecution executeSecKill(long seckillId, long userPhone, String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}
