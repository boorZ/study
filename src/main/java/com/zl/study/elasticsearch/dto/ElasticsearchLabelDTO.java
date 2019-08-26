package com.zl.study.elasticsearch.dto;

/**
 * @author 周林
 * @Description 文档全文检索标签数据传输对象
 * @email prometheus@noask-ai.com
 * @date 2019/8/19
 */
public class ElasticsearchLabelDTO {
    /**
     * label_id :  标签id
     */
    private Long labelId;

    /**
     * label_name :  标签名称
     */
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

    public ElasticsearchLabelDTO() {
    }

    public ElasticsearchLabelDTO(Long labelId, String labelName) {
        this.labelId = labelId;
        this.labelName = labelName;
    }
}
