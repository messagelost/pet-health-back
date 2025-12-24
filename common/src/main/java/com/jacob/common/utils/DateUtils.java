package com.jacob.common.utils;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateUtils {
    /**
     * 毫秒时间戳转指定格式日期字符串
     * @param timestamp 毫秒时间戳
     * @param pattern 格式（如：yyyy-MM-dd HH）
     * @return 格式化后的日期
     */
    public static String timestampToStr(Long timestamp, String pattern) {
        if (timestamp == null) {
            return "";
        }
        return DateUtil.format(new Date(timestamp), pattern);
    }

    /**
     * 按小时聚合时间（用于分析时间分布）
     */
    public static String groupByHour(Long timestamp) {
        return timestampToStr(timestamp, "yyyy-MM-dd HH");
    }

    /**
     * 按天聚合时间
     */
    public static String groupByDay(Long timestamp) {
        return timestampToStr(timestamp, "yyyy-MM-dd");
    }
}
