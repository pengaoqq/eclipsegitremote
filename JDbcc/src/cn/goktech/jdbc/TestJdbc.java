package cn.goktech.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.junit.Test;

import com.mysql.jdbc.Driver;

import com.mysql.jdbc.Statement;

import cn.goktech.entity.Student;

public class TestJdbc {
	/**
	 * Driver: 是一个所有数据库产商都需要实现的一个接口
	 * 可以通过Driver获取数据库连接(将java程序和数据库发生关系)
	 * @throws Exception 
	 */
	@Test
	public void testDriver() throws Exception {
		//1、得到Driver对象
		Driver driver = new Driver();
		//2、准备参数
		Properties properties = new Properties();
		properties.setProperty("user", "root");
		properties.setProperty("password", "");
		//3、数据库url
		String jdbcUrl = "jdbc:mysql://localhost:3306/db1";
		//4、调用driver的connect方法获取数据库连接
		Connection con = driver.connect(jdbcUrl, properties);
		System.out.println(con);
	}
	/**
	 * 需求：将程序和数据连接信息解耦
	 * 解决：通过配置文件读取相关信息
	 * @throws Exception
	 */
	@Test
	public void testDriver2() throws Exception {
		//1、获取外部配置文件
		InputStream is = new FileInputStream("src/cn/goktech/jdbc/jdbc.propertie");
		Properties properties = new Properties();
		properties.load(is);
		//2、准备参数
		String driverClass = properties.getProperty("driverClass");
		String user = properties.getProperty("user");
		String pass = properties.getProperty("pass");
		String jdbcUrl = properties.getProperty("jdbcUrl");
		Properties info = new Properties();
		info.setProperty("user", user);
		info.setProperty("password", pass);
		//3、反射机制  通过全类名获该类的对象
		Driver driver = (Driver) Class.forName(driverClass).newInstance();
		//4、通过connect方法获取数据库连接
		Connection con = driver.connect(jdbcUrl, info);
		System.out.println(con);
	}
	/**
	 * DriverManager -- 数据库管理驱动类
	 * 1、管理驱动
	 * 2、获取连接
	 * 		1.准备4个数据：1、驱动全类名  com.mysql.jdbc.Driver	2、用户名	3、密码	4、URL
	 * 		2.通过 DriverManager的静态方法getConnection 获取连接
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDriverManager() throws Exception {
		//1、获取外部配置文件
		InputStream is = new FileInputStream("src/cn/goktech/jdbc/jdbc.propertie");
		Properties properties = new Properties();
		properties.load(is);
		//2、准备参数
		String driverClass = properties.getProperty("driverClass");
		String user = properties.getProperty("user");
		String pass = properties.getProperty("pass");
		String jdbcUrl = properties.getProperty("jdbcUrl");
		//获取连接时传入的参数 properties类型的 2个K-V  K值一定是user 和password
		Properties info = new Properties();
		info.setProperty("user", user);
		info.setProperty("password", pass);
		//3、反射机制  通过全类名获该类的对象
		Class.forName(driverClass);
		//4、通过getConnection方法获取数据库连接
		Connection con = DriverManager.getConnection(jdbcUrl, info);
		System.out.println(con);
	}
	/**
	 * Statement -- 可以执行SQL的对象
	 * connection.createStatement()得到statement对象
	 * @throws Exception
	 */
	@Test
	public void testStatement() throws Exception {
		Connection connection = null;
		Statement statement = null;
		try {
			//1、获取数据库连接
			connection = JdbcUtil.getConnection();
			//2、建立数据库连接通道
			statement = (Statement) connection.createStatement();
			//3、准备一个sql
			String sql = "insert into student1 values(id,'倩倩','女')";
			//4、调用executeUpdate方法执行更新操作
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//5、关闭数据库
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection.close();
		}
	}
	/**
	 * Statement的executeQuery得到
	 * 		1、有一个游标(指针)，默认指向第一行之前
	 * 		2、next() 1.让游标移动一行  2.返回值标识有没有下一行
	 * @throws Exception
	 */
	@Test
	public void testResultSet() throws Exception {
		Connection connection = null;
		Statement statement = null;
		try {
			//1、获取数据库连接
			connection = JdbcUtil.getConnection();
			//2、通过数据库连接createStatement得到Statement对象
			statement = (Statement) connection.createStatement();
			//3、准备一个sql
			String sql = "select * from student1";
			//4、调用executeQuery方法执行查询操作
			ResultSet resultSet = statement.executeQuery(sql);
			//5、解析resultSet -- next()	getxxx(index)
			while(resultSet.next()){
				System.out.print(resultSet.getString(1) + '\t');
				System.out.print(resultSet.getString(2) + '\t');
				System.out.print(resultSet.getString(3) + '\t');
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//6、关闭数据库
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection.close();
		}
	}
	/**
	 * 1、是statement的子接口
	 * 2、参数可以用？号表示
	 * 3、可以通过setxxx()为参数设置
	 * @throws Exception
	 */
	@Test
	public void testPreparedStatement() throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Student student = new Student(35, "加菲猫", "男");
			//1、获取数据库连接
			connection = JdbcUtil.getConnection();
			//2、准备一个sql
			String sql = "insert into student1 values(id,?,?)";
			//3、通过数据库连接prepareStatement得到Statement对象
			ps = connection.prepareStatement(sql);
			//4、通过setxxx 设置参数
			ps.setString(1, "小王");
			ps.setString(2, "男");
			//5、调用executeUpdate方法执行更新操作
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//6、关闭数据库
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection.close();
		}
	}
	
	@Test
	public void test1() throws Exception{
		Connection con = JdbcUtil.getConnection();
		System.out.println(con);
	}
	
	@Test
	public void test2() throws Exception{
		String sql = "insert into student1 values(id,?,?)";
		JdbcUtil.update(JdbcUtil.getConnection(), sql, "王三", "男");
	}
}
