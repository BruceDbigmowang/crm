package com.knowhy.crm.controller;

import com.knowhy.crm.dao.CompanyInfoDAO;
import com.knowhy.crm.dao.DataDetailDAO;
import com.knowhy.crm.dao.ManufactureDAO;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.CompanyInfo;
import com.knowhy.crm.pojo.DataDetail;
import com.knowhy.crm.pojo.Manufacture;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.service.SalePlanService;
import com.knowhy.crm.service.SaveDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class OnlineSurveyController {

    @Autowired
    SaveDataService saveDataService;
    @Autowired
    DataDetailDAO dataDetailDAO;
    @Autowired
    SalePlanService planService;
    @Autowired
    SalesPlanDAO planDAO;
    @Autowired
    CompanyInfoDAO companyInfoDAO;
    @Autowired
    ManufactureDAO manufactureDAO;

    @RequestMapping("/getVcode")
    public String getCode(HttpSession session){
        String vcode = (String)session.getAttribute("code");
        return vcode;
    }

    @RequestMapping("/saveCompanyInfo")
    public String saveBussinessInfo(@RequestParam("salePlanID")String salePlanID , @RequestParam("companyName")String companyName , @RequestParam("contact")String contact , @RequestParam("contactWay")String contactWay , @RequestParam("wechatNum")String wechatNum , HttpSession session){
        if("".equals(companyName) || companyName == null){
            return "请填写您的公司名称";
        }
        if("".equals(contact) || contact == null){
            return "请填写您的姓名";
        }
        if("".equals(contactWay) || contactWay == null){
            return "请填写您的联系方式";
        }
        if("".equals(wechatNum) || wechatNum == null){
            return "请选择手机号与微信号是否一致或填写微信号";
        }
        try{

            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setSalePlanID(salePlanID);
            companyInfo.setCompanyName(companyName);
            companyInfo.setContact(contact);
            companyInfo.setContactWay(contactWay);
            companyInfo.setWechatNum(wechatNum);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date nowadays = new Date();
            String dateString = formatter.format(nowadays);
            companyInfo.setCreateDate(dateString);
            companyInfo.setCreateTime(nowadays);
            saveDataService.saveCompany(companyInfo);
            int cid = saveDataService.getCid(companyInfo);
            session.setAttribute("cid" , cid);
            session.setAttribute("companyInfo" , companyInfo);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(2);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/updateCompanyInfoFirst")
    public String saveCompanyInfoFirst(HttpServletRequest request , HttpSession session){
//        int cid = (int)session.getAttribute("cid");
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        String register = request.getParameter("registerMoney");
        BigDecimal registerMoney;
        if(!"".equals(register) && register != null){
            try{
                registerMoney = new BigDecimal(register).setScale(4 , BigDecimal.ROUND_HALF_UP);
            }catch (Exception e){
                return "注册资金填写错误，应填写数字(最多保留后四位小数)";
            }
        }else{
            registerMoney = null;
        }
        String establishTime = request.getParameter("establishTime");
        if("".equals(establishTime) || establishTime == null){
            establishTime = null;
        }

        String bussinessNature = request.getParameter("bussinessNature");
        if("".equals(bussinessNature) || bussinessNature == null){
            return "请选择公司性质";
        }
        String sonCompanyNum = request.getParameter("sonCompanyNum");
        if("".equals(sonCompanyNum) || sonCompanyNum == null){
            return "请选择子公司数量";
        }
        String employeeNum = request.getParameter("employeeNum");
        if("".equals(employeeNum) || employeeNum == null ){
            return "请选择公司员工数量";
        }

        try{
            saveDataService.updateCompanyInfo(cid , registerMoney , establishTime , bussinessNature , sonCompanyNum , employeeNum);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(3);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/updatePic")
    public String savePicture(@RequestParam("images") MultipartFile[] files , HttpServletRequest request) throws IOException {

        //将图片存储到img/sale目录下

        for (int i = 0; i < files.length; i++) {
            MultipartFile oneFile = files[i];
            String path = request.getSession().getServletContext().getRealPath("img/product/");
            String filename ;
            if (!oneFile.isEmpty()) {
                filename = oneFile.getOriginalFilename();
                File filepath = new File(path, filename);
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                oneFile.transferTo(new File(path + File.separator + filename));
            }
        }
        return "文件上传成功";
    }

    @RequestMapping("/updateCompanyInfoSecond")
    public String saveCompanyInfoSecond(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        String industry = request.getParameter("industry");
        if("".equals(industry) || industry == null){
            return "请选择公司所属行业";
        }
        String industryNature = request.getParameter("industryNature");
        if("".equals(industryNature) || industryNature == null){
            return "请选择您公司是";
        }
        String product = request.getParameter("product");
        if("".equals(product) || product == null){
            return "请选择您公司的产品";
        }
        String picture = request.getParameter("picture");
        if(picture == null || "".equals(picture)){
            picture = null;
        }
        try{
            saveDataService.updateCompanyTwice(cid , industry , industryNature , product , picture);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(4);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/saveKnowMorePage")
    @Transactional
    public String saveKnowMore(HttpServletRequest request ){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        String means = request.getParameter("means");
        if("".equals(means) || means == null){
            return "请选择你是通过何途径了解诺而为";
        }
        String toolManage = request.getParameter("toolManage");
        if("".equals(toolManage) || toolManage == null){
            return "请选择您公司之前是否做过外包服务";
        }
        String facilitatorName = null;
        String problem = null;
        if(toolManage.equals("是")){
            facilitatorName = request.getParameter("facilitatorName");
            if("".equals(facilitatorName) ||facilitatorName == null){
                return "请填写服务商名称";
            }
            problem = request.getParameter("problem");
            if("".equals(problem) || problem == null){
                return "请填写之前服务遇到的问题";
            }
        }
        String consume = request.getParameter("consume");
        if("".equals(consume) || consume == null){
            return "请选择刀具总消耗";
        }
        String principle = request.getParameter("principal");
        if("".equals(principle) || principle == null){
            return "请填写项目负责人";
        }
        String mobile = request.getParameter("mobile");
        if("".equals(mobile) || mobile == null){
            return "请填写手机号码";
        }
        String email = request.getParameter("email");
        if(email == null || "".equals(email)){
            email = null;
        }
        String ask = request.getParameter("ask");
        if("".equals(ask) || ask == null){
            return "请选择项目进展要求";
        }
            saveDataService.saveKnowMore(cid , means , toolManage , facilitatorName , problem , consume , principle , mobile , email , ask);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(5);
            planDAO.save(salesPlan);

        return "OK";
    }

    @RequestMapping("/saveManufactureOne")
    public String saveOne(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        Manufacture manufacture = new Manufacture();
        if(saveDataService.findByCid(cid) != null){
            manufacture = saveDataService.findByCid(cid);
        }
        manufacture.setCid(cid);
        String production = request.getParameter("production");
        if("".equals(production) || production == null){
            return "请填写您公司近三年的平均产值";
        }
        manufacture.setProduction(production);
        String market = request.getParameter("market");
        if("".equals(market) || market == null){
            return "请选择您公司的销售市场";
        }
        manufacture.setMarket(market);
        String competitor = request.getParameter("competitor");
        if(!"".equals(competitor) && competitor != null){
            manufacture.setCompetitor(competitor);
        }

        String project = request.getParameter("project");
        if(!"".equals(project) && project != null){
            manufacture.setProject(project);
        }
            saveDataService.save(manufacture);
            int mid = saveDataService.findByCid(cid).getId();
            System.out.println(mid);
            session.removeAttribute("mid");
            session.setAttribute("mid" , mid);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(6);
            planDAO.save(salesPlan);

        return "OK";
    }

    @RequestMapping("/saveManufactureTwo")
    public String saveTwo(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        int mid = manufactureDAO.findByCid(cid).get(0).getId();
//        int mid = (int)session.getAttribute("mid");
        Manufacture manufacture = saveDataService.findById(mid);
        String assetType = request.getParameter("assetType");
        if("".equals(assetType) || assetType == null){
            return "请选择设备类型";
        }
        manufacture.setAssetType(assetType);
        String assetSource = request.getParameter("assetSource");
        if("".equals(assetSource) || assetSource == null){
            return "请选择刀具的采购来源";
        }
        manufacture.setResource(assetSource);
        String brand = request.getParameter("brand");
        if(!"".equals(brand) && brand != null){
            manufacture.setBrand(brand);
        }
        String amount = request.getParameter("assetNum");
        if("".equals(amount) || amount == null){
            return "请选择设备数量";
        }
        manufacture.setAmount(amount);
        String life = request.getParameter("life");
        if("".equals(life) || life == null){
            return "请选择设备使用年限";
        }
        manufacture.setLife(life);
        String form = request.getParameter("form");
        if("".equals(form) || form == null){
            return "请选择您公司产品生产的形式";
        }
        manufacture.setForm(form);
        try{
            saveDataService.save(manufacture);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(7);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }

        return "OK";
    }

    @RequestMapping("/saveManufactureThree")
    public String saveThree(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        int mid = manufactureDAO.findByCid(cid).get(0).getId();
        Manufacture manufacture = saveDataService.findById(mid);
        String buyMoney = request.getParameter("buyMoney");
        if("".equals(buyMoney) || buyMoney == null){
            return "请选择近3年的刀具采购金额";
        }
        manufacture.setBuyMoney(buyMoney);
        String buySource = request.getParameter("buySource");
        if("".equals(buySource) || buySource == null){
            return "请选择刀具采购的主要来源";
        }
        manufacture.setBuySource(buySource);
        String buyBrand = request.getParameter("buyBrand");
        if(!"".equals(buyBrand) && buyBrand != null){
            manufacture.setBuyBrand(buyBrand);
        }

        String buyModel = request.getParameter("buyModel");
        if("".equals(buyModel) || buyModel == null){
            return "请选择刀具采购的模式";
        }
        manufacture.setBuyModel(buyModel);
        String manageModel = request.getParameter("manageModel");
        if("".equals(manageModel) || manageModel == null){
            return "请选择刀具库存管理的模式";
        }
        manufacture.setManageModel(manageModel);
        saveDataService.save(manufacture);
        SalesPlan salesPlan = planService.findById(salePlanID);
        salesPlan.setStep(8);
        planDAO.save(salesPlan);
        return "OK";
    }

    @RequestMapping("/saveDataOne")
    public String saveFirst(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        DataDetail dataDetail = new DataDetail();
        List<DataDetail> dataDetailList = dataDetailDAO.findByCid(cid);
        if(dataDetailList != null && dataDetailList.size() != 0){
            dataDetail = dataDetailList.get(0);
        }
        dataDetail.setCid(cid);
        String zt = request.getParameter("ztzb");
        if(!"".equals(zt) && zt != null){
            try{
                BigDecimal ztzb = new BigDecimal(zt).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setZtzb(ztzb);
            }catch (Exception e){
                return "请正确填写钻头占比，输入值为数字（最多保留四位小数）";
            }
        }

        String xd = request.getParameter("xdzb");
        if(!"".equals(xd) && xd != null){
            try{
                BigDecimal xdzb = new BigDecimal(xd).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setXdzb(xdzb);
            }catch (Exception e){
                return "请正确填写铣刀占比，输入值为数字（最多保留四位小数）";
            }
        }

        String sz = request.getParameter("szzb");
        if(!"".equals(sz) && sz != null){
            try{
                BigDecimal szzb = new BigDecimal(sz).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setSzzb(szzb);
            }catch (Exception e){
                return "请正确填写丝锥占比，输入值为数字（最多保留四位小数）";
            }
        }

        String QTO = request.getParameter("qtOne");
        if(!"".equals(QTO) && QTO != null){
            try{
                BigDecimal qtOne = new BigDecimal(QTO).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setQtOne(qtOne);
            }catch (Exception e){
                return "请正确填写其它占比，输入值为数字（最多保留四位小数）";
            }
        }

        String cdp = request.getParameter("cdpzb");
        if(!"".equals(cdp) && cdp != null){
            try{
                BigDecimal cdpzb = new BigDecimal(cdp).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setCdpzb(cdpzb);
            }catch(Exception e){
                return "请正确填写车刀片占比，输入值为数字（最多保留四位小数）";
            }
        }

        String xdp = request.getParameter("xdpzb");
        if(!"".equals(xdp) && xdp != null){
            try{
                BigDecimal xdpzb = new BigDecimal(xdp).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setXdpzb(xdpzb);
            }catch (Exception e){
                return "请正确填写铣刀片占比，输入值为数字（最多保留四位小数）";
            }
        }

        String tdp = request.getParameter("tdpzb");
        if(!"".equals(tdp) && tdp != null ){
            try{
                BigDecimal tdpzb = new BigDecimal(tdp).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setTdpzb(tdpzb);
            }catch (Exception e){
                return "请正确填写镗刀片占比，输入值为数字（最多保留四位小数）";
            }
        }

        String QTT = request.getParameter("qtTwo");
        if(!"".equals(QTT) && QTT != null){
            try{
                BigDecimal qtTwo = new BigDecimal(QTT).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setQtTwo(qtTwo);
            }catch (Exception e){
                return "请正确填写其它占比，输入值为数字（最多保留四位小数）";
            }
        }

        String cbnzb = request.getParameter("cbn");
        if(!"".equals(cbnzb) && cbnzb != null){
            try{
                BigDecimal cbn = new BigDecimal(cbnzb).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setCbn(cbn);
            }catch (Exception e){
                return "请正确填写CBN占比，输入值为数字（最多保留四位小数）";
            }
        }

        String pcdzb = request.getParameter("pcd");
        if(!"".equals(pcdzb) && pcdzb != null){
            try{
                BigDecimal pcd = new BigDecimal(pcdzb).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setPcd(pcd);
            }catch (Exception e){
                return "请正确填写PCD占比，输入值为数字（最多保留四位小数）";
            }
        }

        String hj = request.getParameter("hjzb");
        if(!"".equals(hj) && hj != null){
            try{
                BigDecimal hjzb = new BigDecimal(hj).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setHjzb(hjzb);
            }catch (Exception e){
                return "请正确填写焊接超硬占比，输入值为数字（最多保留四位小数）";
            }
        }


        String hasGSG = request.getParameter("hasGSG");
        if(!"".equals(hasGSG) && hasGSG != null){
            dataDetail.setHasGSG(hasGSG);
            if(hasGSG.equals("有")){
                String gsgBrand = request.getParameter("gsgBrand");
                if("".equals(gsgBrand) || gsgBrand == null){
                    return "请填写高速钢占比";
                }
                dataDetail.setGsgBrand(gsgBrand);
            }
        }
        try{
            saveDataService.saveOrUpdate(dataDetail);
            int did = saveDataService.getByCid(cid).getId();
            session.setAttribute("did" , did);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(9);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/saveDataTwo")
    public String saveSecond(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        int did = dataDetailDAO.findByCid(cid).get(0).getId();
        DataDetail dataDetail = saveDataService.getById(did);
        String stockMoney = request.getParameter("stockMoney");
        if("".equals(stockMoney) || stockMoney == null){

            return "请填写库存总金额";
        }
        dataDetail.setStockMoney(stockMoney);
        String stock1 = request.getParameter("stockOne");
        if(!"".equals(stock1) && stock1 != null){
            try{
                BigDecimal stockOne = new BigDecimal(stock1).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setStockOne(stockOne);
            }catch (Exception e){
                return "请正确填写库存<90天的占比，输入值为数字（最多保留四位小数）";
            }
        }
        String stock2 = request.getParameter("stockTwo");
        if(!"".equals(stock2) && stock2 != null){
            try{
                BigDecimal stockTwo = new BigDecimal(stock2).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setStockTwo(stockTwo);
            }catch (Exception e){
                return "请正确填写库存为90-180天的占比，输入值为数字（最多保留四位小数）";
            }
        }
        String stock3 = request.getParameter("stockThree");
        if(!"".equals(stock3) && stock3 != null){
            try{
                BigDecimal stockThree = new BigDecimal(stock3).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setStockThree(stockThree);
            }catch (Exception e){
                return "请正确填写库存为180-360天的占比，输入值为数字（最多保留四位小数）";
            }
        }
        String stock4 = request.getParameter("stockFour");
        if(!"".equals(stock4) && stock4 != null){
            try{
                BigDecimal stockFour = new BigDecimal(stock4).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setStockFour(stockFour);
            }catch (Exception e){
                return "请正确填写库存为>360天的占比，输入值为数字（最多保留四位小数）";
            }
        }
        String norm = request.getParameter("normTool");
        if(!"".equals(norm) && norm != null){
            try{
                BigDecimal normTool = new BigDecimal(norm).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setNormTool(normTool);
            }catch (Exception e){
                return "请正确填写标准刀具占比，输入值为数字（最多保留四位小数）";
            }
        }
        String unnorm = request.getParameter("unnormTool");
        if(!"".equals(unnorm) && unnorm != null){
            try{
                BigDecimal unnormTool = new BigDecimal(unnorm).setScale(4 , BigDecimal.ROUND_HALF_UP);
                dataDetail.setUnnormTool(unnormTool);
            }catch (Exception e){
                return "请正确填写非标准刀具占比，输入值为数字（最多保留四位小数）";
            }
        }
        try{
            saveDataService.saveOrUpdate(dataDetail);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(10);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/saveDataThree")
    public String saveThird(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        int did = dataDetailDAO.findByCid(cid).get(0).getId();
        DataDetail dataDetail = saveDataService.getById(did);
        String hasERP = request.getParameter("hasERP");
        if("".equals(hasERP) || hasERP == null){
            return "请选择是否使用过ERP";
        }
        dataDetail.setHasERP(hasERP);
        if(hasERP.equals("是")){
            String erpBrand = request.getParameter("erpBrand");
            if("".equals(erpBrand) || erpBrand == null){
                return "请填写ERP品牌";
            }
            dataDetail.setErpBrand(erpBrand);
        }
        String hasMES = request.getParameter("hasMES");
        if("".equals(hasMES) || hasMES == null){
            return "请选择是否使用过MES";
        }
        dataDetail.setHasMES(hasMES);
        if(hasMES.equals("是")){
            String mesBrand = request.getParameter("mesBrand");
            if("".equals(mesBrand) || mesBrand == null){
                return "请填写MES品牌";
            }
            dataDetail.setMesBrand(mesBrand);
        }
        String shiftManage = request.getParameter("shiftManage");
        if(!"".equals(shiftManage) && shiftManage != null){
            dataDetail.setShiftManage(shiftManage);
        }

        String productRest = request.getParameter("productRest");
        if(!"".equals(productRest) && productRest != null){
            dataDetail.setProductRest(productRest);
        }

        String pn = request.getParameter("productNum");
        if(!"".equals(pn) && pn != null){
            try{
                int productNum = Integer.parseInt(pn);
                dataDetail.setProductNum(productNum);
            }catch (Exception e){
                return "请正确填写生产线数量，输入值为整数";
            }
        }
        try{
            saveDataService.saveOrUpdate(dataDetail);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(11);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/saveDataFour")
    public String saveFourth(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        int did = dataDetailDAO.findByCid(cid).get(0).getId();
        DataDetail dataDetail = saveDataService.getById(did);
        String sp = request.getParameter("stockPerson");
        if("".equals(sp) || sp == null){
            return "请填写库存人员数量";
        }
        try{
            int stockPerson = Integer.parseInt(sp);
            dataDetail.setStockPerson(stockPerson);
        }catch (Exception e){
            return "请正确填写库存人员数量，输入值为整数";
        }
        String gp = request.getParameter("grantPerson");
        if(!"".equals(gp) && gp != null){
            try{
                int grantPerson = Integer.parseInt(gp);
                dataDetail.setGrantPerson(grantPerson);
            }catch (Exception e){
                return "请正确填写刀具调试发放人员数量，输入值为整数";
            }
        }

        String grantWay = request.getParameter("grantWay");
        if("".equals(grantWay) || grantWay == null){
            return "请选择刀具发放方式";
        }
        dataDetail.setGrantWay(grantWay);
        String returnWay = request.getParameter("returnWay");
        if(!"".equals(returnWay) && returnWay != null){
            dataDetail.setReturnWay(returnWay);
        }

        String payCycle = request.getParameter("payCycle");
        if(!"".equals(payCycle) && payCycle != null){
            dataDetail.setPayCycle(payCycle);
        }

        String payWay = request.getParameter("payWay");
        if(!"".equals(payWay) && payWay != null){
            dataDetail.setPayWay(payWay);
        }
        try{
            saveDataService.saveOrUpdate(dataDetail);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setStep(12);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/saveDataFive")
    public String saveFifth(HttpServletRequest request , HttpSession session){
        String salePlanID = request.getParameter("salePlanID");
        int cid = companyInfoDAO.findBySalePlanID(salePlanID).get(0).getId();
        int did = dataDetailDAO.findByCid(cid).get(0).getId();
        DataDetail dataDetail = saveDataService.getById(did);
        String repair = request.getParameter("repair");
        if("".equals(repair) || repair == null){
            return "请选择是否修磨";
        }
        dataDetail.setRepair(repair);
        if(repair.equals("修")){
            String rs = request.getParameter("repairSpend");
            if("".equals(rs) || rs == null){
                return "请填写修磨花费";
            }
            try{
                BigDecimal repairSpend = new BigDecimal(rs).setScale(4,BigDecimal.ROUND_HALF_UP);
                dataDetail.setRepairSpend(repairSpend);
            }catch (Exception e){
                return "请正确填写修磨发给，输入值为数字（最多保留四位小数）";
            }
        }
        String optimize = request.getParameter("optimize");
        if("".equals(optimize) || optimize == null){
            return "请选择优化流程";
        }
        dataDetail.setOptimize(optimize);
        String appeal = request.getParameter("appeal");
        if("".equals(appeal) || appeal == null){
            return "请选择主要诉求";
        }
        dataDetail.setAppeal(appeal);
        try{
            saveDataService.saveOrUpdate(dataDetail);
            SalesPlan salesPlan = planService.findById(salePlanID);
            salesPlan.setPlanStatus("fourth");
            salesPlan.setStep(1);
            planDAO.save(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/loadSurveyDetail")
    public int getStep(String salePlanID){
        return planService.getStep(salePlanID);
    }
}
