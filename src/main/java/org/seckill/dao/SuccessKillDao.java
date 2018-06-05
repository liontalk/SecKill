package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKill;

/**
 * Created by ZhouZhe on 2018/6/2.
 */
public interface SuccessKillDao {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKill(@Param("seckillId") long seckillId, @Param("userPhone")long userPhone);


    /**
     * 根据id查找SuccessKilled 并携带秒杀对象实体
     * @param secKillID
     * @return
     */
    SuccessKill queryByIdWithSeckill(@Param("secKillID") long secKillID,@Param("userPhone") long userPhone);

}
