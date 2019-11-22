package com.zl.study.drools.service;

import com.zl.study.drools.model.Product;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/11/22 16:36
 */
@Service
public class JewelleryShopService {
    /*这个类好像是配合Drools使用的加载kmodul.xml规则*/
    private final KieContainer kieContainer;

    @Autowired
    public JewelleryShopService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public Product getProductDiscount(Product product) {
        KieSession kieSession = kieContainer.newKieSession("rulesSession");
        kieSession.insert(product);
        kieSession.fireAllRules();
        kieSession.dispose();
        return product;
    }
}
