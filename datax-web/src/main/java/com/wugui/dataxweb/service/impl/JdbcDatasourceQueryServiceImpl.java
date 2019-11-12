package com.wugui.dataxweb.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.wugui.common.dstool.query.QueryToolFactory;
import com.wugui.common.dstool.query.rdbms.AbstractRdbmsQueryTool;
import com.wugui.common.entity.JobJdbcDatasource;
import com.wugui.dataxweb.service.IJobJdbcDatasourceService;
import com.wugui.dataxweb.service.JdbcDatasourceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName JdbcDatasourceQueryServiceImpl
 * @Version 1.0
 * @since 2019/7/31 20:51
 */
@Service
public class JdbcDatasourceQueryServiceImpl implements JdbcDatasourceQueryService {

    @Autowired
    private IJobJdbcDatasourceService jobJdbcDatasourceService;

    @Override
    public List<String> getTables(Long id) {
        //获取数据源对象
        JobJdbcDatasource jdbcDatasource = jobJdbcDatasourceService.getById(id);
        //queryTool组装
        if (ObjectUtil.isNull(jdbcDatasource)) {
            return Lists.newArrayList();
        }
        AbstractRdbmsQueryTool queryTool = QueryToolFactory.getRdbmsQueryTool(jdbcDatasource);
        return queryTool.getTableNames();
    }

    @Override
    public List<String> getColumns(Long id, String tableName) {
        //获取数据源对象
        JobJdbcDatasource jdbcDatasource = jobJdbcDatasourceService.getById(id);
        //queryTool组装
        if (ObjectUtil.isNull(jdbcDatasource)) {
            return Lists.newArrayList();
        }
        AbstractRdbmsQueryTool queryTool = QueryToolFactory.getRdbmsQueryTool(jdbcDatasource);
        return queryTool.getColumnNames(tableName);
    }

    @Override
    public List<String> getColumnsByQuerySql(Long datasourceId, String querySql) {
        //获取数据源对象
        JobJdbcDatasource jdbcDatasource = jobJdbcDatasourceService.getById(datasourceId);
        //queryTool组装
        if (ObjectUtil.isNull(jdbcDatasource)) {
            return Lists.newArrayList();
        }
        AbstractRdbmsQueryTool queryTool = QueryToolFactory.getRdbmsQueryTool(jdbcDatasource);
        return queryTool.getColumnsByQuerySql(querySql);
    }
}
