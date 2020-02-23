package com.wugui.common.dstool.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;
import com.wugui.common.entity.JobJdbcDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源工具类
 *
 * @author zhouhongfa1996@gmail.com
 * @version 1.0
 * @since 2020/1/5
 */
public final class DatasourceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceUtils.class);

    private static Map<Long, DruidDataSource> DATASOURCE_MAP = new ConcurrentHashMap<>(2);

    private static Map<Long, Connection> CONNECTION_MAP = new ConcurrentHashMap<>(2);

    /**
     * 构建 DataSource 对象
     *
     * @param jdbcDatasource
     * @return
     */
    public static DataSource getDatasource(JobJdbcDatasource jdbcDatasource) {
        return buildDruidDatasource(jdbcDatasource);
    }

    private static DataSource buildDruidDatasource(JobJdbcDatasource jdbcDatasource) {
        //这里默认使用 Druid 数据源
        DruidDataSource druidDataSource = new DruidDataSource();
        if (ObjectUtil.isNotNull(jdbcDatasource.getId()) && CONNECTION_MAP.containsKey(jdbcDatasource.getId())) {
            LOGGER.info("get datasource from DATASOURCE_MAP");
            druidDataSource = DATASOURCE_MAP.get(jdbcDatasource.getId());
            return druidDataSource;
        }
        //获取驱动类名
        try {
            //判断是否有指定
            if (StrUtil.isNotBlank(jdbcDatasource.getJdbcDriverClass())) {
                druidDataSource.setDriverClassName(jdbcDatasource.getJdbcDriverClass());
            } else {
                druidDataSource.setDriverClassName(JdbcUtils.getDriverClassName(jdbcDatasource.getJdbcUrl()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
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
        DATASOURCE_MAP.put(jdbcDatasource.getId(), druidDataSource);
        return druidDataSource;
    }

    /**
     * 获取connection
     *
     * @param jdbcDatasource
     * @return
     */
    //todo 优化代码
    public static Connection getConnection(JobJdbcDatasource jdbcDatasource) {
        Connection connection;
        try {
            if (ObjectUtil.isNotNull(jdbcDatasource.getId()) && CONNECTION_MAP.containsKey(jdbcDatasource.getId())) {
                LOGGER.info("get connection from CONNECTION_MAP");
                connection = CONNECTION_MAP.get(jdbcDatasource.getId());
            } else {
                connection = getDatasource(jdbcDatasource).getConnection();
                saveConnection(jdbcDatasource.getId(), connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //尝试重新获取连接
            try {
                connection = getDatasource(jdbcDatasource).getConnection();
                saveConnection(jdbcDatasource.getId(), connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
        //判断是否可用
        try {
            if (!connection.isValid(500)) {
                connection = getDatasource(jdbcDatasource).getConnection();
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            LOGGER.error("连接已失效，尝试获取新连接……");
            //这里重新获取新的
            try {
                connection = getDatasource(jdbcDatasource).getConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
                //移除
                CONNECTION_MAP.remove(jdbcDatasource.getId());
                //返回空
                return null;
            }
        }
        saveConnection(jdbcDatasource.getId(), connection);
        return connection;
    }

    private static void saveConnection(Long datasourceId, Connection connection) {
        //如果没有id，则忽略
        if (datasourceId != null) {
            CONNECTION_MAP.put(datasourceId, connection);
        }
    }
}
