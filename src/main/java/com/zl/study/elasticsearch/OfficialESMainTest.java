package com.zl.study.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.zl.study.elasticsearch.bean.SearchBean;
import org.apache.http.HttpHost;
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

    @Test
    public void test() {
        System.out.println(23*15);
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
            Request request = new Request("POST", type+"_search?pretty");
//            request.addParameter("", "134608357452419072");
            SearchBean searchBean = new SearchBean();
            searchBean.setId("134608331368042496");
            /**
             * 设置 context内容 两种方式
             * 1. request.setEntity(new NStringEntity("{\"json\":\"text\"}",ContentType.APPLICATION_JSON));
             * 2. request.setJsonEntity("{\"json\":\"text\"}");
             */
            request.setJsonEntity("{\"id\":134608357452419072}");
//            request.setJsonEntity(searchBean.toString());
            Response response = client.performRequest(request);
            String s = EntityUtils.toString(response.getEntity());
            System.out.println("s==============" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void searchTypeName() {
        Request request = new Request("GET", type);

    }


    /** 获取客户端 **/
    @Before
    public void getClient() throws Exception{
//        client =  RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")).build();
        RestClientBuilder http = RestClient.builder(new HttpHost("192.168.1.23", 9101, "http"));
        client =  RestClient.builder(new HttpHost("192.168.1.23", 9101, "http")).build();
        type = "/sense/_doc/";
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
