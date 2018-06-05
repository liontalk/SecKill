package org.seckill.dao;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动的时候加载springIoc容器
 * <p>
 * Created by ZhouZhe on 2018/6/4.
 */

@RunWith(SpringJUnit4ClassRunner.class)

/**
 * 告诉junit spring的配置文件
 */
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {

    /**
     * 注入 Dao实现类依赖
     *
     * @throws Exception
     */
    @Autowired
    private SecKillDao secKillDao;


    @Test
    public void reduceNumber() throws Exception {
        int countNumber = secKillDao.reduceNumber(1000L,new Date());
        System.out.println(countNumber);
    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        SecKill secKill = secKillDao.queryById(id);
        System.out.println(secKill.getName());
        System.out.println(secKill);
    }

    @Test
    public void queryAll() throws Exception {
        List<SecKill> list = secKillDao.queryAll(0, 100);
        for (SecKill secKill : list) {
            System.out.println(secKill);
        }
    }

}