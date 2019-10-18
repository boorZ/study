package com.zl.study.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.study.elasticsearch.entity.DocFullLabelDTO;
import com.zl.study.elasticsearch.entity.DocSearchDTO;
import com.zl.study.elasticsearch.entity.DocSearchLabelDTO;
import com.zl.study.elasticsearch.entity.DocSearchLabelTypeDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.WeightBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 周林
 * @Description DSL查询生成器
 * @email prometheus@noask-ai.com
 * @date 2019/10/15
 */
public class QueryBuildersUtils {

    @Test
    public void test() {
        SearchSourceBuilder dsl = getDSL(null, "国家", null, 0, 1, 10);
        SearchRequest searchRequest = new SearchRequest("sense");
        searchRequest.source(dsl);
        RestHighLevelClient restHighLevelClient = getRestHighLevelClient();
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            TotalHits totalHits = search.getHits().getTotalHits();
            long value = totalHits.value;
            SearchHit[] hits = search.getHits().getHits();
            List<DocFullLabelDTO> labelList = new ArrayList<>();
            for (SearchHit hit : hits) {
                // source
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                JSONObject jsonObject = new JSONObject(sourceAsMap);
                DocSearchDTO docSearchDTO = jsonObject.toJavaObject(DocSearchDTO.class);

                // highlight
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField docName = highlightFields.get("docName");
                Text[] docNameList = docName.getFragments();
                HighlightField fullText = highlightFields.get("fullText");
                Text[] fullTextList = fullText.getFragments();

                if (docNameList.length >= 1 && StringUtils.isNotEmpty(docNameList[0].toString())) {
                    docSearchDTO.setDocName(docNameList[0].toString());
                }
                if (fullTextList.length >= 1 && StringUtils.isNotEmpty(fullTextList[0].toString())) {
                    docSearchDTO.setDocInfo(fullTextList[0].toString());
                }

                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RestClientBuilder getClientBuilder () {
        return RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"));
    }
    private static RestHighLevelClient getRestHighLevelClient () {
        return new RestHighLevelClient(getClientBuilder());
    }
    public static RestClient getClient () {
        return getClientBuilder().build();
    }

    public static void getBulkRequest(BulkRequest bulkRequest, String docId, String docName, List<String> docNameSuggest) {
        String index = "sense_suggest";
        bulkRequest.add(new IndexRequest(index).id(docId)
                .source(XContentType.JSON,"docId", docId, "docName", docName, "docNameSuggest", docNameSuggest));
    }
    public static void addSuggest (BulkRequest bulkRequest) {
        try {
            RestHighLevelClient client = getRestHighLevelClient();
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void suggest(){
        // 编写DSL
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // suggest
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        // prefix completion
        CompletionSuggestionBuilder docNameSuggest = SuggestBuilders.completionSuggestion("docNameSuggest").skipDuplicates(true);
        docNameSuggest.prefix("归");
        suggestBuilder.addSuggestion("doc_name_suggest", docNameSuggest);

        searchSourceBuilder.suggest(suggestBuilder);

        SearchRequest searchRequest = new SearchRequest("sense_suggest");
        searchRequest.source(searchSourceBuilder);

        RestHighLevelClient restHighLevelClient = getRestHighLevelClient();
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Suggest suggest = search.getSuggest();
            Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> doc_name_suggest = suggest.getSuggestion("doc_name_suggest");
            for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> entry : doc_name_suggest.getEntries()) {
                Text text1 = entry.getText();
                for (Suggest.Suggestion.Entry.Option option : entry.getOptions()) {
                    Text text = option.getText();
                    System.out.println();
                }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void selectAll(){
        // DSL
        // 聚合
        TermsAggregationBuilder aggsTermsLabelTypeId = AggregationBuilders.terms("group_label_type_id");
        aggsTermsLabelTypeId.field("labelList.labelId");
        aggsTermsLabelTypeId.size(10000);

        TopHitsAggregationBuilder top_labelList = AggregationBuilders.topHits("top_labelList");
        top_labelList.size(1);
        String[] list = new String[4];
        list[0] = "labelList.labelId";
        list[1] = "labelList.labelName";
        list[2] = "labelList.labelTypeId";
        list[3] = "labelList.labelTypeName";
        top_labelList.fetchSource(list, null);
        aggsTermsLabelTypeId.subAggregation(top_labelList);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(aggsTermsLabelTypeId);

        SearchRequest searchRequest = new SearchRequest("sense_4");
        searchRequest.source(sourceBuilder);

        RestHighLevelClient restHighLevelClient = getRestHighLevelClient();
        // 标签集
        List<DocFullLabelDTO> labelFullList = new ArrayList<>();
        try {
            String s = searchRequest.toString();
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // label
            Aggregations aggregations = search.getAggregations();


//            SearchHit[] hits = search.getHits().getHits();
//            for (SearchHit hit : hits) {
//                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//                JSONObject jsonObject = new JSONObject(sourceAsMap);
//                DocSearchDTO docSearchDTO = jsonObject.toJavaObject(DocSearchDTO.class);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取标签
        List<DocSearchLabelTypeDTO> lists = getLists(labelFullList);
    }

    /**
     * 获取标签
     * @param labelFullList
     * @return
     */
    private List<DocSearchLabelTypeDTO> getLists (List<DocFullLabelDTO> labelFullList) {
        Map<Long, List<DocFullLabelDTO>> labelsMap = labelFullList.stream().collect(Collectors.groupingBy(DocFullLabelDTO::getLabelTypeId));
        List<DocSearchLabelTypeDTO> resultLabelList = new ArrayList<>();
//        DocSearchLabelDTO
        for (Map.Entry<Long, List<DocFullLabelDTO>> map : labelsMap.entrySet()) {
            DocSearchLabelTypeDTO labelType = new DocSearchLabelTypeDTO();
            List<DocSearchLabelDTO> labelList = new ArrayList<>();
            List<DocFullLabelDTO> value = map.getValue();


            Set<Long> ids = value.stream().map(DocFullLabelDTO::getLabelId).collect(Collectors.toSet());
            //过滤重复内容
            if (ids.size() == 2){
                System.out.println();
            }
            if (ids.size() < value.size()) {
                List<DocFullLabelDTO> collect = value.stream().filter(v -> value.contains(v.getLabelId())).collect(Collectors.toList());
                System.out.println();
            }
//             value.stream().filter(e -> ids.contains(e.getIdCard())).collect(Collectors.toList());
            System.out.println();
            for (DocFullLabelDTO fullLabelDTO : value) {
                // 获取标签 Id与Name
                DocSearchLabelDTO docSearchLabelDTO = new DocSearchLabelDTO(fullLabelDTO.getLabelId(), fullLabelDTO.getLabelName());
                labelList.add(docSearchLabelDTO);
                // 获取标签分类 Id与Name
                labelType.setLabelTypeId(fullLabelDTO.getLabelTypeId());
                labelType.setLabelTypeName(fullLabelDTO.getLabelTypeName());
            }
            labelList = labelList.stream().distinct().collect(Collectors.toList());
            labelType.setLabelList(labelList);
            resultLabelList.add(labelType);
        }
        return null;

    }

    /**
     * 获取DSLJson语句
     * @param labels 标签IdList
     * @param searchValue 搜索值
     * @param validStatus 有效状态
     * @param collation 排序规则
     * @param pageIndex 当前页
     * @param pageSize 页大小
     * @return DSL查询语句
     */
    // create zhoulin 2019/08/22
    private static SearchSourceBuilder getDSL(String labels, String searchValue, String validStatus, Integer collation, Integer pageIndex, Integer pageSize) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from((pageIndex-1) * pageSize);
        sourceBuilder.size(pageSize);
        // 获取DSL核心Query
        getDocBoolQuery(labels, searchValue, validStatus, sourceBuilder);
        /* sort */
        if (collation != null && (collation == 1 || collation == 2)) {
            sourceBuilder.sort("dispatchTime", collation == 1 ? SortOrder.ASC : SortOrder.DESC);
        }
        /* highlight */
        if (StringUtils.isNotEmpty(searchValue)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("fullText", 150, 1);
            highlightBuilder.field("docName");
            highlightBuilder.field("writNo");
            sourceBuilder.highlighter(highlightBuilder);
        }
        return sourceBuilder;
    }

    /**
     * 获取DSL核心Query
     * @param labels
     * @param searchValue
     * @param validStatus
     * @param sourceBuilder
     */
    // create zhoulin 2019/09/12
    private static void getDocBoolQuery(String labels, String searchValue, String validStatus, SearchSourceBuilder sourceBuilder) {
        /* bool */
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        /* bool -- 有效状态 */
        if (StringUtils.isNotEmpty(validStatus)) {
            // bool -- 有效状态
            BoolQueryBuilder boolEffectiveStatusQuery = QueryBuilders.boolQuery();
            boolEffectiveStatusQuery.must(QueryBuilders.termsQuery("effectiveStatus.keyword", "全文失效"));
            boolQuery.must(boolEffectiveStatusQuery);
        }

        /* bool -- 标签s */
        JSONArray labelsArray = StringUtils.isNotEmpty(labels) ? JSON.parseArray(labels) : null;
        if (CollectionUtils.isNotEmpty(labelsArray)) {
            List<DocSearchLabelTypeDTO> labelTypeList = labelsArray.toJavaList(DocSearchLabelTypeDTO.class);
            for (DocSearchLabelTypeDTO label : labelTypeList) {
                List<Long> labelIds = label.getLabelList().stream().map(DocSearchLabelDTO::getLabelId).collect(Collectors.toList());
                // bool -- 标签
                BoolQueryBuilder boolLabelIdListStatusQuery = QueryBuilders.boolQuery();
                boolLabelIdListStatusQuery.must(QueryBuilders.termsQuery("labelList.labelId", labelIds));
                boolQuery.must(boolLabelIdListStatusQuery);
            }
        }

        /* bool -- 搜索值 */
        if (StringUtils.isNotEmpty(searchValue)) {
            /* queries */
            // multi_match
            MultiMatchQueryBuilder queriesMultiMatch = QueryBuilders.multiMatchQuery(searchValue, "docName", "writNo").boost(0.4f);
            // match
            MatchQueryBuilder queriesMatch = QueryBuilders.matchQuery("fullText", searchValue).minimumShouldMatch("100%").boost(0.4f);
            // function_score
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[2];
            // function_score 过滤条件1：文号完全相等 -- 加10分
            TermQueryBuilder functionFilterTermWritNo = QueryBuilders.termQuery("writNo.keyword", searchValue);
            filterFunctionBuilders[0] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(functionFilterTermWritNo, new WeightBuilder().setWeight(50));
            // function_score 过滤条件2：文档名称完全相等 -- 加10分
            TermQueryBuilder functionFilterTermDocName = QueryBuilders.termQuery("docName.keyword", searchValue);
            filterFunctionBuilders[1] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(functionFilterTermDocName, new WeightBuilder().setWeight(50));
            FunctionScoreQueryBuilder queriesFunctionScore = QueryBuilders.functionScoreQuery(filterFunctionBuilders).boostMode(CombineFunction.SUM);

            // 组装queries
            /* dis_max */
            DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
            disMaxQueryBuilder.add(queriesMultiMatch);
            disMaxQueryBuilder.add(queriesMatch);
            disMaxQueryBuilder.add(queriesFunctionScore);
            disMaxQueryBuilder.tieBreaker(0.2f);
            boolQuery.must(disMaxQueryBuilder);

            // 用于过滤不匹配搜索值的数据
            boolQuery.must(QueryBuilders.multiMatchQuery(searchValue, "docName", "writNo", "fullText").boost(0L));
        }
        sourceBuilder.query(boolQuery);

        // 聚合
        TermsAggregationBuilder aggsTermsLabelTypeId = AggregationBuilders.terms("group_label_type_id");
        aggsTermsLabelTypeId.field("labelList.labelId");
        aggsTermsLabelTypeId.size(10000);

        TopHitsAggregationBuilder top_labelList = AggregationBuilders.topHits("top_labelList");
        top_labelList.size(1);
        String[] list = new String[4];
        list[0] = "labelList.labelId";
        list[1] = "labelList.labelName";
        list[2] = "labelList.labelTypeId";
        list[3] = "labelList.labelTypeName";
        top_labelList.fetchSource(list, null);
        aggsTermsLabelTypeId.subAggregation(top_labelList);
        sourceBuilder.aggregation(aggsTermsLabelTypeId);
    }

    /**
     * {
     *   "aggregations": {
     *     "group_label_type_id": {
     *       "terms": {
     *         "field": "docId",
     *         "size": 10000,
     *         "min_doc_count": 1,
     *         "shard_min_doc_count": 0,
     *         "show_term_doc_count_error": false
     *       }
     *       ,
     *       "aggregations": {
     *         "top_labelList": {
     *           "top_hits": {
     *             "from": 0,
     *             "size": 1,
     *             "version": false,
     *             "seq_no_primary_term": false,
     *             "explain": false,
     *             "_source": {
     *               "includes": [
     *                 "labelList.labelId",
     *                 "labelList.labelName",
     *                 "labelList.labelTypeId",
     *                 "labelList.labelTypeName"
     *               ],
     *               "excludes": []
     *             }
     *           }
     *         }
     *       }
     *     }
     *   }
     * }
     */
    @Test
    public void getAllLabel () {
        TermsAggregationBuilder groupLabelTypeId = AggregationBuilders.terms("group_label_type_id");
        groupLabelTypeId.field("labelList.labelTypeId");
        groupLabelTypeId.size(100000);
        TermsAggregationBuilder groupLabelId = AggregationBuilders.terms("group_label_id");
        groupLabelId.field("labelList.labelId");
        groupLabelId.size(100000);
        groupLabelTypeId.subAggregation(groupLabelId);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(groupLabelTypeId);

        SearchRequest searchRequest = new SearchRequest("sense");
        searchRequest.source(sourceBuilder);
        RestHighLevelClient restHighLevelClient = getRestHighLevelClient();
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<DocSearchLabelTypeDTO> labelTypeList = new ArrayList<>();
            Terms labelTypeIdTerms = search.getAggregations().get("group_label_type_id");
            for (Terms.Bucket labelTypeIdBucket : labelTypeIdTerms.getBuckets()) {
                Number labelTypeId = labelTypeIdBucket.getKeyAsNumber();
                // 取子聚合
                Terms labelIdTerms = labelTypeIdBucket.getAggregations().get("group_label_id");
                List<DocSearchLabelDTO> labelList = new ArrayList<>();
                for (Terms.Bucket labelIdBucket : labelIdTerms.getBuckets()) {
                    Number labelId = labelIdBucket.getKeyAsNumber();
                    labelList.add(new DocSearchLabelDTO(labelId.longValue(), null));
                    System.out.println();
                }
                labelTypeList.add(new DocSearchLabelTypeDTO(labelTypeId.longValue(), null, labelList));
            }
            System.out.println();


//            List<DocFullLabelDTO> docFullLabelList = new ArrayList<>();
//            JSONObject jsonObject = JSONObject.parseObject(search.toString());
//            JSONObject aggregations = jsonObject.getJSONObject("aggregations");
//            JSONObject jsonObject1 = aggregations.getJSONObject("lterms#group_doc_id");
//            JSONArray buckets = jsonObject1.getJSONArray("buckets");
//            for (Object bucket : buckets) {
//                JSONObject bucketJsonObject = JSONObject.parseObject(bucket.toString());
//                JSONObject hits = bucketJsonObject.getJSONObject("top_hits#top_labelList").getJSONObject("hits");
//                JSONArray hitsArray = hits.getJSONArray("hits");
//                for (Object o : hitsArray) {
//                    JSONObject endHitsJsonObject = JSONObject.parseObject(o.toString());
//                    JSONObject source = endHitsJsonObject.getJSONObject("_source");
//                    JSONArray labelList = source.getJSONArray("labelList");
//                    List<DocFullLabelDTO> docFullLabelDTOS = labelList.toJavaList(DocFullLabelDTO.class);
//                    docFullLabelList.addAll(docFullLabelDTOS);
////                    System.out.println();
//                }
//            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
