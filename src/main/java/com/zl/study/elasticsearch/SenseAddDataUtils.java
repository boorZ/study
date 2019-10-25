package com.zl.study.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.zl.study.elasticsearch.entity.DocFullSearchBaseDTO;
import com.zl.study.jdbc.c3p0.C3P0Utils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 周林
 * @Description sense添加数据
 * @email prometheus@noask-ai.com
 * @date 2019/10/18
 */
public class SenseAddDataUtils {
    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
    private static String SQL_NAME = "tax_sense_test_1_2";
    private static String SQL_USER = "root";
    private static String SQL_PASSWORD = "Root123456";
    private static Integer PAGE_SIZE = 9000;
    private static String SENSE_URL = "sense";
    private static String SUGGEST_MAPPING = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\" : {\n" +
            "      \"docId\": {\"type\": \"long\"}, \n" +
            "      \"docName\":{\"type\": \"completion\"},\n" +
            "      \"docType\":{\"type\": \"long\"},\n" +
            "      \"docNameSuggest\" : {\n" +
            "        \"properties\": {\n" +
            "          \"weight\": {\"type\": \"long\"},\n" +
            "          \"contents\": {\"type\": \"completion\"}\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
    @Test
    public void zl () {
    }
//
//    private static String FORM_MAPPING = "{\n" +
//            "  \"mappings\": {\n" +
//            "    \"properties\": {\n" +
//            "      \"docId\": {\n" +
//            "        \"type\": \"long\"\n" +
//            "      },\n" +
//            "      \"docName\": {\n" +
//            "        \"type\": \"text\",\n" +
//            "        \"fields\": {\n" +
//            "          \"keyword\": {\n" +
//            "            \"type\": \"keyword\",\n" +
//            "            \"ignore_above\": 256\n" +
//            "          }\n" +
//            "        }\n" +
//            "      },\n" +
//            "      \"effectiveStatus\": {\n" +
//            "        \"type\": \"long\"\n" +
//            "      },\n" +
//            "      \"formKind\": {\n" +
//            "        \"type\": \"long\"\n" +
//            "      },\n" +
//            "      \"invalidTime\": {\n" +
//            "        \"type\": \"date\"\n" +
//            "      },\n" +
//            "      \"validTime\": {\n" +
//            "        \"type\": \"date\"\n" +
//            "      },\n" +
//            "      \"nameAttach\": {\n" +
//            "        \"type\": \"text\",\n" +
//            "        \"fields\": {\n" +
//            "          \"keyword\": {\n" +
//            "            \"type\": \"keyword\",\n" +
//            "            \"ignore_above\": 256\n" +
//            "          }\n" +
//            "        }\n" +
//            "      },\n" +
//            "      \"serialNumber\": {\n" +
//            "        \"type\": \"text\",\n" +
//            "        \"fields\": {\n" +
//            "          \"keyword\": {\n" +
//            "            \"type\": \"keyword\",\n" +
//            "            \"ignore_above\": 256\n" +
//            "          }\n" +
//            "        }\n" +
//            "      },\n" +
//            "      \"selfMadeChannelName\": {\n" +
//            "        \"type\": \"text\",\n" +
//            "        \"fields\": {\n" +
//            "          \"keyword\": {\n" +
//            "            \"type\": \"keyword\",\n" +
//            "            \"ignore_above\": 256\n" +
//            "          }\n" +
//            "        }\n" +
//            "      },\n" +
//            "      \"classificationIndex\": {\n" +
//            "        \"properties\": {\n" +
//            "          \"k\": {\n" +
//            "            \"type\": \"text\",\n" +
//            "            \"fields\": {\n" +
//            "              \"keyword\": {\n" +
//            "                \"type\": \"keyword\",\n" +
//            "                \"ignore_above\": 256\n" +
//            "              }\n" +
//            "            }\n" +
//            "          },\n" +
//            "          \"v\": {\n" +
//            "            \"type\": \"text\",\n" +
//            "            \"fields\": {\n" +
//            "              \"keyword\": {\n" +
//            "                \"type\": \"keyword\",\n" +
//            "                \"ignore_above\": 256\n" +
//            "              }\n" +
//            "            }\n" +
//            "          }\n" +
//            "        }\n" +
//            "      },\n" +
//            "      \"writeInstructionsHtml\": {\n" +
//            "        \"type\": \"text\",\n" +
//            "        \"fields\": {\n" +
//            "          \"keyword\": {\n" +
//            "            \"type\": \"keyword\",\n" +
//            "            \"ignore_above\": 256\n" +
//            "          }\n" +
//            "        }\n" +
//            "      },\n" +
//            "      \"policyBasisHtml\": {\n" +
//            "        \"type\": \"text\",\n" +
//            "        \"fields\": {\n" +
//            "          \"keyword\": {\n" +
//            "            \"type\": \"keyword\",\n" +
//            "            \"ignore_above\": 256\n" +
//            "          }\n" +
//            "        }\n" +
//            "      }\n" +
//            "    }\n" +
//            "  }\n" +
//            "}";

//    public static void main(String[] args) throws PropertyVetoException, SQLException {
//        // 添加Mapping
////        addMapping(SUGGEST_MAPPING, "sense_suggest");
////        addMapping(FORM_MAPPING, "sense_form");
//        String sql = "SELECT * FROM `t_doc_form` where is_enable = 'Y';";
//        ResultSet rs = C3P0Utils.getconnection(sql, SQL_NAME, SQL_USER, SQL_PASSWORD);
//        while (rs.next()) {
//            long docId = rs.getLong("doc_form_id");
//            ResultSet rs_image = C3P0Utils.getconnection("SELECT * FROM `t_doc_form_image` where doc_form_id="+docId+";", SQL_NAME, SQL_USER, SQL_PASSWORD);
//            while (rs_image.next()) {
//                Map<String, Object> formImageMap = new HashMap<>();
//                formImageMap.put("id", rs_image.getLong("doc_form_image_id"));
//                formImageMap.put("url", rs_image.getString("image_path"));
//                System.out.println();
//            }
//        }
//    }
    /** 添加Mapping **/
    @Test
    public void addMapping() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("sense_suggest");
        request.source(SUGGEST_MAPPING, XContentType.JSON);
        request.waitForActiveShards(ActiveShardCount.DEFAULT);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 添加标签数据
     */
    @Test
    public void addLabel() throws PropertyVetoException, SQLException {
        String index = "sense_test";
        Integer totalPage = getTotalPage("select count(*) from t_label where is_enable = 'Y'");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT\n" +
                    "\tlabel.label_id,\n" +
                    "\tlabel.label_name,\n" +
                    "\tlabel.label_type_id,\n" +
                    "\t(select label_type_name from t_label_type where label_type_id = label.label_type_id) label_type_name\n" +
                    "FROM\n" +
                    "\t`t_label` label\n" +
                    "\twhere label.is_enable = 'Y'\n limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql, SQL_NAME, SQL_USER, SQL_PASSWORD);
            BulkRequest bulkRequest = new BulkRequest();
            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                long labelId = rs.getLong("label_id");
                dataMap.put("labelId", labelId);
                dataMap.put("labelName", rs.getString("label_name"));
                dataMap.put("labelTypeId", rs.getLong("label_type_id"));
                dataMap.put("labelTypeName", rs.getString("label_type_id"));
                bulkRequest.add(new IndexRequest(index).id(labelId+"").source(dataMap));
            }
            rs.close();
            C3P0Utils.closePs();
            bulk(bulkRequest);
        }
    }
    /**
     * 添加sense（征管规范）及建议数据
     */
    @Test
    public void addSenseNorm() throws PropertyVetoException, SQLException {
        String index = "sense_norm";
        Integer totalPage = getTotalPage("select count(*) from t_doc_business_norm where is_enable = 'Y';");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_doc_business_norm` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql, SQL_NAME, SQL_USER, SQL_PASSWORD);

            BulkRequest bulkRequest = new BulkRequest();
            BulkRequest suggestBulkRequest = new BulkRequest();
            BulkRequest senseBulkRequest = new BulkRequest();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                // Id
                long docId = rs.getLong("doc_business_norm_id");
                dataMap.put("docId", docId);
                // 名称
                String docName = rs.getString("norm_name");
                dataMap.put("docName", docName);
                // 编号
                dataMap.put("serialNumber", rs.getString("norm_no"));
                // 全文内容Text
                dataMap.put("fullText", rs.getString("full_text"));
                // 章节 id、父级Id、名称、内容、级别、排序字段
                List<Map<String, Object>> formImageList = new ArrayList<>();
                ResultSet rs_image = C3P0Utils.getconnection("SELECT * FROM `t_doc_business_norm_sections` where doc_business_norm_id="+docId+";", SQL_NAME, SQL_USER, SQL_PASSWORD);
                while (rs_image.next()) {
                    Map<String, Object> formImageMap = new HashMap<>();
                    formImageMap.put("id", rs_image.getLong("sections_id"));
                    formImageMap.put("pId", rs_image.getLong("parent_sections_id"));
                    String name = rs_image.getString("sections_name") == null ? "" : rs_image.getString("sections_name");
                    formImageMap.put("name", name);
                    String content = rs_image.getString("sections_info_html") == null ? "" : rs_image.getString("sections_info_html");
                    content = content.replaceAll("style=\"[\\s\\S]*?\"", "");
                    formImageMap.put("content", content);
                    formImageMap.put("level", rs_image.getInt("sections_level"));
                    formImageMap.put("orderBy", rs_image.getInt("orderby"));
                    if (!content.equals("") && ! content.equalsIgnoreCase("<p class=\"MsoNormal\"><span lang=\"EN-US\">&nbsp;</span></p >")) {
                        formImageList.add(formImageMap);
                    }
                }
                rs_image.close();
                C3P0Utils.closePs();
                dataMap.put("normSections", formImageList);

                Map map = pojoToMap(dataMap, "1");
                senseBulkRequest.add(new IndexRequest(SENSE_URL).id(docId+"")
                        .source(map));

                bulkRequest.add(new IndexRequest(index).id(docId+"")
                        .source(dataMap));

                addSenseSuggest(docId, docName, 1L, suggestBulkRequest);
            }
            rs.close();
            C3P0Utils.closePs();
            bulk(bulkRequest);
            bulk(suggestBulkRequest);
            bulk(senseBulkRequest);
        }
    }
    /**
     * 添加sense（征管规范目录)
     */
    @Test
    public void addSenseNormDirectory() throws PropertyVetoException, SQLException {
        String index = "sense_norm_directory";
        Integer totalPage = getTotalPage("select count(*) from t_business_norm_directory;");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_business_norm_directory` limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql, SQL_NAME, SQL_USER, SQL_PASSWORD);

            BulkRequest bulkRequest = new BulkRequest();
            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                // Id
                long docId = rs.getLong("norm_directory_id");
                dataMap.put("docId", docId);
                // 名称
                String docName = rs.getString("norm_name");
                dataMap.put("docName", docName);
                // 编号
                dataMap.put("serialNumber", rs.getString("norm_no"));
                // 番外类型代码
                dataMap.put("code", rs.getString("norm_type_code"));
                // 父Id
                dataMap.put("pId", rs.getLong("p_norm_directory_id"));
                // 版本
                dataMap.put("version", rs.getString("version"));
                // 排序字段
                dataMap.put("orderBy", rs.getString("orderby"));

                bulkRequest.add(new IndexRequest(index).id(docId+"")
                        .source(dataMap));
            }
            rs.close();
            C3P0Utils.closePs();
            bulk(bulkRequest);
        }
    }

    /**
     * 添加sense（表证单书）及建议数据
     */
    @Test
    public void addSenseBzds() throws PropertyVetoException, SQLException {
        String index = "sense_form";
        Integer totalPage = getTotalPage("select count(*) from t_doc_form where is_enable = 'Y';");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_doc_form` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql, SQL_NAME, SQL_USER, SQL_PASSWORD);
            BulkRequest bulkRequest = new BulkRequest();
            BulkRequest suggestBulkRequest = new BulkRequest();
            BulkRequest senseBulkRequest = new BulkRequest();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (rs.next()) {
                // 分类索引：业务部门、业务类别、表单类型、设置依据
                List<Map<String, String>> list = new ArrayList<>();
                String businessDept = rs.getString("business_dept");
                String businessType = rs.getString("business_type");
                String formType = rs.getString("form_type");
                String formSource = rs.getString("form_source");

                Map<String, String> map = getMap("业务部门", businessDept);
                Map<String, String> map1 = getMap("业务类别", businessType);
                Map<String, String> map2 = getMap("表单类型", formType);
                Map<String, String> map3 = getMap("设置依据", formSource);

                list.add(map);
                list.add(map1);
                list.add(map2);
                list.add(map3);

                // 表单、表单图片
                Map<String, Object> dataMap = new HashMap<>();
                // Id
                long docId = rs.getLong("doc_form_id");
                dataMap.put("docId", docId);
                // 编号
                String serialNumber = rs.getString("form_no");
                dataMap.put("serialNumber", serialNumber);
                // 名称
                String name = rs.getString("form_name");
                dataMap.put("docName", name);
                // 名称附名称
                String attachName = rs.getString("form_attach_name");
                dataMap.put("nameAttach", attachName);
                // 表单性质
                dataMap.put("formKind", rs.getInt("form_kind"));
                // 自制渠道名称
                dataMap.put("selfMadeChannelName", rs.getString("self_made_channel_name"));
                // 生效时间
//                dataMap.put("validTime", format.format(rs.getDate("valid_time")));
                dataMap.put("validTime", rs.getDate("valid_time")+"");
                // 失效时间
//                dataMap.put("invalidTime", format.format(rs.getDate("invalid_time")));
                dataMap.put("invalidTime", rs.getDate("invalid_time")+"");
                // 有效状态
                dataMap.put("effectiveStatus", rs.getString("is_enable").equals("Y") ? 1 : 3);
                // 分类索引
                dataMap.put("classificationIndex", list);
                // 政策依据
                dataMap.put("policyBasisHtml", rs.getString("policy_basis_html"));
                // 其余内容
                dataMap.put("fullHtml", rs.getString("write_Instructions_html"));
                // 表单
                List<Map<String, Object>> formImageList = new ArrayList<>();
                ResultSet rs_image = C3P0Utils.getconnection("SELECT * FROM `t_doc_form_image` where doc_form_id="+docId+";", SQL_NAME, SQL_USER, SQL_PASSWORD);
                while (rs_image.next()) {
                    Map<String, Object> formImageMap = new HashMap<>();
                    formImageMap.put("id", rs_image.getLong("doc_form_image_id"));
                    formImageMap.put("url", rs_image.getString("image_path"));
                    formImageList.add(formImageMap);
                }
                rs_image.close();
                C3P0Utils.closePs();
                dataMap.put("formImage", formImageList);

                bulkRequest.add(new IndexRequest(index).id(docId+"")
                        .source(dataMap));

                Map mapasd = pojoToMap(dataMap, "2");
                senseBulkRequest.add(new IndexRequest(SENSE_URL).id(docId+"")
                        .source(mapasd));

                addSenseSuggest(docId, serialNumber + name + attachName,2L, suggestBulkRequest);
            }
            rs.close();
            C3P0Utils.closePs();
            bulk(senseBulkRequest);
            bulk(bulkRequest);
            bulk(suggestBulkRequest);
        }
    }

    /**
     * 添加sense（法律法规）及建议数据
     */
    @Test
    public void addSenseFlfg() throws PropertyVetoException, SQLException {
        PAGE_SIZE = 1000;
        String index = "sense_test";
        Integer totalPage = getTotalPage("select count(*) from t_doc_law where is_enable = 'Y'");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_doc_law` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql, SQL_NAME, SQL_USER, SQL_PASSWORD);

            BulkRequest bulkRequest = new BulkRequest();
            BulkRequest senseBulkRequest = new BulkRequest();
            BulkRequest suggestBulkRequest = new BulkRequest();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                // Id
                long docId = rs.getLong("doc_law_id");
                dataMap.put("docId", docId);
                // 名称
                String docName = rs.getString("law_name");
                dataMap.put("docName", docName);
                // 状态
                dataMap.put("effectiveStatus", rs.getInt("law_status"));
                // 简介
                dataMap.put("docInfo", rs.getString("law_info"));
                // 发文单位
                dataMap.put("dispatchUnit", rs.getString("dispatch_unit"));
                // 发文时间
