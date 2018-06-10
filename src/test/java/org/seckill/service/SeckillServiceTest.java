package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
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
        List<SecKill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void getSecKillById() throws Exception {
        long id = 1000L;
        SecKill secKill = seckillService.getSecKillById(id);
        logger.info("secKill={}", secKill);
    }

    @Test
    public void exportSecKillUrl() {
        long id = 1000L;
        Exposer exposer = seckillService.exportSecKillUrl(id);
        System.out.println(exposer);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSecKill() throws Exception {
        long id = 1000L;
        long userPhone = 18701797171L;
        String md5 = "c3f0907d160304e09bbe7adac6067d37";

        SeckillExecution seckillExecution = null;
        try {
            seckillExecution = seckillService.executeSecKill(id, userPhone, md5);
            logger.info("seckillExecution={}", seckillExecution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * 代码测试完整的逻辑
     *
     * @throws Exception
     */
    @Test
    public void seckillLogic() throws Exception {
        long id = 1000L;
        long userPhone = 18701797173L;
        Exposer exposer = seckillService.exportSecKillUrl(id);
        if (exposer.isExposed()) {
            SeckillExecution seckillExecution = null;
            try {
                seckillExecution = seckillService.executeSecKill(id, userPhone, exposer.getMd5());
                logger.info("seckillExecution={}", seckillExecution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }

    }


    @Test
    public void executeSecKillByProcedure() {
        long seckillId = 1002L;
        long phone = 18701797878L;
        Exposer exposer = seckillService.exportSecKillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSecKillByProcedure(seckillId, phone, md5);
            logger.info("秒杀结果：" + seckillExecution.getStatusInfo());
        }
    }
}