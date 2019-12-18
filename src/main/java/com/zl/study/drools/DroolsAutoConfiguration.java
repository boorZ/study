//package com.zl.study.drools;
//
//import org.kie.api.KieBase;
//import org.kie.api.KieServices;
//import org.kie.api.builder.*;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
//import org.kie.internal.io.ResourceFactory;
////import org.kie.spring.KModuleBeanFactoryPostProcessor;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//import java.io.IOException;
//
///**
// * @author 周林
// * @Description
// * @email prometheus@noask-ai.com
// * @date 2019/11/25 14:46
// */
//@Configuration
//public class DroolsAutoConfiguration {
//
//    private static final String RULES_PATH = "rules/";
//
//    @Bean
//    @ConditionalOnMissingBean(KieFileSystem.class)
//    public KieFileSystem kieFileSystem() throws IOException {
//        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
//        for (Resource file : getRuleFiles()) {
//            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
//        }
//        return kieFileSystem;
//    }
//
//    private Resource[] getRuleFiles() throws IOException {
//        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//        return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(KieContainer.class)
//    public KieContainer kieContainer() throws IOException {
//        final KieRepository kieRepository = getKieServices().getRepository();
//
//        kieRepository.addKieModule(() -> kieRepository.getDefaultReleaseId());
//
//        KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
//        kieBuilder.buildAll();
//
//        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
//    }
//
//    private KieServices getKieServices() {
//        return KieServices.Factory.get();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(KieBase.class)
//    public KieBase kieBase() throws IOException {
//        return kieContainer().getKieBase();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(KieSession.class)
//    public KieSession kieSession() throws IOException {
//        return kieContainer().newKieSession();
//    }
//
//    //下面这个需要额外导入kie-spring依赖
////        <dependency>
////            <groupId>org.kie</groupId>
////            <artifactId>kie-spring</artifactId>
////            <version>6.5.0.Final</version>
////        </dependency>
////    @Bean
////    @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
////    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
////        return new KModuleBeanFactoryPostProcessor();
////    }
//}