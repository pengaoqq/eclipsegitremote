package cn.goktech.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import cn.goktech.jdbc.JdbcUtil;
/**
 * MetaData访问元数据
 * 		1)DatabaseMetaData -- 记录数据库或表的一些信息
 * 		2)ResultSetMetaData -- 记录二维表一些信息
 * @author 24963
 *
 */
public class TestDatameta {
	@Test
	public void datametaTest() throws Exception {
		Connection connection = JdbcUtil.getConnection();
		DatabaseMetaData data = connection.getMetaData();
		//访问驱动名
		System.out.println(data.getDriverName());
		//访问数据库名
		System.out.println(data.getDatabaseProductName());
		//访问数据库版本号
		System.out.println(data.getDatabaseProductVersion());
	}
	@Test
	public void resultSetDataTest() throws Exception {
		Connection connection = JdbcUtil.getConnection();
		String sql = "select * from user";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		ResultSetMetaData data = resultSet.getMetaData();
		//返回多少列字段
		System.out.println(data.getColumnCount());
		//返回第二列的字段名
		System.out.println(data.getColumnName(2));
		//返回第一列字段的别名
		System.out.println(data.getColumnLabel(1));
	}
}
