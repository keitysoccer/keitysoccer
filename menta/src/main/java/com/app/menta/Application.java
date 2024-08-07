package com.app.menta;

import com.app.menta.logging.RestApiLoggingFilter;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * LogFilterの設定
	 * @return
	 */
	@Bean
	public FilterRegistrationBean logFilter(){
		//フィルタのオブジェクトを1番目に実行するフィルタとして追加
		FilterRegistrationBean bean = new FilterRegistrationBean(new RestApiLoggingFilter());
		//コントローラ・静的コンテンツ全てのリクエストに対してフィルタを有効化
		bean.addUrlPatterns("/api/*");
		//フィルタの実行順序を1に設定
		bean.setOrder(1);
		return bean;
	}

	/**
	 * データソース Application.ymlに接続文字列を記載
	 * @return
	 */
	@Bean(name = "dataSource")
	@ConfigurationProperties("spring.datasource.test")
	public DataSource dataSource(){
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	/**
	 * トランザクションマネージャー
	 * @return
	 */
	@Bean(name = "datasourcemanager")
	public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource")DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}

}
