package com.qheai.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.util.StringUtil;

/**
 * 数据库连接封装类
 * @author CM-TOSCI
 *
 */
@SuppressWarnings("rawtypes")
public class DBService {
	
	private static Logger log = LoggerFactory.getLogger(DBService.class);
	
	/**
     * 查询结果集中最后一列的标志
     */
    private static final int END_COL = -1;

    /**
     * 数据库连接
     */
    private Connection connection;

    
    /**
     * 根据数据库名构造一个自动提交事务的数据库操作封装类。
     * @param dbName 数据库名称（Constants.DBNAME_SMP or Constants.DBNAME_SCP）
     * @throws Exception 
     */
    public DBService() throws SQLException {
    	try {
			this.connection = DBConnection.getConnection();
		} catch (Exception e) {
			DBService.log.error("初始化DBService错误",e);
		}
    }
    
    /**
     * 设置数据库是否自动提交
     * @param autoCommit true，自动提交。
     * @throws SQLException
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.connection.setAutoCommit(autoCommit);
    }
    
    /**
     * 提交数据库更新
     * @throws SQLException
     */
    public void commit() throws SQLException {
    	this.connection.commit();
    }

    /**
     * 回滚数据库操作（忽略异常）
     * @throws SQLException 
     */
    public void rollback() throws SQLException {
        if (this.connection != null)
        {
        	this.connection.rollback();
        }
    }
    
    /**
     * 关闭数据库连接（忽略异常）
     */
    public void close() throws SQLException {
        if (this.connection != null)
        {
        	DBConnection.close(this.connection);
        }
    }
    
