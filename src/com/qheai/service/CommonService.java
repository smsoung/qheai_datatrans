package com.qheai.service;

import java.util.Iterator;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.config.MsgConfig;
import com.qheai.dao.CommonDao;

/**
 * 按请求发送数据业务类
 * 
 * @author ssm
 *
 */
public class CommonService
{

	private static Logger logger = LoggerFactory.getLogger(CommonService.class);

	CommonDao dao = new CommonDao();

	/**
	 * 按请求发送出诊记录
	 * 
	 * @param parameter
	 * @param msgType
	 * @param fromId
	 */
	public void getCZJL(Element body, String msgType, String fromId)
	{
		try
		{
			logger.info("订阅来自" + fromId + "的" + msgType + "消息");
			String condition = getCondition(body);
			dao.getCZJL(condition, msgType, fromId);
		} catch (Exception e)
		{
			logger.error("订阅消息解析格式异常：", e);
		}
	}

	/**
	 * 按请求发送紧急救援记录
	 * 
	 * @param parameter
	 * @param pSYY
	 * @param msgType
	 * @param fromId
	 */
	public void getJJJYJL(Element body, String msgType, String fromId)
	{
		try
		{
			logger.info("订阅来自" + fromId + "的" + msgType + "消息");
			String condition = getCondition(body);
			dao.getJJJYJL(condition, msgType, fromId);
		} catch (Exception e)
		{
			logger.error("订阅消息解析格式异常：", e);
		}
	}

	// 解析body节点 ,并拼接字符串作为conditon条件
	@SuppressWarnings("unchecked")
	public String getCondition(Element body)
	{
		String condition = "";
		// 迭代body节点下面的所有子节点
		Iterator<Element> iterator = body.elementIterator();
		while (iterator.hasNext())
		{
			Element e = iterator.next();
			if (e.elementTextTrim(MsgConfig.QueryType).equals("1"))
			{
				String xb = e.elementTextTrim(MsgConfig.Value);
				if (xb != null && xb.length() > 0)
				{
					condition += " and tac.性别=" + "'" + xb + "'";
				}
			} else if (e.elementTextTrim(MsgConfig.QueryType).equals("2"))
			{
				String from = e.elementTextTrim(MsgConfig.From);
				String to = e.elementTextTrim(MsgConfig.To);
				if (from != null && from.length() <= 0)
				{
					condition += " and tac.开始受理时刻<=" + "'" + to + "'";
				} else if (to != null && to.length() <= 0)
				{
					condition += " and tac.开始受理时刻>=" + "'" + from + "'";
				} else
				{
					condition += " and tac.开始受理时刻>=" + "'" + from + "'"
							+ " and tac.开始受理时刻<=" + "'" + to + "'";
				}
			} else if (e.elementTextTrim(MsgConfig.QueryType).equals("3"))
			{
				Iterator<Element> iterator1 = e
						.elementIterator(MsgConfig.Value);
				String conditionOfIn = "";
				while (iterator1.hasNext())
				{
					Element e1 = iterator1.next();
					String value = e1.getTextTrim();
					if (value != null && value.length() > 0)
					{
						conditionOfIn += "'" + value + "'" + ",";
					}
				}
				if (conditionOfIn.length() > 0)
				{
					conditionOfIn = conditionOfIn.substring(0,
							conditionOfIn.length() - 1);
				}
				condition += " and tac.送往地点 in(" + conditionOfIn + ")";
			}
		}
		return condition;
	}
}
