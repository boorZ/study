package com.zl.study.elasticsearch.dto;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/8/23
 */
public class ElasticsearchSerachCountryDTO {
    private Long id;
    private String name;
    private Long heat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHeat() {
        return heat;
    }

    public void setHeat(Long heat) {
        this.heat = heat;
    }

    public ElasticsearchSerachCountryDTO() {
    }

    public ElasticsearchSerachCountryDTO(Long id, String name, Long heat) {
        this.id = id;
        this.name = name;
        this.heat = heat;
    }
}
