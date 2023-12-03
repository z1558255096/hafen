package com.moil.hafen.common.enums;

/**
 * @author 900045
 * @description:
 * @name CloudService
 * @date By 2021-03-12 15:48:58
 */
public enum CloudService {

	/**
	 * 七牛云
	 */
	QINIU(1),
	/**
	 * 阿里云
	 */
	ALIYUN(2),
	/**
	 * 腾讯云
	 */
	QCLOUD(3);

	private int value;

	CloudService(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
