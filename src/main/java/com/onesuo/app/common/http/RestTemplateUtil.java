package com.onesuo.app.common.http;

import com.onesuo.app.common.utils.BizUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j(topic = "http")
public class RestTemplateUtil {
    //默认http请求读取时间
    public static Integer DEFAULT_HTTP_READ_TIMEOUT = 30000;
    //默认http请求连接时间
    public static Integer DEFAULT_HTTP_CONNECT_TIMEOUT = 60000;

    private static RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(DEFAULT_HTTP_READ_TIMEOUT);
        simpleClientHttpRequestFactory.setConnectTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
        restTemplate.setRequestFactory(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.getInterceptors().add(new MyHttpInterceptor());
    }

    /**
     * get
     * @param url
     * @param t
     * @param httpHeaders
     * @param <T>
     * @return
     */
    public static <T> T doGet(String url, Class<T> t, HttpHeaders httpHeaders) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(httpHeaders);
        log.info("get调用{}", url);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, t).getBody();
    }

    public static <T> T doGet(String url, Class<T> t) {
        return doGet(url, t, null);
    }

    /**
     * obj get
     * @param url
     * @param t
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T doGet(String url, Class<T> t, Object obj){
        url = BizUtils.prepareGetUrl(obj, url);
        HttpEntity<Object> requestEntity = new HttpEntity<>(obj, new HttpHeaders());
        log.info("get调用{}", url);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, t).getBody();
    }

    /**
     * post
     * @param url
     * @param t
     * @param httpHeaders
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, Class<T> t, HttpHeaders httpHeaders, MultiValueMap<String, Object> params) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, httpHeaders);
        log.info("post调用{}", url);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, t).getBody();
    }

    /**
     * post
     * @param url
     * @param t
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, Class<T> t, MultiValueMap<String, Object> params) {
        log.info("post调用{}", url);
        return doPost(url, t, new HttpHeaders(), params);
    }

    /**
     * post对象
     * @param url
     * @param t
     * @param httpHeaders
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T doPostObject(String url, Class<T> t, HttpHeaders httpHeaders, Object obj) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(obj, httpHeaders);
        log.info("post调用{}", url);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, t).getBody();
    }

    /**
     * post对象
     * @param url
     * @param t
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T doPostObject(String url, Class<T> t, Object obj) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(obj, new HttpHeaders());
        log.info("post调用{}", url);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, t).getBody();
    }

    /**
     * put对象
     * @param url
     * @param t
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T doPutObject(String url, Class<T> t, Object obj){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(obj, headers);
        log.info("put调用{}", url);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, t).getBody();
    }
}