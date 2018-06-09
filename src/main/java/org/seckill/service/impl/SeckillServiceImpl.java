package org.seckill.service.impl;

import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStatusEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.xml.crypto.dsig.DigestMethod;
import java.util.Date;
import java.util.List;


/**
 * Created by ZhouZhe on 2018/6/4.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private static final String SALT = "ZHOUZHE";

    @Autowired
    private SecKillDao secKillDao;

    @Autowired
    private SuccessKillDao successKillDao;

    @Autowired
    private RedisDao redisDao;

    @Override
    public List<SecKill> getSeckillList() {
        return secKillDao.queryAll(0, 4);
    }

    @Override
    public SecKill getSecKillById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    /**
     * 暴露秒杀接口
     *
     * @param seckillId
     * @return
     */
    @Override
    public Exposer exportSecKillUrl(long seckillId) {
        // 优化点：缓存优化，超时的基础上一致性
        /**
         * get from cache
         * if null
         *    get from db
         *  else
         *    put cache
         *    logic 或return
         */
        // 1 访问redis
        SecKill secKill = redisDao.getSecKill(seckillId);
        if (secKill == null) {
            // 2 数据库获取
            secKill = secKillDao.queryById(seckillId);
            if (secKill == null) {
                return new Exposer(false, seckillId);
            } else {
                // 放入到redis 中
                redisDao.putSecKill(secKill);
            }
        }
       /*
       未优化之前的代码
       SecKill secKill = secKillDao.queryById(seckillId);
        if (secKill == null) {
            return new Exposer(false, seckillId);
        }*/
        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        /**
         * 当前系统的时间
         */
        Date now = new Date();
        if (startTime.getTime() > now.getTime() || endTime.getTime() < now.getTime()) {
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = null;
        md5 = getMd5(seckillId);
        Exposer exposer = new Exposer(true, md5, seckillId);
        return exposer;
    }


    private String getMd5(long seckillId) {
        String md5 = seckillId + "/" + SALT;
        String result = DigestUtils.md5DigestAsHex(md5.getBytes());
        return result;
    }


    /**
     * 使用声明式 事务的优点：
     * 1.开发团队达成一致规定，明确标注事务方法的编程风格
     * 2 保证事务执行的方法尽可能的短，不要穿插其他的网络操作RPC/HTTP请求或者剥离到事务方法的外部
     * 3 不会所有的方法都需要事务 只有一条修改操作，只读操作不需要事务控制
     */
    @Override
    @Transactional
    public SeckillExecution executeSecKill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        try {
            if (md5 == null || !md5.equals(getMd5(seckillId))) {
                throw new SeckillException("seckill data rewrite");
            }
            /**
             * 减库存
             */
            Date nowDate = new Date();
            int insertCount = successKillDao.insertSuccessKill(seckillId, userPhone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("repeat seckill");
            } else {

                int updateCount = secKillDao.reduceNumber(seckillId, nowDate);
                if (updateCount <= 0) {
                    //没有更新到秒杀记录 秒杀结束
                    logger.debug("seckill closed");
                    throw new SeckillCloseException("seckill closed");
                } else {
                    //秒杀成功
                    SuccessKill successKill = successKillDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatusEnum.SUCCESS, successKill);
                }

            }

            /*int updateCount = secKillDao.reduceNumber(seckillId, nowDate);
            if (updateCount <= 0) {
                //没有更新到秒杀记录 秒杀结束
                logger.debug("seckill closed");
                throw new SeckillCloseException("seckill closed");
            } else {
                *//**
             * 记录购买行为
             *//*
             int insertCount = successKillDao.insertSuccessKill(seckillId, userPhone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("repeat seckill");
            } else {
                    //秒杀成功
                    SuccessKill successKill = successKillDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatusEnum.SUCCESS, successKill);
            }
            }*/
        } catch (SeckillCloseException seckill) {
            throw seckill;
        } catch (RepeatKillException repeat) {
            throw repeat;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            /**
             * 所有编译器异常，转化为运行时异常
             */
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }
}
