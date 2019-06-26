package com.zl.study.traversal.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 描 述: HashSet遍历效率
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/16
 * 版 本: v1.0
 **/
public class AnalyzeArrayListDemo {
    private static void analyzeArrayListMain1(List<Object> list, Object contrastData) {
        long staTime = System.currentTimeMillis();
        boolean contains = list.contains(contrastData);
        System.out.println(contains);
        long endTime = System.currentTimeMillis();
        System.out.println("ArrayList1："+(endTime-staTime)+"毫秒！");

    }
    private static void analyzeArrayListMain2(List<Object> list, Object contrastData) {
        long staTime = System.currentTimeMillis();
        for (Object o : list) {
            if (Objects.equals(o, contrastData)) {
                System.out.println(true);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("ArrayList2："+(endTime-staTime)+"毫秒！");
    }
    private static void analyzeArrayListMain3(List<Object> list, Object contrastData) {
        long staTime = System.currentTimeMillis();
        Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next(), contrastData)) {
                System.out.println(true);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("ArrayList3："+(endTime-staTime)+"毫秒！");
    }
    private static void analyzeArrayListMain4(List<Object> list, Object contrastData) {
        long staTime = System.currentTimeMillis();
        list.forEach(o -> {
            if (Objects.equals(o, contrastData)) {
                System.out.println(true);
            }
        });
        long endTime = System.currentTimeMillis();
        System.out.println("ArrayList4："+(endTime-staTime)+"毫秒！");
    }

    /**
     *  HashSet遍历效率分析
     *      1000以下包括1000效率都差不多
     *      10000 = analyzeArrayListMain1 < analyzeArrayListMain3 < analyzeArrayListMain2 < analyzeArrayListMain4
     *      100000 = analyzeArrayListMain1 < analyzeArrayListMain3 < analyzeArrayListMain2 < analyzeArrayListMain4
     *      1000000 = analyzeArrayListMain1 < analyzeArrayListMain2 < analyzeArrayListMain3 < analyzeArrayListMain4
     *      10000000 = analyzeArrayListMain1 < analyzeArrayListMain2 < analyzeArrayListMain3 < analyzeArrayListMain4
     * @param statisticAmount   添加统计数量
     * @param contrastData      对比值
     */
    public static void analyzeArrayListMain (Integer statisticAmount, Object contrastData) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < statisticAmount; i++) {
            list.add(i);
        }
        analyzeArrayListMain1(list, contrastData);
        analyzeArrayListMain2(list, contrastData);
        analyzeArrayListMain3(list, contrastData);
        analyzeArrayListMain4(list, contrastData);
    }
    public static void analyzeArrayListMain (Object contrastData, List<Object> list) {
        analyzeArrayListMain1(list, contrastData);
        analyzeArrayListMain2(list, contrastData);
        analyzeArrayListMain3(list, contrastData);
        analyzeArrayListMain4(list, contrastData);
    }
}
