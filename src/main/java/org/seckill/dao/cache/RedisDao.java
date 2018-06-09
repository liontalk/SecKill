package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by ZhouZhe on 2018/6/9.
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }


    /**
     * 从缓存中去取数据
     *
     * @param seckillId
     * @return
     */
    public SecKill getSecKill(long seckillId) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {

                String key = "seckill:" + seckillId;
                //并没有实现内部序列化操作
                //get -->byte[] -->反序列化--》Object(Seckill)
                // 采用自定义序列化
                // protostuff:pojo
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //空对象
                    SecKill secKill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, secKill, schema);
                    // seckill 被反序列化
                    return secKill;
                }

            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 网缓存中存放数据
     *
     * @param secKill
     * @return
     */
    public String putSecKill(SecKill secKill) {
        // set Object(Seckill) --> byte[]  -->发送给Redis
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + secKill.getSecKillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(secKill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // 超时缓存
                int timeout = 50 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
