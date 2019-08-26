package com.zl.study.elasticsearch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 周林
 * @Description 简单Es
 * @email prometheus@noask-ai.com
 * @date 2019/8/12
 */
public class LowLevelRESTClientUtil {
    private RestClient client;
//            = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")).build();
    private String type;

    @Test
    public void test() throws IOException {
        // 发送请求
//        client.performRequest("GET", "/");
        RestClientBuilder http = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"));
        RestClient build = http.build();
        // 执行请求
//        build.performRequest()
        // 执行异步请求
//        build.performRequestAsync();
//        List<Node> nodes = build.getNodes();
//        System.out.println("======" + nodes);

        // 设置一个侦听器，每次节点出现故障时都会收到通知，以防需要采取措施。启用嗅探失败时在内部使用。
//        http.setFailureListener(new RestClient.FailureListener() {
//            @Override
//            public void onFailure(Node node) {
//                // (1)
//            }
//        });

        String jsonString = "{" +
                "\"user\":\"周林\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch-----ZL\"" +
                "}";
        Request request = new Request("GET","/test/_doc/1");
//        request.addParameter("id", "1");
//        request.setJsonEntity(jsonString);
        // 添加请求参数
//        request.addParameter();
//        request.setOptions(RequestOptions.DEFAULT);
//
//        HttpEntity requestEntity = request.getEntity();
//        system("requestEntity", requestEntity);
//        String endpoint = request.getEndpoint();
//        system("endpoint", endpoint);
//        String method = request.getMethod();
//        system("method", method);
//        RequestOptions options = request.getOptions();
//        system("options", options);
//        Map<String, String> parameters = request.getParameters();
//        system("parameters", parameters);

        System.out.println("response===================================================================");
        Response response = build.performRequest(new Request("GET","/test/_doc/1"));
//        system("response", response);
//        Header[] headers = response.getHeaders();
//        system("headers", Arrays.toString(headers));
        HttpEntity responseEntity = response.getEntity();
        system("responseEntity", responseEntity);
//        HttpHost host = response.getHost();
//        system("host", host);
//        RequestLine requestLine = response.getRequestLine();
//        system("requestLine", requestLine);
//        List<String> warnings = response.getWarnings();
//        system("warnings", warnings);
//        StatusLine statusLine = response.getStatusLine();
//        system("statusLine", statusLine);

        InputStream content = responseEntity.getContent();
        system("content", content.toString());

        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(content, StandardCharsets.UTF_8);
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        System.out.println(out);

//        system("read", read);

//        Header contentEncoding = responseEntity.getContentEncoding();
//        system("contentEncoding", contentEncoding);
//        long contentLength = responseEntity.getContentLength();
//        system("contentLength", contentLength);
//        Header contentType = responseEntity.getContentType();
//        system("contentType", contentType);
//        boolean chunked = responseEntity.isChunked();
//        system("chunked", chunked);
//        boolean repeatable = responseEntity.isRepeatable();
//        system("repeatable", repeatable);
//        boolean streaming = responseEntity.isStreaming();
//        system("streaming", streaming);

        build.close();
    }
    @Test
    public void selectById() {
        try {
            // 执行请求
            Response response = client.performRequest(new Request("GET","/test/_doc/1"));
            // 响应
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
//            this.outPutResponseContent(content);
            String s = EntityUtils.toString(response.getEntity());
            this.system("s", s);
//            ByteArrayOutputStream result = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = content.read(buffer)) != -1) {
//                result.write(buffer, 0, length);
//            }
//            system("result", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void add () {
        try {
            String jsonString = "{" +
                    "\"user\":\"周林POST\"," +
                    "\"postDate\":\"2013-01-30\"," +
                    "\"message\":\"trying out Elasticsearch-----ZL2\"" +
                    "}";
            Request request = new Request("POST", "test/_doc/3");
            request.setJsonEntity(jsonString);
            Response response = client.performRequest(request);
            this.outPutResponseContent(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void put () {
        try {
            Request request = new Request("PUT","/test/_doc/2");
            request.addParameter("user", "zl");
            request.addParameter("message", "trying out low leve Elasticsearch");
            Response response = client.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void del () {
        try {
            Request request = new Request(HttpDelete.METHOD_NAME,"/test/_doc/WzZzimwBJe7d3jZ-RXJT");
            client.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void outPutResponseContent (InputStream inputStream) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        system("result===========", result);
    }
    private void system (String oName, Object o) {
        System.out.println(oName + "==================" + o);
//        String simpleName = o.getClass().getSimpleName();
//        System.out.println(o.getClass().getSimpleName() + "==================" + o);
    }

    @Test
    public void testSerach () {
//        client.performRequest();
    }
    /** 获取客户端 **/
    @Before
    public void getClient() throws Exception{
        client =  RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")).build();
        type = "test/_doc";
    }

    /** 关闭客户端 **/
    @After
    public void closeClient() {
        if (this.client != null) {
            try {
                this.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
