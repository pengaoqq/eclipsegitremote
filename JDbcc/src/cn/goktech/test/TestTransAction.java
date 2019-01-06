package cn.goktech.test;



import java.sql.Connection;

import org.junit.Test;

import com.mysql.jdbc.Statement;

import cn.goktech.jdbc.JdbcUtil;
/**
 * 事务处理：一个业务(操作)逻辑通过多个子业务(操作)组成,那么这几个子业务
 * 			要么都成功，要么都不成功
 * 事务控制只能在同一个连接
 * @author 24963
 *
 */
public class TestTransAction {
	@Test
	public void transActionTest() throws Exception {
		Connection connection = null;
		try {
			connection = JdbcUtil.getConnection();
			//1、取消自动提交事务
			connection.setAutoCommit(false);
			String sql = "update student1 set name = '张三1' where age = 1";
			update(sql, connection);
			int i = 1/0;
			String sql2 = "update student1 set name = '小王1' where age = 3";
			update(sql2, connection);
		} catch (Exception e) {
			e.printStackTrace();
			//2、出现异常   在catch里回滚事务
			connection.rollback();
		} finally {
			//3、所有子操作执行完后提交事务
			connection.commit();
			connection.close();
		}
		
	}
	
	public void update(String sql, Connection connection) throws Exception {
		Statement statement = (Statement) connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
	}
}
