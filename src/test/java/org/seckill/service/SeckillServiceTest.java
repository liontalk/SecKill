package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ZhouZhe on 2018/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<SecKill> list =seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getSecKillById() throws Exception {
        long id = 1000L;
        SecKill secKill = seckillService.getSecKillById(id);
        logger.info("secKill={}",secKill);
    }

    @Test
    public void exportSecKillUrl() {
        long id = 1000L;
        Exposer exposer = seckillService.exportSecKillUrl(id);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSecKill() throws Exception {
    }

}