package com.qheai.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qheai.bean.CZJL;
import com.qheai.bean.JJJYJL;
import com.qheai.util.UUIDGenerator;
import com.qheai.util.XmlUtil;
import com.qheai.util.config.PropertiesBean;
import com.qheai.util.db.DBService;

/**
 * 发送数据访问层
 * 
 * @author ssm
 *
 */
public class SendDataDao
{

	private static Logger logger = LoggerFactory.getLogger(SendDataDao.class);
	private static String basId = PropertiesBean.getSourceID();

	/**
	 * 发送出诊记录新增数据
	 * 
	 * @param newTime
	 * @param oldTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void sendNewCZJL(String oldTime, String newTime)
	{
		DBService dbs = null;
		List<CZJL> list = null;
		String sql = null;

		try
		{
			dbs = new DBService();
			sql = "select count(*) from TAcceptEvent tac left join TAlarmEvent tal on tal.事件编码=tac.事件编码 left join TTask tt on tt.事件编码=tac.事件编码 and tt.受理序号=tac.受理序号 left join TAmbulance ta on ta.车辆编码=tt.车辆编码 where tal.是否测试=0 and tac.开始受理时刻>='"
					+ oldTime + "' and tac.开始受理时刻<='" + newTime + "'";
			int all = Integer.parseInt(dbs.getString(sql));
			int size = 100;

			if (all > size)
			{
				// 可以多次返回
				int n = all / size + 1;
				int y = all % size;
				for (int i = 0; i < n; i++)
				{
					if (i != n - 1)
					{
						sql = "select XB,NL,JZRQ,ZS,ZD,XZB,YZB,CPH,PSYY from (select tac.性别 XB,tac.年龄 NL,CONVERT(varchar(19),tac.开始受理时刻,120) JZRQ,tac.主诉 ZS,tac.病种判断 ZD,tac.X坐标 XZB,tac.Y坐标 YZB,ta.车牌号码 CPH,tac.送往地点 PSYY,ROW_NUMBER() over(order by tac.开始受理时刻) as rows from TAcceptEvent tac left join TAlarmEvent tal on tal.事件编码=tac.事件编码 left join TTask tt on tt.事件编码=tac.事件编码 and tt.受理序号=tac.受理序号 left join TAmbulance ta on ta.车辆编码=tt.车辆编码 where tal.是否测试=0 and tac.开始受理时刻>='"
								+ oldTime
								+ "' and tac.开始受理时刻<='"
								+ newTime
								+ "') as CZJL where rows between "
								+ (i * size + 1) + " and " + (i * size + size);

						list = dbs.getListBean(CZJL.class, sql, new String[] {
								"XB", "NL", "JZRQ", "ZS", "ZD", "XZB", "YZB",
								"CPH", "PSYY" });

						XmlUtil.sendXmls("AKDZ_CZJL", i, size, basId, "all",
								UUIDGenerator.getNoCrossUUID(), "0001", list);

					} else if (y != 0)
					{
						// 最后一次余数不能为0 否则请求为空
						sql = "select XB,NL,JZRQ,ZS,ZD,XZB,YZB,CPH,PSYY from (select tac.性别 XB,tac.年龄 NL,CONVERT(varchar(19),tac.开始受理时刻,120) JZRQ,tac.主诉 ZS,tac.病种判断 ZD,tac.X坐标 XZB,tac.Y坐标 YZB,ta.车牌号码 CPH,tac.送往地点 PSYY,ROW_NUMBER() over(order by tac.开始受理时刻) as rows from TAcceptEvent tac left join TAlarmEvent tal on tal.事件编码=tac.事件编码 left join TTask tt on tt.事件编码=tac.事件编码 and tt.受理序号=tac.受理序号 left join TAmbulance ta on ta.车辆编码=tt.车辆编码 where tal.是否测试=0 and tac.开始受理时刻>='"
								+ oldTime
								+ "' and tac.开始受理时刻<='"
								+ newTime
								+ "') as CZJL where rows between "
								+ (i * size + 1) + " and " + (i * size + y);

						list = dbs.getListBean(CZJL.class, sql, new String[] {
								"XB", "NL", "JZRQ", "ZS", "ZD", "XZB", "YZB",
								"CPH", "PSYY" });

						XmlUtil.sendXmls("AKDZ_CZJL", i, y, basId, "all",
								UUIDGenerator.getNoCrossUUID(), "0001", list);

					}
				}
			} else
			{
				// 一次查询结束返回
				sql = "select XB,NL,JZRQ,ZS,ZD,XZB,YZB,CPH,PSYY from (select tac.性别 XB,tac.年龄 NL,CONVERT(varchar(19),tac.开始受理时刻,120) JZRQ,tac.主诉 ZS,tac.病种判断 ZD,tac.X坐标 XZB,tac.Y坐标 YZB,ta.车牌号码 CPH,tac.送往地点 PSYY,ROW_NUMBER() over(order by tac.开始受理时刻) as rows from TAcceptEvent tac left join TAlarmEvent tal on tal.事件编码=tac.事件编码 left join TTask tt on tt.事件编码=tac.事件编码 and tt.受理序号=tac.受理序号 left join TAmbulance ta on ta.车辆编码=tt.车辆编码 where tal.是否测试=0 and tac.开始受理时刻>='"
						+ oldTime
						+ "' and tac.开始受理时刻<='"
						+ newTime
						+ "') as CZJL";

				list = dbs
						.getListBean(CZJL.class, sql, new String[] { "XB",
								"NL", "JZRQ", "ZS", "ZD", "XZB", "YZB", "CPH",
								"PSYY" });

				XmlUtil.sendXmls("AKDZ_CZJL", 0, all, basId, "all",
						UUIDGenerator.getNoCrossUUID(), "0001", list);

			}
		} catch (Exception e)
		{
			logger.error("查询出诊记录异常:{}", e);
			e.printStackTrace();
		} finally
		{
			if (dbs != null)
			{
				try
				{
					dbs.close();
				} catch (SQLException e)
				{
					logger.error("查询出诊记录关闭链接异常：{}", e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 发送紧急救援记录新增数据
	 * 
	 * @param newTime
	 * @param oldTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void sendNewJJJYJL(String oldTime, String newTime)
	{
		List<JJJYJL> list = null;
		DBService dbs = null;
		String sql = null;

		try
		{
			dbs = new DBService();
			sql = "select count(*) from TAcceptEvent tac left join TAlarmEvent tal on tal.事件编码=tac.事件编码 where tal.是否测试=0 and tac.开始受理时刻>='"
					+ oldTime + "' and tac.开始受理时刻<='" + newTime + "'";
			int all = Integer.parseInt(dbs.getString(sql));
			int size = 100;

			if (all > size)
			{
				// 可以多次返回
				int n = all / size + 1;
				int y = all % size;
				for (int i = 0; i < n; i++)
				{
					if (i != n - 1)
					{
						sql = "select SJMC,SJDD,FSSJ,PSYY from (select tal.事件名称 SJMC,tac.现场地址 SJDD,CONVERT(varchar(19),tac.开始受理时刻,120) FSSJ,tac.送往地点 PSYY,ROW_NUMBER() over(order by tac.开始受理时刻) as rows from TAcceptEvent tac left join TAlarmEvent tal on tal.事件编码=tac.事件编码 where tal.是否测试=0 and tac.开始受理时刻>='"
								+ oldTime
								+ "' and tac.开始受理时刻<='"
								+ newTime
								+ "') as JJJYJL where rows between "
								+ (i * size + 1) + " and " + (i * size + size);

						list = dbs.getListBean(JJJYJL.class, sql, new String[] {
								"SJMC", "SJDD", "FSSJ", "PSYY" });

						XmlUtil.sendXmls("AKDZ_JJJYJL", i, size, basId, "all",
								UUIDGenerator.getNoCrossUUID(), "0001", list);

					} else if (y != 0)
					{
						// 最后一次余数不能为0 否则请求为空
						sql = "select SJMC,SJDD,FSSJ,PSYY from (select tal.事件名称 SJMC,tac.现场地址 SJDD,CONVERT(varchar(19),tac.开始受理时刻,120) FSSJ,tac.送往地点 PSYY,ROW_NUMBER() over(order by tac.开始受理时刻) as rows from TAcceptEvent tac left join TAlarmEvent tal on tal.事件编码=tac.事件编码 where tal.是否测试=0 and tac.开始受理时刻>='"
								+ oldTime
								+ "' and tac.开始受理时刻<='"
								+ newTime
								+ "') as JJJYJL where rows between "
								+ (i * size + 1) + " and " + (i * size + y);

						list = dbs.getListBean(JJJYJL.class, sql, new String[] {
								"SJMC", "SJDD", "FSSJ", "PSYY" });

						XmlUtil.sendXmls("AKDZ_JJJYJL", i, y, basId, "all",
								UUIDGenerator.getNoCrossUUID(), "0001", list);

					}
				}
			} else
			{
				// 一次查询结束返回
				sql = "select SJMC,SJDD,FSSJ,PSYY from (select tal.事件名称 SJMC,tac.现场地址 SJDD,CONVERT(varchar(19),tac.开始受理时刻,120) FSSJ,tac.送往地点 PSYY,ROW_NUMBER() over(order by tac.开始受理时刻) as rows from TAcceptEvent tac left join TAlarmEvent tal on tal.事件编码=tac.事件编码 where tal.是否测试=0 and tac.开始受理时刻>='"
						+ oldTime
						+ "' and tac.开始受理时刻<='"
						+ newTime
						+ "') as JJJYJL";

				list = dbs.getListBean(JJJYJL.class, sql, new String[] {
						"SJMC", "SJDD", "FSSJ", "PSYY" });

				XmlUtil.sendXmls("AKDZ_JJJYJL", 0, all, basId, "all",
						UUIDGenerator.getNoCrossUUID(), "0001", list);

			}
		} catch (Exception e)
		{
			logger.error("查询紧急救援记录异常:{}", e);
			e.printStackTrace();
		} finally
		{
			if (dbs != null)
			{
				try
				{
					dbs.close();
				} catch (SQLException e)
				{
					logger.error("查询紧急救援记录关闭链接异常：{}", e);
					e.printStackTrace();
				}
			}
		}
	}

}
