package com.qheai.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {

	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	
	public static String[] reflect(String beanName){
        String[] fieldArr = null;
		try {
			List<String> list = new ArrayList<String>();
			// 通过bean名字获取字段名
			@SuppressWarnings("rawtypes")
			Class beanClass = Class.forName(beanName);
			Field[] fields = beanClass.getDeclaredFields();
			if(fields != null && fields.length > 0){
			    for (Field field : fields) {
			        field.setAccessible(true);  //设置这个可访问私有属性
			        list.add(field.getName());
			    }
			}
			fieldArr = new String[list.size()]; 
			for (int i = 0; i < fieldArr.length; i++) {
				fieldArr[i] = list.get(i);
			}
		} catch (Exception e) {
			logger.error("获取bean属性字段异常：", e);
			e.printStackTrace();
		}
        return fieldArr;
    }
}
