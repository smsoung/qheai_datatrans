package com.qheai.config;

import com.qheai.util.config.PropertiesBean;
import com.topsci.tools.KafkaExceptions.NullSystemIDException;
import com.topsci.tools.kafka.producer.KafkaProducer;

public class MsgConfig
{

	public static KafkaProducer kafkaProducer;

	static
	{
		try
		{
			kafkaProducer = new KafkaProducer(PropertiesBean.getKafkaHost(),
					System.getProperty("user.dir")
							+ PropertiesBean.getSslAddr());
		} catch (NullSystemIDException ex)
		{
			ex.printStackTrace();
		}
	}

	// 集成平台消息键
	public static String ENVELOPE = "Envelope";
	public static String MSGHEAD = "Header";
	public static String MSGBODY = "Body";
	public static String MSG_MSGSENTTIME = "MsgSentTime";
	public static String MSG_MSGSN = "MsgSN";
	public static String MSG_FORMSYSID = "FormSysID";
	public static String MSG_TOSYSID = "ToSysID";
	public static String MSG_MSGOID = "MsgOID";
	public static String MSG_MSGOTIME = "MsgOTime";
	public static String MSG_SESSIONID = "SessionID";
	public static String MSG_MSGTYPE = "MsgType";
	public static String MSG_MSGCODE = "MsgCode";
	public static String MSG_MSGNUM = "MsgNum";
	public static String MSG_MSGCOUNT = "MsgCount";

	public static String QueryType = "QueryType";
	public static String Value = "Value";
	public static String From = "From";
	public static String To = "To";
}
