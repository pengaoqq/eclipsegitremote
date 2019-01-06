package cn.goktech.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcUtil {
	public static Connection getConnection() throws Exception {
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
		return con;
	}
	
	//传入一个SQL 传入OBject类型的数据 （多个） ---更新操作
	public static void update(Connection connection, String sql, Object...args) throws Exception {		//可变参数 JDK1.5的新特性
		PreparedStatement ps = null;
		try {
			//2、通过数据库连接prepareStatement得到Statement对象
			ps = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			//3、调用executeUpdate方法执行更新操作
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//4、关闭数据库
			ps.close();
		}
	}

	public static List<Object> select(Connection connection, String className, String sql, Object ...args) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<>();
		try {
			ps = connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++)
			{
				ps.setObject(i+1, args[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int count=rsmd.getColumnCount();
			while(rs.next())
			{
				Object object=Class.forName(className).newInstance();
				Map<String, Object> map = new HashMap<>();
				for(int i=0;i<count;i++)
				{
					map.put(rsmd.getColumnLabel(i+1),rs.getObject(i+1));
				}
				Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
				String name=null;
				while(it.hasNext())
				{
					Map.Entry<String, Object> entry = it.next();
					Class clazz = object.getClass();
					name = entry.getKey();
					Field field = clazz.getDeclaredField(name);
					field.setAccessible(true);
					field.set(object, entry.getValue());
				}
				list.add(object);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ps.close();
		}
		return null;
	}
}


