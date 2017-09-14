package com.qheai.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MD5 加密类，用于生成 MD5 密文值
 * 
 * @author quietywind
 */
public class MD5Utilities {
	
	private static Logger logger = LoggerFactory.getLogger(MD5Utilities.class);

	/***
	 * 生成32位md5码
	 * 
	 * @param 待加密字符串
	 * @return 返回32位md5码
	 */
	public static String md5Encode(String inStr) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
			logger.warn("init MD5 encrypt class error, message is: {}",
					ex.toString());
			return "";
		}

		byte[] byteArray;
		try {
			byteArray = inStr.getBytes("UTF-8");

			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = md5Bytes[i] & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (UnsupportedEncodingException unEnEx) {
			logger.error(
					"MD5 encrypt class error, system don't support UTF-8 encoding: {}",
					unEnEx.toString());
			throw new Exception(
					"MD5 encrypt class, system don't support UTF-8 encoding.",
					unEnEx);
		}

	}
}
