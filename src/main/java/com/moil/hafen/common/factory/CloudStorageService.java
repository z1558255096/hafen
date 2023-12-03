package com.moil.hafen.common.factory;

import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 云存储(支持七牛、阿里云、腾讯云、又拍云)
 * @author 900045
 * @description:
 * @name CloudStorageService
 * @date By 2021-03-12 15:45:33
 */
public abstract class CloudStorageService {

	/** 云存储配置信息 */
	CloudStorageConfig config;


	/**
	 * 文件路径
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回上传路径
	 */
	public String getPath(String prefix, String suffix) {
		//生成uuid
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		//文件路径
		String path = DateUtil.format2(new Date(), "yyyyMMdd") + "/" + uuid;

		if(StringUtils.isNotBlank(prefix)){
			path = prefix + "/" + path;
		}

		return path + suffix;
	}

	/**
	 * 文件上传
	 * @param data    文件字节数组
	 * @param path    文件路径，包含文件名
	 * @return        返回http地址
	 */
	public abstract String upload(byte[] data, String path) throws FebsException;

	/**
	 * 文件上传
	 * @param data     文件字节数组
	 * @param suffix   后缀
	 * @return         返回http地址
	 */
	public abstract String uploadSuffix(byte[] data, String suffix) throws FebsException;

	/**
	 * 文件上传
	 * @param inputStream   字节流
	 * @param path          文件路径，包含文件名
	 * @return              返回http地址
	 */
	public abstract String upload(InputStream inputStream, String path) throws FebsException;

	/**
	 * 文件上传
	 * @param inputStream  字节流
	 * @param suffix       后缀
	 * @return             返回http地址
	 */
	public abstract String uploadSuffix(InputStream inputStream, String suffix) throws FebsException;
}
