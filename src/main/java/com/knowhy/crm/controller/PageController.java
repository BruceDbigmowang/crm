package com.knowhy.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/foreCustomerManage")
    public String toCustomerManage(){
        return "pages/customer";
    }

    @RequestMapping("/forePool")
    public String toPool(){
        return "pages/pool";
    }

    @RequestMapping("/foreIntroduce")
    public String toIntroduce(){
        return "pages/introduce";
    }

    @RequestMapping("/foreSecret")
    public String toSecret(){
        return "pages/secret";
    }

    @RequestMapping("/foreSurveyOnline")
    public String tosurveyOnline(){
        return "pages/surveyOnline";
    }

    @RequestMapping("/foreSurveyOffline")
    public String toSurveyOffline(){
        return "pages/surveyOffline";
    }

    @RequestMapping("/foreAccountInner")
    public String toAccountAdd(){
        return "pages/accountAdd";
    }

    @RequestMapping("/foreScheme")
    public String toScheme(){
        return "pages/scheme";
    }

    @RequestMapping("/foreContractPrevious")
    public String toContractFirst(){
        return "pages/contractPrevious";
    }

    @RequestMapping("/foreContractLater")
    public String toContractLater(){
        return "pages/contractLater";
    }
}
