package com.qheai.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.config.MsgConfig;
import com.qheai.util.config.PropertiesBean;

/**
 * 将list分批次发布出去
 * @author topsci
 *
 */
public class XmlUtil {
	
	private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);
	private static String topci = PropertiesBean.getWriteTopic();
	
	/**
	 * 单个bean转换发送
	 * @param msgType
	 * @param masNum
	 * @param masCount
	 * @param fromId
	 * @param toId
	 * @param bean
	 * @return
	 */
	public static <T> boolean sendXml(String msgType, int masNum, int masCount, String fromId, String toId,String sessionID, String msgCode,T bean){
		boolean b = false; 
		String resmsg = "";
		try {
			if (bean != null) {
				StringBuilder sb = new StringBuilder("<Body>");
				String bodyXml = JaxbUtil.convertToXml(bean).replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
				if (bodyXml != null && !"".equals(bodyXml)) {
					sb.append(bodyXml);
					sb.append("</Body>");
					resmsg = addHead(msgType,masNum, masCount, fromId, toId,sessionID,msgCode, sb.toString());
					logger.info("发送xml："+resmsg);
					//发送给平台指定的KAFKA 主题
					MsgConfig.kafkaProducer.sendProducer(topci, resmsg);
					b = true;
				}
			}
		} catch (Exception e) {
			logger.error("数据发送主题失败：",e);
			e.printStackTrace();
		}
		return b;
	}
	
	/**
	 * 将全部的list发布出去
	 * @param msgType
	 * @param masNum
	 * @param masCount
	 * @param fromId
	 * @param toId
	 * @param list
	 * @return
	 */
	public static <T> boolean sendXmls(String msgType, int masNum, int masCount, String fromId, String toId, String sessionID,String msgCode,List<T> list){
		boolean b = false;
		Object bean = null;
		String resmsg = "";
		if(list.size()>0){
			StringBuilder sb = new StringBuilder("<Body>");
			StringBuilder bodyXml = new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				bodyXml.append(JaxbUtil.convertToXml(bean).replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", ""));
			}
			if (bodyXml != null && !"".equals(bodyXml.toString())) {
				sb.append(bodyXml);
				sb.append("</Body>");
				resmsg = addHead(msgType, masNum, masCount, fromId, toId,sessionID,msgCode, sb.toString());
				logger.info("发送xml："+resmsg);
				//发送给平台指定的KAFKA 主题
				MsgConfig.kafkaProducer.sendProducer(topci, resmsg);
				b = true;
			}
		}else { //没有数据
			resmsg = "<Body></Body>";
			resmsg = addHead(msgType, masNum, masCount, fromId, toId,sessionID,msgCode, resmsg);
			logger.info("发送xml："+resmsg);
//			System.out.println(resmsg);
			//发送给平台指定的KAFKA 主题
			MsgConfig.kafkaProducer.sendProducer(topci, resmsg);
			b = true;
		}
		return b;
	}
	
	/**
	 * 添加消息头
	 * @param mt
	 * @param state
	 * @param body
	 * @return
	 */
	public static String addHead(String msgType, int masNum, int masCount, String fromId, String toId,String sessionID,String msgCode, String body){
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		xml.append("<Envelope><Header><MsgSentTime>");
		xml.append(DateUtil.getNow3());
		xml.append("</MsgSentTime><MsgSN>");
		xml.append(UUIDGenerator.getNoCrossUUID().toUpperCase());
		xml.append("</MsgSN><FormSysID>");
		xml.append(fromId);
		xml.append("</FormSysID><ToSysID>");
		xml.append(toId);
		xml.append("</ToSysID><MsgOID>");
		xml.append(fromId);
		xml.append("</MsgOID><MsgOTime>");
		xml.append(DateUtil.getNow3());
		xml.append("</MsgOTime><SessionID>");
		xml.append(sessionID);
		xml.append("</SessionID><MsgType>");
		xml.append(msgType);
		xml.append("</MsgType><MsgCode>");
		xml.append(msgCode);
		xml.append("</MsgCode><MsgNum>");
		xml.append(masNum);
		xml.append("</MsgNum><MsgCount>");
		xml.append(masCount);
		xml.append("</MsgCount></Header>");
		xml.append(body);
		xml.append("</Envelope>");
		return xml.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Object> getBodyList(String bodyXml){
		List<Object> list = null;
		try {
			list = new ArrayList<Object>();
			Document document = DocumentHelper.parseText(bodyXml);
			Element body = document.getRootElement(); //得到 Body
			
			List<Element> elementList = body.elements();
			for (Element element : elementList) {
				String beanXml = element.asXML();
				beanXml = beanXml.replace(" xmlns=\"http://schema.ultra-as.com\"", "");
				String className = "";
				if (element.getName().equals("JC_WSRY")) {
					className = "com.topsci.qheai.bean.HealthOfficerBean";
				}else if (element.getName().equals("JC_YLSB")) {
					className = "com.topsci.qheai.bean.HospitalBean";
				}
				Object bean = JaxbUtil.converyToJavaBean(beanXml, Class.forName(className));
				list.add(bean);
			}
		} catch (Exception e) {
			logger.error("xml解析异常：", e);
			e.printStackTrace();
		}
		return list;
	}
}
