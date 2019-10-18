package com.zl.study.elasticsearch.entity;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/10/16
 */
public class DocFullLabelDTO {
    private Long labelId;
    private String labelName;
    private Long labelTypeId;
    private String labelTypeName;

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

    public Long getLabelTypeId() {
        return labelTypeId;
    }

    public void setLabelTypeId(Long labelTypeId) {
        this.labelTypeId = labelTypeId;
    }

    public String getLabelTypeName() {
        return labelTypeName;
    }

    public void setLabelTypeName(String labelTypeName) {
        this.labelTypeName = labelTypeName;
    }
}