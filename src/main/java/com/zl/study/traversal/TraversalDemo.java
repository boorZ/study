package com.zl.study.traversal;

import com.zl.study.traversal.demo.AnalyzeArrayListDemo;
import com.zl.study.traversal.demo.bean.PrePluginMiniJoke1Entity;
import com.zl.study.traversal.demo.repository.PrePluginMiniJoke1Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 描 述: 请描述功能
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/16
 * 版 本: v1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application.properties"})
@SpringBootTest
public class TraversalDemo {
    @Autowired
    private PrePluginMiniJoke1Repository repository;

    @Test
    public void testDemo () {
        List<Object> list = new ArrayList<>();
        List<PrePluginMiniJoke1Entity> repositoryList = repository.findAll();
        Object contrastData = "龙";
        repositoryList.forEach(pre -> list.add(pre.getTitle()));
        AnalyzeArrayListDemo.analyzeArrayListMain(contrastData, list);

    }
    private static void main(String[] args) {
        // HashSet
//        AnalyzeHashSetDemo.analyzeHashSetMain(1, 1);
        // ArrayList
        AnalyzeArrayListDemo.analyzeArrayListMain(10000000, 1);


    }
}