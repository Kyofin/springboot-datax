package com.wugui.common.dstool.query.rdbms;

import com.wugui.common.dstool.query.QueryToolInterface;
import com.wugui.common.entity.JobJdbcDatasource;

import java.sql.SQLException;

/**
 * TODO
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName PostgresqlQueryTool
 * @Version 1.0
 * @since 2019/8/2 11:28
 */
public class PostgresqlQueryTool extends AbstractRdbmsQueryTool implements QueryToolInterface {
    public PostgresqlQueryTool(JobJdbcDatasource jobJdbcDatasource) throws SQLException {
        super(jobJdbcDatasource);
    }

}
