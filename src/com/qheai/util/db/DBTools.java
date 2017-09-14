package com.qheai.util.db;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据库操作封装的工具类，具有连接、操作、关闭的一体性。
 * 调用每个方法都会创建一个连接，方法返回后会及时关闭数据库。
 * 并且一些update或updates方法具有事务处理的功能，
 * 因此可以安全的的直接使用该类，若要控制更加复杂的事务，
 * 用该类一个方法解决不了的情况下，则考虑使用<code>DBService</code>类。
 * @author CM-TOSCI
 */
@SuppressWarnings("rawtypes")
public class DBTools {
	
    /**
     * 获得一个首行首列的字符串
     * @param sql SQL语句
     * @return
     */
    public static String getString(String sql) throws SQLException{
		DBService db = new DBService();
        try {
            return db.getString(sql);
        } finally {
            db.close();
        }
    }
    
    /**
     * 采用预编译SQL语句形式首行首列的字符串
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 返回字符串，若没有查询到数据则返回null。
     * @throws SQLException
     */
    public static String getString(String sql, Object[] params) throws SQLException {
    	DBService db = new DBService();
        try {
            return db.getString(sql,params);
        } finally {
            db.close();
        }
    }
    
    /**
     * 获取首列的list集合
     * @param sql SQL语句
     * @return
     * @throws SQLException 
     */
	public static List<String> getStringList(String sql) throws SQLException {
    	DBService db = new DBService();
        try {
            return db.getStringList(sql);
        } finally {
            db.close();
        }
    }
    
    /**
     * 利用预编译SQL语句查询数据库，获得元素为String的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return List(String)元素为String的列表
     * @throws SQLException
     */
	public static List<String> getStringList(String sql, Object[] params) throws SQLException {
		DBService db = new DBService();
        try {
            return db.getStringList(sql,params);
        } finally {
            db.close();
        }
    }
	
	/**
	 * 根据SQL语句查询数据库并将取得的第一行数据存放到String数组中并返回。
	 * @param sql SQL语句
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
	 */
	public static String[] getStringArray(String sql) throws SQLException{
		DBService db = new DBService();
        try {
            return db.getStringArray(sql);
        } finally {
            db.close();
        }
	}
	
	/**
     * 根据预编译SQL语句查询数据库并将取得的第一行数据存放到String数组中并返回。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public static String[] getStringArray(String sql, Object[] params) throws SQLException {
    	DBService db = new DBService();
        try {
            return db.getStringArray(sql,params);
        } finally {
            db.close();
        }
    }
    
    /**
     * 是否查询到数据
     * @param sql 查询SQL语句
     * @return boolean 查询的结果集中有数据则返回true，否则返回false。
     * @throws SQLException
     */
    public static boolean hasData(String sql) throws SQLException
    {
    	DBService db = new DBService();
        try {
            return db.hasData(sql);
        } finally {
            db.close();
        }
    }
    
    /**
     * 采用预编译SQL语句形式获得是否查询到数据
     * @param sql 预编译SQL语句，该SQL中只允许一个参数
     * @param params SQL参数，可为null。
     * @return 查询到数据则返回true
     * @throws SQLException
     */
    public static boolean hasData(String sql, Object[] params) throws SQLException {
    	DBService db = new DBService();
        try {
            return db.hasData(sql,params);
        } finally {
            db.close();
        }
    }
    
	
	/**
     * 利用预编译SQL语句查询数据库，并从结果集的指定起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @return List(String[])元素为String数组的列表
     * @throws SQLException
     */
    public static List<String[]> getListArray(String sql) throws SQLException
    {
    	DBService db = new DBService();
        try {
            return db.getListArray(sql);
        } finally {
            db.close();
        }
    }
    
    /**
     * 利用预编译SQL语句查询数据库，并从结果集的指定起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @throws SQLException
     */
    public static List<String[]> getListArray(String sql, Object[] params) throws SQLException
    {
    	DBService db = new DBService();
        try {
            return db.getListArray(sql,params);
        } finally {
            db.close();
        }
    }
	
    
    /**
     * 返回一行数组组装成JavaBean
     * @param <T>
     * @param sql
     * @return 
     * @return
     */
	public static Object getBean(Class cls,String sql,String[] beanArray) throws Exception{
    	DBService db = new DBService();
        try {
            return db.getBean(cls,sql,beanArray);
        } finally {
            db.close();
        }
    }
    
    /**
     * 返回一行数组组装成JavaBean
     * @param <T>
     * @param sql
     * @return 
     * @return
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static Object getBean(Class cls,String sql,Object[] params,String[] beanArray) throws SQLException, InstantiationException, IllegalAccessException {
    	DBService db = new DBService();
        try {
            return db.getBean(cls,sql,params,beanArray);
        } finally {
            db.close();
        }
    }
    
    /**
     * 根据sql语句查询返回一个JavaBean集合
     * @param sql
     * @return
     */
	public static List getListBean(Class cls,String sql,String[] beanArray) throws SQLException, InstantiationException, IllegalAccessException {
    	DBService db = new DBService();
        try {
            return db.getListBean(cls,sql,beanArray);
        } finally {
            db.close();
        }
    }
    
    /**
     * 采用预编译的方式根据sql语句查询返回一个JavaBean集合
     * @param cls
     * @param sql
     * @param params
     * @param beanArray
     * @return
     * @throws SQLException
     */
    public static List getListBean(Class cls,String sql,Object[] params,String[] beanArray) throws SQLException, InstantiationException, IllegalAccessException {
    	DBService db = new DBService();
        try {
            return db.getListBean(cls,sql,params,beanArray);
        } finally {
            db.close();
        }
    }
    
    /**
     * 执行一次给定的 INSERT、UPDATE or DELETE SQL 语句
     * @param sql SQL语句
     * @return 插入、更新或删除的行数
     * @throws SQLException
     */
    public static int update(String sql) throws SQLException
    {
    	DBService db = new DBService();
        try {
            return db.update(sql);
        } finally {
            db.close();
        }
    }
    
    /**
     * 采用预编译形式执行一次给定的 INSERT、UPDATE or DELETE SQL 语句
     * @param sql 预编译SQL语句
     * @param params SQL 参数，可为null。
     * @return 插入、更新或删除的行数
     * @throws SQLException
     */
    public static int update(String sql, Object[] params) throws SQLException
    {
    	DBService db = new DBService();
        try {
            return db.update(sql,params);
        } finally {
            db.close();
        }
    }
    
}
