package com.zl.study.elasticsearch.bean;

/**
 * @author 周林
 * @Description 搜索测试Bean
 * @email prometheus@noask-ai.com
 * @date 2019/8/17
 */
public class SearchBean {
    private String id;
    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
