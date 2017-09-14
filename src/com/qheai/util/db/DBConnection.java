package com.qheai.util.db;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 数据库连接管理
 * @author CM-TOSCI
 *
 */
public class DBConnection {
	
	private static Logger log = LoggerFactory.getLogger(DBConnection.class);
	
	/**
	 * 获取数据库连接
	 * @return 数据库连接
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception{
		return ConnectionManager.getInstance().getConnection();
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn 数据库连接
	 */
	public static void close(Connection conn)
	{
		try {
			conn.close();
		} catch (Throwable e) {
			DBConnection.log.error("关闭数据库连接错误：",e);
		}
	}

}
