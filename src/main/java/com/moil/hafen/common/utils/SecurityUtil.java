package com.moil.hafen.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码安全帮助类
 * @author 900045
 * @description:
 * @name SecurityUtil
 * @date By 2021-03-11 16:30:16
 */
public final class SecurityUtil {

	private static final BigInteger PRIVATE_D = new BigInteger(
			"3206586642942415709865087389521403230384599658161226562177807849299468150139");

	private static final BigInteger N = new BigInteger(
			"7318321375709168120463791861978437703461807315898125152257493378072925281977");


	static MessageDigest getDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private SecurityUtil() {

	}

	/**
	 * 解析前台传送的加密字符
	 *
	 * @param str
	 * @return
	 * @author lihe 2012-9-26 上午10:51:28
	 * @see
	 */
	public static String getDecryptLoginPassword(String str) {
		byte[] bytes = HexUtil.toByteArray(str);
		BigInteger bigInteger = new BigInteger(bytes);

		BigInteger variable = bigInteger.modPow(PRIVATE_D, N);
		// 计算明文对应的字符串
		byte[] mt = variable.toByteArray();
		StringBuilder buffer = new StringBuilder();
		for (int i = mt.length - 1; i > -1; i--) {
			buffer.append((char) mt[i]);
		}
		return buffer.substring(0, buffer.length() - 10);
	}

	/**
	 * 生成密码安全码
	 *
	 * @return
	 * @author lihe 2012-9-27 上午9:40:51
	 * @see
	 */
	public static String getNewPsw() {
		String s1 = MdFiveUtil.md5Hex(String.valueOf(System.currentTimeMillis()));
		String s2 = UUIDUtil.getUUID();
		return s1 + s2;
	}

	public static String getStoreLoginPwd(String userCode, String logPwd, String psw) {
		return MdFiveUtil.md5Hex(userCode + MdFiveUtil.md5Hex(logPwd) + psw);
	}


	public static byte[] md5(String data, String charset) {
		if (charset == null) {
			return getDigest().digest(data.getBytes());
		}
		try {
			return getDigest().digest(data.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}
	
}
