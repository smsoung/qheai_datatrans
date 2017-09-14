package com.qheai.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * <p>
 * Title: CZJL
 * </p>
 * <p>
 * Description: 出诊记录
 * </p>
 * <p>
 * Company: anchor
 * </p>
 * 
 * @author ssm
 * @date 2017年5月25日 上午10:19:42
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AKDZ_CZJL")
@XmlType(propOrder = { "XB", "NL", "JZRQ", "ZS", "ZD", "XZB", "YZB", "CPH",
		"PSYY" })
public class CZJL
{

	@XmlElement(required = true, nillable = true)
	private String XB;// 性别
	@XmlElement(required = true, nillable = true)
	private String NL;// 年龄
	@XmlElement(required = true, nillable = true)
	private String JZRQ;// 接诊日期
	@XmlElement(required = true, nillable = true)
	private String ZS;// 主诉
	@XmlElement(required = true, nillable = true)
	private String ZD;// 诊断
	@XmlElement(required = true, nillable = true)
	private String XZB;// X坐标
	@XmlElement(required = true, nillable = true)
	private String YZB;// Y坐标
	@XmlElement(required = true, nillable = true)
	private String CPH;// 车牌号
	@XmlElement(required = true, nillable = true)
	private String PSYY;// 派送医院

	public String getXB()
	{
		return XB;
	}

	public void setXB(String xB)
	{
		XB = xB;
	}

	public String getNL()
	{
		return NL;
	}

	public void setNL(String nL)
	{
		NL = nL;
	}

	public String getJZRQ()
	{
		return JZRQ;
	}

	public void setJZRQ(String jZRQ)
	{
		JZRQ = jZRQ;
	}

	public String getZS()
	{
		return ZS;
	}

	public void setZS(String zS)
	{
		ZS = zS;
	}

	public String getZD()
	{
		return ZD;
	}

	public void setZD(String zD)
	{
		ZD = zD;
	}

	public String getXZB()
	{
		return XZB;
	}

	public void setXZB(String xZB)
	{
		XZB = xZB;
	}

	public String getYZB()
	{
		return YZB;
	}

	public void setYZB(String yZB)
	{
		YZB = yZB;
	}

	public String getCPH()
	{
		return CPH;
	}

	public void setCPH(String cPH)
	{
		CPH = cPH;
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
