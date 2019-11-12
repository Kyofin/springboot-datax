package com.wugui.common.dstool.query.rdbms;

import com.wugui.common.dstool.query.QueryToolInterface;
import com.wugui.common.entity.JobJdbcDatasource;

import java.sql.SQLException;

/**
 * mysql数据库使用的查询工具
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName MySQLQueryTool
 * @Version 1.0
 * @since 2019/7/18 9:31
 */
public class MySQLQueryTool extends AbstractRdbmsQueryTool implements QueryToolInterface {

    public MySQLQueryTool(JobJdbcDatasource codeJdbcDatasource) throws SQLException {
        super(codeJdbcDatasource);
    }

}