    /**
     * 获得一个首行首列的字符串
     * @param sql SQL语句
     * @return
     */
    public String getString(String sql) throws SQLException{
    	DBService.log.info(sql);
    	Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            String str = null;
            if (rs.next())
            {
                str = rs.getString(1);
            }
            return str;
        }
        finally
        {
            this.closeResultSet(rs);
            this.closeStatement(stmt);
        }
    }
    
    /**
     * 采用预编译SQL语句形式首行首列的字符串
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 返回字符串，若没有查询到数据则返回null。
     * @throws SQLException
     */
    public String getString(String sql, Object[] params) throws SQLException {
    	DBService.log.info(sql+"|"+StringUtil.connect2(params));
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            String str = null;
            if (rs.next())
            {
                str = rs.getString(1);
            }
            return str;
        }
        finally
        {
        	this.closeResultSet(rs);
        	this.closeStatement(stmt);
        }
    }
    
    /**
     * 获取首列的list集合
     * @param sql SQL语句
     * @return
     * @throws SQLException 
     */
    public List<String> getStringList(String sql) throws SQLException {
    	DBService.log.info(sql);
    	Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            int columnCount = rs.getMetaData().getColumnCount();
            List<String> list = new ArrayList<String>();

            while (rs.next())
            {
                for (int i = 1; i <= columnCount; i++)
                {
                    list.add(StringUtil.formatNull(rs.getString(i)));
                }
            }
            return list;
        }
        finally
        {
        	this.closeResultSet(rs);
        	this.closeStatement(stmt);
        }
    }
    
    /**
     * 利用预编译SQL语句查询数据库，获得元素为String的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return List(String)元素为String的列表
     * @throws SQLException
     */
	public List<String> getStringList(String sql, Object[] params) throws SQLException {
		DBService.log.info(sql+"|"+StringUtil.connect2(params));
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            rs = stmt.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            List<String> list = new ArrayList<String>();

            while (rs.next())
            {
                for (int i = 1; i <= columnCount; i++)
                {
                    list.add(StringUtil.formatNull(rs.getString(i)));
                }
            }
            return list;
        }
        finally
        {
            this.closeResultSet(rs);
            this.closeStatement(stmt);
        }
    }
	
	/**
	 * 根据SQL语句查询数据库并将取得的第一行数据存放到String数组中并返回。
	 * @param sql SQL语句
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
	 */
	public String[] getStringArray(String sql) throws SQLException{
		DBService.log.info(sql);
		Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            String[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new String[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = StringUtil.formatNull(rs.getString(i + 1));
                }
            }
            return array;
        }
        finally
        {
        	this.closeResultSet(rs);
        	this.closeStatement(stmt);
        }
	}
	
	/**
     * 根据预编译SQL语句查询数据库并将取得的第一行数据存放到String数组中并返回。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public String[] getStringArray(String sql, Object[] params) throws SQLException {
    	DBService.log.info(sql+"|"+StringUtil.connect2(params));
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            String[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new String[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = StringUtil.formatNull(rs.getString(i + 1));
                }
            }
            return array;
        }
        finally
        {
        	this.closeResultSet(rs);
        	this.closeStatement(stmt);
        }
    }
    
    /**
     * 是否查询到数据
     * @param sql 查询SQL语句
     * @return boolean 查询的结果集中有数据则返回true，否则返回false。
     * @throws SQLException
     */
    public boolean hasData(String sql) throws SQLException
    {
    	DBService.log.info(sql);
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            return rs.next();
        }
        finally
        {
        	this.closeResultSet(rs);
        	this.closeStatement(stmt);
        }
    }
    
    /**
     * 采用预编译SQL语句形式获得是否查询到数据
     * @param sql 预编译SQL语句，该SQL中只允许一个参数
     * @param params SQL参数，可为null。
     * @return 查询到数据则返回true
     * @throws SQLException
     */
    public boolean hasData(String sql, Object[] params) throws SQLException {
    	DBService.log.info(sql+"|"+StringUtil.connect2(params));
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.prepareStatement(sql);
            if (params != null){
                for (int i = 0; i < params.length; i++){
                    stmt.setObject(i + 1, params[i]);
                }
                rs = stmt.executeQuery();
                if (rs.next())
                {
                    return true;
                }
            }
            return false;
        }
        finally
        {
        	this.closeResultSet(rs);
        	this.closeStatement(stmt);
        }
    }
    
    /**
     * 根据SQL语句查询数据库，并从结果集的指定起始列至指定终止列获得元素为String[]的列表。
     * @param sql SQL查询语句
     * @param startCol 获取结果集的起始列，从0开始（包含起始列）。
     * @param endCol 获取结果集的终止列（不包含终止列），如果为<code>END_COL</code>，则获得从起始列开始往后所有的列。
     * @return List(String[])元素为String[]的列表
     * @throws SQLException
     */
	public List<String[]> getListArray(String sql, int startCol, int endCol) throws SQLException {
		DBService.log.info(sql);
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);

            if (endCol == DBService.END_COL)
            {
                endCol = rs.getMetaData().getColumnCount();
            }

            int len = endCol - startCol;

            List<String[]> list = new ArrayList<String[]>();
            while (rs.next())
            {
                String[] info = new String[len];
                for (int i = 0, j = startCol; j < endCol; i++, j++)
                {
                    info[i] = StringUtil.formatNull(rs.getString(j + 1));
                    
                }
                list.add(info);
            }
            return list;
        }
        finally
        {
            this.closeResultSet(rs);
            this.closeStatement(stmt);
        }
    }
	
	/**
     * 利用预编译SQL语句查询数据库，并从结果集的起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @param startCol 获取结果集的起始列，从0开始（包含起始列）。
     * @param endCol 获取结果集的终止列（不包含终止列），如果为<code>END_COL</code>，则获得从起始列开始往后所有的列。
     * @return List(String[])元素为String数组的列表
     * @throws SQLException
     */
	public List<String[]> getListArray(String sql, Object[] params, int startCol, int endCol) throws SQLException {
		DBService.log.info(sql+"|"+StringUtil.connect2(params));
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = this.connection.prepareStatement(sql);

            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            rs = stmt.executeQuery();

            if (endCol == DBService.END_COL)
            {
                endCol = rs.getMetaData().getColumnCount();
            }

            int len = endCol - startCol;

            List<String[]> list = new ArrayList<String[]>();
            while (rs.next())
            {
                String[] info = new String[len];
                for (int i = 0, j = startCol; j < endCol; i++, j++)
                {
                    info[i] = StringUtil.formatNull(rs.getString(j + 1));
                }
                list.add(info);
            }
            return list;
        }
        finally
        {
            this.closeResultSet(rs);
            this.closeStatement(stmt);
        }
    }
	
	/**
     * 利用预编译SQL语句查询数据库，并从结果集的指定起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @return List(String[])元素为String数组的列表
     * @throws SQLException
     */
    public List<String[]> getListArray(String sql) throws SQLException
    {
        return this.getListArray(sql, 0, DBService.END_COL);
    }
    
    /**
     * 利用预编译SQL语句查询数据库，并从结果集的指定起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @throws SQLException
     */
    public List<String[]> getListArray(String sql, Object[] params) throws SQLException
    {
        return this.getListArray(sql, params, 0, DBService.END_COL);
    }
	
    /**
     * 利用预编译SQL语句查询数据库，并从结果集的指定起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @param startCol 获取结果集的起始列，从0开始（包含起始列）。
     * @return List(String[])元素为String数组的列表
     * @throws SQLException
     */
    public List<String[]> getListArray(String sql, int startCol) throws SQLException
    {
        return this.getListArray(sql, startCol, DBService.END_COL);
    }
	
	/**
     * 利用预编译SQL语句查询数据库，并从结果集的指定起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @param startCol 获取结果集的起始列，从0开始（包含起始列）。
     * @return List(String[])元素为String数组的列表
     * @throws SQLException
     */
    public List<String[]> getListArray(String sql, Object[] params, int startCol) throws SQLException
    {
        return this.getListArray(sql, params, startCol, DBService.END_COL);
    }
    
    /**
     * 返回一行数组组装成JavaBean
     * @param <T>
     * @param sql
     * @return 
     * @return
     */
    public Object getBean(Class cls,String sql,String[] beanArray) throws Exception{
    	String[] strs = this.getStringArray(sql);
    	if (strs != null) {
    		return this.populate(cls.newInstance(), strs, beanArray);
		}else{
			return null;
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
    public Object getBean(Class cls,String sql,Object[] params,String[] beanArray) throws SQLException, InstantiationException, IllegalAccessException {
    	String strs[] = this.getStringArray(sql, params);
    	return this.populate(cls.newInstance(), strs, beanArray);
    }
    
    /**
     * 根据sql语句查询返回一个JavaBean集合
     * @param sql
     * @return
     */
    public List getListBean(Class cls,String sql,String[] beanArray) throws SQLException, InstantiationException, IllegalAccessException {
    	List<Object> objList = new ArrayList<Object>();
    	List<String[]> strsList = this.getListArray(sql);
    	int size = strsList.size();
    	String[] strs = null;
    	Object obj = null;
    	for (int i = 0; i < size; i++) {
			strs = strsList.get(i);
			obj = cls.newInstance();
			this.populate(obj, strs, beanArray);
			objList.add(obj);
		}
    	return objList;
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
	public List getListBean(Class cls,String sql,Object[] params,String[] beanArray) throws SQLException, InstantiationException, IllegalAccessException {
		DBService.log.info(sql+"|"+StringUtil.connect2(params)+"|"+cls.getName());
    	List<Object> objList = new ArrayList<Object>();
    	List<String[]> strsList = this.getListArray(sql,params);
    	int size = strsList.size();
    	String[] strs = null;
    	Object obj = null;
    	for (int i = 0; i < size; i++) {
			strs = strsList.get(i);
			obj = cls.newInstance();
			this.populate(obj, strs, beanArray);
			objList.add(obj);
		}
    	return objList;
    }
    
    
    private Object populate(Object obj,String resStrs[],String[] beanArray) throws SQLException, InstantiationException, IllegalAccessException {
    	Map<String, String> map = new HashMap<String, String>();
    	if (resStrs != null && resStrs.length == beanArray.length) {
			int length = resStrs.length;
			for (int i = 0; i < length; i++) {
				map.put(beanArray[i], StringUtil.formatNull(resStrs[i]));
			}
			try{
            	BeanUtils.populate(obj, map);
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
		}
    	return obj;
    }
    
    /**
     * 执行一次给定的 INSERT、UPDATE or DELETE SQL 语句
     * @param sql SQL语句
     * @return 插入、更新或删除的行数
     * @throws SQLException
     */
    public int update(String sql) throws SQLException
    {
    	DBService.log.info(sql);
        Statement stmt = null;
        try{
            stmt = this.connection.createStatement();
            return stmt.executeUpdate(sql);
        }finally{
            this.closeStatement(stmt);
        }
    }
    
    /**
     * 采用预编译的形式批处理形式一次执行给定的 INSERT、UPDATE or DELETE SQL 语句，
     * @param sql 预编译SQL语句
     * @param paramList List(Object[]) SQL 参数，可为null。
     * @return 返回更新的总行数
     * @throws SQLException
     */
    public int[] updates(List<String> sqlList) throws SQLException
    {
    	DBService.log.info("执行批量处理");
    	int[] rows = new int[0];
    	Statement stmt = null;
        try
        {
        	if (sqlList != null) {
				int size = sqlList.size();
				stmt = this.connection.createStatement();
				for (int i = 0; i < size; i++) {
					stmt.addBatch(sqlList.get(i));
				}
				rows = stmt.executeBatch();
			}
            return rows;
        }
        finally
        {
            closeStatement(stmt);
        }
    }
    
    /**
     * 采用预编译的形式批处理形式一次执行给定的 INSERT、UPDATE or DELETE SQL 语句，
     * @param sql 预编译SQL语句
     * @param paramList List(Object[]) SQL 参数，可为null。
     * @return 返回更新的总行数
     * @throws SQLException
     */
    public int[] updates(String sql, List<Object[]> paramList) throws SQLException
    {
    	DBService.log.info("执行批量处理:"+sql);
    	int[] rows = new int[0];
        PreparedStatement stmt = null;
        try
        {
            stmt = this.connection.prepareStatement(sql);
            if (paramList != null)
            {
                for (int i = 0; i < paramList.size(); i++)
                {
                    Object[] objs = (Object[]) paramList.get(i);
                    for (int j = 0; j < objs.length; j++)
                    {
                        stmt.setObject(j + 1, objs[j]);
                    }
                    stmt.addBatch();
                }
                rows = stmt.executeBatch();
            }
            return rows;
        }
        finally
        {
            closeStatement(stmt);
        }
    }
    
    /**
     * 采用预编译形式执行一次给定的 INSERT、UPDATE or DELETE SQL 语句
     * @param sql 预编译SQL语句
     * @param params SQL 参数，可为null。
     * @return 插入、更新或删除的行数
     * @throws SQLException
     */
    public int update(String sql, Object[] params) throws SQLException
    {
    	DBService.log.info(sql+"|"+StringUtil.connect2(params));
        PreparedStatement stmt = null;
        try
        {
            stmt = this.connection.prepareStatement(sql);
            if (params != null){
                for (int i = 0; i < params.length; i++){
                	if (params[i] instanceof Timestamp) {
                		 stmt.setTimestamp(i + 1, (Timestamp)params[i]);
					}else{
						stmt.setObject(i + 1, params[i]);
					}
                }
            }
            return stmt.executeUpdate();
        }finally{
            this.closeStatement(stmt);
        }
    }
    
    /**
     * 根据sql返回一个ResultSet
     * @return
     * @throws SQLException 
     */
    public ResultSet query(String sql) throws SQLException{
    	return this.connection.createStatement().executeQuery(sql);
    }
    
    /**
     * 关闭结果集（忽略异常）
     * @param rs 结果集
     */
    private void closeResultSet(ResultSet rs) {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 关闭SQL语句对象（忽略异常）
     * @param stmt SQL语句对象
     */
    private void closeStatement(Statement stmt) {
        if (stmt != null)
        {
            try
            {
                stmt.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
