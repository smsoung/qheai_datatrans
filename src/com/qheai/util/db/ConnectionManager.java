package com.qheai.util.db;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.qheai.util.config.PropertiesBean;


/**
 * 
 * 连接池管理类
 * @author CM-TOSCI
 *
 */
public class ConnectionManager {
	
	private static Logger log = LoggerFactory.getLogger(ConnectionManager.class);
	
	private static ConnectionManager instance;
	
	/**
	 * 连接池管理对象
	 */
    private ComboPooledDataSource dataSource;
    
    /**
     * 初始化连接池
     * @throws Exception
     */
    private ConnectionManager() throws Exception{
    	this.dataSource = new ComboPooledDataSource();
    	this.dataSource.setDriverClass(PropertiesBean.getDb_Driver());
    	this.dataSource.setJdbcUrl(PropertiesBean.getDb_Connection());
    	this.dataSource.setUser(PropertiesBean.getDb_UserName());
    	this.dataSource.setPassword(PropertiesBean.getDb_Password());
    	this.dataSource.setMinPoolSize(PropertiesBean.getMinPoolSize());
    	this.dataSource.setMaxPoolSize(PropertiesBean.getMaxPoolSize());
    	this.dataSource.setInitialPoolSize(PropertiesBean.getInitialPoolSize());
    	this.dataSource.setMaxIdleTime(PropertiesBean.getMaxIdleTime());
    	this.dataSource.setAcquireIncrement(PropertiesBean.getAcquireIncrement());
    	this.dataSource.setAcquireRetryAttempts(PropertiesBean.getAcquireRetryAttempts());
    	this.dataSource.setAcquireRetryDelay(PropertiesBean.getAcquireRetryDelay());
    	this.dataSource.setTestConnectionOnCheckin(PropertiesBean.isTestConnectionOnCheckin());
    	this.dataSource.setTestConnectionOnCheckout(PropertiesBean.isTestConnectionOnCheckin());
    	this.dataSource.setAutomaticTestTable(PropertiesBean.getAutomaticTestTable());
    	this.dataSource.setIdleConnectionTestPeriod(PropertiesBean.getIdleConnectionTestPeriod());
    	this.dataSource.setCheckoutTimeout(PropertiesBean.getCheckoutTimeout());
    	this.dataSource.setAutoCommitOnClose(PropertiesBean.isAutoCommitOnClose());
    	this.dataSource.setBreakAfterAcquireFailure(PropertiesBean.isBreakAfterAcquireFailure());
    	this.dataSource.setNumHelperThreads(10);
    	this.dataSource.setMaxConnectionAge(25);   //空闲超过25秒 则自动断开连接
    }

    /**
     * 获取C3P0数据库连接池管理对象DataSoure
     * @return
     */
    public ComboPooledDataSource getPoolDataSource(){
    	return this.dataSource;
    }
    
    /**
     * 获取连接池唯一实例
     * @return
     */
    public static synchronized ConnectionManager getInstance(){
    	if(null == ConnectionManager.instance){
    		try{
    			ConnectionManager.instance = new ConnectionManager();
    		}catch(Exception e){
    			ConnectionManager.log.error("获取连接池对象出错：",e);
    			e.printStackTrace();
    		}
    	}
    	return instance;
    }
    
    /**
     * 获取连接对象
     * @return Connection实例
     */
    public final Connection getConnection(){
    	try{
//    		ConnectionManager.log.info("核心线程："+ThreadPoolManage.getThreadPoolManage().getCorePoolSize()+",最大线程："+ThreadPoolManage.getThreadPoolManage().getMaximumPoolSize()+",当前线程1ActiveCount："+ThreadPoolManage.getThreadPoolManage().getActiveCount()+",当前线程2CompletedTaskCount："+ThreadPoolManage.getThreadPoolManage().getCompletedTaskCount()+",当前线程3："+ThreadPoolManage.getThreadPoolManage().getTaskCount()+",当前队列:"+ThreadPoolManage.getThreadPoolManage().getQueue().size());
    		ConnectionManager.log.info("=======最大连接数："+this.dataSource.getMaxPoolSize()+",最小连接数："+this.dataSource.getMinPoolSize()+",当前连接数："+this.dataSource.getNumBusyConnections()+",空闲连接数："+this.dataSource.getNumIdleConnections()+",总连接数："+this.dataSource.getNumConnections());
    		return this.dataSource.getConnection();
    	}catch(Exception e){
    		ConnectionManager.log.error("获取连接对象：",e);
    		return null;
    	}
    }
    
    /**
     * 销毁连接池实例
     */
    @Override
	protected void finalize() throws Throwable{
    	DataSources.destroy(this.dataSource);
    	super.finalize();
    }
}
