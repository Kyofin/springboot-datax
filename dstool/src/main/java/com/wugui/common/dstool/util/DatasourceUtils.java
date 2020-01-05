package com.wugui.common.dstool.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;
import com.wugui.common.entity.JobJdbcDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源工具类
 *
 * @author zhouhongfa@gz-yibo.com
 * @version 1.0
 * @since 2020/1/5
 */
public final class DatasourceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceUtils.class);

    private static final Map<Long, DataSource> DATA_SOURCE_MAP = new ConcurrentHashMap<>(2);

    /**
     * 构建 DataSource 对象
     *
     * @param jdbcDatasource
     * @return
     */
    public static DataSource getDatasource(JobJdbcDatasource jdbcDatasource) {
        DataSource dataSource;
        if (DATA_SOURCE_MAP.containsKey(jdbcDatasource.getId())) {
            LOGGER.info("从DATA_SOURCE_MAP中读取");
            dataSource = DATA_SOURCE_MAP.get(jdbcDatasource.getId());
        } else {
            dataSource = buildDruidDatasource(jdbcDatasource);
        }
        return dataSource;
    }

    private static DataSource buildDruidDatasource(JobJdbcDatasource jdbcDatasource) {
        //这里默认使用 Druid 数据源
        DruidDataSource druidDataSource = new DruidDataSource();
        //获取驱动类名
        try {
            //判断是否有指定
            if (StrUtil.isNotBlank(jdbcDatasource.getJdbcDriverClass())) {
                druidDataSource.setDriverClassName(jdbcDatasource.getJdbcDriverClass());
            } else {
                druidDataSource.setDriverClassName(JdbcUtils.getDriverClassName(jdbcDatasource.getJdbcUrl()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        druidDataSource.setUsername(jdbcDatasource.getJdbcUsername());
        druidDataSource.setPassword(jdbcDatasource.getJdbcPassword());
        druidDataSource.setUrl(jdbcDatasource.getJdbcUrl());

        // 设置一些属性
        druidDataSource.setDefaultReadOnly(true);
        druidDataSource.setEnable(true);
        druidDataSource.setConnectionErrorRetryAttempts(3);
        druidDataSource.setKeepAlive(true);
        druidDataSource.setMaxActive(1);
        druidDataSource.setKillWhenSocketReadTimeout(true);

        DATA_SOURCE_MAP.put(jdbcDatasource.getId(), druidDataSource);
        return druidDataSource;
    }

}
