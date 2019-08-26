package com.zl.study.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.study.elasticsearch.dto.ElasticsearchDTO;
import com.zl.study.elasticsearch.dto.ElasticsearchLabelDTO;
import com.zl.study.elasticsearch.dto.ElasticsearchSerachCountryDTO;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author 周林
 * @Description 入口
 * @email prometheus@noask-ai.com
 * @date 2019/8/20
 */
public class mainElasticsearch {
    @Test
    public void searchDel(){
        List<String> idList = new ArrayList<>();
        idList.add("2620190548132118886");
        idList.add("567200086113602897");
        idList.add("5359168502427305663");
        idList.add("-9125671765910075038");
        idList.add("-154729782061481567");
        idList.add("-2404086479822436911");
        idList.forEach(id -> {
//            String name = "8971318300537144446";
            RestClient client = RestClient.builder(new HttpHost("192.168.1.23", 9101, "http")).build();
            Request request = new Request(HttpDelete.METHOD_NAME, "sense_4_count/_doc/" + id);
            try {
                client.performRequest(request);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Test
    public void getAll(){
//        Request request = new Request(HttpGet.METHOD_NAME, "sense_4_count/_search/");
//        request.setJsonEntity("" +
//                "{" +
//                "  \"size\": 0, " +
//                "  \"query\": {" +
//                "    \"constant_score\": {" +
//                "      \"filter\": {" +
//                "        \"exists\": {" +
//                "          \"field\": \"labelTypeName\"" +
//                "        }" +
//                "      }" +
//                "    }" +
//                "  }," +
//                "  \"aggs\": {" +
//                "    \"group_by_label\": {" +
//                "      \"terms\": {" +
//                "        \"field\": \"labelTypeName.keyword\"" +
//                "      }" +
//                "    }" +
//                "  }" +
//                "}");
//        String dataJsonString = ElasticsearchUtlis.getDataJsonString(request);
//        JSONObject jsonObject = JSON.parseObject(dataJsonString);
//        JSONArray aggregations = jsonObject.getJSONObject("aggregations").getJSONObject("group_by_label").getJSONArray("buckets");
//        for (Object o : aggregations.toArray()) {
//            JSONObject jsonObject1 = JSON.parseObject(o.toString());
//            System.out.println("=======key:" + jsonObject1.get("key")+ ", count:" + jsonObject1.get("doc_count"));
//        }



        Request request = new Request(HttpGet.METHOD_NAME, "sense_4_count/_search/");
        request.setJsonEntity(
                "{" +
                "  \"size\": 0, " +
                "  \"query\": {" +
                "    \"term\": {" +
                "      \"labelTypeName.keyword\": \"标签类型3\"" +
                "    }" +
                "  }," +
                "  \"aggs\": {" +
                "    \"group_by_label\": {" +
                "      \"terms\": {" +
                "        \"field\": \"labelName.keyword\"" +
                "      }" +
                "    }" +
                "  }" +
                "}");
        String dataJsonString = ElasticsearchUtlis.getDataJsonString(request);
        JSONObject jsonObject = JSON.parseObject(dataJsonString);
        JSONArray jsonArray = jsonObject.getJSONObject("aggregations").getJSONObject("group_by_label").getJSONArray("buckets");
        if (jsonArray.size() != 0) {
            for (Object o : jsonArray.toArray()) {
                JSONObject jsonObject1 = JSON.parseObject(o.toString());
                System.out.println("===============" + JSON.parseObject(o.toString()));
            }
        }
    }

    @Test
    public void searchAdd(){
        String name = "财政部、国家税务总局关于进一步提高部分货物出口退税率的通知";
//        RestClient client = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")).build();
        RestClient client = RestClient.builder(new HttpHost("192.168.1.23", 9101, "http")).build();
        Request request = new Request(HttpPost.METHOD_NAME, "sense_4_count/_doc/" + name);
        String objectJson = JSONObject.toJSONString(new ElasticsearchSerachCountryDTO(UUID.randomUUID().getMostSignificantBits(), name, 2L));
        request.setJsonEntity(objectJson);
        try {
            client.performRequest(request);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void searchCount(){
        String name = "周";
        RestClient client = RestClient.builder(new HttpHost("192.168.1.23", 9101, "http")).build();
        Request request = new Request(HttpGet.METHOD_NAME, "sense_4_count/_doc/" + name);
        try {
            Response response = client.performRequest(request);
            String s = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSON.parseObject(s);
            System.out.println(s);
            System.out.println("==============="+ jsonObject.getJSONObject("_source"));
            System.out.println("==============="+ jsonObject.getJSONObject("_source").get("heat"));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void add () {
        Long docId = 1L;
        while(docId < 10000){
            RestClient client = ElasticsearchUtlis.getClient();
            Request request = new Request(HttpPost.METHOD_NAME, "test/_doc/" + docId);
            String objectJson = JSONObject.toJSONString(this.EsPojo(docId));
            request.setJsonEntity(objectJson);
            try {
                client.performRequest(request);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            docId ++;
        }
    }

    @Test
    public void addExtends () {
        Long docId = 2L;
        while(docId < 100){
            RestClient client = ElasticsearchUtlis.getClient();
            Request request = new Request(HttpPost.METHOD_NAME, "zl/_doc/" + docId);
            request.setJsonEntity("");
            try {
                client.performRequest(request);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            docId ++;
        }
    }
    @Test
    public void id() {
        RestClient client = ElasticsearchUtlis.getClient();
        Request request = new Request(HttpPost.METHOD_NAME, "sense_4/_search");
        request.setJsonEntity("{" +
                "\"query\": {" +
                "\"bool\": {" +
                "\"must\": [" +
                "{\"match\": {" +
                    "\""+"_id"+"\": \""+"156712814352797734"+"\"" +
                "}}" +
                "]" +
                "}" +
                "}, \"size\": 0" +
                "}");
        try {
            Response response = client.performRequest(request);
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            JSONObject hitsList = jsonObject.getJSONObject("hits");
            JSONObject totalList = hitsList.getJSONObject("total");
            Integer value = totalList.getInteger("value");
            System.out.println(totalList);
            System.out.println(value);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test() {
        Long labelId = 143156564319014912L;
        Integer pageIndex = 1, pageSize = 5;
        String serachValue = "增值税";
        Boolean timePositive = true;
        RestClient client = ElasticsearchUtlis.getClient();
        Request request = new Request(HttpPost.METHOD_NAME, "sense_4/_search");

        request.setJsonEntity("{" +
                    "\"from\": 0, \"size\": 3}");
        try {
            Response response = client.performRequest(request);
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            InputStream content = response.getEntity().getContent();
//            System.out.println(jsonObject);
            System.out.println("=============================================");
//            String hits = jsonObject.getString("hits");
            JSONObject hitsList = jsonObject.getJSONObject("hits");
            JSONArray hits = hitsList.getJSONArray("hits");
            Object[] objects = hits.toArray();
            new ArrayList<>(Arrays.asList(objects));
            List<String> ResultList = new ArrayList<>();
            for (Object object : objects) {
                JSONObject jsonObject1 = JSON.parseObject(object.toString());
                ResultList.add(jsonObject1.getString("_source"));
            }
//            Object[] objects = hits1.toArray();
//            System.out.println(hits);
//            JSONObject totalList = hits.getJSONObject("total");
//            Integer total = totalList.getInteger("value");
//            System.out.println("总页数：" + total/3);
//            System.out.println("总条数：" + total);
            System.out.println("=============================================");
//            String s = EntityUtils.toString(response.getEntity());
//            System.out.println(s);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectLabelId () {
        Long labelId = 143156564319014912L;
        Integer pageIndex = 1, pageSize = 5;
        RestClient client = ElasticsearchUtlis.getClient();
        Request request = new Request(HttpPost.METHOD_NAME, "sense/_search");
//        request.setJsonEntity("" +
//                "{\"query\": {" +
//                    "\"bool\": {" +
//                        "\"should\" :[" +
//                            "{\"match\": { \"labelList.labelId\":\"" + labelId + "\"}}" +
//                        "]" +
////                ", \"minimum_should_match\": \"50%\" " +
//                    "}}," +
//                    "\"from\": \"" + pageIndex + "\", \"size\": \"" + pageSize + "\"" +
////                    "\"sort\": [" +
////                        "{\"date\": {\"order\":\"desc\"}}" +
////                    "]" +
//                "}");

        request.setJsonEntity(
                "GET sense/_search" +
                        "{" +
                        "  \"from\": 1," +
                        "  \"size\": 5," +
                        "  \"query\": {" +
                        "    \"bool\": {" +
                        "      \"must\": [" +
                        "        {\"term\": {" +
                        "          \"__no\": \"2488\"" +
                        "        }}" +
                        "      ]," +
                        "      \"should\": [" +
                        "        " +
                        "        {\"match\": {" +
                        "          \"__title\": \"财政部\"" +
                        "        }}," +
                        "        {\"match\": {" +
                        "          \"__text\": \"财政部\"" +
                        "        }}," +
                        "        {\"match\": {" +
                        "          \"__info\": \"财政部\"" +
                        "        }}," +
                        "        {\"match\": {" +
                        "          \"__releaseId\": \"134613061624401920\"" +
                        "        }}" +
                        "      ]" +
                        "    }" +
                        "  }," +
                        "  \"sort\": [" +
                        "    {" +
                        "      \"_score\": {" +
                        "        \"order\": \"desc\"" +
                        "      }" +
                        "    }" +
                        "  ]" +
                        "}");
        try {
            Response response = client.performRequest(request);
            String s = EntityUtils.toString(response.getEntity());
            System.out.println(s);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ElasticsearchDTO EsPojo (Long docId) {
        ElasticsearchDTO elasticsearchDto = new ElasticsearchDTO();
        List<ElasticsearchLabelDTO> labelList = new ArrayList<>();
        labelList.add(new ElasticsearchLabelDTO(143156564319014912L, "测试标签" + docId));
        labelList.add(new ElasticsearchLabelDTO(149253152934203393L, "测试标签" + docId + 1));
        elasticsearchDto.setLabelList(labelList);

        elasticsearchDto.setId(docId);
        elasticsearchDto.setDocNumber((docId * 2) + "");
        elasticsearchDto.setDocName("======================================测试文档名称" + docId + "======================================");
        elasticsearchDto.setDocTitle("测试标题名称" + docId);
        elasticsearchDto.setDocInfo("测试简介名称" + docId);
        elasticsearchDto.setDocDigest("测试摘要名称" + docId);
        elasticsearchDto.setDocContent("测试内容" + docId);
        elasticsearchDto.setDocStatus("文档状态");
        elasticsearchDto.setDocTypeName("测试文档分类名称" + docId);
        elasticsearchDto.setDocVersionId((docId * 3) + "");
        elasticsearchDto.setDocWritNo(docId + "");
        elasticsearchDto.setDocDispatchUnit("测试发文单位" + docId);
        elasticsearchDto.setDocDispatchTime(System.currentTimeMillis() + "");
        elasticsearchDto.setDocDisabledStatus("全文有效");
        elasticsearchDto.setDocDisabledTime(System.currentTimeMillis() + "");

        elasticsearchDto.setPageView(docId + 1 + "");
        elasticsearchDto.setClickView(docId + 2 + "");
        elasticsearchDto.setHeat(docId + 3 + "");
        elasticsearchDto.setSortField(docId + 4 + "");

        return elasticsearchDto;
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
        System.out.println("result===========" + result);
    }
}
