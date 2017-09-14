package com.qheai.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 操作日期配置文件类
 * 
 * @author ssm
 *
 */
public class PropertiesUtil
{
	private static String fileName = "Date.properties";

	/**
	 * 根据key获取时间信息
	 * 
	 * @param key
	 * @return
	 */
	public static String GetValueByKey(String key)
	{
		Properties props = new Properties();
		try
		{
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(fileName);
			props.load(in);
			String value = props.getProperty(key);
			in.close();
			return value;
		} catch (FileNotFoundException e)
		{
			System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			System.out.println("装载文件--->失败!");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 写入时间信息
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public static void SetProperties(String key, String value)
	{
		Properties props = new Properties();
		try
		{
			String filePath = PropertiesUtil.class.getClassLoader()
					.getResource(fileName).getPath();
			OutputStream fos = new FileOutputStream(filePath);
			props.setProperty(key, value);
			props.store(fos, null);
			fos.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("写入属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			e.printStackTrace();
		} catch (IOException e)
		{
			System.out.println("写入文件--->失败!");
			e.printStackTrace();
		}

	}
}
