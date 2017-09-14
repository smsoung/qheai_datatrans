package com.qheai.bootstart;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.constant.Constants;
import com.qheai.handler.MessageHandler;
import com.qheai.handler.SendDataThread;
import com.qheai.service.SendDataService;
import com.qheai.util.CalendarUtil;
import com.qheai.util.config.ConfigParser;
import com.qheai.util.config.PropertiesBean;
import com.topsci.tools.clientcheck.ClientAliveMonitor;
import com.topsci.tools.kafka.consume.KafkaConsumer;

/**
 * 启动程序
 * 
 * @author ssm
 *
 */
public class BootStart
{

	private static Logger logger = LoggerFactory.getLogger(BootStart.class);
	private static SendDataService service = new SendDataService();

	public static void main(String[] args)
	{
		try
		{
			/*********************** 华丽的分割线，以下是业务模块 *************************/
			logger.info("初始化配置文件。。。。。");
			ResourceBundle cfgrb = ResourceBundle.getBundle("cfg");
			ConfigParser configParser = new ConfigParser();
			// 初始化配置文件
			configParser.parse(cfgrb);
			/*********************** 华丽的分割线，以下是业务模块 *************************/
			boolean alreadyRunning;
			try
			{
				JUnique.acquireLock(PropertiesBean.getAppId());
				alreadyRunning = false;
			} catch (AlreadyLockedException e)
			{
				alreadyRunning = true;
			}
			if (!alreadyRunning)
			{
				logger.info("系统启动中。。。。。");

				ClientAliveMonitor.setSystemId(
						PropertiesBean.getSourceID(),
						PropertiesBean.getKafkaHost(),
						System.getProperty("user.dir")
								+ PropertiesBean.getSslAddr());

				logger.info("系统启动成功。。。。。");
				String producerType = PropertiesBean.getProcedureType();
				switch (producerType)
				{
				case Constants.PRODUCER_CONSUME:
					// 订阅
					KafkaConsumer consumer = new KafkaConsumer(
							PropertiesBean.getKafkaHost(),
							PropertiesBean.getReadTopic(),
							System.getProperty("user.dir")
									+ PropertiesBean.getSslAddr());
					consumer.consume(MessageHandler.class); // 监听端口 接受集成平台请求的数据
					break;
				case Constants.PRODUCER_PRODUCE:
					// 发布
					Thread sendDataThread = new Thread(new SendDataThread());
					sendDataThread.setName("sendDataThread");
					sendDataThread.start();
					break;
				case Constants.PRODUCER_ALL:
					// 订阅+发布
					Date firstTime = CalendarUtil.getFirstTime();
					Timer timer = new Timer();
					timer.schedule(new TimerTask()
					{
						@Override
						public void run()
						{
							logger.info("任务开始执行");
							service.sendData();
						}
					}, firstTime, 1000 * 60 * 60 * 24);

					// ScheduledExecutorService executorService =
					// Executors.newScheduledThreadPool(5);
					// executorService.scheduleAtFixedRate(new SendDataThread(),
					// 0, 5, TimeUnit.MINUTES); //1天扫一次 有更新主动给平台发布变动数据

					KafkaConsumer consumerr = new KafkaConsumer(
							PropertiesBean.getKafkaHost(),
							PropertiesBean.getReadTopic(),
							System.getProperty("user.dir")
									+ PropertiesBean.getSslAddr());
					consumerr.consume(MessageHandler.class); // 监听端口 接受集成平台请求的数据

					break;
				default:
					logger.error("程序类型配置错误");
					break;
				}
			} else
			{
				logger.warn("服务已启动，请勿重复启动");
			}
		} catch (Exception e)
		{
			logger.error("服务启动错误。。。{}", e);
			e.printStackTrace();
		}
	}
}
