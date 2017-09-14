package test;

import com.topsci.tools.KafkaExceptions.NullSystemIDException;
import com.topsci.tools.clientcheck.ClientAliveMonitor;
import com.topsci.tools.kafka.producer.KafkaProducer;

/**
 * Created by lzw on 2016/11/1.
 */
public class ProducerTest
{
	public static void main(String[] args)
	{
		ClientAliveMonitor
				.setSystemId(
						"TEST_READ",
						"10.63.5.66:9292,10.63.5.67:9292,10.63.5.68:9292,130.130.51.62:9292,130.130.51.63:9292",
						"D:/workspace/qheai_datatrans/conf/client.truststore.jks");
		KafkaProducer producer;
		try
		{
			producer = new KafkaProducer(
					"10.63.5.66:9292,10.63.5.67:9292,10.63.5.68:9292,130.130.51.62:9292,130.130.51.63:9292",
					"D:/workspace/qheai_datatrans/conf/client.truststore.jks");

			String xml = "<Envelope><Header><MsgSentTime>20161206163709</MsgSentTime>"
					+ "<MsgSN>47CVBAS5TSRTEWTYEZETTYTWDG</MsgSN><FormSysID>TEST_READ</FormSysID><ToSysID>ALL</ToSysID><MsgOID>TEST_READ</MsgOID><MsgOTime>20161206163709</MsgOTime>"
					+ "<SessionID>28AAZMGCJCMKBHKGD</SessionID><MsgType>AKDZ_CZJL</MsgType><MsgCode>0002</MsgCode><MsgNum>0</MsgNum><MsgCount>1</MsgCount></Header><Body>"
					+ "<XB><QueryType>1</QueryType><Value>男</Value></XB>"
					+ "<JZRQ><QueryType>2</QueryType><From>2017-01-01</From><To>2017-01-02</To></JZRQ>"
					+ "<PSYY><QueryType>3</QueryType><Value>解放军第四医院</Value><Value>西宁市第二人民医院</Value><Value>青海省妇产儿童医院</Value></PSYY>"
					+ "</Body></Envelope>";
			System.out.println(xml);
			producer.sendProducer("RECEIVE_TOPIC", xml);

		} catch (NullSystemIDException e1)
		{
			e1.printStackTrace();
		}
	}
}
