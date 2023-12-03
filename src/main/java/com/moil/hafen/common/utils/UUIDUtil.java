package com.moil.hafen.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 900045
 * @description:
 * @name UUIDUtil
 * @date By 2021-04-09 14:36:46
 */
public class UUIDUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(UUIDUtil.class);

	private static UUIDFactory uuid = null;

	static {
		try {
			uuid = UUIDFactory.getInstance("UUID");
		} catch (Exception e) {
			LOGGER.info("Init UIDFactory Failed", e);
		}
	}

	/**
	 * Constructor for the UUID object
	 */
	private UUIDUtil() {
	}

	/**
	 * 获取uuid字符
	 *
	 * @author lihe 2013-7-4 下午5:31:09
	 * @return
	 * @see
	 * @since
	 */
	public static String getUUID() {
		return uuid.getNextUID();
	}
}
