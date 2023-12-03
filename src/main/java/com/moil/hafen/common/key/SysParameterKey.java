package com.moil.hafen.common.key;

/**
 * @author 900045
 * @description:
 * @name SysParameterKey
 * @date By 2021-03-12 15:20:08
 */
public enum SysParameterKey implements RedisKey {
	
	/** 存入 缓存的 key*/
	SYS_PARAMETER_KEY("sys:config:"),


	
	
	// ===============对应 t_sys_parameter 表中的 param_key==============================
	/**微信小程序appID**/
	USER_MINI_PROGRAM_WX_APP_ID("USER_MINI_PROGRAM_WX_APP_ID"),

	/**微信小程序secret*/
	USER_MINI_PROGRAM_WX_SECRET("USER_MINI_PROGRAM_WX_SECRET"),

	/**微信小程序获取sessionKey 请求接口*/
	USER_MINI_PROGRAM_WX_GET_SESSION_URL("USER_MINI_PROGRAM_WX_GET_SESSION_URL"),

	/**微信小程序接口调用凭证*/
	USER_MINI_PROGRAM_WX_GET_ACCESS_TOKEN_URL("USER_MINI_PROGRAM_WX_GET_ACCESS_TOKEN_URL"),
	

	;

	private String key;

	SysParameterKey() {
		key = this.name();
	}

	SysParameterKey(String key) {
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
