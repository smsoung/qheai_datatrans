package com.qheai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.dao.SendDataDao;
import com.qheai.util.CalendarUtil;
import com.qheai.util.PropertiesUtil;
import com.qheai.util.DateUtil;

/**
 * 发送数据业务类
 * 
 * @author ssm
 *
 */
public class SendDataService
{

	private static Logger logger = LoggerFactory
			.getLogger(SendDataService.class);

	private SendDataDao dao = new SendDataDao();

	/**
	 * 主动发布更新数据
	 */
	public void sendData()
	{
		try
		{
			// 从配置文件中获取前n的时间(如果第一次启动,查历史的数据的期限可以自己在Date.properties配置文件中设置)
			String oldTime = PropertiesUtil.GetValueByKey("props_date");
			logger.info("从配置文件中获取时间: " + oldTime);
			// 获取每次任务的执行时间
			String newTime = DateUtil.DateToString(CalendarUtil.getNewTime());
			logger.info("获取每一次任务的执行时间: " + newTime);
			// 发送出诊记录
			logger.info("开始发送出诊记录");
			dao.sendNewCZJL(oldTime, newTime);
			logger.info("出诊记录发送完成");
			// 发送紧急救援记录
			logger.info("开始发送紧急救援记录");
			dao.sendNewJJJYJL(oldTime, newTime);
			logger.info("紧急救援记录发送完成");
			// 把当天执行任务时间存到配置文件中
			PropertiesUtil.SetProperties("props_date", newTime);
			logger.info("存到配置文件中的时间: " + newTime);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
