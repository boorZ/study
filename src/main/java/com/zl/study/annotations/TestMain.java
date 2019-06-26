package com.zl.study.annotations;

import java.lang.reflect.Method;

/**
 * 描 述: 请描述功能
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/24 0024
 * 版 本: v1.0
 **/
public class TestMain {
    public static void main(String[] args) {
        Class clazz = TestServletImpl.class;
        // 获取注解类对象
        Class anno = TestAnnotation.class;

        /* =============================================================================== */
        // 通过反射判断类上是否加了指定的注解
        if (clazz.isAnnotationPresent(anno)) {
            // 通过反射获取注解的实例
            TestAnnotation annotation = (TestAnnotation) clazz.getAnnotation(anno);
            // 打印注解携带的信息
            System.out.println(annotation.value());
        }

        /* =============================================================================== */
        // 通过反射获取类里面的所有方法
        Method[] methods = clazz.getMethods();
        // 遍历所有方法，判断方法上是否加了指定的注解
        for (Method method : methods) {
            if (method.isAnnotationPresent(anno)) {
                // 获取注解的实例
                TestAnnotation annotation = (TestAnnotation)method.getAnnotation(anno);
                // 打印注解携带的信息
                System.out.println(annotation.value());
            }
        }

        /* =============================================================================== */

    }
}