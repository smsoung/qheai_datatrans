package com.qheai.util.config;


/**
 * 配置文件实体Bean 用于设置一些应用启动时候的必要配置，例如，数据库信息等
 */
public class PropertiesBean {

	/**
	 * 数据库驱动
	 */
	private static String db_Driver;
	
	/**
	 * 数据库连接字符串
	 */
	private static String db_Connection;
	
	/**
	 * 数据库登录名
	 */
	private static String db_UserName;
	
	/**
	 * 数据库密码
	 */
	private static String db_Password;
	
	/**
	 * 连接池中保留的最小连接数
	 */
	private static int minPoolSize;
	
	/**
	 * 连接池中保留的最小连接数
	 */
	private static int maxPoolSize;
	
	/**
	 * 初始化时获取10个连接，取值应在minPoolSize与maxPoolSize之间
	 */
	private static int initialPoolSize;
	
	/**
	 * 最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃
	 */
	private static int maxIdleTime;
	
	/**
	 * 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
	 */
	private static int acquireIncrement;
	
	/**
	 * 定义在从数据库获取新连接失败后重复尝试的次数
	 */
	private static int acquireRetryAttempts;
	
	/**
	 * 两次连接中间隔时间，单位毫秒
	 */
	private static int acquireRetryDelay;
	
	/**
	 * 获取连接时是否校验连接的有效性
	 */
	private static boolean testConnectionOnCheckin;
	
	/**
	 * 获取连接时是否校验连接的有效性
	 */
	private static boolean testConnectionOnCheckout;
	
	/**
	 * automaticTestTable和idleConnectionTestPeriod为配合校验连接有效性用
	 */
	private static String automaticTestTable;
	
	/**
	 * 每小时检查所有连接池中的空闲连接
	 */
	private static int idleConnectionTestPeriod;
	
	/**
	 * 当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException,如设为0则无限期等待。单位毫秒
	 */
	private static int checkoutTimeout;
	
	/**
	 * 连接关闭时默认将所有未提交的操作回滚
	 */
	private static boolean autoCommitOnClose;
	
	/**
	 * 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效 
	 * 保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试 
	 * 获取连接失败后该数据源将申明已断开并永久关闭
	 */
	private static boolean breakAfterAcquireFailure;
	
	/**
	 * 线程池中核心线程数量
	 */
	private static int corePoolSize;
	
	/**
	 * 线程池中最大线程数量
	 */
	private static int maximumPoolSize;
	
	/**
	 * 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	 */
	private static int keepAliveTime;
	
	/**
	 * 队列大小
	 */
	private static int queueSize;
	
	/**
	 * 线程池中使用的队列类型 1：不使用队列,新线程直接提交到线程池 2：有界队列 3：无界队列
	 */
	private static int queueType;

	/**
	 * sourceID
	 */
	private static String sourceID;
	
	/**
	 * 服务唯一标识
	 */
	private static String appId;
	
	/**
	 * kafka地址
	 */
	private static String kafkaHost;
	
	/**
	 * 读主题的名字
	 */
	private static String readTopic;
	
	/**
	 * 写主题的名字
	 */
	private static String writeTopic;
	
	/**
	 * 程序类型   consume订阅     produce发布  all全部
	 */
	private static String procedureType;
	
	/**
	 * ssl文件地址
	 */
	private static String sslAddr;
	
	
	public static String getDb_Driver() {
		return db_Driver;
	}

	protected static void setDb_Driver(String db_Driver) {
		PropertiesBean.db_Driver = db_Driver;
	}

	public static String getDb_Connection() {
		return db_Connection;
	}

	protected static void setDb_Connection(String db_Connection) {
		PropertiesBean.db_Connection = db_Connection;
	}

	public static String getDb_UserName() {
		return db_UserName;
	}

	protected static void setDb_UserName(String db_UserName) {
		PropertiesBean.db_UserName = db_UserName;
	}

	public static String getDb_Password() {
		return db_Password;
	}

	protected static void setDb_Password(String db_Password) {
		PropertiesBean.db_Password = db_Password;
	}

	public static int getMinPoolSize() {
		return minPoolSize;
	}

	protected static void setMinPoolSize(int minPoolSize) {
		PropertiesBean.minPoolSize = minPoolSize;
	}

	public static int getMaxPoolSize() {
		return maxPoolSize;
	}

	protected static void setMaxPoolSize(int maxPoolSize) {
		PropertiesBean.maxPoolSize = maxPoolSize;
	}

	public static int getInitialPoolSize() {
		return initialPoolSize;
	}

