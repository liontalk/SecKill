-- 秒杀的执行存储过程
-- CONSOLE ; 转换为$$
DELIMITER $$
drop  procedure if EXISTS success_kill;
-- 定义存储过程
-- 参数： in 输入参数  out:输出参数
-- row_count(); 返回上一条修改类型影响的sql（delete,insert,update）行数
-- row_count() 0 未修改数  1：表示修改的函数  0> sql错误/未执行修改的sql
CREATE PROCEDURE `success_kill`(
  IN  v_seckill_id    BIGINT,
  IN  v_seckill_phone BIGINT,
  IN  v_seckill_time  TIMESTAMP,
  OUT r_result        INT
)
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION;
    INSERT IGNORE INTO success_kill (
      seckill_id, user_phone, create_time
    ) VALUES (
      v_seckill_id, v_seckill_phone, v_seckill_time
    );
    SELECT row_count()
    INTO insert_count;
    IF (insert_count = 0)
    THEN
      ROLLBACK;
      SET r_result = -1;
    ELSEIF (insert_count < 0)
      THEN
        ROLLBACK;
        SET r_result = -2;
    ELSE
      UPDATE seckill
      SET number = number - 1
      WHERE seckill_id = v_seckill_id
            AND start_time < v_seckill_time
            AND end_time > v_seckill_time
            AND number > 0;
      SELECT row_count()
      INTO insert_count;
      IF (insert_count = 0)
      THEN
        ROLLBACK;
        SET r_result = 0;
      ELSEIF (insert_count < 0)
        THEN
          ROLLBACK;
          SET r_result = -2;
      ELSE
        COMMIT;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$$
-- 存储过程定义结束

DELIMITER ;
set @r_result = -3;
-- 执行存储过程
call success_kill(1003,18701797179,now(),@r_result);

-- 获取结果
SELECT @r_result;

-- 存储过程
-- 1.存储过程优化：事务行级锁持有的时间
-- 2.不要过度依赖存储过程
-- 3.简单的逻辑可以应用存储过程
-- 4.QPS:一个秒杀单6000/qps

