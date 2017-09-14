package com.qheai.handler;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.config.MsgConfig;
import com.qheai.service.CommonService;
import com.topsci.tools.kafka.consume.ConsumeThread;

/**
 * 监听端口（通过MsgCode值确定所接收到的数据是什么作用） 0001 下发数据 0002 主动发起的订阅数据(服务端索要数据) 100* 错误原因
 */
public class MessageHandler extends ConsumeThread
{

	private static Logger logger = LoggerFactory
			.getLogger(MessageHandler.class);
	private static Logger elog = LoggerFactory.getLogger("errordata");

	private CommonService service = new CommonService();
	private String msg;

	public void run()
	{
		try
		{
			logger.info("接收到的消息：{}", super.getMessage());
			msg = super.getMessage();

			if (msg != null && !"".equals(msg))
			{
				Document document = DocumentHelper.parseText(msg);
				Element envelope = document.getRootElement(); // 得到 Envelope
				Element header = envelope.element(MsgConfig.MSGHEAD);
				Element body = envelope.element(MsgConfig.MSGBODY);

				String fromId = header.elementText(MsgConfig.MSG_FORMSYSID); // 消息类型码
				String msgCode = header.elementText(MsgConfig.MSG_MSGCODE); // 消息类型码
				String msgType = header.elementText(MsgConfig.MSG_MSGTYPE); // 数据类型

				switch (msgCode)
				{
				case "0002": //
					if ("AKDZ_CZJL".equals(msgType))
					{
						logger.info("接收到{}订阅出诊记录的请求", fromId);
						service.getCZJL(body, msgType, fromId);
					} else if ("AKDZ_JJJYJL".equals(msgType))
					{
						logger.info("接收到{}订阅紧急救援记录的请求", fromId);
						service.getJJJYJL(body, msgType, fromId);
					}
					break;
				default: // 错误返回结果
					// 记录错误数据
					elog.error("数据请求异常：" + msg);
					break;
				}
			} else
			{
				logger.info("接收到的消息为空");
			}
		} catch (Exception e)
		{
			logger.error("消息处理错误{}", e.getMessage());
			e.printStackTrace();
		}
	}
}
