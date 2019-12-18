//package com.zl.study.drools.controller;
//
//import com.pingan.core.entity.UserInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.kie.api.KieBase;
//import org.kie.api.runtime.KieSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
///**
// * @author 周林
// * @Description
// * @email prometheus@noask-ai.com
// * @date 2019/11/25 16:12
// */
//@Slf4j
//@Controller
//public class DroolRuleController {
//
//    @Autowired
//    private KieBase kieBase;
//
//    //http://localhost:8080/rule
//    @RequestMapping("/rule")
//    @ResponseBody
//    public String rule(){
//        //StatefulKnowledgeSession
//        KieSession kieSession = kieBase.newKieSession();
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUsername("superbing");
//        userInfo.setTelephone("13618607409");
//
//        kieSession.insert(userInfo);
//        kieSession.setGlobal("log",log);
//        int firedCount = kieSession.fireAllRules();
//        kieSession.dispose();
//        System.out.println("触发了" + firedCount + "条规则");
//        return "触发了" + firedCount + "条规则";
//    }
//}