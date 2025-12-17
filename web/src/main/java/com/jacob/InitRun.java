package com.jacob;

import com.jacob.common.redis.RedisUtils;
import io.lettuce.core.RedisConnectionException;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;


@Component("initRun")
public class InitRun implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitRun.class);
    private static final LocalDateTime APP_START_TIME = LocalDateTime.now();

    @Resource
    private DataSource dataSource;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public void run(ApplicationArguments args)  {
        LocalDateTime initFinishTime = LocalDateTime.now();
        Duration duration = Duration.between(APP_START_TIME, initFinishTime);
        long totalMillis = duration.toMillis();
        try {
            dataSource.getConnection();
            redisUtils.get("test");
            logger.info("服务器启动成功！总耗时{}毫秒",totalMillis);
        }catch (SQLException e){
            logger.error("数据库配置错误，请检查");
        }catch (RedisConnectionException e){
            logger.error("redis配置错误，请检查");
        }catch (Exception e){
            logger.error("服务启动失败");
        }
    }
}
