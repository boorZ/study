package com.zl.study.drools.model;

import com.zl.study.drools.enums.SearchEnum;

import java.util.List;

/**
 * @author 周林
 * @Description Product实体，有类型与折扣两个类型
 * @email prometheus@noask-ai.com
 * @date 2019/11/22 16:34
 */
public class SearchRule {
    private String searchValue;
    private Integer docType;
    private List<SearchEnum> searchEnumList;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public List<SearchEnum> getSearchEnumList() {
        return searchEnumList;
    }

    public void setSearchEnumList(List<SearchEnum> searchEnumList) {
        this.searchEnumList = searchEnumList;
    }
}
