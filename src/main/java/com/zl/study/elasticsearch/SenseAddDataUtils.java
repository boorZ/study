package com.zl.study.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zl.study.elasticsearch.entity.DocFullSearchBaseDTO;
import com.zl.study.elasticsearch.entity.DocRelationDTO;
import com.zl.study.jdbc.BeanConfig;
import com.zl.study.jdbc.c3p0.C3P0Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 周林
 * @Description sense添加数据
 * @email prometheus@noask-ai.com
 * @date 2019/10/18
 */
public class SenseAddDataUtils {
    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(BeanConfig.HOST_NAME, BeanConfig.PORT, "http")));
    //    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.1.23", 9101, "http")));
    private static Integer PAGE_SIZE = 5000;

    private static String test = "";
    private static String SENSE = "se"+test;
    private static String SENSE_NORM = "se_norm"+test;        // 1
    private static String SENSE_NORM_DIRECTORY = "se_norm_directory";
    private static String SENSE_FORM = "se_form"+test;        // 2
    private static String SENSE_LAW = "se_law"+test;          // 3
    private static String SENSE_LAW_DIRECTORY = "se_law_directory";
    private static String SENSE_POLICY = "se_policy"+test;    // 4
    private static String SENSE_TAX_NORM = "se_tax_norm"+test;// 5
    private static String SENSE_SEARCH_LOG = "se_search_log";
    private static String SENSE_HOT_SEARCH = "se_hot_search";
    private static String SENSE_HOT_DOC = "se_hot_doc";
    private static String SENSE_LABEL = "se_label";

    /**
     * 运行此方法。税务数据带回家。(跑完数据在4分钟左右)
     * @throws PropertyVetoException
     * @throws SQLException
     * @throws IOException
     */
    @Test
    public void main() throws PropertyVetoException, SQLException, IOException {
//        // 删除所有相关Mapping
//        delMappingAll();
//        // 建立Mapping
//        addMappingList();
//        // 添加征管规范数据 566
//        addSenseNorm();
//        // 添加征管规范目录 578
//        addSenseNormDirectory();
//        // 添加表证表单数据 990
//        addSenseForm();
//        // 添加法律法规数据 12988 标注 3659
//        addSenseLaw();
//        // 添加法律法规目录 942
//        addSenseLawDirectory();
//        // 添加政策解读数据 1010
//        addSensePolicy();
        // 添加热度数据
//        addHot();
    }


    /**
     * 添加热度数据
     * @throws PropertyVetoException
     * @throws SQLException
     */
//    @Test
    public static void addHot() throws PropertyVetoException, SQLException {
        ResultSet rs = C3P0Utils.getconnection("SELECT * FROM `t_doc_policy` where is_enable = 'Y' limit 0, 10;");
        BulkRequest bulkRequest = new BulkRequest();
        while (rs.next()) {
            Map<String, Object> dataMap = new HashMap<>();
            long docId = rs.getLong("doc_policy_id");
            dataMap.put("docId", docId);
            dataMap.put("docName", rs.getString("policy_name"));
            dataMap.put("docType", 4L);
            bulkRequest.add(new IndexRequest(SENSE_HOT_SEARCH).id(docId+"")
                    .source(dataMap));
        }
        rs.close();
        C3P0Utils.closePs();
        bulk(bulkRequest);

        ResultSet rsDoc = C3P0Utils.getconnection("SELECT * FROM `t_doc_business_norm` where is_enable = 'Y' limit 0, 10;");
        BulkRequest bulkRequestDoc = new BulkRequest();
        while (rsDoc.next()) {
            Map<String, Object> dataMap = new HashMap<>();
            long docId = rsDoc.getLong("doc_business_norm_id");
            dataMap.put("docId", docId);
            dataMap.put("docName", rsDoc.getString("norm_name"));
            dataMap.put("docType", 1L);
            bulkRequestDoc.add(new IndexRequest(SENSE_HOT_DOC).id(docId+"")
                    .source(dataMap));
        }
        rsDoc.close();
        C3P0Utils.closePs();
        bulk(bulkRequestDoc);

    }
    /**
     * 添加Mapping
     **/
    @Test
    public void addMappingList() throws IOException {
        // 详情通用Mapping
        String commonMapping = "{\n" +
                "  \"mappings\": {\n" +
                "    \"properties\": {\n" +
                "      \"docNameSuggest\" : {\n" +
                "        \"properties\": {\n" +
                "          \"weight\": {\"type\": \"long\"},\n" +
                "          \"contents\": {\"type\": \"completion\"}\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
//        commonMapping = "{\n" +
//                "  \"mappings\": {\n" +
//                "    \"properties\": {\n" +
//                "      \"docNameSuggest\": {\n" +
//                "        \"properties\": {\n" +
//                "          \"weight\": {\n" +
//                "            \"type\": \"long\"\n" +
//                "          },\n" +
//                "          \"contents\": {\n" +
//                "            \"type\": \"completion\"\n" +
//                "          }\n" +
//                "        }\n" +
//                "      },\n" +
//                "      \"docName\": {\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\"\n" +
//                "      },\n" +
//                "      \"fullText\": {\n" +
//                "        \"type\": \"text\",\n" +
//                "        \"analyzer\": \"ik_max_word\"\n" +
//                "      }\n" +
//                "    }\n" +
//                "  }\n" +
//                "}";
        addMapping(commonMapping, SENSE_LAW);
        addMapping(commonMapping, SENSE);
        addMapping(commonMapping, SENSE_NORM);
        addMapping(commonMapping, SENSE_FORM);
        addMapping(commonMapping, SENSE_POLICY);
    }

    /**
     * 添加sense（征管规范）
     */
