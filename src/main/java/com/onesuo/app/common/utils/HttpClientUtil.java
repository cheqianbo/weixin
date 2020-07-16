package com.onesuo.app.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.*;
import org.apache.http.util.EntityUtils;

import javax.management.OperationsException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
	/**
	 * 字符集编码操作类
	 */
//	private static EncodeUtil encodeUtil = new EncodeUtil();
	/**
	 * 超时时间
	 */
	private static int overtime = 30000;
	/**
	 * 链接池
	 */
	private static PoolingClientConnectionManager connectionManager = null;
	/**
	 * 链接参数
	 */
	private static HttpParams httpParams = null;
	/**
	 * 日志记录
	 */
	private static Log log = LogFactory.getLog(HttpClientUtil.class);

	/**
	 * 获取页面源代码
	 * @param strUrl      要抓取页面的地址
	 * @param charset     页面的字符编码        可以为 null
	 * @return            页面源代码
	 */
	public static String getInfo(String strUrl, String charset) {
		if ((strUrl == null) || (strUrl.trim().equals(""))) {
			return "connect error";
		}
		String htmlContent = "";
		HttpClient httpClient = getHttpClient(overtime);
		HttpGet getMethod = getGetMethod(overtime);
		try {
			if (charset == null || charset.equals("")) {
				charset = EncodeUtils.urlEncode(strUrl);
			}
			getMethod.setURI(formatURI(strUrl, charset));
			HttpResponse response = httpClient.execute(getMethod);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			/*判断访问的状态码*/
			if (statusCode != HttpStatus.SC_OK) {
				htmlContent = "connect error:"+statusCode;
			}else{
				htmlContent = httpEnToString(entity, charset);
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			log.debug("getInfo："+e);
			htmlContent = "connect error";
		} finally {
			getMethod.abort();
		}
		return htmlContent;
	}

	/**
	 * 获取页面状态 测试连接使用
	 * @param strUrl
	 * @param overtime
	 * @return
	 */
	public static int getStatusCode(String strUrl, int overtime){
		HttpClient httpClient = getHttpClient(overtime);
		HttpGet getMethod = getGetMethod(overtime);
		int statusCode = 0;
		try{
			getMethod.setURI(formatURI(strUrl, null));
			HttpResponse response = httpClient.execute(getMethod);
			statusCode = response.getStatusLine().getStatusCode();
		}catch (Exception e) {
			log.debug("getStatusCode:"+e);
		}finally{
			getMethod.abort();
		}
		return statusCode;
	}
	
	/**
	 * 下载文件
	 * @param strUrl      下载文件的地址
	 * @param filePath    存放文件的地址（包括文件名称）
	 * @return            下载文件是否成功
	 */
	public static boolean downloadFile(String strUrl, String filePath) {
		boolean isSuccess = false;
		HttpClient httpClient = getHttpClient(overtime);
		HttpGet get = getGetMethod(overtime);
		
		FileOutputStream output = null;
		try {
			URL url = new URL(strUrl);
			String referer = "http://" + url.getHost();
			get.setURI(formatURI(strUrl, "UTF-8"));
			/*解决 TOO MANAY OPEN FILES 问题和防盗链问题*/
			get.setHeader("Referer", referer);
			get.setHeader("HTTP_REFERER", referer);
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK){
				File storeFile = new File(filePath);
				output = new FileOutputStream(storeFile);
				// 得到网络资源的字节数组,并写入文件
				output.write(EntityUtils.toByteArray(entity));
				isSuccess =  true;
			}
		} catch (Exception e) {
			log.debug("downloadFile:"+e);
			isSuccess = false;
		} finally {
			get.abort();
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					log.debug("下载文件释放连接失败！");
				}
			}
		}
		return isSuccess;
	}

	/**
	 * 向某页面POST数据
	 * @param strUrl      地址
	 * @param qparams     内容
	 * @param charset     字符编码
	 * @return          页面返回的数据
	 */
	public static String postInfo(String strUrl, List<NameValuePair> qparams, String charset){
		HttpClient httpClient = getHttpClient(overtime);
		HttpPost post = getPostMethod(overtime);
		HttpEntity entity = null;
		String htmlcontent = "";
		try{
			if(charset == null || "".equals(charset)){
				charset = EncodeUtils.urlEncode(strUrl);
			}
			post.setURI(formatURI(strUrl, charset));
			post.addHeader("ApiKey", "395255112366292992");
			post.setHeader("ApiKey", "395255112366292992");
			post.setEntity(new UrlEncodedFormEntity(qparams, charset));
	
	        HttpResponse response = httpClient.execute(post);
	        entity = response.getEntity();
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	htmlcontent = httpEnToString(entity, charset);
	        }else{
	        	htmlcontent = "connect error:"+statusCode;
	        }
			EntityUtils.consume(entity);
		}catch (Exception e) {
			log.debug("postInfo："+e);
			htmlcontent = "404";
		}finally{
			post.abort();
		}
		return htmlcontent;
	}

	/**
	 * 向某页面POST数据
	 * @param strUrl      地址
	 * @param qparams     内容
	 * @param charset     字符编码
	 * @return          页面返回的数据
	 */
	public static String postInfoCheck(String strUrl, List<NameValuePair> qparams, String charset){
		HttpClient httpClient = getHttpClient(overtime);
		HttpPost post = getPostMethod(overtime);
		HttpEntity entity = null;
		String htmlcontent = "";
		try{
			if(charset == null || "".equals(charset)){
				charset = EncodeUtils.urlEncode(strUrl);
			}
			post.setURI(formatURI(strUrl, charset));
			post.addHeader("ApiKey", "458642887437975552");
			post.setHeader("ApiKey", "458642887437975552");
			post.setEntity(new UrlEncodedFormEntity(qparams, charset));
	
	        HttpResponse response = httpClient.execute(post);
	        entity = response.getEntity();
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	htmlcontent = httpEnToString(entity, charset);
	        }else{
	        	htmlcontent = "connect error:"+statusCode;
	        }
			EntityUtils.consume(entity);
		}catch (Exception e) {
			log.debug("postInfo："+e);
			htmlcontent = "404";
		}finally{
			post.abort();
		}
		return htmlcontent;
	}
	
	/**
	 * 向某页面POST数据
	 * @param strUrl      地址
	 * @param qparams     内容
	 * @param charset     字符编码
	 * @return          页面返回的数据
	 */
	public static String postSMS(String strUrl, List<NameValuePair> qparams, String charset){
		HttpClient httpClient = getHttpClient(overtime);
		HttpPost post = getPostMethod(overtime);
		HttpEntity entity = null;
		String htmlcontent = "";
		try{
			if(charset == null || "".equals(charset)){
				charset = EncodeUtils.urlEncode(strUrl);
			}
			post.setURI(formatURI(strUrl, charset));
			post.setEntity(new UrlEncodedFormEntity(qparams, charset));
	
	        HttpResponse response = httpClient.execute(post);
	        entity = response.getEntity();
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	htmlcontent = httpEnToString(entity, charset);
	        }else{
	        	htmlcontent = "connect error:"+statusCode;
	        }
			EntityUtils.consume(entity);
		}catch (Exception e) {
			log.debug("postInfo："+e);
			htmlcontent = "404";
		}finally{
			post.abort();
		}
		return htmlcontent;
	}
	
	/**
	 * 获取表头
	 * @param strUrl 地址
	 * @return       表头
	 */
	public static Header[] getHeaders(String strUrl){
		HttpClient httpClient = getHttpClient(overtime);
		HttpHead  head = getHeadMethod(overtime);
		Header[] headers = null;
		try{
			head.setURI(formatURI(strUrl, null));
			HttpResponse response = httpClient.execute(head);  
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != HttpStatus.SC_NOT_FOUND){
				headers = response.getAllHeaders();
			}
		}catch (Exception e) {
			log.debug("getHeaders："+e);
			headers = null;
		}finally{
			head.abort();
		}
		return headers;
	}
	/**
	 * 获取HttpClient
	 * @param overtime 超时时间
	 * @return HttpClient
	 */
	private static HttpClient getHttpClient(int overtime) {
		if(connectionManager == null){
			SchemeRegistry supportedSchemes = new SchemeRegistry();
			supportedSchemes.register(new Scheme("http", 80, PlainSocketFactory
					.getSocketFactory()));
			supportedSchemes.register(new Scheme("https", 443, SSLSocketFactory
					.getSocketFactory()));
			connectionManager = new PoolingClientConnectionManager(
					supportedSchemes);
			/*设置最大连接数*/
			connectionManager.setMaxTotal(2000);
			/*设置每个路由的默认最大连接到20*/
			connectionManager.setDefaultMaxPerRoute(200);
			/*对LocalHost:80设置最大连接到50*/
			HttpHost localhost = new HttpHost("locahost", 80);
			connectionManager.setMaxPerRoute(new HttpRoute(localhost), 200);
		}
		if(httpParams == null){
			/*设置HTTPHEAD*/
			List<BasicHeader> headers = new ArrayList<BasicHeader>();
			headers.add(new BasicHeader("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
			headers.add(new BasicHeader("Accept-Language", "zh,en"));
			httpParams = new BasicHttpParams();
			httpParams.setParameter("http.default-headers", headers);
			HttpProtocolParams.setContentCharset(httpParams, "GB18030");
			HttpProtocolParams.setHttpElementCharset(httpParams, "GB18030");
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					overtime);
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, overtime);
			httpParams.setParameter("http.protocol.expect-continue", false);
			/*设置cookies标准 解决 采集163网站出现的警告*/
			httpParams.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		}
		return new DefaultHttpClient(connectionManager, httpParams);
	}

	/**
	 * getGetMethod:(设置httpMethod的属性).
	 * @param  overtime 超时时间
	 * @return HttpGet  
	 */
	private static HttpGet getGetMethod(int overtime) {
		HttpGet getMethod = new HttpGet();
		getMethod.setHeader("Connection", "close");
		getMethod.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
				Integer.valueOf(overtime));
		getMethod.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,
				Integer.valueOf(overtime));
		getMethod.getParams().setParameter("http.protocol.head-body-timeout",
				Integer.valueOf(overtime));
		getMethod.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		return getMethod;
	}

	/**
	 * getPostMethod:(设置httpMethod的属性).
	 * @param  overtime 超时时间
	 * @return HttpPost 
	 */
	private static HttpPost getPostMethod(int overtime) {
		HttpPost postMethod = new HttpPost();
		postMethod.setHeader("Connection", "close");
		postMethod.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
				Integer.valueOf(overtime));
		postMethod.getParams().setParameter("http.protocol.head-body-timeout",
				Integer.valueOf(overtime));
		postMethod.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		return postMethod;
	}
	
	/**
	 * getHeadMethod:设置httpMethod的属性.
	 * @param  overtime 超时时间
	 * @return HttpHead 
	 */
	private static HttpHead getHeadMethod(int overtime) {
		HttpHead head = new HttpHead();
		head.setHeader("Connection", "close");
		head.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
				Integer.valueOf(overtime));
		head.getParams().setParameter("http.protocol.head-body-timeout",
				Integer.valueOf(overtime));
		head.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
				false);
		return head;
	}
	/**
	 * 将URL转换成URI
	 * @param strUrl     URL
	 * @param charset    字符编码
	 * @return           URI
	 */
	private static URI formatURI(String strUrl, String charset){
		URL url = null;
		URI uri = null;
		try {
			strUrl = strUrl.replaceAll("&amp;", "&");
			strUrl = strUrl.replaceAll(" ", "%20");
//			strUrl = encodeUtil.encodeStr(strUrl, charset);
			url = new URL(strUrl.trim());
			uri = null;
			try{
				uri = URI.create(strUrl.trim());
			}catch (Exception e) {
				log.debug("URL不符合规范：" + strUrl);
			}
			if(uri == null){
				uri = new URI(url.getProtocol(), url.getAuthority(), url.getPath(), url.getQuery(), null);
			}
		} catch (Exception e) {
			log.debug("formatURI："+e);
		}
		return uri;
	}
	
	/**
	 * 获取HTML内容
	 * @param httpEntity HTTPENTITY
	 * @param charset    编码
	 * @return           HTML内容
	 */
	private static String httpEnToString(HttpEntity httpEntity, String charset){
		StringBuffer html = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(httpEntity
					.getContent(), charset));
			if (reader != null) {
				String inputLine = null;
				while ((inputLine = reader.readLine()) != null) {
					html.append(inputLine);
					html.append("\n");
				}
			}
		} catch (Exception e) {
			log.debug("httpEnToString：" + e);
			html.delete(0, html.length());
			html.append("connect error");
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return html.toString();
	}
	
	/**
	 * 向某页面POST数据
	 * @param strUrl      地址
	 * @param qparams     内容
	 * @param charset     字符编码
	 * @return          页面返回的数据
	 * @throws Exception 
	 */
	public static String postInfoCustom(String strUrl, List<NameValuePair> qparams,JSONArray qheader, String charset) throws Exception{
		HttpClient httpClient = getHttpClient(overtime);
		HttpPost post = getPostMethod(overtime);
		HttpEntity entity = null;
		String htmlcontent = "";
		try{
			if(charset == null || "".equals(charset)){
				charset = EncodeUtils.urlEncode(strUrl);
			}
			post.setURI(formatURI(strUrl, charset));
			for(int i=0;i<qheader.size();i++) {
				JSONObject header = qheader.getJSONObject(i);
				post.addHeader(header.getString("key"), header.getString("value"));
				post.setHeader(header.getString("key"), header.getString("value"));
			}
			
			post.setEntity(new UrlEncodedFormEntity(qparams, charset));
	
	        HttpResponse response = httpClient.execute(post);
	        entity = response.getEntity();
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode == HttpStatus.SC_OK){
	        	htmlcontent = httpEnToString(entity, charset);
	        }else{
	        	htmlcontent = "connect error:"+statusCode;
	        	throw new OperationsException(htmlcontent);
	        }
			EntityUtils.consume(entity);
		}catch (Exception e) {
			throw e;
		}finally{
			post.abort();
		}
		return htmlcontent;
	}
	
	public static String getInfoCustom(String strUrl,JSONArray paramArray,JSONArray qheader, String charset) throws Exception{
		String params = "";
		for(int i=0;i<paramArray.size();i++) {
			JSONObject param = paramArray.getJSONObject(i);
			String value = param.getString("value");
			Integer isUrlEncoding = param.getInteger("isUrlEncoding");
			if(isUrlEncoding == 1) {
				value = URLEncoder.encode(value);
			}
			params = params + param.getString("key") + "=" + value + "&";
		}
		if(params.length() > 1 && "&".equals(params.indexOf(params.length()-1))) {
			params = params.substring(0, params.length() -1);
		}
		String result = "";
        BufferedReader in = null;
        try {
        	URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); 
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            for(int i=0;i<qheader.size();i++) {
    			JSONObject header = qheader.getJSONObject(i);
    			// 设置通用的请求属性
                connection.addRequestProperty(header.getString("key"), header.getString("value"));
    		}
            connection.connect();
            if(StringUtils.isNotBlank(params)) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(params);
                writer.close();
            }

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
            	// 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(),charset));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }else if(responseCode >= 400){
            	// 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(
                		connection.getErrorStream(),charset));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                throw new OperationsException("接口返回:" + result);
            }else {
            	throw new OperationsException("接口返回" + responseCode + "错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationsException(e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
	}
	
	/**
	 * http get请求
	 * @param url 请求地址
	 * @param param 请求参数
	 * @return 返回结果
	 */
	public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
