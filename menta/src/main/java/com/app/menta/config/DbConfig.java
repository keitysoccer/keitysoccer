package com.app.menta.config;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.NoCacheSqlFileRepository;
import org.seasar.doma.jdbc.SqlFileRepository;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.PostgresDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@Component
@Scope("singleton")
@Primary
public class DbConfig implements Config {

    private Dialect dialect = new PostgresDialect();

    private SqlFileRepository sqlFileRepository = new NoCacheSqlFileRepository();

    private DataSource dataSource;

    @Autowired
    public void setDataSource(@Qualifier("dataSource")DataSource dataSource) {
        // SpringJdbcのTransaction管理下に置くためwrappingする
        this.dataSource = new TransactionAwareDataSourceProxy(dataSource);
    }

    @Override
    public String getDataSourceName(){return getClass().getName();}

    @Override
    public Dialect getDialect(){return this.dialect;}

    @Override
    public SqlFileRepository getSqlFileRepository(){return this.sqlFileRepository;}

    @Override
    public DataSource getDataSource(){return this.dataSource;}

}