//    @Test
    public static void addSenseNorm() throws PropertyVetoException, SQLException {
        // norm
        List<DocRelationDTO> normToFormRelation = getSql(2,"select a.doc_form_id id, b.form_name as name, b.form_no no,a.doc_business_norm_id docId FROM `m_business_norm_form` a " +
                "inner join t_doc_form b on b.doc_form_id = a.doc_form_id;");
        List<DocRelationDTO> normToLawRelation = getSql(3,"SELECT a.doc_law_id id, b.law_name name, b.doc_no no,a.doc_business_norm_id docId FROM `m_business_norm_law` a " +
                "inner join t_doc_law b on b.doc_law_id = a.doc_law_id;");
        normToFormRelation.addAll(normToLawRelation);

        // 章节
        ResultSet sectionsRs = C3P0Utils.getconnection("SELECT * FROM `t_doc_business_norm_sections`;");
        List<Map<String, Object>> normSectionsList = new ArrayList<>();
        while (sectionsRs.next()) {
            Map<String, Object> normSectionsMap = new HashMap<>();
            normSectionsMap.put("id", sectionsRs.getLong("sections_id"));
            normSectionsMap.put("docId", sectionsRs.getLong("doc_business_norm_id"));
            normSectionsMap.put("pId", sectionsRs.getLong("parent_sections_id"));
            String name = sectionsRs.getString("sections_name") == null ? "" : sectionsRs.getString("sections_name");
            normSectionsMap.put("name", name);
            String content = sectionsRs.getString("sections_info_html") == null ? "" : sectionsRs.getString("sections_info_html");
            content = content.replaceAll("style=\"[\\s\\S]*?\"", "");
            normSectionsMap.put("content", content);
            normSectionsMap.put("level", sectionsRs.getInt("sections_level"));
            normSectionsMap.put("orderBy", sectionsRs.getInt("orderby"));
            normSectionsList.add(normSectionsMap);
        }
        if (sectionsRs == null) {
            sectionsRs.close();
            C3P0Utils.closePs();
        }

        Integer totalPage = getTotalPage("select count(*) from t_doc_business_norm where is_enable = 'Y';");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_doc_business_norm` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql);
            BulkRequest bulkRequest = new BulkRequest();
            BulkRequest bulkSenseRequest = new BulkRequest();
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
                List<Map<String, Object>> normSectionsFullList = normSectionsList.stream().filter(da -> da.get("docId").equals(docId)).collect(Collectors.toList());
                dataMap.put("normSections", normSectionsFullList);
                // relation
                List<Map<String, Object>> relationList = normToFormRelation.stream().filter(da -> da.getDocId().equals(docId)).map(DocRelationDTO::getDataMap).collect(Collectors.toList());
                dataMap.put("relation", relationList);
                dataMap.put("docNameSuggest", getSenseSuggest(docName));
                // sense_norm
                bulkRequest.add(new IndexRequest(SENSE_NORM).id(docId+"")
                        .source(dataMap));
                // sense
                Map seMap = pojoToMap(dataMap, "1");
                bulkSenseRequest.add(new IndexRequest(SENSE).id(docId+"")
                        .source(seMap));
            }
            if (rs != null) {
                rs.close();
                C3P0Utils.closePs();
            }
            bulk(bulkRequest);
            bulk(bulkSenseRequest);
        }
    }
    /**
     * 添加sense（征管规范目录)
     */
//    @Test
    public static void addSenseNormDirectory() throws PropertyVetoException, SQLException {
        Integer totalPage = getTotalPage("select count(*) from t_business_norm_directory;");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_business_norm_directory` limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql);

            BulkRequest bulkRequest = new BulkRequest();
            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                // Id
                long id = rs.getLong("norm_directory_id");
                dataMap.put("id", id);
                dataMap.put("docId", "");
                // 名称
                String docName = rs.getString("norm_name");
                dataMap.put("dirName", docName);
                // 编号
                dataMap.put("dirNum", rs.getString("norm_no"));
                // 番外类型代码
                dataMap.put("code", rs.getString("norm_type_id"));
                // 父Id
                dataMap.put("pId", rs.getLong("p_norm_directory_id"));
                // 版本
                dataMap.put("version", rs.getString("version"));
                // 排序字段
                dataMap.put("orderBy", rs.getString("orderby"));

                bulkRequest.add(new IndexRequest(SENSE_NORM_DIRECTORY).id(id+"")
                        .source(dataMap));
            }
            if (rs != null) {
                rs.close();
                C3P0Utils.closePs();
            }
            bulk(bulkRequest);
        }
    }

    /**
     * 添加sense（表证单书）
     */
