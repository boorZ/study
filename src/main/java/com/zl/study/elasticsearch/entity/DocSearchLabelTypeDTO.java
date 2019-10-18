package com.zl.study.elasticsearch.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 周林
 * @Description ES全文检索搜索条件值 数据传输对象
 * @email prometheus@noask-ai.com
 * @date 2019/8/22
 */
public class DocSearchLabelTypeDTO implements Serializable {
    /** 标签分类id */
    private Long labelTypeId;
    /** 标签分类名称 */
    private String labelTypeName;
    /** 标签分类名称 */
    private List<DocSearchLabelDTO> labelList;

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

    public List<DocSearchLabelDTO> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<DocSearchLabelDTO> labelList) {
        this.labelList = labelList;
    }

    public DocSearchLabelTypeDTO() {
    }

    public DocSearchLabelTypeDTO(Long labelTypeId, String labelTypeName) {
        this.labelTypeId = labelTypeId;
        this.labelTypeName = labelTypeName;
    }

    public DocSearchLabelTypeDTO(Long labelTypeId, String labelTypeName, List<DocSearchLabelDTO> labelList) {
        this.labelTypeId = labelTypeId;
        this.labelTypeName = labelTypeName;
        this.labelList = labelList;
    }

    @Override
    public String toString() {
        return "{" +
                "labelTypeId=" + labelTypeId +
                ", labelTypeName='" + labelTypeName + '\'' +
                ", labelList=" + labelList +
                '}';
    }
}