//                dataMap.put("dispatchTime", format.format(rs.getDate("dispatch_time")));
                dataMap.put("dispatchTime",rs.getDate("dispatch_time")+"");
                // 生效时间
//                String validTime = null;
//                if (rs.getDate("valid_time") != null) {
//                    validTime = format.format(rs.getDate("valid_time"));
//                }
//                dataMap.put("validTime", validTime);
                dataMap.put("validTime", rs.getDate("valid_time")+"");
                // 失效时间
//                String invalidTime = null;
//                if (rs.getDate("invalid_time") != null) {
//                    invalidTime = format.format(rs.getDate("invalid_time"));
//                }
//                dataMap.put("invalidTime", invalidTime);
                dataMap.put("invalidTime", rs.getDate("invalid_time")+"");
                // 字号
                dataMap.put("writNo", rs.getString("writ_no"));
                // 字号类型
                dataMap.put("writNoType", rs.getString("writ_no_type"));
                // 全文内容Html
                dataMap.put("fullHtml", rs.getString("full_html"));
                // 全文内容Text
                dataMap.put("fullText", rs.getString("full_text"));
                // 标注：编号、类型范围、项、注释内容、参见依据、时间、状态、内容
//            dataMap.put("bz", rs.getString("full_text"));
                // 附件：id、名称、内容、类型、路径
