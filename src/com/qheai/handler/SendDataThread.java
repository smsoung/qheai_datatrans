package com.qheai.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.service.SendDataService;

/**
 * 发布数据的handler(由于改用timer定时器,弃之不用)
 */
public class SendDataThread implements Runnable
{

	private static Logger logger = LoggerFactory
			.getLogger(SendDataThread.class);
	private SendDataService service = new SendDataService();

	public void run()
	{
		try
		{
			logger.info("开始执行查询任务");
			service.sendData();
		} catch (Exception e)
		{
			logger.error("消息处理错误{}", e.getMessage());
			e.printStackTrace();
		}
	}
}
