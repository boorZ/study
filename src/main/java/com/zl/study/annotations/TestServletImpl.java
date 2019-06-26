package com.zl.study.annotations;

import org.springframework.stereotype.Service;

/**
 * 描 述: 请描述功能
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/24 0024
 * 版 本: v1.0
 **/
@Service
@TestAnnotation(value="普通类", okIs = false)
public class TestServletImpl {

    @TestAnnotation(value="成员变量")
    private String testName;

    @TestAnnotation(value="方法")
    public void testServletImpl() {
        System.out.println("接口实现类！");
    }
}