	protected static void setInitialPoolSize(int initialPoolSize) {
		PropertiesBean.initialPoolSize = initialPoolSize;
	}

	public static int getMaxIdleTime() {
		return maxIdleTime;
	}

	protected static void setMaxIdleTime(int maxIdleTime) {
		PropertiesBean.maxIdleTime = maxIdleTime;
	}

	public static int getAcquireIncrement() {
		return acquireIncrement;
	}

	protected static void setAcquireIncrement(int acquireIncrement) {
		PropertiesBean.acquireIncrement = acquireIncrement;
	}

	public static int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	protected static void setAcquireRetryAttempts(int acquireRetryAttempts) {
		PropertiesBean.acquireRetryAttempts = acquireRetryAttempts;
	}

	public static int getAcquireRetryDelay() {
		return acquireRetryDelay;
	}

	protected static void setAcquireRetryDelay(int acquireRetryDelay) {
		PropertiesBean.acquireRetryDelay = acquireRetryDelay;
	}

	public static boolean isTestConnectionOnCheckin() {
		return testConnectionOnCheckin;
	}

	protected static void setTestConnectionOnCheckin(boolean testConnectionOnCheckin) {
		PropertiesBean.testConnectionOnCheckin = testConnectionOnCheckin;
	}

	public static boolean isTestConnectionOnCheckout() {
		return testConnectionOnCheckout;
	}

	protected static void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
		PropertiesBean.testConnectionOnCheckout = testConnectionOnCheckout;
	}

	public static String getAutomaticTestTable() {
		return automaticTestTable;
	}

	protected static void setAutomaticTestTable(String automaticTestTable) {
		PropertiesBean.automaticTestTable = automaticTestTable;
	}

	public static int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	protected static void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		PropertiesBean.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public static int getCheckoutTimeout() {
		return checkoutTimeout;
	}

	protected static void setCheckoutTimeout(int checkoutTimeout) {
		PropertiesBean.checkoutTimeout = checkoutTimeout;
	}

	public static boolean isAutoCommitOnClose() {
		return autoCommitOnClose;
	}

	protected static void setAutoCommitOnClose(boolean autoCommitOnClose) {
		PropertiesBean.autoCommitOnClose = autoCommitOnClose;
	}

	public static boolean isBreakAfterAcquireFailure() {
		return breakAfterAcquireFailure;
	}

	protected static void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
		PropertiesBean.breakAfterAcquireFailure = breakAfterAcquireFailure;
	}

	public static int getCorePoolSize() {
		return corePoolSize;
	}

	protected static void setCorePoolSize(int corePoolSize) {
		PropertiesBean.corePoolSize = corePoolSize;
	}

	public static int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	protected static void setMaximumPoolSize(int maximumPoolSize) {
		PropertiesBean.maximumPoolSize = maximumPoolSize;
	}

	public static int getKeepAliveTime() {
		return keepAliveTime;
	}

	protected static void setKeepAliveTime(int keepAliveTime) {
		PropertiesBean.keepAliveTime = keepAliveTime;
	}

	public static int getQueueSize() {
		return queueSize;
	}

	protected static void setQueueSize(int queueSize) {
		PropertiesBean.queueSize = queueSize;
	}

	public static int getQueueType() {
		return queueType;
	}

	protected static void setQueueType(int queueType) {
		PropertiesBean.queueType = queueType;
	}

	public static String getSourceID() {
		return sourceID;
	}

	public static void setSourceID(String sourceID) {
		PropertiesBean.sourceID = sourceID;
	}

	public static String getAppId() {
		return appId ;
	}

	public static void setAppId(String appId) {
		PropertiesBean.appId = appId;
	}

	public static String getKafkaHost() {
		return kafkaHost;
	}

	public static void setKafkaHost(String kafkaHost) {
		PropertiesBean.kafkaHost = kafkaHost;
	}

	public static String getReadTopic() {
		return readTopic;
	}

	public static void setReadTopic(String readTopic) {
		PropertiesBean.readTopic = readTopic;
	}

	public static String getWriteTopic() {
		return writeTopic;
	}

	public static void setWriteTopic(String writeTopic) {
		PropertiesBean.writeTopic = writeTopic;
	}

	public static String getProcedureType() {
		return procedureType;
	}

	public static void setProcedureType(String procedureType) {
		PropertiesBean.procedureType = procedureType;
	}

	public static String getSslAddr() {
		return sslAddr;
	}

	public static void setSslAddr(String sslAddr) {
		PropertiesBean.sslAddr = sslAddr;
	}
	
}