//            List<Map<String, Object>> fjList = new ArrayList<>();
//            ResultSet rs_fj = C3P0Utils.getconnection("SELECT * FROM `t_doc_law_attachment` where is_enable = 'Y';", SQL_NAME, SQL_USER, SQL_PASSWORD);
//            while (rs_fj.next()) {
//                Map<String, Object> fjMap = new HashMap<>();
//                fjMap.put("id", rs.getLong("id"));
//                fjMap.put("name", rs.getString("attachment_name"));
//                fjMap.put("content", rs.getString("refer_content"));
//                fjMap.put("type", rs.getInt("attachment_type"));
//                fjMap.put("url", rs.getString("refer_path"));
//                fjList.add(fjMap);
//            }
//            dataMap.put("fj", fjList);
                // 目录：Id、名称、级别、关联Id
//            dataMap.put("directory", rs.getString("full_text"));

//                Map map = pojoToMap(dataMap, "3");
//                senseBulkRequest.add(new IndexRequest(SENSE_URL).id(docId+"").source(map));

                bulkRequest.add(new IndexRequest(index).id(docId+"")
                        .source(dataMap));

//                addSenseSuggest(docId, docName, 3L, suggestBulkRequest);
            }
            rs.close();
            C3P0Utils.closePs();
//            bulk(senseBulkRequest);
            bulk(bulkRequest);
            System.out.println();
