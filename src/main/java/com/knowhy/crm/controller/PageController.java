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

    @RequestMapping("/foreCustomerInfo")
    public String toCustomerInfo(){
        return "pages/customerInfo";
    }

    @RequestMapping("/foreAddFile")
    public String toAddFile(){
        return "pages/addFile";
    }

    @RequestMapping("/foreExtraFile")
    public String toExtraFile(){
        return "pages/extraFile";
    }

    @RequestMapping("/foreContractSum")
    public String toContractSum(){
        return "pages/contractSum";
    }

    @RequestMapping("/foreSchedule")
    public String toSchedule(){
        return "pages/schedule";
    }

    @RequestMapping("/foreOperate")
    public String toOperate(){
        return "pages/operate";
    }

    @RequestMapping("/foreAddOpportunity")
    public String toAddOpportunity(){
        return "pages/addOpportunity";
    }

    @RequestMapping("/foreUpdateOpportunity")
    public String toUpdateOpportunity(){
        return "pages/updateOpportunity";
    }

    @RequestMapping("/foreMarketingOnline")
    public String toMarketingOnline(){
        return "pages/marketingOnline";
    }

    @RequestMapping("/foreMarketingOffline")
    public String toMarketingOffline(){
        return "pages/marketingOffline";
    }

    @RequestMapping("/foreMarketSpend")
    public String toMarketSpend(){
        return "pages/marketSpend";
    }

    @RequestMapping("/foreTechnicalService")
    public String toTechnicalService(){
        return "pages/technicalService";
    }

    @RequestMapping("/foreMonthlyReport")
    public String toMonthlyReport(){
        return "pages/monthlyReport";
    }

    @RequestMapping("/foreAllRoles")
    public String toAllRoles(){
        return "pages/rolesManage";
    }

    @RequestMapping("/foreArrangeSale")
    public String toArrangeSale(){
        return "pages/saleArrange";
    }

    @RequestMapping("/foreContractManage")
    public String toContractManage(){
        return "pages/contractManage";
    }

    @RequestMapping("/foreAddressManage")
    public String toAddressManage(){
        return "pages/addressManage";
    }

    @RequestMapping("/foreSatisfaction")
    public String toSatisfaction(){
        return "pages/satisfaction";
    }

    @RequestMapping("/foreAllArea")
    public String toArea(){
        return "pages/areas";
    }

    @RequestMapping("/foreUserCenter")
    public String toUserCenter(){
        return "pages/accountCenter";
    }
}
