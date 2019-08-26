package com.zl.study.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.zl.study.elasticsearch.bean.SearchBean;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/8/14
 */
public class OfficialESMainTest {

    private RestClient client;
    private String type;
    private String search;

    @Test
    public void test() {
//        System.out.println(23*15);
        String sTing = "123";
        System.out.println(sTing);
    }
    @Test
    public void searchALL() {
        try {
            Request request = new Request("GET", type + "/_search/");
            request.addParameter("q", "__id:134608357452419072,134607858137305088");
//            request.setJsonEntity();
            Response response = client.performRequest(request);
            String s = EntityUtils.toString(response.getEntity());
            System.out.println("s=============="+s);
//            response.getEntity().
            // http://192.168.1.23:9101/sense/_doc/_search/?q=unit:%22%E5%9B%BD%E5%AE%B6%E7%A8%8E%E5%8A%A1%E6%80%BB%E5%B1%80%22
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {"source": {"query": {"{{query_type}}": {"name": "{{name}}" }}}, "params": {"query_type": "match_phrase_prefix", "name": "Smith"}}
     *
     * {"source": {"query": {"match":  {"user" : "{{username}}" }}}, "params": {"username": "john"}}
     */
    @Test
    public void searchId() {
        try {
            // + "134608357452419072"
            // 索引的处理 //索引/类型/唯一ID
            /**
             * 创建请求信息 参数(请求方式, 请求地址【索引名称/文档类型/文档唯一编号】) 如:/project/test/1 项目索引/test类型/编号1
             *
             * 增加索引文档方式一   "PUT","/project/test/1"  需要唯一ID
             * 增加索引文档方式二   "POST","/project/test/"  不需要指定唯一ID,会随机生成唯一字符串
             * 查询索引文档方式     "GET","/project/test/1"  根据搜索语法可以进行查询
             */
//            HttpGet.METHOD_NAME
//            HttpPost.METHOD_NAME
//            HttpPut.METHOD_NAME
//            HttpDelete.METHOD_NAME
//            HttpHost.DEFAULT_SCHEME_NAME
            Request request = new Request(HttpPost.METHOD_NAME, search);
//            request.addParameter("", "134608357452419072");
            /**
             * 设置 context内容 两种方式
             * 1. request.setEntity(new NStringEntity("{\"json\":\"text\"}",ContentType.APPLICATION_JSON));
             * 2. request.setJsonEntity("{\"json\":\"text\"}");
             */
            // 查Id
//            request.setJsonEntity("{\"query\":{\"match\":{\"" + "__id" + "\":\"" + 134607885630967808L + "\"}}}");
            // 查Id和Name
            // bool查询采用：匹配越多越好，因此每个match子句的分值会被累加来得到文档最终的_score。匹配两个子句的文档相比那些只匹配一个子句的文档的分值会高一些。
            request.setJsonEntity("{ " +
                    "\"query\": { " +
                        "\"bool\": { " +
                            "\"should\": [ " +
                                "{ \"match\": { \"__id\":\"" + 134607885630967808L + "\"}}," +
                                "{ \"match\": { \"__size\":\"" + "财税〔2016〕129号" + "\"}}" +
                            "]" +
                        "}" +
                    "}" +
                "}");
            Response response = client.performRequest(request);
            String s = EntityUtils.toString(response.getEntity());
            System.out.println("s==============" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void searchTypeName() {
        Request request = new Request(HttpPost.METHOD_NAME, search);
//        request.setJsonEntity("{\"query\":{\"match\":{\"" + "__typeName" + "\":\"" + "法律" + "\"}}}");
        request.setJsonEntity("{\"query\":{\"match\":{\"" + "__no" + "\":\"" + "3233" + "\"}}}");
        commonOut(request);
    }

    @Test
    public void searchPut() {
        Request request = new Request(HttpPut.METHOD_NAME, search);
//        request.setJsonEntity("{\"query\":{\"match\":{\"" + "__typeName" + "\":\"" + "法律" + "\"}}}");
        request.setJsonEntity("{\"query\":{\"match\":{\"" + "__no" + "\":\"" + "3233" + "\"}}}");
        commonOut(request);
    }


    /** 获取客户端 **/
    @Before
    public void getClient() {
//        client =  RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")).build();
//        RestClientBuilder http = RestClient.builder(new HttpHost("192.168.1.23", 9101, "http"));
        client =  RestClient.builder(new HttpHost("192.168.1.23", 9101, "http")).build();
        type = "/sense/_doc/";
        search = type + "_search";
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

    private void commonOut(Request request) {
        try {
            Response response = client.performRequest(request);
            String s = EntityUtils.toString(response.getEntity());
            System.out.println("s==============" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
