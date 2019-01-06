package cn.goktech.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import com.mysql.jdbc.Statement;

import cn.goktech.jdbc.JdbcUtil;
/**
 * SQL注入：通过非法手段将数据包里面的数据进行修改
 * JAVA里面可以通过PreparedStatement防止SQL注入
 * 
 * DOS攻击
 * @author 24963
 *
 */
public class TestInjection {
	@Test
	public void injection() throws Exception{
		Connection connection = JdbcUtil.getConnection();
		String name = "10";		//'or 1=1 or'	SQL注入攻击
		String pass = "10";
		String sql = "select  * from user where name = ? and pass = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, name);
		ps.setString(2, pass);
		ResultSet resultSet = ps.executeQuery();
		if(resultSet.next()){
			System.out.println("登陆成功");
		}else{
			System.out.println("用户名或密码错误");
		}
		resultSet.close();
		ps.close();
		connection.close();
	}
	
	@Test
	public void testStatement() throws Exception{
		Connection connection = JdbcUtil.getConnection();
		Statement statement = (Statement) connection.createStatement();
		connection.setAutoCommit(false);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String name = i + "";
			String pass = i + "";
			String sql = "insert into user values('"+name+"','"+pass+"')";
			statement.executeUpdate(sql);
		}
		long end = System.currentTimeMillis();
		System.out.println("共消耗了" + (end-start)/1000 + "秒");
		connection.commit();
		statement.close();
		connection.close();
	}
	
	@Test
	public void testPreparedStatement() throws Exception{
		Connection connection = JdbcUtil.getConnection();
		String sql = "insert into user values(?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		connection.setAutoCommit(false);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String name = i + "";
			String pass = i + "";
			ps.setString(1, name);
			ps.setString(2, pass);
			ps.executeUpdate(sql);
		}
		long end = System.currentTimeMillis();
		System.out.println("共消耗了" + (end-start)/1000 + "秒");
		connection.commit();
		ps.close();
		connection.close();
	}
}
