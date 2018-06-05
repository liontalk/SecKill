package org.seckill.exception;

/** 重复秒杀异常
 * Created by ZhouZhe on 2018/6/4.
 */
public class RepeatKillException extends SeckillException {



    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatKillException(String message) {
        super(message);
    }
}
