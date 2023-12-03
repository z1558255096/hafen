package com.moil.hafen.common.factory;

import com.moil.hafen.common.enums.CloudService;
import com.moil.hafen.common.exception.FebsException;

/**
 * 文件上传Factory
 * @author 900045
 * @description:
 * @name OssFactory
 * @date By 2021-03-12 15:39:28
 */
public final class OssFactory {

	public static CloudStorageService build(CloudStorageConfig config) throws FebsException {
		//获取云存储配置信息
//		CloudStorageConfig config = sysParameterService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
		if(config.getType() == CloudService.QINIU.getValue()){
			return new QiNiuCloudStorageService(config);
		}else if(config.getType() == CloudService.ALIYUN.getValue()){
			return new AliYunCloudStorageService(config);
		}else if(config.getType() == CloudService.QCLOUD.getValue()){
			return new TenCentCloudStorageService(config);
		}

		return null;
	}
}
