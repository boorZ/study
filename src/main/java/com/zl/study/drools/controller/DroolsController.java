package com.zl.study.drools.controller;

import com.zl.study.drools.model.SearchRule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/11/22 16:35
 */
@RestController
public class DroolsController {
    private final KieContainer kieContainer;

    @Autowired
    public DroolsController(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    /*暴露出来的api接口，通过捕获type=进行后续规则*/
    @RequestMapping(value = "/getDiscount", method = RequestMethod.GET, produces = "application/json")
    public SearchRule getQuestions(@RequestParam String searchValue) {

        SearchRule searchRule = new SearchRule();
        searchRule.setSearchValue(searchValue);

        KieSession kieSession = kieContainer.newKieSession("rulesSession");
        kieSession.insert(searchRule);
        kieSession.fireAllRules();
        kieSession.dispose();

        return searchRule;
    }
}
