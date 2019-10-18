package com.zl.study.elasticsearch;

import com.hankcs.hanlp.HanLP;
import com.zl.study.jdbc.c3p0.C3P0Utils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author 周林
 * @Description sense添加数据
 * @email prometheus@noask-ai.com
 * @date 2019/10/18
 */
public class SenseAddDataUtils {
    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

    /**
     * 添加sense（法律法规）数据
     */
    @Test
    public void addSenseFlfg () {
    }
    /**
     * 添加sense（法律法规）建议数据
     */
    @Test
    public void addSenseFlfgSuggest () {
        try {
            ResultSet rs = C3P0Utils.getConnection("SELECT * FROM `t_doc_info`");
            BulkRequest bulkRequest = new BulkRequest();
            while (rs.next()) {
                long docId = rs.getLong("doc_info_id");
                String docName = rs.getString("doc_name");
                List<String> docNameSuggestList = splitDocName(docName);
                List<String> strings = HanLP.extractPhrase(docNameSuggestList.get(docNameSuggestList.size()-1), 20);
                docNameSuggestList.addAll(strings);
                Map<String, Object> docNameSuggest = new HashMap<>();
                docNameSuggest.put("weight", 0);
                docNameSuggest.put("contents", docNameSuggestList);
                getBulkRequest(bulkRequest, docId+"", docName, docNameSuggest);
            }
            addSuggest(bulkRequest);
            rs.close();
            C3P0Utils.close();
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    public static void getBulkRequest(BulkRequest bulkRequest, String docId, String docName, Map<String, Object> docNameSuggest) {
        String index = "sense_suggest";
        bulkRequest.add(new IndexRequest(index).id(docId)
                .source(XContentType.JSON,"docId", docId, "docName", docName, "docNameSuggest", docNameSuggest));
    }
    public static void addSuggest (BulkRequest bulkRequest) {
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 拆分规则 */
    public List<String> splitDocName (String docName) {
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

}
