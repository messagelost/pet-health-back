package com.jacob.common.utils;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 自定义雪花算法ID生成器
 */
@Component
public class SnowflakeIdGenerator implements IdentifierGenerator {

    private final Sequence sequence;

    /**
     * 构造函数：初始化雪花算法的 workerId 和 datacenterId
     * @param workerId 机器ID
     * @param datacenterId 数据中心ID
     */
    public SnowflakeIdGenerator(
            @Value("${snowflake.worker-id:0}") long workerId,
            @Value("${snowflake.datacenter-id:0}") long datacenterId) {

        if (workerId < 0 || workerId > 31) {
            throw new IllegalArgumentException("workerId 必须在 0-31 之间");
        }
        if (datacenterId < 0 || datacenterId > 31) {
            throw new IllegalArgumentException("datacenterId 必须在 0-31 之间");
        }

        this.sequence = new Sequence(workerId, datacenterId);
    }


    /**
     * 生成带前缀的雪花ID
     * @param prefix 业务前缀
     * @return 格式：前缀 + 雪花ID
     */
    public String generateIdWithPrefix(String prefix) {
        long snowflakeId = sequence.nextId();
        return StringUtils.isNotBlank(prefix) ? prefix + snowflakeId : Long.toString(snowflakeId);
    }

    /**
     * 默认生成无前缀雪花ID
     */
    @Override
    public Long nextId(Object entity) {
        return sequence.nextId();
    }
}