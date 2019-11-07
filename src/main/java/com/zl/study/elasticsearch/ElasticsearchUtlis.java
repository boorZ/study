package com.zl.study.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.exception.DiebuIllegalArgumentException;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.Assert;

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
        return RestClient.builder(new HttpHost("127.0.0.1", 9101, "http"));
    }

    // create：zhoulin 2019/08/22
    public static RestClient getClient () {
        return getClientBuilder().build();
    }

    // create zhoulin 2019/10/17
    public static RestHighLevelClient getRestHighLevelClient () {
        return new RestHighLevelClient(getClientBuilder());
    }

    /**
     * 将ES返回值以Json字符串格式返回
     * @param request 请求
     * @return 响应Json字符串
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

    public static SearchResponse getSearchResponse (String indice, SearchSourceBuilder dsl) {
        // 获取高级客户端
        RestHighLevelClient client = getRestHighLevelClient();
        try {
            // 编写DSL语句
            SearchRequest searchRequest = new SearchRequest(indice);
            // 获取DSL
            searchRequest.source(dsl);
            // 执行DSL
            return client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new DiebuIllegalArgumentException("执行失败");
    }

    /**
     * 将ES返回值以Json返回
     * @param key 字段名
     * @param value 字段值
     * @param endpoint 请求Url
     * @param precise true:精确 false 模糊
     * @return 响应JSONObjcet
     */
    // create：zhoulin 2019/08/27
    public static JSONObject getDataJson (Object key, Object value, String endpoint, boolean precise) {
        return commonQuery(key, value, endpoint, precise);
    }

    /**
     * 对单个字段进行校验
     * @param key 字段名
     * @param value 字段值
     * @param endpoint 请求Url
     * @param precise true:精确 false 模糊
     */
    // create：zhoulin 2019/08/22
    public static void judgeExist (Object key, Object value, String endpoint, boolean precise) {
        Integer count = getAmount(key, value, endpoint, precise);
        Assert.state(count != 0, "没有该文件");
    }

    /**
     * 查询该字段对应的数量
     * @param key 字段名
     * @param value 字段值
     * @param endpoint 请求Url
     * @param precise true:精确 false 模糊
     */
    // create：zhoulin 2019/08/22
    public static Integer getAmount (Object key, Object value, String endpoint, boolean precise) {
        JSONObject hitsList = commonQuery(key, value, endpoint, precise);
        JSONObject totalList = hitsList.getJSONObject("total");
        return totalList.getInteger("value");
    }

    /**
     * 公共查询代码
     * @param key 字段名
     * @param value 字段值
     * @param endpoint 请求Url
     * @param precise true:精确 false 模糊
     * @return 响应JSONObjcet
     */
    // create：zhoulin 2019/08/27
    private static JSONObject commonQuery (Object key, Object value, String endpoint, boolean precise) {
        endpoint = endpoint == null ? "sense_4" : endpoint;
        Request request = new Request(HttpGet.METHOD_NAME, endpoint + "/_search");
        String jsonEntity = precise ?
                "{\"query\": {\"term\": {\"" + key + "\": \"" + value + "\"}}}"
                :
                "{\"query\": {\"match\": {\"" + key + "\": \"" + value + "\"}}}";
        request.setJsonEntity(jsonEntity);
        String dataJsonString = ElasticsearchUtlis.getDataJsonString(request);
        JSONObject jsonObject = JSON.parseObject(dataJsonString);
        JSONObject hits = jsonObject.getJSONObject("hits");
        return jsonObject.getJSONObject("hits");
    }

}
