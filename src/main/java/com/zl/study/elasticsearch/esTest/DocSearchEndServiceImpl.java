package com.zl.study.elasticsearch.esTest;

import com.alibaba.fastjson.JSONObject;
import com.zl.study.elasticsearch.entity.DocSearchLabelDTO;
import com.zl.study.elasticsearch.entity.DocSearchLabelTypeDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/10/21
 */
public class DocSearchEndServiceImpl {
    RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

    @Test
    public void te () {
        System.out.println(StringUtils.isBlank("    123"));
    }
    @Test
    public void selectList () {
        try {
            // 编写DSL语句
            SearchRequest searchRequest = new SearchRequest("sense");
            TermsAggregationBuilder groupLabelId = AggregationBuilders.terms("group_label_id");
            groupLabelId.field("labelList.labelId");
            groupLabelId.size(100000);

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.aggregation(groupLabelId);

            searchRequest.source(sourceBuilder);
            // 获取高级客户端
            // 执行DSL
            SearchResponse searchData = client.search(searchRequest, RequestOptions.DEFAULT);

            Terms labelIdTerms = searchData.getAggregations().get("group_label_id");
            // 获取所有标签Id
            List<Long> labelIdList = labelIdTerms.getBuckets().stream().map(m -> m.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<DocFullLabelDTO> labelBeanAll = getLabelBeanAll(labelIdList);

            Map<Long, List<DocFullLabelDTO>> labelBeanMap = labelBeanAll.stream().collect(Collectors.groupingBy(DocFullLabelDTO::getLabelTypeId));

            List<DocSearchLabelTypeDTO> labelTypeList = new ArrayList<>();
            for (Map.Entry<Long, List<DocFullLabelDTO>> map : labelBeanMap.entrySet()) {
                List<DocFullLabelDTO> value = map.getValue();
                String docTypeName = "";
                Long docTypeId = 0L;
                List<DocSearchLabelDTO> labelList = new ArrayList<>();
                for (DocFullLabelDTO i : value) {
                    docTypeName = i.getLabelTypeName();
                    docTypeId = i.getLabelTypeId();
                    labelList.add(new DocSearchLabelDTO(i.getLabelId(), i.getLabelName()));
                }
                DocSearchLabelTypeDTO fullLabel = new DocSearchLabelTypeDTO(docTypeId, docTypeName, labelList);
                labelTypeList.add(fullLabel);
            }

            System.out.println();

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<DocFullLabelDTO> getLabelBeanAll (List<Long> labelIdList) {
        // DSL
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("labelId", labelIdList);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(termsQueryBuilder);
        sourceBuilder.size(labelIdList.size());

        SearchRequest searchRequest = new SearchRequest("sense_label");
        searchRequest.source(sourceBuilder);
        List<DocFullLabelDTO> fullLabelList = new ArrayList<>();
        try {
            SearchResponse searchLabel = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = searchLabel.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                JSONObject jsonObject = new JSONObject(sourceAsMap);
                DocFullLabelDTO labelDTO = jsonObject.toJavaObject(DocFullLabelDTO.class);
                fullLabelList.add(labelDTO);
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullLabelList;
    }
}