//            bulk(suggestBulkRequest);
        }
    }

    /**
     * 添加sense（政策解读）及建议数据
     */
    @Test
    public void addSensePolicy() throws PropertyVetoException, SQLException {
        String index = "sense_policy";
        Integer totalPage = getTotalPage("select count(*) from t_doc_policy where is_enable = 'Y'");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_doc_policy` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql, SQL_NAME, SQL_USER, SQL_PASSWORD);

            BulkRequest bulkRequest = new BulkRequest();
            BulkRequest senseBulkRequest = new BulkRequest();
            BulkRequest suggestBulkRequest = new BulkRequest();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                // Id
                long docId = rs.getLong("doc_policy_id");
                dataMap.put("docId", docId);
                // 名称
                String docName = rs.getString("policy_name");
                dataMap.put("docName", docName);
                // 来源
                dataMap.put("source", rs.getString("origin"));
                // 发文时间
                dataMap.put("dispatchTime",rs.getDate("release_date")+"");
                // 是否官方解读
                dataMap.put("isOfficial", rs.getString("is_official"));
                // 全文内容Html
                dataMap.put("fullHtml", rs.getString("full_html"));
                // 全文内容Text
                dataMap.put("fullText", rs.getString("full_text"));

                Map map = pojoToMap(dataMap, "4");
                senseBulkRequest.add(new IndexRequest(SENSE_URL).id(docId+"")
                        .source(map));

                bulkRequest.add(new IndexRequest(index).id(docId+"")
                        .source(dataMap));

                addSenseSuggest(docId, docName, 4L, suggestBulkRequest);
            }
            rs.close();
            C3P0Utils.closePs();
            bulk(senseBulkRequest);
            bulk(bulkRequest);
            bulk(suggestBulkRequest);
        }
    }

    private Map pojoToMap (Map<String, Object> dataMap, String type) {
        JSONObject jsonObject = new JSONObject(dataMap);
        DocFullSearchBaseDTO sense = jsonObject.toJavaObject(DocFullSearchBaseDTO.class);
        sense.setDocType(type);

        Object o = JSONObject.toJSON(sense);
        Map map = (Map) JSONObject.parse(o.toString());
        return map;
    }

    private Integer getTotalPage(String sql) throws SQLException, PropertyVetoException {
        ResultSet rs_count = C3P0Utils.getconnection(sql, SQL_NAME, SQL_USER, SQL_PASSWORD);
        int total = 0;
        if (rs_count.next()) {
            total = rs_count.getInt(1);
        }
        rs_count.close();
        C3P0Utils.closePs();
        return total / PAGE_SIZE + (total % PAGE_SIZE == 0 ? 0 : 1);
    }

    /** 添加建议数据 **/
    private void addSenseSuggest(Long id, String name, Long docType, BulkRequest bulkRequest) {
        String index = "sense_suggest";
        // 拆分名称——正则
        List<String> docNameSuggestList = splitDocName(name);
        // 拆分名称——HanLP
        List<String> hanLpList = HanLP.extractPhrase(docNameSuggestList.get(docNameSuggestList.size()-1), 20);
        docNameSuggestList.addAll(hanLpList);

        Map<String, Object> docNameSuggest = new HashMap<>();
        docNameSuggest.put("weight", 0);
        docNameSuggest.put("contents", docNameSuggestList);
        bulkRequest.add(new IndexRequest(index).id(id+"")
                .source(XContentType.JSON,"docId", id, "docName", name, "docType", docType, "docNameSuggest", docNameSuggest));
    }

    /** 执行 **/
    public static void bulk(BulkRequest bulkRequest) {
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
//            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 拆分规则 **/
    public List<String> splitDocName(String docName) {
        List<String> docNameList = new ArrayList<>();
//        String docName = "国务院关于各省、自治区、直辖市农业税平均税率的规定";
        // 去掉财政部《》
        docName = docName.replaceAll("[<<>>]", "");
        docNameList.add(docName);
        // 获取 *最后一个关于 最后一个的* 中间的数据
        String split1 = docName.replaceAll("^.+?关于", "").replaceAll(".[^的]+$", "");
        if (split1.equals("")) {
            split1 = docName;
        }
        if (!split1.equals(docName)) {
            docNameList.add(split1);
        }
        // 去掉财政部《》
//        String split2 = docName.replaceAll(".*<<", "").replaceAll(">>.*", "");
//        if (!split2.equals(docName)) {
//            docNameList.add("2"+split2);
//        }
        // 去掉财政部
        String split3 = split1.replaceAll("^.*?财政部", "");
        if (split3.length() == (split1.length() - 3)) {
            split3 = split3.replaceAll("^.?、", "");
            docNameList.add(split3);
        }
        // 去掉国务院
        String split4 = split1.replaceAll("^.*?国务院", "");
        if (split4.length() == (split1.length() - 3)) {
            split4 = split4.replaceAll("^.?、", "");
            docNameList.add(split4);
        }
        // 去掉税务总局
        String split5 = split1.replaceAll("^.*?税务总局", "");
        if (split5.length() == (split1.length() - 4)) {
            split5 = split5.replaceAll("^.?、", "");
            docNameList.add(split5);
        }
        // 去掉中华人民共和国
        String split6 = split1.replaceAll("^.*?中华人民共和国", "");
        if (split6.length() == (split1.length() - 7)) {
            split6 = split6.replaceAll("^.?、", "");
            docNameList.add(split6);
        }
        if (docNameList.size() > 3) {
            System.out.println();
        }
        return docNameList;
    }

    /** 获取Map **/
    private static Map<String, String> getMap(String k, String v) {
        Map<String, String> map = new HashMap<>();
        map.put("k", k);
        map.put("v", v);
        return map;
    }

    /** 匹配正则 **/
    private static List<String> getMatcherData(String regex, CharSequence input){
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(input);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group(0);
            group = group.trim();
            list.add(group);
        }
        return list;
    }
}
