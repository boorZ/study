package com.zl.study.elasticsearch.dto;


import java.util.List;

/**
 * @author 周林
 * @Description 文档全文检索数据传输对象
 * @email prometheus@noask-ai.com
 * @date 8/19/2019
 */
public class ElasticsearchDTO {
    /**标签*/
    private List<ElasticsearchLabelDTO> labelList;
    /**文档id*/
    private Long id;
    /**文档编号*/
    private String docNumber;
    /**文档名称*/
    private String docName;
    /**文档标题*/
    private String docTitle;
    /**文档简介*/
    private String docInfo;
    /**文档摘要*/
    private String docDigest;
    /**文档内容*/
    private String docContent;
    /**文档状态*/
    private String docStatus;
    /**文档类型*/
    private String docTypeName;
    /**文档版本*/
    private String docVersionId;
    /**文档字号*/
    private String docWritNo;
    /**文档发文单位*/
    private String docDispatchUnit;
    /**文档发文时间*/
    private String docDispatchTime;
    /**文档失效状态*/
    private String docDisabledStatus;
    /**文档失效时间*/
    private String docDisabledTime;
    /**访问量*/
    private String pageView;
    /**点击量*/
    private String clickView;
    /**热度*/
    private String heat;
    /**排序字段*/
    private String sortField;

    public List<ElasticsearchLabelDTO> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<ElasticsearchLabelDTO> labelList) {
        this.labelList = labelList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocInfo() {
        return docInfo;
    }

    public void setDocInfo(String docInfo) {
        this.docInfo = docInfo;
    }

    public String getDocDigest() {
        return docDigest;
    }

    public void setDocDigest(String docDigest) {
        this.docDigest = docDigest;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public String getDocVersionId() {
        return docVersionId;
    }

    public void setDocVersionId(String docVersionId) {
        this.docVersionId = docVersionId;
    }

    public String getDocWritNo() {
        return docWritNo;
    }

    public void setDocWritNo(String docWritNo) {
        this.docWritNo = docWritNo;
    }

    public String getDocDispatchUnit() {
        return docDispatchUnit;
    }

    public void setDocDispatchUnit(String docDispatchUnit) {
        this.docDispatchUnit = docDispatchUnit;
    }

    public String getDocDispatchTime() {
        return docDispatchTime;
    }

    public void setDocDispatchTime(String docDispatchTime) {
        this.docDispatchTime = docDispatchTime;
    }

    public String getDocDisabledStatus() {
        return docDisabledStatus;
    }

    public void setDocDisabledStatus(String docDisabledStatus) {
        this.docDisabledStatus = docDisabledStatus;
    }

    public String getDocDisabledTime() {
        return docDisabledTime;
    }

    public void setDocDisabledTime(String docDisabledTime) {
        this.docDisabledTime = docDisabledTime;
    }

    public String getPageView() {
        return pageView;
    }

    public void setPageView(String pageView) {
        this.pageView = pageView;
    }

    public String getClickView() {
        return clickView;
    }

    public void setClickView(String clickView) {
        this.clickView = clickView;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
}
