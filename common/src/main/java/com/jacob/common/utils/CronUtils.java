package com.jacob.common.utils;

import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

@Component
public class CronUtils {

    /**
     * 获取下一次执行时间
     *
     * @param cron Cron表达式
     * @return 下一次执行时间
     */
    public static LocalDateTime next(String cron) {

        CronExpression exp = CronExpression.parse(cron);

        return LocalDateTime.from(Objects.requireNonNull(exp.next(
                ZonedDateTime.now())).toInstant());

    }
}
