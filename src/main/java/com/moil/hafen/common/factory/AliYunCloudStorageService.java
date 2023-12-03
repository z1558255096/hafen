package com.moil.hafen.common.factory;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.moil.hafen.common.exception.FebsException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 * @author 900045
 * @description:
 * @name AliYunCloudStorageService
 * @date By 2021-03-12 15:47:37
 */
public class AliYunCloudStorageService extends CloudStorageService {


	private OSS ossClient ;

	public AliYunCloudStorageService(CloudStorageConfig config){
		this.config = config;

		//初始化
		init();
	}

	private void init(){
		ossClient  = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
				config.getAliyunAccessKeySecret());
	}

	@Override
	public String upload(byte[] data, String path) throws FebsException {
		return upload(new ByteArrayInputStream(data), path);
	}

	@Override
	public String upload(InputStream inputStream, String path) throws FebsException {
		try {
			ossClient.putObject(config.getAliyunBucketName(), path, inputStream);
		} catch (Exception e){
			throw new FebsException("上传文件失败，请检查配置信息");
		}

		return config.getAliyunDomain() + "/" + path;
	}

	@Override
	public String uploadSuffix(byte[] data, String suffix) throws FebsException {
		return upload(data, getPath(config.getAliyunPrefix(), suffix));
	}

	@Override
	public String uploadSuffix(InputStream inputStream, String suffix) throws FebsException {
		return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
	}
}
