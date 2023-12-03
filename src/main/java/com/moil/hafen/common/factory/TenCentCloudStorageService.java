package com.moil.hafen.common.factory;

import com.moil.hafen.common.exception.FebsException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 900045
 * @description:
 * @name TenCentCloudStorageService
 * @date By 2021-03-12 15:54:42
 */
public class TenCentCloudStorageService extends CloudStorageService {

	/**
	 * <groupId>com.qcloud</groupId>
	 * 	<artifactId>cos_api</artifactId>
	 * 	<version>${qcloud.cos.version}</version> 4.4
	 * 	private COSClient client
	 */

	

	public TenCentCloudStorageService(CloudStorageConfig config){
		this.config = config;

		//初始化
		init();
	}

	private void init(){
		/*Credentials credentials = new Credentials(config.getQcloudAppId(), config.getQcloudSecretId(),
				config.getQcloudSecretKey())

		//初始化客户端配置
		ClientConfig clientConfig = new ClientConfig()
		//设置bucket所在的区域，华南：gz 华北：tj 华东：sh
		clientConfig.setRegion(config.getQcloudRegion())

		client = new COSClient(clientConfig, credentials)
		*/
	}

	@Override
	public String upload(byte[] data, String path) {
		//腾讯云必需要以"/"开头
		if(!path.startsWith("/")) {
			path = "/" + path;
		}

		/*
		//上传到腾讯云
		UploadFileRequest request = new UploadFileRequest(config.getQcloudBucketName(), path, data)
		String response = client.uploadFile(request)

		JSONObject jsonObject = JSONObject.parseObject(response)
		if(jsonObject.getInteger("code") != 0) {
			throw new CustomRunTimeException("文件上传失败，" + jsonObject.getString("message"))
		}
		*/

		return config.getQcloudDomain() + path;
	}

	@Override
	public String upload(InputStream inputStream, String path) throws FebsException {
		try {
			byte[] data = IOUtils.toByteArray(inputStream);
			return this.upload(data, path);
		} catch (IOException e) {
			throw new FebsException("上传文件失败");
		}
	}

	@Override
	public String uploadSuffix(byte[] data, String suffix) {
		return upload(data, getPath(config.getQcloudPrefix(), suffix));
	}

	@Override
	public String uploadSuffix(InputStream inputStream, String suffix) throws FebsException {
		return upload(inputStream, getPath(config.getQcloudPrefix(), suffix));
	}
}
