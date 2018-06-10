package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SecKill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhouZhe on 2018/6/2.
 */
public interface SecKillDao {

    /**
     * 减少库存
     * @param seckillId
     * @param killTime
     * @return
     */
     int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);


    /**
     * 查询秒杀商品的详情
     * @param seckillId
     * @return
     */
     SecKill queryById(@Param("seckillId")long seckillId);


    /**
     * 根据偏移量去查找秒杀商品的列表
     * @param offset
     * @param limit
     * @return
     */
     List<SecKill> queryAll(@Param("offset")int offset,@Param("limit") int limit);


    /**
     * 使用存储过程进行数据秒杀
     * @param map
     */
    void executeSecKillByProcedure(Map<String,Object> map);
}
