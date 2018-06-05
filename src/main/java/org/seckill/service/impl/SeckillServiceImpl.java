package org.seckill.service.impl;

import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public List<SecKill> getSeckillList() {
        return secKillDao.queryAll(0, 4);
    }

    @Override
    public SecKill getSecKillById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSecKillUrl(long seckillId) {
        SecKill secKill = secKillDao.queryById(seckillId);
        if (secKill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();

        /**
         * 当前系统的时间
         */
        Date now = new Date();
        if (startTime.getTime() > now.getTime() || endTime.getTime() < now.getTime()) {
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = "";
        md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }


    private String getMd5(long seckillId) {
        String md5 = seckillId + "/" + SALT;
        String result = DigestUtils.md5DigestAsHex(md5.getBytes());
        return result;
    }

    @Override
    public SeckillExecution executeSecKill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        return null;
    }
}
