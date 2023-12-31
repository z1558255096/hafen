package com.moil.hafen.common.http;

import com.moil.hafen.common.exception.FebsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 900045
 * @description:
 * @name HttpRequester
 * @date By 2021-03-10 18:37:05
 */
@Slf4j
public final class HttpRequester {

	private static Logger logger                 = LoggerFactory.getLogger(HttpRequester.class);

	/** 请求文本类型 */

	public static final String FORM_CONTENT_TYPE      = "application/x-www-form-urlencoded";
	/** 请求文本类型 */

	public static final String APPLICATION_JSON       = "application/json";
	/** 请求文本类型 */

	public static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	/** 字符  */

	private static final String FORM_CHARSET           = "UTF-8";

	/**  请求超时时间  5 s */

	private static final int                          CONNECT_TIME_OUT       = 10000;

	/**  传输超时时间  10s  */

	private static final int                          SOCKET_TIME_OUT        = 30000;

	/**  连接池最大并发连接数  */

	private static final int                          POOL_MAX_TOTAL         = 50;
	/**    连接池单路由最大并发数  */

	private static final int                          POOL_MAX_PERROUTE      = 10;

	/**  请求配置集 */

	private static Map<String, RequestConfig> requestConfigMap       = new HashMap<>();

	/**
	 *  HTTP client 链接管理器
	 */

	private static PoolingHttpClientConnectionManager POOL_CM                = null;
	static {
		//采用绕过验证的方式处理https请求  
		SSLContext sslcontext = createIgnoreVerifySSL();
		if (sslcontext != null) {
			// 设置协议http和https对应的处理socket链接工厂的对象  
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
			POOL_CM = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			//连接池最大并发连接数
			POOL_CM.setMaxTotal(POOL_MAX_TOTAL);
			//单路由最大并发数
			POOL_CM.setDefaultMaxPerRoute(POOL_MAX_PERROUTE);
		}

		try {
			SSLContextBuilder builder = createIgnoreVerifySSLBuilder();
			SSLConnectionSocketFactory sSLConnectionSocketFactory = new SSLConnectionSocketFactory(
					builder.build(), new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" }, null,
					NoopHostnameVerifier.INSTANCE);
			// 设置协议http和https对应的处理socket链接工厂的对象  
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", sSLConnectionSocketFactory).build();
			POOL_CM = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			//连接池最大并发连接数
			POOL_CM.setMaxTotal(POOL_MAX_TOTAL);
			//单路由最大并发数
			POOL_CM.setDefaultMaxPerRoute(POOL_MAX_PERROUTE);
		} catch (KeyManagementException | NoSuchAlgorithmException | FebsException e) {
			logger.error("init PoolingHttpClientConnectionManager E", e);
		}

	}

	/**
	 * post请求
	 *
	 * @param url         url地址
	 * @param reqParam     参数
	 * @param reqHeader     请求头
	 * @return
	 * @throws BusinessException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */

	public static String post(String url, Map<String, String> reqParam,
                              Map<String, String> reqHeader, int connectTimeout,
                              int socketTimeout) throws FebsException {
		String result = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			//设置请求和传输超时时间
			RequestConfig requestConfig = fetchConfig(connectTimeout, socketTimeout);
			httpPost.setConfig(requestConfig);
			//请求参数封装
			paramsHandler(httpPost, reqParam);
			//请求头参数
			Header[] headers = headerHandler(reqHeader);
			httpPost.setHeaders(headers);
			//执行请求
			CloseableHttpResponse response = fetchClient().execute(httpPost);
			org.apache.http.HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(entity, FORM_CHARSET);
			} else {
				result = response.getStatusLine().toString();
			}
			//消耗掉response
			EntityUtils.consume(entity);
			try {
				response.close();
			} catch (IOException e) {
				logger.error("HttpRequester-post response close", e);
				throw new Exception(e);
			}
		} catch (SocketTimeoutException e) {
			log.error("HTTP_STOCK_TIME_OUT:{}", e);
			throw new FebsException("HTTP_STOCK_TIME_OUT");
		} catch (ConnectTimeoutException e) {
			log.error("HTTP_CONNECT_TIME_OUT:{}", e);
			throw new FebsException("HTTP_CONNECT_TIME_OUT");
		} catch (Exception e) {
			log.error("HTTP_REQUEST_ERROR:{}", e);
			throw new FebsException("HTTP_REQUEST_ERROR");
		}
		return result;
	}

	/**
	 * post请求
	 * @param url         url地址
	 * @param reqParam     参数
	 * @return
	 * @throws Exception
	 */

	public static String post(String url, Map<String, String> reqParam,
                              Map<String, String> reqHeader) throws FebsException {
		return post(url, reqParam, reqHeader, CONNECT_TIME_OUT, SOCKET_TIME_OUT);
	}

	/**
	 * post请求
	 * @param url         url地址
	 * @param reqParam     参数
	 * @return
	 * @throws Exception
	 */

	public static String post(String url, Map<String, String> reqParam, int connectTimeout,
                              int socketTimeout) throws FebsException {
		return post(url, reqParam, null, connectTimeout, socketTimeout);
	}

	/**
	 * post请求
	 *
	 * @param url         url地址
	 * @param data     参数
	 * @return
	 * @throws FebsException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */

	public static String sendPost(String url, String data) {
		String response = null;
		CloseableHttpResponse httpresponse = null;
		try {
			HttpPost httppost = new HttpPost(url);
			StringEntity stringentity = new StringEntity(data,
					ContentType.create("text/json", "UTF-8"));
			httppost.setEntity(stringentity);
			httpresponse = fetchClient().execute(httppost);
			response = EntityUtils.toString(httpresponse.getEntity());
			//消耗掉response
			EntityUtils.consume(stringentity);
		} catch (Exception e) {
			logger.error("SEND-POST E", e);
		} finally {
			if (httpresponse != null) {
				try {
					httpresponse.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return response;
	}

	/**
	 *  post请求   返回响应流
	 *
	 * @param url 请求地址
	 * @param reqParam   请求参数
	 * @param reqHeader  请求头
	 * @return
	 * @throws Exception
	 */

	public static InputStream postForStream(String url, Map<String, String> reqParam,
                                            Map<String, String> reqHeader) throws Exception {

		return postForStream(url, reqParam, reqHeader, CONNECT_TIME_OUT, SOCKET_TIME_OUT);
	}

	/**
	 *   post请求   返回响应流
	 *
	 * @param url   请求地址
	 * @param reqParam      请求参数
	 * @param reqHeader     请求头
	 * @param socketTimeout   请求超时时间
	 * @param connectTimeout  输入超时时间
	 * @return
	 * @throws Exception
	 */

	public static InputStream postForStream(String url, Map<String, String> reqParam,
                                            Map<String, String> reqHeader, int socketTimeout,
                                            int connectTimeout) throws Exception {
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);

		//请求参数封装
		paramsHandler(httpPost, reqParam);
		//请求头参数
		Header[] headers = headerHandler(reqHeader);
		httpPost.setHeaders(headers);

		response = fetchClient().execute(httpPost);
		org.apache.http.HttpEntity entity = response.getEntity();
		if (response.getStatusLine().getStatusCode() == 200) {
			return entity.getContent();
		}
		throw new FebsException("请求失败，状态码："+response.getStatusLine().getStatusCode());
	}

	/**
	 * map转成url拼接请求参数
	 * https://api.weixin.qq.com/cgi-bin/token?
	 * grant_type=client_credential&appid=wxda47ff7ff90c03cf&secret=8bfdf63b063433f64114890d452e690e&
	 * @author: 900045
	 * @date: 2021-04-01 10:52:09
	 * @throws
	 * @param url:
	 * @param param: grant_type : x  /	appid : x  /	secret : x
	 * @return: java.lang.String
	 **/
	public static String packagingUrl(String url, Map<String, String> param){
		StringBuilder sb = new StringBuilder(url);
		if (param.size()>0){
			sb.append("?");
			int i = 1;
			for (String key : param.keySet()) {
				sb.append(key + "=");
				if (StringUtils.isEmpty(param.get(key))) {
					sb.append("&");
				} else {
					String value = param.get(key);
					try {
						value = URLEncoder.encode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						logger.error("", e);
					}
					if ( i < param.size()) {
						sb.append(value + "&");
					} else {
						sb.append(value);
					}
				}
				i++;
			}
		}
		logger.error("拼接后的请求{}",sb);
		return sb.toString();
	}

	/**
	 *
	 * 发送get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */

	public static String get(String url) throws Exception {
		return get(url, null);
	}

	/**
	 * 发送get请求
	 * @param url    路径
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */

	public static String get(String url, Map<String, String> reqHeader) throws Exception {
		String result = null;
		HttpGet httpGet = new HttpGet(url);
		//设置请求和传输超时时间
		RequestConfig requestConfig = fetchConfig(CONNECT_TIME_OUT, SOCKET_TIME_OUT);
		httpGet.setConfig(requestConfig);
		//请求头参数
		Header[] headers = headerHandler(reqHeader);
		httpGet.setHeaders(headers);

		CloseableHttpResponse response = fetchClient().execute(httpGet);
		//建立的http连接，仍旧被response1保持着，允许我们从网络socket中获取返回的数据
		//为了释放资源，我们必须手动消耗掉response1或者取消连接（使用CloseableHttpResponse类的close方法）
		HttpEntity entity = response.getEntity();
		/**请求发送成功，并得到响应**/

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			/**读取服务器返回过来的json字符串数据**/

			result = EntityUtils.toString(entity);
		} else {
			result = response.getStatusLine().toString();
		}
		//消耗掉response
		EntityUtils.consume(entity);
		try {
			response.close();
		} catch (IOException e) {
			logger.error(" get ", e);
			throw new Exception(e);
		}
		return result;

	}


	private static CloseableHttpClient fetchClient() throws FebsException {
		try {
			if (POOL_CM == null) {
				throw new FebsException("createConnectionManager Fail!");
			}
			return HttpClients.custom()
					.setServiceUnavailableRetryStrategy(new ServiceUnavailableRetryStrategy() {

						@Override
						public boolean retryRequest(HttpResponse response, int executionCount,
                                                    HttpContext context) {
							logger.info(" HTTP- setServiceUnavailableRetryStrategy:{}",
									executionCount);
							return false;
						}

						@Override
						public long getRetryInterval() {
							return 3;
						}
					}).setConnectionReuseStrategy(new ConnectionReuseStrategy() {

						@Override
						public boolean keepAlive(HttpResponse arg0, HttpContext arg1) {
							logger.info(" HTTP- ConnectionReuseStrategy:{}", arg1);
							return false;
						}
					}).setConnectionManager(POOL_CM).build();
		} catch (Exception e) {
			throw new FebsException("fetchClient E");
		}
	}

	/**
	 *  获取请求配置对象
	 *
	 * @param connectTimeout
	 * @param socketTimeout
	 * @return
	 */

	private static RequestConfig fetchConfig(int connectTimeout, int socketTimeout) {
		String key = Integer.toString(connectTimeout) + "-" + Integer.toString(socketTimeout);
		RequestConfig config = requestConfigMap.get(key);
		if (config != null) {
			return config;
		}
		config = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).build();
		return config;
	}

	/**
	 *   HTTP 请求头封装
	 *
	 * @param reqHeader
	 * @return
	 */

	private static Header[] headerHandler(Map<String, String> reqHeader) {
		if (reqHeader == null || reqHeader.isEmpty()) {
			return null;
		}
		Header[] headers = new Header[reqHeader.size()];
		int index = 0;
		for (String key : reqHeader.keySet()) {
			headers[index] = new BasicHeader(key, reqHeader.get(key));
			index++;
		}
		return headers;
	}

	/**
	 *   HTTP 请求参数封装
	 *
	 * @param reqParam
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	private static void paramsHandler(HttpPost httpPost,
									  Map<String, String> reqParam) throws UnsupportedEncodingException {
		if (reqParam == null || reqParam.isEmpty()) {
			return;
		}
		//封装参数
		List<NameValuePair> nvpList = paramsHandler(reqParam);
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvpList, FORM_CHARSET);
		formEntity.setContentType(FORM_CONTENT_TYPE);
		httpPost.setEntity(formEntity);
	}

	/**
	 *   HTTP 请求参数封装
	 *
	 * @param reqParams
	 * @return
	 */

	private static List<NameValuePair> paramsHandler(Map<String, String> reqParams) {
		if (reqParams == null || reqParams.isEmpty()) {
			return null;
		}
		List<NameValuePair> nvpList = new ArrayList<NameValuePair>(reqParams.size());
		for (Map.Entry<String, String> entry : reqParams.entrySet()) {
			//value一定要用 String.valueOf()
			nvpList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
		}
		return nvpList;
	}

	/**
	 * 绕过验证
	 *
	 * @return
	 * @throws FebsException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */

	private static SSLContextBuilder createIgnoreVerifySSLBuilder() throws FebsException {
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			// 全部信任 不做身份鉴定
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates,
                                         String s) throws CertificateException {
					return true;
				}
			});
			return builder;
		} catch (Exception e) {
			log.error("init  SSLContextBuilder E:{}",e);
			throw new FebsException("init  SSLContextBuilder E");
		}
	}

	/**
	 * 绕过验证
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Deprecated
	protected static SSLContext createIgnoreVerifySSL() {
		try {
			SSLContext sc = SSLContext.getInstance("SSLv3");
			// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
			X509TrustManager trustManager = new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
											   String paramString) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
											   String paramString) throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			sc.init(null, new TrustManager[] { trustManager }, null);
			return sc;
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			logger.error("createSSLContext E: ", e);
		}
		return null;
	}
}
