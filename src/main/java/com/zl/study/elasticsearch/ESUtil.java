package com.zl.study.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class ESUtil {
    private RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));;

    /**
     * 查询
     */
    @Test
    public void selectlist () {
        try {
            // 查询多个文档库，其中多个文档库名之间用逗号隔开
//            SearchRequest searchRequest = new SearchRequest("posts2","posts", "posts2", "posts1");

            SearchRequest searchRequest = new SearchRequest("test");
            // 搜索条件
//            searchRequest.source(searchSourceBuilder);
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("search==========" + search);

            SearchHits searchHits = search.getHits();
            for (SearchHit hit : searchHits) {
                System.out.println("hit==========" + hit);
            }

//            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
//            SearchRequest searchRequest = new SearchRequest("test");
//            searchRequest.scroll(scroll);
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            searchSourceBuilder.query(matchQuery("title", "Elasticsearch"));
//            searchRequest.source(searchSourceBuilder);
//
//            //通过发送初始搜索请求来初始化搜索上下文
//            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//            String scrollId = searchResponse.getScrollId();
//            SearchHit[] searchHits = searchResponse.getHits().getHits();
//
//            //通过循环调用搜索滚动api来检索所有搜索命中，直到没有文档返回
//            while (searchHits != null && searchHits.length > 0) {
//                //处理返回的搜索结果
//
//                //创建一个新的搜索滚动请求，保存最后返回的滚动标识符和滚动间隔
//                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//                scrollRequest.scroll(scroll);
//                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
//                scrollId = searchResponse.getScrollId();
//                searchHits = searchResponse.getHits().getHits();
//            }
//            //完成滚动后，清除滚动上下文
//            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
//            clearScrollRequest.addScrollId(scrollId);
//            ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
//            System.out.println("clearScrollResponse==========" + clearScrollResponse);
//            RestStatus status = clearScrollResponse.status();
//            System.out.println("status==========" + status);
//            int numFreed = clearScrollResponse.getNumFreed();
//            System.out.println("numFreed==========" + numFreed);
//            boolean succeeded = clearScrollResponse.isSucceeded();
//            System.out.println("succeeded======================================"+succeeded);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private QueryBuilder matchQuery(String title, String elasticsearch) {
        return null;
    }

    /**
     * 查询
     */
    @Test
    public void selectById () {
        //查
        try {
            GetRequest getRequest = new GetRequest(
                    "test", //索引名称
                    "1");   //文档id
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            System.out.println("getResponse==========" + getResponse);
            String index = getResponse.getIndex();
            System.out.println("index==========" + index);
            String id = getResponse.getId();
            System.out.println("id==========" + id);
            if (getResponse.isExists()) {
                long version = getResponse.getVersion();
                System.out.println("version==========" + version);
                //以字符串形式检索文档
                String sourceAsString = getResponse.getSourceAsString();
                System.out.println("sourceAsString==========" + sourceAsString);
                //以Map<String, Object>的形式检索文档
                Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
                System.out.println("sourceAsMap==========" + sourceAsMap);
                //以byte[]形式检索文档
                byte[] sourceAsBytes = getResponse.getSourceAsBytes();
                System.out.println("sourceAsBytes==========" + Arrays.toString(sourceAsBytes));
            } else {
                System.out.println("失败========================");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除
     */
    @Test
    public void del () {
        try {
            DeleteRequest request = new DeleteRequest(
                    "posts",    //索引
                    "1");       //文档id
            DeleteResponse deleteResponse = client.delete(
                    request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改
     */
//    @Test
//    public void put () {
//        try {
//            //索引
//            UpdateRequest  request = new UpdateRequest("test");
//            //文档id
//            request.id("1");
//            String jsonString = "{" +
//                    "user:周林(修改成功)," +
//                    "postDate:2013-01-30," +
//                    "message:trying out Elasticsearch-----ZL" +
//                    "}";
//            request.doc(jsonString, XContentType.JSON);
//
//            UpdateResponse updateResponse = client.update(
//                    request, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 添加
     */
    @Test
    public void add () {
        try {
            //索引
            IndexRequest request = new IndexRequest("test");
            //文档id
            request.id("2");
            String jsonString = "{" +
                    "\"user\":\"周林\"," +
                    "\"postDate\":\"2013-01-30\"," +
                    "\"message\":\"trying out Elasticsearch-----ZL\"" +
                    "}";
            //以字符串形式提供的文档源
            request.source(jsonString, XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 判断是否存在该文档
     */
    @Test
    public void judge(){
        GetRequest getRequest = new GetRequest(
                "test", //索引
                "1");    //文档id
        getRequest.fetchSourceContext(new FetchSourceContext(false)); //禁用fetching _source.
        getRequest.storedFields("_none_");
        boolean exists = false;
        try {
            exists = client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("==============================="+exists);
    }
//    /** 获取客户端 **/
//    @Before
//    public void getClient() throws Exception{
//        RestClientBuilder http = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"));
//        client = new RestHighLevelClient(http);
//    }
//
//    /** 关闭客户端 **/
//    @After
//    public void closeClient() {
//        if (this.client != null) {
//            try {
//                this.client.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
