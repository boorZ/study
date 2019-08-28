package com.zl.study.elasticsearch;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.elasticsearch.client.Request;
import org.junit.Test;

/**
 * @author 周林
 * @Description 简单Es
 * @email prometheus@noask-ai.com
 * @date 2019/8/12
 */
public class LowLevelRESTClientUtil {

    @Test
    public void selectById() {
        // 执行请求
        Request request = new Request(HttpGet.METHOD_NAME, "/test/_doc/1");
        String dataJsonString = ElasticsearchUtlis.getDataJsonString(request);
        System.out.println(dataJsonString);
    }
    @Test
    public void selectList() {
        // 执行请求
        Request request = new Request(HttpGet.METHOD_NAME, "/test/_search");
        String dataJsonString = ElasticsearchUtlis.getDataJsonString(request);
        System.out.println(dataJsonString);
    }
    @Test
    public void add () {
        String jsonString = "{" +
                "\"user\":\"周林POST\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch-----ZL2\"" +
                "}";
        // 执行请求
        Request request = new Request(HttpPost.METHOD_NAME, "test/_doc/3");
        request.setJsonEntity(jsonString);
        ElasticsearchUtlis.getDataJsonString(request);
    }
    @Test
    public void put () {
        // 执行请求
        Request request = new Request(HttpPut.METHOD_NAME,"/test/_doc/2");
        request.addParameter("user", "zl");
        request.addParameter("message", "trying out low leve Elasticsearch");
        ElasticsearchUtlis.getDataJsonString(request);
    }

    @Test
    public void del () {
        // 执行请求
        Request request = new Request(HttpDelete.METHOD_NAME, "/test/_doc/1");
        ElasticsearchUtlis.getDataJsonString(request);
    }
}
