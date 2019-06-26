package com.zl.study.traversal.demo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * 描 述: HashSet遍历效率
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/16
 * 版 本: v1.0
 **/
public class AnalyzeHashSetDemo {
    private static void analyzeHashSetMain1(Set<Object> set, Object contrastData) {
        long staTime = System.currentTimeMillis();
        boolean contains = set.contains(contrastData);
        System.out.println(contains);
        long endTime = System.currentTimeMillis();
        System.out.println("HashSet3："+(endTime-staTime)+"毫秒！");

    }
    private static void analyzeHashSetMain2(Set<Object> set, Object contrastData) {
        long staTime = System.currentTimeMillis();
        for (Object o : set) {
            if (Objects.equals(o, contrastData)) {
                System.out.println(true);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("HashSet2："+(endTime-staTime)+"毫秒！");
    }
    private static void analyzeHashSetMain3(Set<Object> set, Object contrastData) {
        long staTime = System.currentTimeMillis();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next(), contrastData)) {
                System.out.println(true);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("HashSet3："+(endTime-staTime)+"毫秒！");

    }

    /**
     *  ArrayList遍历效率分析
     *      1000以下包括1000效率都差不多
     *      10000 = analyzeHashSetMain1 < analyzeHashSetMain3 < analyzeHashSetMain2
     *      100000 = analyzeHashSetMain1 < analyzeHashSetMain3 < analyzeHashSetMain2
     *      1000000 = analyzeHashSetMain1 < （analyzeHashSetMain3 和 analyzeHashSetMain2差不多，但是analyzeHashSetMain3 < analyzeHashSetMain2 的次数多些）
     *      10000000 = analyzeHashSetMain1 < analyzeHashSetMain2 < analyzeHashSetMain3
     * @param statisticAmount   添加统计数量
     * @param contrastData      对比值
     */
    public static void analyzeHashSetMain (Integer statisticAmount, Object contrastData) {
        Set<Object> list = new HashSet<>();
        for (int i = 0; i < statisticAmount; i++) {
            list.add(i);
        }
        analyzeHashSetMain1(list, contrastData);
        analyzeHashSetMain2(list, contrastData);
        analyzeHashSetMain3(list, contrastData);
    }
}
