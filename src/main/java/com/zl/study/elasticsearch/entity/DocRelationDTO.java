package com.zl.study.elasticsearch.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 周林
 * @description
 * @email prometheus@noask-ai.com
 * @date 2019/10/22
 */
public class DocRelationDTO implements Serializable {
    private Long docId;
    private Map<String, Object> dataMap;

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
