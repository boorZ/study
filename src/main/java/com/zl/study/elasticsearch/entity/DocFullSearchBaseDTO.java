package com.zl.study.elasticsearch.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/10/22
 */
public class DocFullSearchBaseDTO implements Serializable {
    /** 文档id */
    private Long docId;
    /** 文档分类 */
    private String docType;
    /** 文档名称 */
    private String docName;
    /** 有效状态 */
    private String effectiveStatus;
    /** 编号 **/
    private String serialNumber;
    /** 标签 **/
    private List<DocFullLabelDTO> labelList;

    /** 文档简介 */
    private String docInfo;
    /** 文号 */
    private String writNo;
    /** 发布时间 */
    private String dispatchTime;
    /** 来源 */
    private String source;
    /** 分类索引 */
    private List<Map<String, String>> classificationIndex;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getWritNo() {
        return writNo;
    }

    public void setWritNo(String writNo) {
        this.writNo = writNo;
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Map<String, String>> getClassificationIndex() {
        return classificationIndex;
    }

    public void setClassificationIndex(List<Map<String, String>> classificationIndex) {
        this.classificationIndex = classificationIndex;
    }

    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(String effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocInfo() {
        return docInfo;
    }

    public void setDocInfo(String docInfo) {
        this.docInfo = docInfo;
    }

    public List<DocFullLabelDTO> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<DocFullLabelDTO> labelList) {
        this.labelList = labelList;
    }
}
