package com.zl.study.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

/**
 * @author 周林
 * @Description 全文检索工具
 * @email prometheus@noask-ai.com
 * @date 2019/8/20
 */
public class ElasticsearchUtlis {

    // create：zhoulin 2019/08/22
    public static RestClientBuilder getClientBuilder () {
        return RestClient.builder(new HttpHost("192.168.1.23", 9101, "http"));
    }

    // create：zhoulin 2019/08/22
    public static RestClient getClient () {
        return getClientBuilder().build();
    }

    /**
     * 将ES返回值以Json字符串格式返回
     * @param request 请求
     * @return Json字符串
     */
    // create：zhoulin 2019/08/22
    public static String getDataJsonString (Request request) {
        RestClient client = getClient();
        String jsonString = null;
        try {
            Response response = client.performRequest(request);
            jsonString = EntityUtils.toString(response.getEntity());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     * 对单个字段进行校验
     * @param key 字段名
     * @param value 字段值
     */
    // create：zhoulin 2019/08/22
    public static void judgeExist (Object key, Object value, String endpoint) {
        Integer count = getAmount(key, value, endpoint);
//        Assert.state(count != 0, "没有该文件");
    }

    /**
     * 查询该字段对应的数量
     * @param key 字段名
     * @param value 字段值
     */
    // create：zhoulin 2019/08/22
    public static Integer getAmount (Object key, Object value, String endpoint) {
        endpoint = endpoint == null ? "sense_4" : endpoint;
        Request request = new Request(HttpGet.METHOD_NAME, endpoint + "/_search");
        request.setJsonEntity("{\"query\": {\"bool\": {\"must\": [{\"match\": {\"" + key +
                "\": \"" + value + "\"}}]}}, \"size\": 0}");
        String dataJsonString = getDataJsonString(request);
        JSONObject jsonObject = JSON.parseObject(dataJsonString);
        JSONObject hitsList = jsonObject.getJSONObject("hits");
        JSONObject totalList = hitsList.getJSONObject("total");
        return totalList.getInteger("value");
    }

}
