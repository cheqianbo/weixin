package com.onesuo.app.common.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 拦截器
 */
@Slf4j(topic = "http")
public class MyHttpInterceptor implements ClientHttpRequestInterceptor {
    public MyHttpInterceptor() {

    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse clientHttpResponse = execution.execute(request, body);

        //如果是debug,输出返回
        InputStream inputStream = clientHttpResponse.getBody();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer, 0, 1024)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
        log.debug("http, Request: url:{}" ,request.getURI().toURL().toString());
        log.debug("http, Response: {}" , new String(baos.toByteArray(), Charset.forName("UTF-8")));
        PClientHttpResponse clientHttpResponse1 = new PClientHttpResponse();
        clientHttpResponse1.inputStream = is1;
        clientHttpResponse1.statusCode = clientHttpResponse.getRawStatusCode();
        clientHttpResponse1.statusText = clientHttpResponse.getStatusText();
        clientHttpResponse1.headers = clientHttpResponse.getHeaders();
        return clientHttpResponse1;
    }

    public static class PClientHttpResponse extends AbstractClientHttpResponse {
        InputStream inputStream;
        int statusCode;
        String statusText;
        HttpHeaders headers;

        @Override
        public int getRawStatusCode() throws IOException {
            return statusCode;
        }

        @Override
        public String getStatusText() throws IOException {
            return statusText;
        }

        @Override
        public void close() {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        @Override
        public InputStream getBody() throws IOException {
            return inputStream;
        }
    }
}