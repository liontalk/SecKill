package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillResult;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.enums.SeckillStatusEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by ZhouZhe on 2018/6/6.
 */
@Controller
@RequestMapping(value = "/seckill")//对应的模块/资源/{ID}/细分 /seckill/list
public class SecKillController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillService seckillService;

    /**
     * 获取列表页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<SecKill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "seckill/list";
    }


    /**
     * 查看详情
     *
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, ModelMap model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        SecKill secKill = seckillService.getSecKillById(seckillId);
        if (secKill == null) {
            return "forward:/seckill/list";
        }
        model.put("secKill",secKill);
        return "seckill/detail";
    }


    /**
     * 暴露秒杀接口
     *
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public SecKillResult<Exposer> exposerSeckillUrl(@PathVariable("seckillId") Long seckillId) {
        SecKillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSecKillUrl(seckillId);
            result = new SecKillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SecKillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }


    /**
     * 执行秒杀功能
     *
     * @param seckillId
     * @param md5
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execute", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public SecKillResult<SeckillExecution> executeSecKill(@PathVariable("seckillId") Long seckillId,
                                                          @PathVariable("md5") String md5,
                                                          @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new SecKillResult<SeckillExecution>(false, "用户未注册");
        }
        SecKillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution = seckillService.executeSecKill(seckillId, userPhone, md5);
            result = new SecKillResult<SeckillExecution>(true, seckillExecution);
        } catch (RepeatKillException repeat) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatusEnum.REPEAT_KILL);
            return new SecKillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillCloseException close) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatusEnum.END);
            return new SecKillResult<SeckillExecution>(true, seckillExecution);
        } catch (Exception e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatusEnum.INNER_ERROR);
            return new SecKillResult<SeckillExecution>(true, seckillExecution);
        }
        return result;
    }


    /**
     * 获取系统时间
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SecKillResult<Long> getSystemTime() {
        Date date = new Date();
        return new SecKillResult<Long>(true, date.getTime());
    }

}