//    @Test
    public static void addSenseForm() throws PropertyVetoException, SQLException {
        // form
        List<DocRelationDTO> formToNormRelation = getSql(1,"SELECT a.doc_business_norm_id id, b.norm_name name, b.norm_no no,a.doc_form_id docId FROM `m_business_norm_form` a " +
                "inner join t_doc_business_norm b on b.doc_business_norm_id = a.doc_business_norm_id;");
        List<DocRelationDTO> formToLawRelation = getSql(3,"SELECT a.doc_law_id id,b.law_name name,b.doc_no no,a.doc_form_id docId FROM `m_law_form` a " +
                "inner join t_doc_law b on b.doc_law_id = a.doc_law_id ;");
        formToNormRelation.addAll(formToLawRelation);

        // 表单图片
        List<DocRelationDTO> formImageList = new ArrayList<>();
        ResultSet rsImage = C3P0Utils.getconnection("SELECT * FROM `t_doc_form_image`;");
        while (rsImage.next()) {
            DocRelationDTO formImage = new DocRelationDTO();
            Map<String, Object> formImageMap = new HashMap<>();
            formImageMap.put("id", rsImage.getLong("doc_form_image_id"));
            formImageMap.put("url", rsImage.getString("image_path"));
            formImage.setDocId(rsImage.getLong("doc_form_id"));
            formImage.setDataMap(formImageMap);
            formImageList.add(formImage);
        }
        if (rsImage != null) {
            rsImage.close();
            C3P0Utils.closePs();
        }

        Integer totalPage = getTotalPage("select count(*) from t_doc_form where is_enable = 'Y';");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_doc_form` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql);
            BulkRequest bulkRequest = new BulkRequest();
            BulkRequest bulkSenseRequest = new BulkRequest();
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
                String docName = rs.getString("form_name");
                dataMap.put("docName", docName);
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
                // relation
                List<Map<String, Object>> relationList = formToNormRelation.stream().filter(da -> da.getDocId().equals(docId)).map(DocRelationDTO::getDataMap).collect(Collectors.toList());
                dataMap.put("relation", relationList);
                // 表单
                List<Map<String, Object>> formImage = formImageList.stream().filter(da -> da.getDocId().equals(docId)).map(DocRelationDTO::getDataMap).collect(Collectors.toList());
                dataMap.put("formImage", formImage);
                // sense
                Map seMap = pojoToMap(dataMap, "2");
                bulkSenseRequest.add(new IndexRequest(SENSE).id(docId+"")
                        .source(seMap));

                dataMap.put("docNameSuggest", getSenseSuggest(docName));

                bulkRequest.add(new IndexRequest(SENSE_FORM).id(docId+"")
                        .source(dataMap));
            }
            if (rs != null) {
                rs.close();
                C3P0Utils.closePs();
            }
            bulk(bulkRequest);
            bulk(bulkSenseRequest);
        }
    }

    /**
     * 添加sense（法律法规）
     */
//    @Test
    public static void addSenseLaw() throws PropertyVetoException, SQLException {
        // law
        List<DocRelationDTO> lawToNormRelation = getSql(1,"SELECT a.doc_business_norm_id id,b.norm_name name,b.norm_no no,a.doc_law_id docId FROM `m_business_norm_law` a " +
                "inner join t_doc_business_norm b on b.doc_business_norm_id = a.doc_business_norm_id;");
        List<DocRelationDTO> lawToFormRelation = getSql(2, "SELECT a.doc_form_id id,b.form_name name,b.form_no no,a.doc_law_id docId FROM `m_law_form` a " +
                "inner join t_doc_form b on b.doc_form_id = a.doc_form_id;");
        List<DocRelationDTO> lawToPolicyRelation = getSql(4,"SELECT a.doc_policy_id id,b.policy_name name,a.doc_law_id docId FROM `m_law_policy` a " +
                "inner join t_doc_policy b on b.doc_policy_id = a.doc_policy_id;");
        lawToNormRelation.addAll(lawToFormRelation);
        lawToNormRelation.addAll(lawToPolicyRelation);

        // 获取法律法规所有标注
        ResultSet bzRs = C3P0Utils.getconnection("select * from t_doc_law_comment where is_enable = 'Y';");
        List<DocRelationDTO> bzFullList = new ArrayList<>();
        while (bzRs.next()) {
            DocRelationDTO bz = new DocRelationDTO();
            Map<String, Object> map = new HashMap<>();
            map.put("id", bzRs.getLong("doc_law_comment_id"));
            map.put("serialNumber", bzRs.getString("comment_no"));
            map.put("type", bzRs.getLong("comment_scope"));
            String item = bzRs.getString("comment_item");
            map.put("item", item == null ? "" : item);
            map.put("content", bzRs.getString("comment_content"));
            bz.setDataMap(map);
            bz.setDocId(bzRs.getLong("doc_law_id"));
            bzFullList.add(bz);
        }
        if (bzRs != null) {
            bzRs.close();
            C3P0Utils.closePs();
        }

        Integer totalPage = getTotalPage("select count(*) from t_doc_law where is_enable = 'Y'");
        for (Integer i = 0; i < totalPage; i++) {
            // 23
            String sql = "SELECT * FROM `t_doc_law` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql);

            BulkRequest bulkRequest = new BulkRequest();
            BulkRequest bulkSenseRequest = new BulkRequest();
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
                dataMap.put("dispatchTime",rs.getString("dispatch_time"));
                // 生效时间
                dataMap.put("validTime", rs.getString("valid_time"));
                // 失效时间
                dataMap.put("invalidTime", rs.getString("invalid_time"));
                // 字号
                dataMap.put("writNo", rs.getString("writ_no"));
                // 字号类型
                dataMap.put("writNoType", rs.getString("writ_no_type"));
                // 全文内容Html
                dataMap.put("fullHtml", rs.getString("full_html"));
                // 全文内容Text
                dataMap.put("fullText", rs.getString("full_text"));
                // 建议库
                dataMap.put("docNameSuggest", getSenseSuggest(docName));
                // 标注
                List<Map<String, Object>> bz = bzFullList.stream().filter(da -> da.getDocId().equals(docId)).map(DocRelationDTO::getDataMap).collect(Collectors.toList());
                dataMap.put("bz", bz);
                // relation
                List<Map<String, Object>> relationList = lawToNormRelation.stream().filter(da -> da.getDocId().equals(docId)).map(DocRelationDTO::getDataMap).collect(Collectors.toList());
                dataMap.put("relation", relationList);
                // sense
                Map seMap = pojoToMap(dataMap, "3");
                bulkSenseRequest.add(new IndexRequest(SENSE).id(docId+"")
                        .source(seMap));
                bulkRequest.add(new IndexRequest(SENSE_LAW).id(docId+"")
                        .source(dataMap));
            }
            if (rs != null) {
                rs.close();
                C3P0Utils.closePs();
            }
            bulk(bulkRequest);
            bulk(bulkSenseRequest);
        }
    }

    /**
     * 添加sense（法律法规目录）
     */
//    @Test
    public static void addSenseLawDirectory() throws PropertyVetoException, SQLException {
        ResultSet rs = C3P0Utils.getconnection("SELECT * FROM `t_doc_law_directory` where is_enable = 'Y';");
        BulkRequest bulkRequest = new BulkRequest();
        while (rs.next()) {
            Map<String, Object> dataMap = new HashMap<>();
            long id = rs.getLong("doc_law_directory_id");
            dataMap.put("id", id);
            dataMap.put("docId", rs.getString("doc_law_id"));
            dataMap.put("pId", rs.getLong("p_doc_law_directory_id"));
            dataMap.put("dirNum", rs.getString("directory_no"));
            dataMap.put("dirName", rs.getString("directory_name"));
            dataMap.put("level", rs.getInt("directory_level"));
            dataMap.put("orderBy", rs.getInt("orderby"));
            bulkRequest.add(new IndexRequest(SENSE_LAW_DIRECTORY).id(id+"")
                    .source(dataMap));
        }
        if (rs != null) {
            rs.close();
            C3P0Utils.closePs();
        }
        bulk(bulkRequest);
    }

    /**
     * 添加sense（政策解读）及建议数据
     */
//    @Test
    public static void addSensePolicy() throws PropertyVetoException, SQLException {
        // policy
        List<DocRelationDTO> policyToLawRelation = getSql(3,"SELECT a.doc_law_id id,b.law_name name,b.doc_no no,a.doc_policy_id docId FROM `m_law_policy` a " +
                "inner join t_doc_law b on b.doc_law_id = a.doc_law_id;");

        Integer totalPage = getTotalPage("select count(*) from t_doc_policy where is_enable = 'Y'");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_doc_policy` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql);
            BulkRequest bulkRequest = new BulkRequest();
            BulkRequest bulkSenseRequest = new BulkRequest();
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
                // relation
                List<Map<String, Object>> relationList = policyToLawRelation.stream().filter(da -> da.getDocId().equals(docId)).map(DocRelationDTO::getDataMap).collect(Collectors.toList());
                dataMap.put("relation", relationList);
                dataMap.put("docNameSuggest", getSenseSuggest(docName));
                bulkRequest.add(new IndexRequest(SENSE_POLICY).id(docId+"")
                        .source(dataMap));

                // sense
                Map seMap = pojoToMap(dataMap, "4");
                bulkSenseRequest.add(new IndexRequest(SENSE).id(docId+"")
                        .source(seMap));
            }
            if (rs != null) {
                rs.close();
                C3P0Utils.closePs();
            }
            bulk(bulkRequest);
            bulk(bulkSenseRequest);
        }
    }

    /**
     * 添加sense（纳税规范)及建议数据
     */
    @Test
    public void addSenseTaxNorm() throws PropertyVetoException, SQLException {

        // 章节
        ResultSet rsImage = C3P0Utils.getconnection("SELECT * FROM `t_doc_tax_norm_sections`;");
        List<Map<String, Object>> taxNormSectionsAllList = new ArrayList<>();
        while (rsImage.next()) {
            Map<String, Object> taxNormSectionsMap = new HashMap<>();
            taxNormSectionsMap.put("id", rsImage.getLong("tax_sections_id"));
            taxNormSectionsMap.put("docId", rsImage.getLong("doc_tax_norm_id"));
//            normSectionsMap.put("pId", rsImage.getLong("parent_sections_id"));
            String name = rsImage.getString("sections_name") == null ? "" : rsImage.getString("sections_name");
            taxNormSectionsMap.put("name", name);
            String content = rsImage.getString("tax_sections_name");
            content = StringUtils.isEmpty(content) ? "" : content;
//            content = content.replaceAll("style=\"[\\s\\S]*?\"", "");
            taxNormSectionsMap.put("content", content);
            taxNormSectionsMap.put("level", rsImage.getInt("tax_sections_levell"));
            taxNormSectionsMap.put("orderBy", rsImage.getInt("orderby"));
            taxNormSectionsAllList.add(taxNormSectionsMap);
        }
        if (rsImage != null) {
            rsImage.close();
            C3P0Utils.closePs();
        }

        Integer totalPage = getTotalPage("select count(*) from t_tax_norm where is_enable = 'Y'");
        for (Integer i = 0; i < totalPage; i++) {
            String sql = "SELECT * FROM `t_tax_norm` where is_enable = 'Y' limit " + (i * PAGE_SIZE) + "," + PAGE_SIZE + ";";
            ResultSet rs = C3P0Utils.getconnection(sql);
            BulkRequest bulkRequest = new BulkRequest();
            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                // Id
                long docId = rs.getLong("doc_tax_norm_id");
                dataMap.put("docId", docId);
                // 名称
                String docName = rs.getString("tax_norm_name");
                dataMap.put("docName", docName);
                // 规范类型id
                dataMap.put("normId", rs.getString("norm_type_id"));
                // 规范编号
                dataMap.put("serialNumber", rs.getString("tax_norm_no"));
                // 所属事项
                // 全文内容Text
                dataMap.put("fullText", rs.getString("full_text"));
                dataMap.put("docNameSuggest", getSenseSuggest(docName));
                // 章节
                List<Map<String, Object>> taxNormSectionsFullList = taxNormSectionsAllList.stream().filter(da -> da.get("docId").equals(docId)).collect(Collectors.toList());
                dataMap.put("normSections", taxNormSectionsFullList);
                bulkRequest.add(new IndexRequest(SENSE_TAX_NORM).id(docId+"")
                        .source(dataMap));
            }
            if (rs != null) {
                rs.close();
                C3P0Utils.closePs();
            }
            bulk(bulkRequest);
        }
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
            ResultSet rs = C3P0Utils.getconnection(sql);
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
            if (rs != null) {
                rs.close();
                C3P0Utils.closePs();
            }
            bulk(bulkRequest);
        }
    }

    public static long getTotalPageES(String indice) {
        SearchSourceBuilder dsl = new SearchSourceBuilder();
        SearchResponse totalResponse = getSearchResponse(indice, dsl);
        long total = totalResponse.getHits().getTotalHits().value;
        return total / PAGE_SIZE + (total % PAGE_SIZE == 0 ? 0 : 1);
    }

    public static SearchResponse getSearchResponse (String indice, SearchSourceBuilder dsl) {
        // 获取高级客户端
        try {
            // 编写DSL语句
            SearchRequest searchRequest = new SearchRequest(indice);
            // 获取DSL
            searchRequest.source(dsl);
            // 执行DSL
            return client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("执行失败");
    }

    /* ===================================================================================================================== */
    /**
     * 字符串日期转换成中文格式日期
     *
     * @param date
     *            字符串日期 yyyy-MM-dd
     * @return yyyy年MM月dd日
     * @throws Exception
     */
    public static String dateToCnDate(String date) {
        String result = "";
        // String[] cnDate = new
        // String[]{"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String[] cnDate = new String[] { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        // String ten = "拾";
        String ten = "十";
        String[] dateStr = date.split("-");
        for (int i = 0; i < dateStr.length; i++) {
            for (int j = 0; j < dateStr[i].length(); j++) {

                String charStr = dateStr[i];
                String str = String.valueOf(charStr.charAt(j));
                if (charStr.length() == 2) {

                    if (charStr.equals("10")) {
                        result += ten;
                        break;
                    } else {
                        if (j == 0) {
                            if (charStr.charAt(j) == '1')
                                result += ten;
                            else if (charStr.charAt(j) == '0')
                                result += "";
                            else
                                result += cnDate[Integer.parseInt(str)] + ten;
                        }
                        if (j == 1) {
                            if (charStr.charAt(j) == '0')
                                result += "";
                            else
                                result += cnDate[Integer.parseInt(str)];
                        }
                    }
                } else {
                    result += cnDate[Integer.parseInt(str)];
                }
            }
            if (i == 0) {
                result += "年";
                continue;
            }
            if (i == 1) {
                result += "月";
                continue;
            }
            if (i == 2) {
                result += "日";
                continue;
            }
        }
        return result;
    }

    private static Map pojoToMap (Map<String, Object> dataMap, String type) {
        JSONObject jsonObject = new JSONObject(dataMap);
        DocFullSearchBaseDTO sense = jsonObject.toJavaObject(DocFullSearchBaseDTO.class);
        sense.setDocType(type);

        Object o = JSONObject.toJSON(sense);
        return (Map) JSONObject.parse(o.toString());
    }

    private static void delMappingAll () throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest();
        deleteIndexRequest.indices(SENSE, SENSE_NORM, SENSE_NORM_DIRECTORY, SENSE_FORM, SENSE_LAW, SENSE_LAW_DIRECTORY, SENSE_POLICY);
        client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
    }
    private static void addMapping(String mapping, String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.source(mapping, XContentType.JSON);
        request.waitForActiveShards(ActiveShardCount.DEFAULT);
        client.indices().create(request, RequestOptions.DEFAULT);
//        client.close();
    }

    private static Integer getTotalPage(String sql) throws SQLException, PropertyVetoException {
        ResultSet rs_count = C3P0Utils.getconnection(sql);
        int total = 0;
        if (rs_count.next()) {
            total = rs_count.getInt(1);
        }
        rs_count.close();
        C3P0Utils.closePs();
        return total / PAGE_SIZE + (total % PAGE_SIZE == 0 ? 0 : 1);
    }

    private static List<DocRelationDTO> getSql(Integer docType, String sql) throws PropertyVetoException, SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://"+ BeanConfig.JPA_IP+"/"+BeanConfig.JPA_DATABASE+
                "?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        cpds.setUser(BeanConfig.JPA_USER);
        cpds.setPassword(BeanConfig.JPA_PASSWORD);
        // 得到一个Connection
        Connection con = cpds.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<DocRelationDTO> formRelation = new ArrayList<>();
        while (rs.next()) {
            DocRelationDTO data = new DocRelationDTO();
            Map<String, Object> dataMap = new HashMap<>();
            String no = "";
            try {
                no = rs.getString("no");
            } catch (SQLException e) {
//                e.printStackTrace();
            }
            dataMap.put("id", rs.getLong("id"));
            dataMap.put("name", rs.getString("name"));
            dataMap.put("dirNum", no);
            dataMap.put("docType", docType);

            data.setDataMap(dataMap);
            data.setDocId(rs.getLong("docId"));
            formRelation.add(data);
        }
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
        if (cpds != null) cpds.close();
        return formRelation;
    }

    /** 获取建议数据 **/
    private static Map<String, Object> getSenseSuggest(String name) {
        // 拆分名称——正则
        List<String> docNameSuggestList = splitDocName(name);
        // 拆分名称——HanLP
        List<String> hanLpList = HanLP.extractPhrase(docNameSuggestList.get(docNameSuggestList.size()-1), 20);
        docNameSuggestList.addAll(hanLpList);

        Map<String, Object> docNameSuggest = new HashMap<>();
        docNameSuggest.put("weight", 0);
        docNameSuggest.put("contents", docNameSuggestList);
        return docNameSuggest;
    }

    /** 执行 **/
    public static void bulk(BulkRequest bulkRequest) {
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
////                client.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /** 拆分规则 **/
    public static List<String> splitDocName(String docName) {
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
//            System.out.println();
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

    public static Map<String, Object> selectById (Long id, String index) throws IOException {
        GetRequest getRequest = new GetRequest();
        getRequest.id(id + "");
        getRequest.index(index);
        GetResponse response= client.get(getRequest, RequestOptions.DEFAULT);
        return response.getSource();
    }
}
