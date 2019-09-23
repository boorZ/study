package com.zl.study.jdbc.bean;

import java.util.List;

/**
 * @author 周林
 * @Description 目标和源节点
 * @email prometheus@noask-ai.com
 * @date 2019/9/23
 */
public class ToAndFromListBO {
    private List<Long> toNodeIdList;
    private List<Long> fromNodeIdList;

    public List<Long> getToNodeIdList() {
        return toNodeIdList;
    }

    public void setToNodeIdList(List<Long> toNodeIdList) {
        this.toNodeIdList = toNodeIdList;
    }

    public List<Long> getFromNodeIdList() {
        return fromNodeIdList;
    }

    public void setFromNodeIdList(List<Long> fromNodeIdList) {
        this.fromNodeIdList = fromNodeIdList;
    }
}
