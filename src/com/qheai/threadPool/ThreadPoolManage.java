package com.qheai.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.util.config.PropertiesBean;


/**
 * 线程池管理类
 * @Package com.topsci.adaptor.qhserver.threadPool 
 * @ClassName: ThreadPoolManage 
 * @Description:
 * @author kjma
 * @date Oct 28, 2015 2:57:57 PM
 * @version
 */
public class ThreadPoolManage extends ThreadPoolExecutor {
	
	private static Logger logger = LoggerFactory.getLogger(ThreadPoolManage.class);

	@SuppressWarnings("unused")
	private boolean isPaused;
	private ReentrantLock pauseLock = new ReentrantLock();
	private Condition unpaused = pauseLock.newCondition();
	private static ThreadPoolManage threadPoolExecutor = null;
    
	public static ThreadPoolManage getThreadPoolManage(){
		return threadPoolExecutor;
	}
	
	/**
	 * 线程池核心线程
	 */
	private static int corePoolSize = 150;
	
	/**
	 * 最大线程
	 */
	private static int maximumPoolSize = 1000;
	
	/**
	 * 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	 */
	private static long keepAliveTime = 1;
	
	/**
	 * 线程等待时间单位
	 */
	private static TimeUnit unit = TimeUnit.SECONDS;
	
	/**
	 * 队列大小
	 */
	private static int queueSize = 10000;
	/**
	 * 线程池中使用的队列类型 1：不使用队列,新线程直接提交到线程池 2：有界队列 3：无界队列
	 */
	private static int queueType = 2;
	
	/**
	 * 等待队列
	 */
	private static BlockingQueue<Runnable> queue = null;

	
	static {
		try{
			corePoolSize = PropertiesBean.getCorePoolSize();
			maximumPoolSize = PropertiesBean.getMaximumPoolSize();
			keepAliveTime = PropertiesBean.getKeepAliveTime();
			queueSize = PropertiesBean.getQueueSize();
			queueType = PropertiesBean.getQueueType();
			
			if(queueType == 2){
				queue = new ArrayBlockingQueue<Runnable>(queueSize);
			}else if(queueType == 3){
				queue = new LinkedBlockingQueue<Runnable>(queueSize);
			}else{
				queue = new SynchronousQueue<Runnable>();
			}
		}catch(Exception e){
			logger.error("SDAThreadPoolExecutor.java锟侥硷拷锟斤拷取cstconfig.xml锟侥硷拷锟斤拷荼锟斤拷?锟斤拷锟斤拷!");
		}
	}

	public ThreadPoolManage() {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, queue);
	}
	
	public static ThreadPoolManage getInstance() {
		if(threadPoolExecutor == null) {
			threadPoolExecutor = new ThreadPoolManage();
		}
		
		return threadPoolExecutor;
	}
	
	public ThreadPoolManage getThreadPool() {
		return threadPoolExecutor;
	}
	
	 public void pause() {
	     pauseLock.lock();
	     try {
	       isPaused = true;
	     } finally {
	       pauseLock.unlock();
	     }
	 }
	 
	 public void resume() {
	     pauseLock.lock();
	     try {
	       isPaused = false;
	       unpaused.signalAll();
	     } finally {
	       pauseLock.unlock();
	     }
	 }
}
