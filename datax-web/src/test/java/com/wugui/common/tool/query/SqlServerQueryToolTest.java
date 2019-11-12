package com.wugui.common.tool.query;

import com.wugui.common.dstool.query.QueryToolFactory;
import com.wugui.common.dstool.query.rdbms.AbstractRdbmsQueryTool;
import com.wugui.common.entity.JobJdbcDatasource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

@Slf4j
public class SqlServerQueryToolTest {
    private AbstractRdbmsQueryTool queryTool;
    private JobJdbcDatasource jdbcDatasource;

    @Before
    public void before() {
        genMysqlDemo();
        queryTool = QueryToolFactory.getRdbmsQueryTool(jdbcDatasource);
    }

    private void genMysqlDemo() {
        jdbcDatasource = new JobJdbcDatasource();
        jdbcDatasource.setDatasourceName("test");
        jdbcDatasource.setJdbcUsername("sa");
        jdbcDatasource.setJdbcPassword("Sa123");
        jdbcDatasource.setJdbcUrl("jdbc:sqlserver://10.20.1.196;databaseName=DataCenter");
        jdbcDatasource.setJdbcDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }

    @Test
    public void getTableNames() {
        List<String> tableNames = queryTool.getTableNames();
        tableNames.forEach(System.out::println);
    }

    @Test
    public void getColumnNames() {
        List<String> columns = queryTool.getColumnNames("BD_EMR_TYPE");
        log.info(columns.toString());
    }
}