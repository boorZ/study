package com.zl.study.elasticsearch.entity;

import java.io.Serializable;

/**
 * @author 周林
 * @Description ES全文检索搜索条件值 数据传输对象
 * @email prometheus@noask-ai.com
 * @date 2019/8/22
 */
public class DocSearchLabelDTO implements Serializable {
    /** 标签id */
    private Long labelId;
    /** 标签名称 */
    private String labelName;

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public DocSearchLabelDTO() {
    }

    public DocSearchLabelDTO(Long labelId, String labelName) {
        this.labelId = labelId;
        this.labelName = labelName;
    }
}
