package com.wugui.common.dstool.query.rdbms;

import com.wugui.common.dstool.query.QueryToolInterface;
import com.wugui.common.entity.JobJdbcDatasource;

import java.sql.SQLException;

/**
 * sql server
 *
 * @author zhouhongfa@gz-yibo.com
 * @version 1.0
 * @since 2019/8/2
 */
public class SqlServerQueryTool extends AbstractRdbmsQueryTool implements QueryToolInterface {
    public SqlServerQueryTool(JobJdbcDatasource jobJdbcDatasource) throws SQLException {
        super(jobJdbcDatasource);
    }
}
