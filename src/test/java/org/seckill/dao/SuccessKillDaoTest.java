package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import static org.junit.Assert.*;

/**
 * Created by ZhouZhe on 2018/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDaoTest {

    @Autowired
    SuccessKillDao successKillDao;


    @Test
    public void insertSuccessKill() throws Exception {
        long id = 1001L;
        long phone = 18701797172L;
        int insertCount = successKillDao.insertSuccessKill(id,phone);
        System.out.println(insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKill successKill = successKillDao.queryByIdWithSeckill(1000L,18701797172L);
        System.out.println(successKill);
        System.out.println(successKill.getSecKill());
    }

}