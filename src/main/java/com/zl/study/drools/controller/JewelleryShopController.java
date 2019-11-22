package com.zl.study.drools.controller;

import com.zl.study.drools.model.Product;
import com.zl.study.drools.service.JewelleryShopService;
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
public class JewelleryShopController {
    private final JewelleryShopService jewelleryShopService;

    @Autowired
    public JewelleryShopController(JewelleryShopService jewelleryShopService) {
        this.jewelleryShopService = jewelleryShopService;
    }
    /*暴露出来的api接口，通过捕获type=进行后续规则*/
    @RequestMapping(value = "/getDiscount", method = RequestMethod.GET, produces = "application/json")
    public Product getQuestions(@RequestParam(required = true) String type) {

        Product product = new Product();
        product.setType(type);

        jewelleryShopService.getProductDiscount(product);

        return product;
    }
}
