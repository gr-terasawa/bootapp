package bootapp.config;

import javax.sql.DataSource;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Autowired
	DataSourceProperties properties;
	DataSource dataSource;

	@ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
	@Bean(destroyMethod = "close")
	DataSource realDataSource() {
		DataSourceBuilder factory = DataSourceBuilder
				.create(this.properties.getClassLoader())
				.url(this.properties.getUrl())
				.username(this.properties.getUsername())
				.password(this.properties.getPassword());
		this.dataSource = factory.build();
		return this.dataSource;
	}

	@Bean
	DataSource dataSource() {
		return new Log4jdbcProxyDataSource(this.dataSource);
	}
}
