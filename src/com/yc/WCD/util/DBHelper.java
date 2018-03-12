package com.yc.WCD.util;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.sql.BLOB;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.logging.log4j.LogManager;

public class DBHelper {
	private static DataSource dataSource;
	static {
		try {
			//Class.forName(DRIVER_CLASS_NAME); // 1.加载驱动
			Properties props = new Properties();
			props.load(DBHelper.class.getClassLoader().getResourceAsStream("db.properties"));
			
			//使用连接池技术, 数据源DBCP
			dataSource = BasicDataSourceFactory.createDataSource(props);
			LogManager.getLogger().debug("加载数据库属性元素构建数据源成功...");
		} catch (Exception e) {
			LogManager.getLogger().error("加载数据库属性元素构建数据源！！！", e);
		}
	}

	/**
	 * 建立连接
	 * 
	 * @return
	 */
	public static Connection getConn() {
		Connection con = null;
		try {
			// 2.建立与数据库的 连接
			//使用连接池技术, 数据源DBCP
			con = dataSource.getConnection();
			LogManager.getLogger().debug("数据库连接成功...");
		} catch (Exception e) {
			LogManager.getLogger().error("数据库连接失败！！！", e);
		}
		return con;
	}

	/**
	 * 关闭操作
	 * 
	 * @param con
	 *            数据库连接
	 * @param st
	 *            sql执行工具
	 * @param rs
	 *            返回结果集
	 */
	public static void close(Connection con, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				LogManager.getLogger().debug("关闭结果集完成...");
			} catch (SQLException e) {
				LogManager.getLogger().error("关闭结果集失败！！！", e);
			}
		}
		if (st != null) {
			try {
				st.close();
				LogManager.getLogger().debug("关闭执行工具完成...");
			} catch (SQLException e) {
				LogManager.getLogger().error("关闭执行工具失败！！！", e);
			}
		}

		if (con != null) {
			try {
				con.close();
				LogManager.getLogger().debug("关闭数据库连接完成...");
			} catch (SQLException e) {
				LogManager.getLogger().error("关闭数据库连接失败！！！", e);
			}
		}
	}

	/**
	 * 
	 * @param sql
	 *            要执行的sql语句 insert, update, delete)
	 * @param params
	 *            执行sql语句需要的参数
	 * @return 执行sql语句受影响的行数
	 */
	public static int doUpdate(String sql, Object... params) {
		Connection con = null;
		PreparedStatement st = null;
		int result = 0;
		try {
			con = getConn();
			LogManager.getLogger().debug("要执行sql语句：" + sql);
			st = con.prepareStatement(sql);
			setParams(st, params); // 设置参数
			LogManager.getLogger().debug("sql执行工具创建成功...");
		} catch (SQLException e) {
			LogManager.getLogger().error("sql执行工具创建失败！！！", e);
		}

		try {
			result = st.executeUpdate(); // 执行sql , 针对insert, delete,
												// update, 返回结果是受影响行数
			LogManager.getLogger().debug("插入数据成功，插入数据的条数是：：" + result);
		} catch (SQLException e) {
			LogManager.getLogger().error("插入数据失败!!!", e);
		}finally{
			// 5.关闭连接
			DBHelper.close(con, st, null);
		}
		return result;
	}
	
	/**
	 * 
	 * @param sql  要执行的sql语句
	 * @param objs 执行sql语句需要的参数
	 * @return  取出数据库的数据,每一条记录是一个map : key是字段名或字段别名(小写字母), value应对字段的值
	 */
	public static List<Map<String, Object>> doQuery(String sql, Object...objs){
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;		
		List<Map<String, Object>> results = null;
		try {
			con = getConn();
			st = con.prepareStatement(sql); // 3.sql执行工具
			setParams(st, objs);
			LogManager.getLogger().debug("sql执行工具创建成功...");
		} catch (SQLException e) {
			LogManager.getLogger().error("sql执行工具创建失败！！！", e);
		}

		try {
			rs = st.executeQuery(); // 4.执行sql取到返回数据白结果集
			LogManager.getLogger().debug("执行sql取到返回数据成功...");
		} catch (SQLException e) {
			LogManager.getLogger().error("执行sql取到返回数据失败！！！", e);
		}

		try {
			ResultSetMetaData rsmd = rs.getMetaData(); // 元数据; 对象取取到的结果集数据的描述
			int cloumCount = rsmd.getColumnCount();
			results = new ArrayList<Map<String, Object>>();
			while (rs.next()) { // 判断结果集是否还有数据 (数据是一条记录的方式取出)
				Map<String, Object> record = new HashMap<String, Object>();
				for (int i = 1; i <= cloumCount; i++) {
					//rsmd.getColumnName(i) ：表的字段名或字段别名
					//rs.getObject(i) : 取到字段对应的值
					record.put(rsmd.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				results.add(record);
			}
			LogManager.getLogger().debug("取出结果集数据完成...");
		} catch (SQLException e) {
			LogManager.getLogger().error("取出结果集数据失败！！！", e);
		} finally {
			DBHelper.close(con, st, rs);
		}
		return results;
	}
	
	/**
	 * 
	 * @param sql  要执行的sql语句
	 * @param objs 执行sql语句需要的参数
	 * @return  取出数据库的数据, key是字段名或字段别名(小写字母), value应对字段的值
	 */
	public static Map<String, Object> doQueryOne(String sql, Object...objs){
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Map<String, Object> results = null;
		try {
			con = getConn();
			st = con.prepareStatement(sql); // 3.sql执行工具
			setParams(st, objs);
			LogManager.getLogger().debug("sql执行工具创建成功...");
		} catch (SQLException e) {
			LogManager.getLogger().error("sql执行工具创建失败！！！", e);
		}

		try {
			rs = st.executeQuery(); // 4.执行sql取到返回数据白结果集
			LogManager.getLogger().debug("执行sql取到返回数据成功...");
		} catch (SQLException e) {
			LogManager.getLogger().error("执行sql取到返回数据失败！！！", e);
		}

		try {
			ResultSetMetaData rsmd = rs.getMetaData(); // 元数据; 对象取取到的结果集数据的描述
			int cloumCount = rsmd.getColumnCount();

			if (rs.next()) { // 判断结果集是否还有数据 (数据是一条记录的方式取出)
				results = new HashMap<String, Object>();
				for (int i = 1; i <= cloumCount; i++) {
					//rsmd.getColumnName(i) ：表的字段名或字段别名
					//rs.getObject(i) : 取到字段对应的值
					Object obj = rs.getObject(i);
					if(obj instanceof BLOB){//是否是blob类型的数据
						Blob blob = rs.getBlob(i);
						obj = blob.getBinaryStream();
					}
					results.put(rsmd.getColumnName(i).toLowerCase(), obj);
				}
			}
			LogManager.getLogger().debug("取出结果集数据完成...");
		} catch (SQLException e) {
			LogManager.getLogger().error("取出结果集数据失败！！！", e);
		} finally {
			DBHelper.close(con, st, rs);
		}
		return results;
	}

	private static void setParams(PreparedStatement st, Object... objs) {
		// 判断是否有参数
		if (objs == null || objs.length == 0) {
			return;
		}
		int flag = 0;
		try {
			for (int i = 0; i < objs.length; i++) {
				flag = i + 1;
				Object obj = objs[i];
				if(obj instanceof InputStream){//判断是否是InputStream类型
					st.setBinaryStream(i+1, (InputStream) obj);
				}else{
					st.setObject(i+1, obj);
				}
			}
		} catch (SQLException e) {
			LogManager.getLogger().error(String.format("注入第%d值时失败!!!", flag),e);
		}
	}
}
