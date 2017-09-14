package com.qheai.util.config;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.util.StringUtil;

/**
 * 配置文件基类解析类
 * @author CM-TOSCI
 *
 */
public class ConfigParser {
	
	private static Logger logger = LoggerFactory.getLogger(ConfigParser.class);
	
	/**
	 * 解析配置文件
	 * @param configFile
	 * @throws Exception
	 */
	public void parse(ResourceBundle bundle) throws Exception {
		try {
			String db_Driver = bundle.getString("db_Driver");
			PropertiesBean.setDb_Driver(StringUtil.formatNull(db_Driver));
		    
		    String db_Connection = bundle.getString("db_Connection");
		    PropertiesBean.setDb_Connection(StringUtil.formatNull(db_Connection)); 
		    
		    String db_UserName = bundle.getString("db_UserName");
		    PropertiesBean.setDb_UserName(StringUtil.formatNull(db_UserName)); 
			
		    String db_Password = bundle.getString("db_Password");
		    PropertiesBean.setDb_Password(StringUtil.formatNull(db_Password)); 
		    
		    String minPoolSize = bundle.getString("MinPoolSize");
		    PropertiesBean.setMinPoolSize(Integer.parseInt(StringUtil.formatNull(minPoolSize))); 
			
		    String maxPoolSize = bundle.getString("MaxPoolSize");
		    PropertiesBean.setMaxPoolSize(Integer.parseInt(StringUtil.formatNull(maxPoolSize))); 
			
		    String initialPoolSize = bundle.getString("InitialPoolSize");
		    PropertiesBean.setInitialPoolSize(Integer.parseInt(StringUtil.formatNull(initialPoolSize))); 
			
		    String maxIdleTime = bundle.getString("MaxIdleTime");
		    PropertiesBean.setMaxIdleTime(Integer.parseInt(StringUtil.formatNull(maxIdleTime))); 
			
		    String acquireIncrement = bundle.getString("AcquireIncrement");
		    PropertiesBean.setAcquireIncrement(Integer.parseInt(StringUtil.formatNull(acquireIncrement))); 
			
		    String acquireRetryAttempts = bundle.getString("AcquireRetryAttempts");
		    PropertiesBean.setAcquireRetryAttempts(Integer.parseInt(StringUtil.formatNull(acquireRetryAttempts))); 
			
		    String acquireRetryDelay = bundle.getString("AcquireRetryDelay");
		    PropertiesBean.setAcquireRetryDelay(Integer.parseInt(StringUtil.formatNull(acquireRetryDelay))); 
			
		    String testConnectionOnCheckin = bundle.getString("TestConnectionOnCheckin");
		    PropertiesBean.setTestConnectionOnCheckin(Boolean.parseBoolean(StringUtil.formatNull(testConnectionOnCheckin))); 
		   
		    String testConnectionOnCheckout = bundle.getString("TestConnectionOnCheckout");
		    PropertiesBean.setTestConnectionOnCheckout(Boolean.parseBoolean(StringUtil.formatNull(testConnectionOnCheckout))); 
			
		    String automaticTestTable = bundle.getString("AutomaticTestTable");
		    PropertiesBean.setAutomaticTestTable(StringUtil.formatNull(automaticTestTable)); 
			
		    String idleConnectionTestPeriod = bundle.getString("IdleConnectionTestPeriod");
		    PropertiesBean.setIdleConnectionTestPeriod(Integer.parseInt(StringUtil.formatNull(idleConnectionTestPeriod))); 
			
		    String checkoutTimeout = bundle.getString("CheckoutTimeout");
		    PropertiesBean.setCheckoutTimeout(Integer.parseInt(StringUtil.formatNull(checkoutTimeout))); 
			
		    String autoCommitOnClose = bundle.getString("AutoCommitOnClose");
		    PropertiesBean.setAutoCommitOnClose(Boolean.parseBoolean(StringUtil.formatNull(autoCommitOnClose))); 
			
		    String breakAfterAcquireFailure = bundle.getString("BreakAfterAcquireFailure");
		    PropertiesBean.setBreakAfterAcquireFailure(Boolean.parseBoolean(StringUtil.formatNull(breakAfterAcquireFailure))); 
		    
		    String corePoolSize = bundle.getString("CorePoolSize");
		    PropertiesBean.setCorePoolSize(Integer.parseInt(StringUtil.formatNull(corePoolSize))); 
		    
		    String maximumPoolSize = bundle.getString("MaximumPoolSize");
		    PropertiesBean.setMaximumPoolSize(Integer.parseInt(StringUtil.formatNull(maximumPoolSize))); 
		    
		    String keepAliveTime = bundle.getString("KeepAliveTime");
		    PropertiesBean.setKeepAliveTime(Integer.parseInt(StringUtil.formatNull(keepAliveTime))); 
		    
		    String queueSize = bundle.getString("QueueSize");
		    PropertiesBean.setQueueSize(Integer.parseInt(StringUtil.formatNull(queueSize))); 
		    
		    String queueType = bundle.getString("QueueType");
		    PropertiesBean.setQueueType(Integer.parseInt(StringUtil.formatNull(queueType))); 
		    
		    String sourceID = bundle.getString("sourceID");
		    PropertiesBean.setSourceID(StringUtil.formatNull(sourceID));
		    
		    String appId = bundle.getString("AppId");
		    PropertiesBean.setAppId(StringUtil.formatNull(appId));
		    
		    String kafkaHost = bundle.getString("KafkaHost");
		    PropertiesBean.setKafkaHost(StringUtil.formatNull(kafkaHost));
		    
		    String readTopic = bundle.getString("ReadTopic");
		    PropertiesBean.setReadTopic(StringUtil.formatNull(readTopic));
		    
		    String writeTopic = bundle.getString("WriteTopic");
		    PropertiesBean.setWriteTopic(StringUtil.formatNull(writeTopic));
		    
		    String procedureType = bundle.getString("ProcedureType");
		    PropertiesBean.setProcedureType(StringUtil.formatNull(procedureType));
		    
		    String sslAddr = bundle.getString("SSLAddr");
		    PropertiesBean.setSslAddr(StringUtil.formatNull(sslAddr));
		    
		}catch (Exception e) {
			ConfigParser.logger.error("读取配置文件错误：",e);
			throw e;
		}
	}
	
	/**
	 * 从配置文件获取信息
	 * @return
	 */
	protected String getProperties(ResourceBundle bundle,String key,String defaultValue) throws NullPointerException {
		String resStr = null;
		if (key != null && key.length() > 0) {
			if (bundle.containsKey(key)) {
				resStr = StringUtil.formatNull(bundle.getString(key));
			}else{
				resStr = defaultValue == null ? "" : defaultValue;
			}
		}else{
			throw new NullPointerException("key is not null");
		}
		return resStr;
	}

}
