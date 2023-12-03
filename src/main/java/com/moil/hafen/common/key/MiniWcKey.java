package com.moil.hafen.common.key;

/**
 * 微信小程序 使用的key
 * @author 900045
 * @description:
 * @name MiniWcKey
 * @date By 2021-03-30 10:48:43
 */
public enum MiniWcKey implements RedisKey {

	AUTH("auth:"),
	;

	/**
	 * key 前缀
	 */
	private String key;


	MiniWcKey() {
		this.key = this.name();
	}

	MiniWcKey(String key) {
		this.key = key;
	}
	
	@Override
	public String getKey() {
		String suffix = getSuffix();
		if (suffix == null) {
			return key;
		}
		return new StringBuilder(this.key).append(SEPARATE).append(suffix).toString();
	}
}
