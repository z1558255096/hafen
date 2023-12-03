package com.moil.hafen.common.factory;

import com.moil.hafen.common.exception.FebsException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 900045
 * @description:
 * @name QiNiuCloudStorageService
 * @date By 2021-03-12 15:55:18
 */
public class QiNiuCloudStorageService extends CloudStorageService {

	/**
	 * <groupId>com.qiniu</groupId>
	 * 	<artifactId>qiniu-java-sdk</artifactId>
	 * 	<version>${qiniu.version}</version> 7.2.23
	 * 	private UploadManager uploadManager
	 */

	private String token;

	public QiNiuCloudStorageService(CloudStorageConfig config){
		this.config = config;

		//初始化
		init();
	}

	private void init(){
		/*uploadManager = new UploadManager(new Configuration(Zone.autoZone()))
		token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).
				uploadToken(config.getQiniuBucketName())*/
	}

	@Override
	public String upload(byte[] data, String path) {
		/*try {
			Response res = uploadManager.put(data, path, token)
			if (!res.isOK()) {
				throw new RuntimeException("上传七牛出错：" + res.toString())
			}
		} catch (Exception e) {
			throw new CustomRunTimeException("上传文件失败，请核对七牛配置信息", e)
		}*/

		return config.getQiniuDomain() + "/" + path;
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
		return upload(data, getPath(config.getQiniuPrefix(), suffix));
	}

	@Override
	public String uploadSuffix(InputStream inputStream, String suffix) throws FebsException {
		return upload(inputStream, getPath(config.getQiniuPrefix(), suffix));
	}
}
