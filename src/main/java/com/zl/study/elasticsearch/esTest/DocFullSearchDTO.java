package com.zl.study.elasticsearch.esTest;

import java.util.List;
import java.util.Map;

/**
 * @author 周林
 * @Description ES全文检索数据传输对象
 * @email prometheus@noask-ai.com
 * @date 2019/10/17
 */
public class DocFullSearchDTO {
    /** 文档id */
    private Long docId;
    /** 编号 */
    private String serialNumber;
    /** 来源 */
    private String source;
    /** 文档分类 */
    private String docType;
    /** 文档名称 */
    private String docName;
    /** 文书字号 */
    private String writNo;
    /** 文档简介 */
    private String docInfo;
    /** 发文机关 */
    private String dispatchUnit;
    /** 发文时间 */
    private String dispatchTime;
    /** 有效状态 */
    private String effectiveStatus;
    /** 分类索引 **/
    private List<Map<String, String>> classificationIndex;
    /** 规范类型代码 **/
    private String normTypeCode;
    /** 规范类型名称 **/
    private String normTypeName;
    /** 标签 **/
    private List<DocFullLabelDTO> labelList;

    public List<DocFullLabelDTO> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<DocFullLabelDTO> labelList) {
        this.labelList = labelList;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getWritNo() {
        return writNo;
    }

    public void setWritNo(String writNo) {
        this.writNo = writNo;
    }

    public String getDocInfo() {
        return docInfo;
    }

    public void setDocInfo(String docInfo) {
        this.docInfo = docInfo;
    }

    public String getDispatchUnit() {
        return dispatchUnit;
    }

    public void setDispatchUnit(String dispatchUnit) {
        this.dispatchUnit = dispatchUnit;
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(String effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    public List<Map<String, String>> getClassificationIndex() {
        return classificationIndex;
    }

    public void setClassificationIndex(List<Map<String, String>> classificationIndex) {
        this.classificationIndex = classificationIndex;
    }

    public String getNormTypeCode() {
        return normTypeCode;
    }

    public void setNormTypeCode(String normTypeCode) {
        this.normTypeCode = normTypeCode;
    }

    public String getNormTypeName() {
        return normTypeName;
    }

    public void setNormTypeName(String normTypeName) {
        this.normTypeName = normTypeName;
    }

}
