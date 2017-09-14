package com.qheai.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * <p>
 * Title: JJJYJL
 * </p>
 * <p>
 * Description: 紧急救援记录
 * </p>
 * <p>
 * Company: anchor
 * </p>
 * 
 * @author ssm
 * @date 2017年5月25日 下午1:49:26
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AKDZ_JJJYJL")
@XmlType(propOrder = { "SJMC", "SJDD", "FSSJ", "PSYY" })
public class JJJYJL
{
	@XmlElement(required = true, nillable = true)
	private String SJMC;// 时间名称
	@XmlElement(required = true, nillable = true)
	private String SJDD;// 事件地点
	@XmlElement(required = true, nillable = true)
	private String FSSJ;// 发生时间
	@XmlElement(required = true, nillable = true)
	private String PSYY;// 派送医院

	public String getSJMC()
	{
		return SJMC;
	}

	public void setSJMC(String sJMC)
	{
		SJMC = sJMC;
	}

	public String getSJDD()
	{
		return SJDD;
	}

	public void setSJDD(String sJDD)
	{
		SJDD = sJDD;
	}

	public String getFSSJ()
	{
		return FSSJ;
	}

	public void setFSSJ(String fSSJ)
	{
		FSSJ = fSSJ;
	}

	public String getPSYY()
	{
		return PSYY;
	}

	public void setPSYY(String pSYY)
	{
		PSYY = pSYY;
	}

}
