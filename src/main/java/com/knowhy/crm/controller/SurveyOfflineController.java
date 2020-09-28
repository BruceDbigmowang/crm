package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class SurveyOfflineController {

    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    AssetDAO assetDAO;
    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    StockDAO stockDAO;
    @Autowired
    PayCycleDAO payCycleDAO;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    SaleAmountDAO saleAmountDAO;
    @Autowired
    PartListDAO partListDAO;
    @Autowired
    KnifeListDAO knifeListDAO;
    @Autowired
    SupplierDAO supplierDAO;
    @Autowired
    BuyListDAO buyListDAO;
    @Autowired
    ReceiveListDAO receiveListDAO;

    @RequestMapping("/saveAssetList")
    @Transactional
    public String toSaveAsset(String salePlanID , String[] assetTypes , String[] assetNums , String[] assetNames , String[] models , String[] brands , String[] suppliers , String[] positions , String[] amounts , String[] residuals, String[] status , String[] ports , String[] coolWays , String[] notes){
        for(int i = 0 ; i < assetTypes.length ; i++){
            int num = i+1;
            Asset asset = new Asset();
            asset.setSalePlanID(salePlanID);
            asset.setAssetType(assetTypes[i]);
            if(assetNums[i] != null && !"".equals(assetNums[i])){
                asset.setAssetNum(assetNums[i]);
            }else{
                return "第"+num+"行设备编号未填写";
            }
            if(assetNames[i] == null || "".equals(assetNames[i])){
                return "第"+num+"行设备名称未填写";
            }else{
                asset.setAssetName(assetNames[i]);
            }
            if(!"".equals(models[i])){
                asset.setModel(models[i]);
            }
            if(!"".equals(brands[i])){
                asset.setBrand(brands[i]);
            }
            if(!"".equals(suppliers[i])){
                asset.setSupplier(suppliers[i]);
            }
            if(!"".equals(positions)){
                asset.setPosition(positions[i]);
            }
            if(!"".equals(amounts[i])){
                try{
                    asset.setAmount(Integer.parseInt(amounts[i]));
                }catch (Exception e){
                    return "第"+num+"行数量填写错误";
                }
            }
            if(!"".equals(residuals[i])){
                try{
                    asset.setResidual(new BigDecimal(residuals[i]));
                }catch (Exception e){
                    return "第"+num+"行预计残值填写错误";
                }
            }
            if(!"".equals(status[i])){
                asset.setStatus(status[i]);
            }
            if(!"".equals(ports[i])){
                asset.setPort(ports[i]);
            }
            if(!"".equals(coolWays[i])){
                asset.setCoolWay(coolWays[i]);
            }
            if(!"".equals(notes[i])){
                asset.setNote(notes[i]);
            }
            assetDAO.save(asset);
            SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
            salesPlan.setStep(2);
            salesPlanDAO.save(salesPlan);
        }
        return "OK";
    }

    @RequestMapping("/saveEmployeeList")
    @Transactional
    public String toSaveEmployee(String salePlanID , String[] accounts , String[] employeeNames , String[] identityIDs , String[] educations , String[] majors , String[] enters , String[] sexs , String[] depts , String[] contacts , String[] relevanceAssets , String[] relevanceParts , String[] notes){
        for(int i = 0 ; i < accounts.length ; i++){
            int num = i + 1;
            Employee employee = new Employee();
            employee.setSalePlanID(salePlanID);
            if(!"".equals(accounts[i])){
                employee.setAccount(accounts[i]);
            }else{
                return "第"+num+"行，请输入员工工号";
            }
            if(!"".equals(employeeNames[i])){
                employee.setName(employeeNames[i]);
            }else{
                return "第"+num+"行，请输入员工姓名";
            }
            if(!"".equals(identityIDs[i])){
                employee.setIdentityNum(identityIDs[i]);
            }
            if(!"".equals(educations[i])){
                employee.setEducation(educations[i]);
            }
            if(!"".equals(majors[i])){
                employee.setMajor(majors[i]);
            }
            if(!"".equals(enters[i])){
                employee.setEnter(enters[i]);
            }
            if(!"".equals(sexs[i])){
                employee.setSex(sexs[i]);
            }
            if(!"".equals(depts[i])){
                employee.setDept(depts[i]);
            }
            if(!"".equals(contacts[i])){
                employee.setContact(contacts[i]);
            }
            if(!"".equals(relevanceAssets[i])){
                employee.setRelevanceAsset(relevanceAssets[i]);
            }
            if(!"".equals(relevanceParts[i])){
                employee.setRelevancePart(relevanceParts[i]);
            }
            if(!"".equals(notes[i])){
                employee.setNote(notes[i]);
            }

            employeeDAO.save(employee);
        }
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        salesPlan.setStep(3);
        salesPlanDAO.save(salesPlan);
        return "OK";
    }

    @RequestMapping("/saveStockList")
    @Transactional
    public String toSaveStock(String salePlanID , String[] frock , String[] spare , String[] chemical , String[] other){
        BigDecimal sum1 = new BigDecimal(0);
        BigDecimal sum2 = new BigDecimal(0);
        BigDecimal sum3 = new BigDecimal(0);
        BigDecimal sum4 = new BigDecimal(0);
        Stock stock = new Stock();
        stock.setSalePlanID(salePlanID);
        stock.setType("工装模具配件");
        BigDecimal frockone = new BigDecimal(0);
        if(!"".equals(frock[0])){
            try{
                frockone = frockone.add(new BigDecimal(frock[0]));
            }catch (Exception e){
                return "工装模具配件，库龄0-30天填写错误（只能填写数字）";
            }
        }
        sum1 = sum1.add(frockone);
        stock.setNumone(frockone);
        BigDecimal frocktwo = new BigDecimal(0);
        if(!"".equals(frock[1])){
            try{
                frocktwo = frocktwo.add(new BigDecimal(frock[1]));
            }catch (Exception e){
                return "工装模具配件，库龄31-120天填写错误（只能填写数字）";
            }
        }
        sum1 = sum1.add(frocktwo);
        stock.setNumtwo(frocktwo);
        BigDecimal frockthree = new BigDecimal(0);
        if(!"".equals(frock[2])){
            try{
                frockthree = frockthree.add(new BigDecimal(frock[2]));
            }catch (Exception e){
                return "工装模具配件，库龄121-360填写错误（只能填写数字）";
            }
        }
        sum1 = sum1.add(frockthree);
        stock.setNumthree(frockthree);
        BigDecimal frockfour = new BigDecimal(0);
        if(!"".equals(frock[3])){
            try{
                frockfour = frockfour.add(new BigDecimal(frock[3]));
            }catch (Exception e){
                return "工装模具配件，库龄361-720填写错误（只能填写数字）";
            }
        }
        sum1 = sum1.add(frockfour);
        stock.setNumfour(frockfour);
        BigDecimal frockfive = new BigDecimal(0);
        if(!"".equals(frock[4])){
            try{
                frockfive = frockfive.add(new BigDecimal(frock[4]));
            }catch (Exception e){
                return "工装模具配件，库龄>720填写错误（只能填写数字）";
            }
        }
        sum1 = sum1.add(frockfive);
        stock.setNumfive(frockfive);
        stock.setSum(sum1);
//-------------------------------------------------
        Stock stock2 = new Stock();
        stock2.setSalePlanID(salePlanID);
        stock2.setType("备品备件");
        BigDecimal spareone = new BigDecimal(0);
        if(!"".equals(spare[0])){
            try{
                spareone = spareone.add(new BigDecimal(spare[0]));
            }catch (Exception e){
                return "备品备件，库龄0-30天填写错误（只能填写数字）";
            }
        }
        sum2 = sum2.add(spareone);
        stock2.setNumone(spareone);
        BigDecimal sparetwo = new BigDecimal(0);
        if(!"".equals(spare[1])){
            try{
                sparetwo = sparetwo.add(new BigDecimal(spare[1]));
            }catch (Exception e){
                return "备品备件，库龄31-120天填写错误（只能填写数字）";
            }
        }
        sum2 = sum2.add(sparetwo);
        stock2.setNumtwo(sparetwo);
        BigDecimal sparethree = new BigDecimal(0);
        if(!"".equals(spare[2])){
            try{
                sparethree = sparethree.add(new BigDecimal(spare[2]));
            }catch (Exception e){
                return "备品备件，库龄121-360填写错误（只能填写数字）";
            }
        }
        sum2 = sum2.add(sparethree);
        stock2.setNumthree(sparethree);
        BigDecimal sparefour = new BigDecimal(0);
        if(!"".equals(spare[3])){
            try{
                sparefour = sparefour.add(new BigDecimal(spare[3]));
            }catch (Exception e){
                return "备品备件，库龄361-720填写错误（只能填写数字）";
            }
        }
        sum2 = sum2.add(sparefour);
        stock2.setNumfour(sparefour);
        BigDecimal sparefive = new BigDecimal(0);
        if(!"".equals(spare[4])){
            try{
                sparefive = sparefive.add(new BigDecimal(spare[4]));
            }catch (Exception e){
                return "备品备件，库龄>720填写错误（只能填写数字）";
            }
        }
        sum2 = sum2.add(sparefive);
        stock2.setNumfive(sparefive);
        stock2.setSum(sum2);

        //-------------------------------------------------
        Stock stock3 = new Stock();
        stock3.setSalePlanID(salePlanID);
        stock3.setType("化学品");
        BigDecimal chemicalone = new BigDecimal(0);
        if(!"".equals(chemical[0])){
            try{
                chemicalone = chemicalone.add(new BigDecimal(chemical[0]));
            }catch (Exception e){
                return "化学品，库龄0-30天填写错误（只能填写数字）";
            }
        }
        sum3 = sum3.add(chemicalone);
        stock3.setNumone(chemicalone);
        BigDecimal chemicaltwo = new BigDecimal(0);
        if(!"".equals(chemical[1])){
            try{
                chemicaltwo = chemicaltwo.add(new BigDecimal(chemical[1]));
            }catch (Exception e){
                return "化学品，库龄31-120天填写错误（只能填写数字）";
            }
        }
        sum3 = sum3.add(chemicaltwo);
        stock3.setNumtwo(chemicaltwo);
        BigDecimal chemicalthree = new BigDecimal(0);
        if(!"".equals(chemical[2])){
            try{
                chemicalthree = chemicalthree.add(new BigDecimal(chemical[2]));
            }catch (Exception e){
                return "化学品，库龄121-360填写错误（只能填写数字）";
            }
        }
        sum3 = sum3.add(chemicalthree);
        stock3.setNumthree(chemicalthree);
        BigDecimal chemicalfour = new BigDecimal(0);
        if(!"".equals(chemical[3])){
            try{
                chemicalfour = chemicalfour.add(new BigDecimal(chemical[3]));
            }catch (Exception e){
                return "化学品，库龄361-720填写错误（只能填写数字）";
            }
        }
        sum3 = sum3.add(chemicalfour);
        stock3.setNumfour(chemicalfour);
        BigDecimal chemicalfive = new BigDecimal(0);
        if(!"".equals(chemical[4])){
            try{
                chemicalfive = chemicalfive.add(new BigDecimal(chemical[4]));
            }catch (Exception e){
                return "化学品，库龄>720填写错误（只能填写数字）";
            }
        }
        sum3 = sum3.add(chemicalfive);
        stock3.setNumfive(chemicalfive);
        stock3.setSum(sum3);

        //-------------------------------------------------
        Stock stock4 = new Stock();
        stock4.setSalePlanID(salePlanID);
        stock4.setType("辅料");
        BigDecimal otherone = new BigDecimal(0);
        if(!"".equals(other[0])){
            try{
                otherone = otherone.add(new BigDecimal(other[0]));
            }catch (Exception e){
                return "辅料，库龄0-30天填写错误（只能填写数字）";
            }
        }
        sum4 = sum4.add(otherone);
        stock4.setNumone(otherone);
        BigDecimal othertwo = new BigDecimal(0);
        if(!"".equals(other[1])){
            try{
                othertwo = othertwo.add(new BigDecimal(other[1]));
            }catch (Exception e){
                return "辅料，库龄31-120天填写错误（只能填写数字）";
            }
        }
        sum4 = sum4.add(othertwo);
        stock4.setNumtwo(othertwo);
        BigDecimal otherthree = new BigDecimal(0);
        if(!"".equals(other[2])){
            try{
                otherthree = otherthree.add(new BigDecimal(other[2]));
            }catch (Exception e){
                return "辅料，库龄121-360填写错误（只能填写数字）";
            }
        }
        sum4 = sum4.add(otherthree);
        stock4.setNumthree(otherthree);
        BigDecimal otherfour = new BigDecimal(0);
        if(!"".equals(other[3])){
            try{
                otherfour = otherfour.add(new BigDecimal(other[3]));
            }catch (Exception e){
                return "辅料，库龄361-720填写错误（只能填写数字）";
            }
        }
        sum4 = sum4.add(otherfour);
        stock4.setNumfour(otherfour);
        BigDecimal otherfive = new BigDecimal(0);
        if(!"".equals(other[4])){
            try{
                otherfive = otherfive.add(new BigDecimal(other[4]));
            }catch (Exception e){
                return "辅料，库龄>720填写错误（只能填写数字）";
            }
        }
        sum4 = sum4.add(otherfive);
        stock4.setNumfive(otherfive);
        stock4.setSum(sum4);

        stockDAO.save(stock);
        stockDAO.save(stock2);
        stockDAO.save(stock3);
        stockDAO.save(stock4);

        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        salesPlan.setStep(4);
        salesPlanDAO.save(salesPlan);

        return "OK";
    }

    @RequestMapping("/savePayCycle")
    @Transactional
    public String tosavePay(String salePlanID , String payone , String paytwo , String paythree , String payfour){
        PayCycle pay = new PayCycle();
        pay.setSalePlanID(salePlanID);
        if("".equals(payone)){
            return "请输入预付款金额";
        }else{
            try{
                BigDecimal one = new BigDecimal(payone);
                pay.setPayone(one);
            }catch (Exception e){
                return "预付款金额填写错误，只能填写数字";
            }
        }
        if("".equals(paytwo)){
            return "请输入30天付款金额";
        }else{
            try{
                BigDecimal two = new BigDecimal(paytwo);
                pay.setPaytwo(two);
            }catch (Exception e){
                return "30天付款金额填写错误，只能填写数字";
            }
        }
        if("".equals(paythree)){
            return "请输入60天付款金额";
        }else{
            try{
                BigDecimal three = new BigDecimal(paythree);
                pay.setPaythree(three);
            }catch (Exception e){
                return "60天付款金额填写错误，只能填写数字";
            }
        }
        if("".equals(payfour)){
            return "请输入90天付款金额";
        }else{
            try{
                BigDecimal four = new BigDecimal(payfour);
                pay.setPayfour(four);
            }catch (Exception e){
                return "90天付款金额填写错误，只能填写数字";
            }
        }
        payCycleDAO.save(pay);
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        salesPlan.setStep(5);
        salesPlanDAO.save(salesPlan);
        return "OK";
    }

    @RequestMapping("/saveProduction")
    @Transactional
    public String toSaveProduction(String salePlanID , String[] productDates , String[] partNums , String[] partNames , String[] allAmounts , String[] amountOnes , String[] amountTwos , String[] amountThrees , String[] notes){
        for(int i = 0 ; i < productDates.length ; i++){
            int num = i+1;
            Product product = new Product();
            product.setSalePlanID(salePlanID);

            if("".equals(productDates[i])){
                return "第"+num+"行生产日期未填写";
            }else{
                product.setProductDate(productDates[i]);
            }
            if("".equals(partNums[i])){
                return "第"+num+"行零件编号未填写";
            }else{
                product.setPartNum(partNums[i]);
            }
            if("".equals(partNames[i])){
                return "第"+num+"行零件名称未填写";
            }else{
                product.setPartName(partNames[i]);
            }
            if(!"".equals(allAmounts[i])){
                try{
                    int allAmount = Integer.parseInt(allAmounts[i]);
                    product.setAllAmount(allAmount);
                }catch (Exception e){
                    return "第"+num+"行投产数量填写错误（只能填写数字）";
                }
            }
            if(!"".equals(amountOnes[i])){
                try{
                    int amountOne = Integer.parseInt(amountOnes[i]);
                    product.setAmountOne(amountOne);
                }catch (Exception e){
                    return "第"+num+"行合格数量填写错误（只能填写数字）";
                }
            }
            if(!"".equals(amountTwos[i])){
                try{
                    int amountTwo = Integer.parseInt(amountTwos[i]);
                    product.setAmountTwo(amountTwo);
                }catch (Exception e){
                    return "第"+num+"行让步接收数量填写错误（只能填写数字）";
                }
            }
            if(!"".equals(amountThrees[i])){
                try {
                    int amountThree = Integer.parseInt(amountThrees[i]);
                    product.setAmountThree(amountThree);
                }catch (Exception e){
                    return "第"+num+"行报废数量填写错误（只能填写数字）";
                }
            }
            if(!"".equals(notes[i])){
                product.setNote(notes[i]);
            }
            productDAO.save(product);
        }
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        salesPlan.setStep(6);
        salesPlanDAO.save(salesPlan);
        return "OK";
    }

    @RequestMapping("/saveSaleMoney")
    @Transactional
    public String toSaveSale(String salePlanID , String[] years , String[] saleMoneys , String[] notes){
        System.out.println(years[0]);
        System.out.println(saleMoneys[0]);
        System.out.println(notes[0]);
        for(int i = 0 ; i < years.length ; i++){
            int num = i+1;
            SaleAmount saleAmount = new SaleAmount();
            saleAmount.setSalePlanID(salePlanID);
            if("".equals(years[i])){
                return "第"+num+"行年份未填写";
            }else{
                saleAmount.setYear(years[i]);
            }
            if("".equals(saleMoneys[i])){
                return "第"+num+"行年销售额未填写";
            }else{
                try{
                    BigDecimal saleMoney = new BigDecimal(saleMoneys[i]);
                    saleAmount.setAmount(saleMoney);
                }catch (Exception e){
                    return "第"+num+"行年销售额填写错误（只能填写数字）";
                }
            }
            if(!"".equals(notes[i])){
                saleAmount.setNote(notes[i]);
            }
            saleAmountDAO.save(saleAmount);
        }
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        salesPlan.setStep(1);
        salesPlan.setPlanStatus("fifth");
        salesPlanDAO.save(salesPlan);
        return "OK";
    }

    @RequestMapping("/savePartList")
    @Transactional
    public String toSavePart(String salePlanID , String[] partNums , String[] partNames , String[] partTypes , String[] materials , String[] hardnesses , String[] origins , String[] yields , String[] standards , String[] scraps , String[] pageNums , String[] approveDates , String[] spendTimes){
        if(!"".equals(partNums[0])){
            for(int i = 0 ; i < partNums.length ; i++){
                int num = i+1;
                PartList part = new PartList();
                part.setSalePlanID(salePlanID);
                if("".equals(partNums[i])){
                    return "第"+num+"行零件编号未填写";
                }else{
                    part.setPageNum(partNums[i]);
                }
                if("".equals(partNames[i])){
                    return "第"+num+"行零件名称未填写";
                }else{
                    part.setPartName(partNames[i]);
                }
                if("".equals(partTypes[i])){
                    return "第"+num+"行零件类型未填写";
                }else{
                    part.setPartType(partTypes[i]);
                }
                if(!"".equals(materials[i])){
                    part.setMaterial(materials[i]);
                }
                if(!"".equals(hardnesses[i])){
                    part.setHardness(hardnesses[i]);
                }
                if(!"".equals(origins[i])){
                    part.setOrigin(origins[i]);
                }
                if(!"".equals(yields[i])){
                    part.setYield(yields[i]);
                }
                if(!"".equals(standards[i])){
                    part.setStandard(standards[i]);
                }
                if(!"".equals(scraps[i])){
                    try{
                        BigDecimal scrap = new BigDecimal(scraps[i]);
                        part.setScrap(scrap);
                    }catch (Exception e){
                        return "第"+num+"行报废率填写错误（只能填写数字）";
                    }
                }
                if(!"".equals(pageNums[i])){
                    part.setPageNum(pageNums[i]);
                }
                if(!"".equals(approveDates[i])){
                    part.setApproveDate(approveDates[i]);
                }
                if (!"".equals(spendTimes[i])){
                    part.setSpendTime(spendTimes[i]);
                }
                partListDAO.save(part);
            }
        }
        return "OK";
    }

    @RequestMapping("/saveKnifeList")
    @Transactional
    public String toSaveKnife(String salePlanID , String[] workpieces , String[] processCodes , String[] processNames , String[] materialCodes , String[] brands , String[] types , String[] models , String[] bladeNums , String[] knifeNums , String[] fixtureCodes , String[] fixtureBrands , String[] coolWays , String[] rotateSpeeds , String[] aoligeis , String[] machineNums , String[] lifes , String[] assets){
        if(!"".equals(workpieces[0])){
            for(int i = 0 ; i < workpieces.length ; i++){
                int num = i + 1;
                KnifeList knife = new KnifeList();
                knife.setSalePlanID(salePlanID);

                if("".equals(workpieces[i])){
                    return "第"+num+"行工件未填写";
                }else{
                    knife.setWorkpiece(workpieces[i]);
                }
                if("".equals(processCodes[i])){
                    return "第"+num+"行工序号未填写";
                }else{
                    knife.setProcessCode(processCodes[i]);
                }
                if("".equals(processNames[i])){
                    return "第"+num+"行工序名未填写";
                }else{
                    knife.setProcessName(processNames[i]);
                }
                if(!"".equals(materialCodes[i])){
                    knife.setMaterialCode(materialCodes[i]);
                }
                if(!"".equals(brands[i])){
                    knife.setBrand(brands[i]);
                }
                if(!"".equals(types[i])){
                    knife.setType(types[i]);
                }
                if(!"".equals(models[i])){
                    knife.setModel(models[i]);
                }
                if(!"".equals(bladeNums[i])){
                    try{
                        int bladeNum = Integer.parseInt(bladeNums[i]);
                        knife.setBladeNum(bladeNum);
                    }catch (Exception e){
                        return "第"+num+"行刃数填写错误（只能填写整数）";
                    }
                }
                if(!"".equals(knifeNums[i])){
                    try{
                        int knifeNum = Integer.parseInt(knifeNums[i]);
                        knife.setKnifeNum(knifeNum);
                    }catch (Exception e){
                        return "第"+num+"行装刀数填写错误（只能填写整数）";
                    }
                }
                if(!"".equals(fixtureCodes[i])){
                    knife.setFixtureCode(fixtureCodes[i]);
                }
                if(!"".equals(fixtureBrands[i])){
                    knife.setFixtureBrand(fixtureBrands[i]);
                }
                if(!"".equals(coolWays[i])){
                    knife.setCoolWay(coolWays[i]);
                }
                if(!"".equals(rotateSpeeds[i])){
                    knife.setRotateSpeed(rotateSpeeds[i]);
                }
                if(!"".equals(aoligeis[i])){
                    knife.setAoligei(aoligeis[i]);
                }
                if(!"".equals(machineNums[i])){
                    knife.setMachineNum(machineNums[i]);
                }
                if(!"".equals(lifes[i])){
                    knife.setLife(lifes[i]);
                }
                if (!"".equals(assets[i])){
                    knife.setAsset(assets[i]);
                }
                knifeListDAO.save(knife);
            }
        }
        return "OK";
    }

    @RequestMapping("/saveSupplierList")
    @Transactional
    public String toSaveSupplier(String salePlanID , String[] supplierCodes , String[] supplierNames , String[] areas , String[] postals , String[] addresses , String[] websites , String[] industrys , String[] natures , String[] brands , String[] payWays , String[] payConditions , String[] contracts , String[] phones , String[] supplierTypes , String[] supplierLevels , String[] principals , String[] buymoneyOnes , String[] buymoneyTwos){
        if(!"".equals(supplierCodes[0])){
            for(int i = 0 ; i < supplierCodes.length ; i++){
                int num = i + 1 ;
                Supplier supplier = new Supplier();
                supplier.setSalePlanID(salePlanID);
                if("".equals(supplierCodes[i])){
                    return "第"+num+"行供应商代码未填写";
                }else{
                    supplier.setSupplierCode(supplierCodes[i]);
                }
                if("".equals(supplierNames[i])){
                    return "第"+num+"行供应商名称未填写";
                }else{
                    supplier.setSupplierName(supplierNames[i]);
                }
                if(!"".equals(areas[i])){
                    supplier.setArea(areas[i]);
                }
                if(!"".equals(postals[i])){
                    supplier.setPostal(postals[i]);
                }
                if(!"".equals(addresses[i])){
                    supplier.setAddress(addresses[i]);
                }
                if(!"".equals(websites[i])){
                    supplier.setWebsite(websites[i]);
                }
                if(!"".equals(industrys[i])){
                    supplier.setIndustry(industrys[i]);
                }
                if(!"".equals(natures[i])){
                    supplier.setNature(natures[i]);
                }
                if(!"".equals(brands[i])){
                    supplier.setBrand(brands[i]);
                }
                if(!"".equals(payWays[i])){
                    supplier.setPayWay(payWays[i]);
                }
                if(!"".equals(payConditions[i])){
                    supplier.setPayCondition(payConditions[i]);
                }
                if(!"".equals(contracts[i])){
                    supplier.setContract(contracts[i]);
                }
                if(!"".equals(phones[i])){
                    supplier.setPhone(phones[i]);
                }
                if(!"".equals(supplierTypes[i])){
                    supplier.setSupplierType(supplierTypes[i]);
                }
                if(!"".equals(supplierLevels[i])){
                    supplier.setSupplierLevel(supplierLevels[i]);
                }
                if(!"".equals(principals[i])){
                    supplier.setPrincipal(principals[i]);
                }
                if (!"".equals(buymoneyOnes[i])){
                    try{
                        BigDecimal buyone = new BigDecimal(buymoneyOnes[i]);
                        supplier.setBuymoneyOne(buyone);
                    }catch (Exception e){
                        return "第"+num+"行前年采购金额输入有误（只允许填写数字）";
                    }
                }
                if(!"".equals(buymoneyTwos[i])){
                    try{
                        BigDecimal buytwo = new BigDecimal(buymoneyTwos[i]);
                        supplier.setBuymoneyTwo(buytwo);
                    }catch (Exception e){
                        return "第"+num+"行去年采购金额输入有误（只允许填写数字）";
                    }
                }
                supplierDAO.save(supplier);
            }
        }
        return "OK";
    }

    @RequestMapping("/saveBuyList")
    @Transactional
    public String toSaveBuy(String salePlanID , String[] assetCodes , String[] partCodes , String[] materialCodes , String[] knifeNames , String[] types , String[] models , String[] brands , String[] lifes , String[] receiveAmounts , String[] receiveDates , String[] notes){
        if(!"".equals(assetCodes[0])){
            for(int i = 0 ; i < assetCodes.length ; i++){
                int num = i + 1;
                BuyList buy = new BuyList();
                buy.setSalePlanID(salePlanID);
                if("".equals(assetCodes[i])){
                    return "第"+num+"行固定资产编号未填写";
                }else{
                    buy.setAssetCode(assetCodes[i]);
                }
                if("".equals(partCodes[i])){
                    return "第"+num+"行零件编号未填写";
                }else{
                    buy.setPartCode(partCodes[i]);
                }
                if("".equals(materialCodes[i])){
                    return "第"+num+"行刀具物料编号未填写";
                }else{
                    buy.setMaterialCode(materialCodes[i]);
                }
                if(!"".equals(knifeNames)){
                    buy.setKnifeName(knifeNames[i]);
                }
                if(!"".equals(types[i])){
                    buy.setKnifeType(types[i]);
                }
                if(!"".equals(models[i])){
                    buy.setModel(models[i]);
                }
                if(!"".equals(brands[i])){
                    buy.setBrand(brands[i]);
                }
                if(!"".equals(lifes[i])){
                    buy.setLife(lifes[i]);
                }
                if(!"".equals(receiveAmounts[i])){
                    buy.setReceiveAmount(receiveAmounts[i]);
                }
                if(!"".equals(receiveDates[i])){
                    buy.setReceiveDate(receiveAmounts[i]);
                }
                if(!"".equals(notes[i])){
                    buy.setNote(notes[i]);
                }
                buyListDAO.save(buy);
            }
        }
        return "OK";
    }

    @RequestMapping("/saveReceiveList")
    @Transactional
    public String toSaveReceive(String salePlanID , String[] materialCodes , String[] materialNames , String[] models , String[] types , String[] receiveAmounts , String[] receivePrices , String[] receiveMoneys , String[] receiveDepts , String[] receiveDates , String[] notes){
        if(!"".equals(materialCodes[0])){
            for(int i = 0 ; i < materialCodes.length ; i++){
                int num = i + 1;
                ReceiveList receive = new ReceiveList();
                receive.setSalePlanID(salePlanID);
                if("".equals(materialCodes[i])){
                    return "第"+num+"行物料编号未填写";
                }else{
                    receive.setMaterialCode(materialCodes[i]);
                }
                if("".equals(materialNames[i])){
                    return "第"+num+"行物料名称未填写";
                }else{
                    receive.setMaterialName(materialNames[i]);
                }
                if(!"".equals(models[i])){
                    receive.setModel(models[i]);
                }
                if(!"".equals(types[i])){
                    receive.setType(types[i]);
                }
                if(!"".equals(receiveAmounts[i])){
                    try{
                        int receiveAmount = Integer.parseInt(receiveAmounts[i]);
                        receive.setReceiveAmount(receiveAmount);
                    }catch (Exception e){
                        return "第"+num+"行领用数量填写错误（只能填写整数）";
                    }
                }
                if(!"".equals(receivePrices[i])){
                    try{
                        BigDecimal receivePice = new BigDecimal(receivePrices[i]);
                        receive.setReceivePice(receivePice);
                    }catch (Exception e){
                        return "第"+num+"行领用单价填写错误（只能填写数字）";
                    }
                }
                if(!"".equals(receiveMoneys[i])){
                    try{
                        BigDecimal receiveMoney = new BigDecimal(receiveMoneys[i]);
                        receive.setReceiveMoney(receiveMoney);
                    }catch (Exception e){
                        return "第"+num+"行领用金额填写错误（只能填写数字）";
                    }
                }
                if(!"".equals(receiveDates[i])){
                    receive.setReceiveDate(receiveDates[i]);
                }
                if(!"".equals(notes[i])){
                    receive.setNote(notes[i]);
                }
                receiveListDAO.save(receive);
            }
        }
        return "OK";
    }
}
