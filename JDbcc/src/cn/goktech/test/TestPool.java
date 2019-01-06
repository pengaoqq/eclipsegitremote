package cn.goktech.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
/**
 * 1、数据库连接池
 * 		1)放的是数据库连接
 * 		2)直接通过数据库连接池来获取连接，而不去和数据库通信，当请求较多时，可以起到缓冲作用
 * 2、DBCP	C3P0 -- 提供相应的jar包(就是一些实现的Java类)
 * 
 * @author 24963
 *
 */
public class TestPool {
	@Test
	public void testDbcp() throws Exception{
		//1、利用反射获取外部配置文件
		InputStream is = new FileInputStream("src/cn/goktech/jdbc/jdbc.propertie");
		Properties properties = new Properties();
		properties.load(is);
		//2、准备参数
		String driverClass =  properties.getProperty("driverClass");
		String user =  properties.getProperty("user");
		String pass =  properties.getProperty("pass");
		String jdbcUrl =  properties.getProperty("jdbcUrl");
		BasicDataSource dataSource = new BasicDataSource();
		//3、设置数据连接四要素
		dataSource.setDriverClassName(driverClass);
		dataSource.setUsername(user);
		dataSource.setPassword(pass);
		dataSource.setUrl(jdbcUrl);
		//4、设置数据连接池的最大值
		dataSource.setMaxActive(100);
		dataSource.setMinIdle(10);
		Connection con = dataSource.getConnection();
		System.out.println(con);
	}
}
