package com.knowhy.crm.service;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.util.OfflineDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class OfflineSurveyService {

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

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String checkOfflineExcelData(HttpServletRequest request, HttpServletResponse response , HttpSession session){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("customerFile");
        int mId = Integer.parseInt(request.getParameter("mId"));
        if (file.isEmpty()) {
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String salePlanID = request.getParameter("salePlanID");

        /**
         * 固定资产清单
         */
        List<List<Object>> assetList = null;
        try {
            assetList = new OfflineDataUtil().getAssetListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到固定资产清单";
        }
        if(assetList.size() > 0){
            for (int i = 0; i < assetList.size(); i++) {
                int no = i+1;
                List<Object> lo = assetList.get(i);
                String assetType = String.valueOf(lo.get(0)).trim();
                if("".equals(assetType)){
                    return "固定资产清单——第"+no+"行请填写固定类型";
                }
                String assetId = String.valueOf(lo.get(1)).trim();
                if("".equals(assetId)){
                    return "固定资产清单——第"+no+"行请填写固定资产编号";
                }
                String assetName = String.valueOf(lo.get(2)).trim();
                if("".equals(assetName)){
                    return "固定资产清单——第"+no+"行请填写固定资产名称";
                }
                String model = String.valueOf(lo.get(3));
                String brand = String.valueOf(lo.get(4));
                String supplier = String.valueOf(lo.get(5));
                String position = String.valueOf(lo.get(6));
                String number = String.valueOf(lo.get(7));
                try{
                    int amount = Integer.parseInt(number);
                }catch (Exception e){
                    return "固定资产清单——第"+no+"行固定资产数量填写错误";
                }
                String residual = String.valueOf(lo.get(8));
                try{
                    BigDecimal residualNum = new BigDecimal(residual);
                }catch (Exception e){
                    return "固定资产清单——第"+no+"行固定资产预计残值填写错误(只能填写是数字)";
                }
                String status = String.valueOf(lo.get(9));
                String port = String.valueOf(lo.get(10));
                String coolWay = String.valueOf(lo.get(11));
            }
        }else{
            List<Asset> assets = assetDAO.findBySalePlanID(salePlanID);
            if(assets == null || assets.size() == 0){
                return "请填写固定资产清单信息";
            }
        }

        /**
         * 员工花名册
         */
        List<List<Object>> employeeList = null;
        try {
            employeeList = new OfflineDataUtil().getEmployeeListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到员工花名册";
        }
        if(employeeList.size() > 0){
            for(int i = 0 ; i < employeeList.size() ; i++){
                int no = i + 1;
                List<Object> lo = employeeList.get(i);
                String employeeAccount = String.valueOf(lo.get(0)).trim();
                if("".equals(employeeAccount)){
                    return "员工花名册——第"+no+"行请填写员工工号";
                }
                String employeeName = String.valueOf(lo.get(1)).trim();
                if("".equals(employeeName)){
                    return "员工花名册——第"+no+"行请填写员工姓名";
                }
                String identityID = String.valueOf(lo.get(2)).trim();
                if(!"".equals(identityID) && identityID.length() != 18){
                    return "员工花名册——第"+no+"行身份证号码填写错误(长度为18)";
                }
                String education = String.valueOf(lo.get(3)).trim();
                String major = String.valueOf(lo.get(4)).trim();
                String enter = String.valueOf(lo.get(5)).trim();
                if(!"".equals(enter)){
                    try{
                        LocalDate enterDate = LocalDate.parse(enter , fmt);
                    }catch (Exception e){
                        return "员工花名册——第"+no+"行入职时间填写错误(格式:yyyy-MM-dd)";
                    }
                }
                String sex = String.valueOf(lo.get(6)).trim();
                String dept = String.valueOf(lo.get(7)).trim();
                String duty = String.valueOf(lo.get(8)).trim();
                String contact = String.valueOf(lo.get(9)).trim();
                String relevanceAsset = String.valueOf(lo.get(10)).trim();
                String relevancePart = String.valueOf(lo.get(11)).trim();
                String note = String.valueOf(lo.get(12)).trim();
            }
        }else{
            List<Employee> employees = employeeDAO.findBySalePlanID(salePlanID);
            if(employees.size() == 0){
                return "请填写员工花名册";
            }
        }

        /**
         * 库龄分析
         */
        List<List<Object>> stockList = null;
        try {
            stockList = new OfflineDataUtil().getStockListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到库龄分析";
        }
        if(stockList.size() > 0){
            for(int i = 0 ; i < stockList.size() ; i++){
                int no = i + 1;
                List<Object> lo = stockList.get(i);
                String type = String.valueOf(lo.get(0)).trim();
                if("".equals(type)){
                    return "即时库龄分析——第"+no+"行请填写品类名称";
                }
                String num1 = String.valueOf(lo.get(1)).trim();
                if("".equals(num1)){
                    return "即时库龄分析——第"+no+"行请填写库龄为0<D≤30天的金额";
                }
                BigDecimal total = BigDecimal.ZERO;
                try{
                    BigDecimal amount1 = new BigDecimal(num1);
                    total = total.add(amount1);
                }catch (Exception e){
                    return "即时库龄分析——第"+no+"库龄为0<D≤30天的金额填写错误(只能填写数字)";
                }
                String num2 = String.valueOf(lo.get(2)).trim();
                if("".equals(num2)){
                    return "即时库龄分析——第"+no+"行请填写库龄为31≤D≤120天的金额";
                }
                try{
                    BigDecimal amount2 = new BigDecimal(num2);
                    total = total.add(amount2);
                }catch (Exception e){
                    return "即时库龄分析——第"+no+"库龄为31≤D≤120天的金额填写错误(只能填写数字)";
                }
                String num3 = String.valueOf(lo.get(3)).trim();
                if("".equals(num3)){
                    return "即时库龄分析——第"+no+"行请填写库龄为121≤D≤360天的金额";
                }
                try{
                    BigDecimal amount3 = new BigDecimal(num3);
                    total = total.add(amount3);
                }catch (Exception e){
                    return "即时库龄分析——第"+no+"库龄为121≤D≤360天的金额填写错误(只能填写数字)";
                }
                String num4 = String.valueOf(lo.get(4)).trim();
                if("".equals(num4)){
                    return "即时库龄分析——第"+no+"行请填写库龄为361≤D≤720天的金额";
                }
                try{
                    BigDecimal amount4 = new BigDecimal(num4);
                    total = total.add(amount4);
                }catch (Exception e){
                    return "即时库龄分析——第"+no+"库龄为361≤D≤720天的金额填写错误(只能填写数字)";
                }
                String num5 = String.valueOf(lo.get(5)).trim();
                if("".equals(num5)){
                    return "即时库龄分析——第"+no+"行请填写库龄为D>720天的金额";
                }
                try{
                    BigDecimal amount5 = new BigDecimal(num5);
                    total = total.add(amount5);
                }catch (Exception e){
                    return "即时库龄分析——第"+no+"库龄为D>720天的金额填写错误(只能填写数字)";
                }
                String num6 = String.valueOf(lo.get(6)).trim();
                if("".equals(num6)){
                    return "即时库龄分析——第"+no+"行请填写合计金额";
                }
                try{
                    BigDecimal amount6 = new BigDecimal(num6);
                    if(total.compareTo(amount6)!=0){
                        return "即时库龄分析——第"+no+"行请填写合计金额与前面的值不符合";
                    }
                }catch (Exception e){
                    return "即时库龄分析——第"+no+"合计金额填写错误(只能填写数字)";
                }
            }
        }else{
            List<Stock> stocks = stockDAO.findBySalePlanID(salePlanID);
            if(stocks.size() == 0){
                return "请填写即时库龄分析";
            }
        }

        /**
         * 付款账期现状
         */
        List<List<Object>> paymentList = null;
        try {
            paymentList = new OfflineDataUtil().getPaymentListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到付款账期现状";
        }
        if(paymentList.size() > 0){
            for(int i = 0 ; i < paymentList.size() ; i++){
                int no = i + 1;
                List<Object> lo = paymentList.get(i);
                String cycle = String.valueOf(lo.get(0));
                if("".equals(cycle)){
                    return "付款账期现状——第"+no+"行请填写付款周期";
                }
                String money = String.valueOf(lo.get(1));
                if("".equals(money)){
                    return "付款账期现状——第"+no+"行请填写金额";
                }
                try {
                    BigDecimal amount = new BigDecimal(money);
                }catch (Exception e){
                    return "付款账期现状——第"+no+"行金额填写错误(只能填写数字)";
                }
            }
        }else{
            List<PayCycle> payCycleList = payCycleDAO.findBySalePlanID(salePlanID);
            if(payCycleList.size() == 0){
                return "请填写付款账期现状";
            }
        }

        /**
         * 生产产量报表
         */
        List<List<Object>> productionList = null;
        try {
            productionList = new OfflineDataUtil().getProductionListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到生产产量报表";
        }
        if(productionList.size() > 0){
            for(int i = 0 ; i < productionList.size() ; i++){
                int no = i + 1;
                List<Object> lo = productionList.get(i);
                String productDay = String.valueOf(lo.get(0)).trim();
                if("".equals(productDay)){
                    return "生产产量报表——第"+no+"行请填写生产日期";
                }
                try{
                    LocalDate productDate = LocalDate.parse(productDay , fmt);
                }catch (Exception e){
                    return "生产产量报表——第"+no+"行生产日期填写错误(格式:yyyy-MM-dd)";
                }
                String partId = String.valueOf(lo.get(1)).trim();
                if("".equals(partId)){
                    return "生产产量报表——第"+no+"行请填写零件编号";
                }
                String partName = String.valueOf(lo.get(2)).trim();
                if("".equals(partName)){
                    return "生产产量报表——第"+no+"行请填写零件名称";
                }
                String num1 = String.valueOf(lo.get(3)).trim();
                if(!"".equals(num1)){
                    try{
                        int amount1 = Integer.parseInt(num1);
                    }catch (Exception e){
                        return "生产产量报表——第"+no+"行投产数量填写错误(只能填写数字)";
                    }
                }
                String num2 = String.valueOf(lo.get(4)).trim();
                if(!"".equals(num2)){
                    try{
                        int amount2 = Integer.parseInt(num2);
                    }catch (Exception e){
                        return "生产产量报表——第"+no+"行合格数量填写错误(只能填写数字)";
                    }
                }
                String num3 = String.valueOf(lo.get(5)).trim();
                if(!"".equals(num3)){
                    try{
                        int amount3 = Integer.parseInt(num3);
                    }catch (Exception e){
                        return "生产产量报表——第"+no+"行让步接收数量填写错误(只能填写数字)";
                    }
                }
                String num4 = String.valueOf(lo.get(6)).trim();
                if(!"".equals(num4)){
                    try{
                        int amount4 = Integer.parseInt(num4);
                    }catch (Exception e){
                        return "生产产量报表——第"+no+"行报废数量填写错误(只能填写数字)";
                    }
                }
                String note = String.valueOf(lo.get(5)).trim();
            }
        }else{
            List<Product> productList = productDAO.findBySalePlanID(salePlanID);
            if(productionList.size() == 0){
                return "请填写上产产量报表";
            }
        }

        /**
         * 销售额
         */
        List<List<Object>> saleAmountList = null;
        try {
            saleAmountList = new OfflineDataUtil().getSaleAmountListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到销售额";
        }
        if(saleAmountList.size() > 0){
            for(int i = 0 ; i < saleAmountList.size() ; i++){
                int no = i + 1;
                List<Object> lo = saleAmountList.get(i);
                String year = String.valueOf(lo.get(0)).trim();
                if("".equals(year)){
                    return "销售额——第"+no+"行请填写年份";
                }
                String num = String.valueOf(lo.get(1)).trim();
                if("".equals(num)){
                    return "销售额——第"+no+"行请填写销售额";
                }else{
                    try{
                        int amount = Integer.parseInt(num);
                    }catch (Exception e){
                        return "销售额——第"+no+"行销售额填写错误(只能填写数字)";
                    }
                }
            }
        }else{
            List<SaleAmount> saleAmounts = saleAmountDAO.findBySalePlanID(salePlanID);
            if(saleAmounts.size() == 0){
                return "请填写销售额";
            }
        }

        /**
         * 零件清单
         */
        List<List<Object>> partList = null;
        try {
            partList = new OfflineDataUtil().getPartListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到零件清单";
        }
        for(int i = 0 ; i < partList.size() ; i++){
            int no = i + 1;
            List<Object> lo = partList.get(i);
            String partId = String.valueOf(lo.get(0)).trim();
            if("".equals(partId)){
                return "零件清单——第"+no+"行请填写零件编号";
            }
            String partName = String.valueOf(lo.get(1)).trim();
            if("".equals(partName)){
                return "零件清单——第"+no+"行请填写零件名称";
            }
            String type = String.valueOf(lo.get(2)).trim();
            if("".equals(type)){
                return "零件清单——第"+no+"行请填写零件类型";
            }
            String material = String.valueOf(lo.get(3)).trim();
            String hardness = String.valueOf(lo.get(4)).trim();
            String origin = String.valueOf(lo.get(5)).trim();
            String yield = String.valueOf(lo.get(6)).trim();
            String standard = String.valueOf(lo.get(7)).trim();
            String scrapNum = String.valueOf(lo.get(8)).trim();
            if(!"".equals(scrapNum)){
                try {
                    BigDecimal scrap = new BigDecimal(scrapNum);
                }catch (Exception e){
                    return "零件清单——第"+no+"行报废率填写错误(只能填写错误)";
                }
            }
            String pageNum = String.valueOf(lo.get(9)).trim();
            String approveDay = String.valueOf(lo.get(10)).trim();
            if(!"".equals(approveDay)){
                try{
                    LocalDate approveDate = LocalDate.parse(approveDay , fmt);
                }catch (Exception e){
                    return "零件清单——第"+no+"行图纸审核时间填写错误(格式:yyyy-MM-dd)";
                }
            }
            String spendTime = String.valueOf(lo.get(11)).trim();
            if(!"".equals(spendTime)){
                try{
                    BigDecimal cost = new BigDecimal(spendTime);
                }catch (Exception e){
                    return "零件清单——第"+no+"行标准工时填写错误(只能填写错误)";
                }
            }

        }


        /**
         * 刀具BOM清单
         */
        List<List<Object>> knifeList = null;
        try{
            knifeList = new OfflineDataUtil().getKnifeListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到刀具BOM清单";
        }
        for(int i = 0 ; i < knifeList.size() ; i++){
            int no = i + 1;
            List<Object> lo = knifeList.get(i);
            String workpiece = String.valueOf(lo.get(0)).trim();
            if("".equals(workpiece)){
                return "刀具BOM清单——第"+no+"行请填写工件";
            }
            String processCode = String.valueOf(lo.get(1)).trim();
            if("".equals(processCode)){
                return "刀具BOM清单——第"+no+"行请填写工序号";
            }
            String processName = String.valueOf(lo.get(2)).trim();
            if("".equals(processName)){
                return "刀具BOM清单——第"+no+"行请填写工序名";
            }
            String materialCode = String.valueOf(lo.get(3)).trim();
            String brand = String.valueOf(lo.get(4)).trim();
            String type = String.valueOf(lo.get(5)).trim();
            String model = String.valueOf(lo.get(6)).trim();
            String bladeNum = String.valueOf(lo.get(7)).trim();
            if(!"".equals(bladeNum)){
                try{
                    int blade = Integer.parseInt(bladeNum);
                }catch (Exception e){
                    return "刀具BOM清单——第"+no+"行刃数填写错误（只能填写整数）";
                }
            }
            String knifeNum = String.valueOf(lo.get(8)).trim();
            if(!"".equals(knifeNum)){
                try{
                    int knifeAmount = Integer.parseInt(knifeNum);
                }catch (Exception e){
                    return "刀具BOM清单——第"+no+"行装刀数填写错误（只能填写整数）";
                }
            }
            String fixtureCode = String.valueOf(lo.get(9)).trim();
            String fixtureBrand = String.valueOf(lo.get(10)).trim();
            String coolWay = String.valueOf(lo.get(11)).trim();
            String rotateSpeed = String.valueOf(lo.get(12)).trim();
            String aoligei = String.valueOf(lo.get(13)).trim();
            String machineNum = String.valueOf(lo.get(14)).trim();
            String life = String.valueOf(lo.get(15)).trim();
            String asset = String.valueOf(lo.get(16)).trim();
        }

        /**
         * 供应商名录
         */
        List<List<Object>> supplierList = null;
        try{
            supplierList = new OfflineDataUtil().getSupplierListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到供应商名录";
        }
        for(int i = 0 ; i < supplierList.size() ; i++){
            int no = i + 1;
            List<Object> lo = supplierList.get(i);

            String supplierCode = String.valueOf(lo.get(0)).trim();
            if("".equals(supplierCode)){
                return "供应商名录——第"+no+"行请填写供应商代码";
            }
            String supplierName = String.valueOf(lo.get(1)).trim();
            if("".equals(supplierName)){
                return "供应商名录——第"+no+"行请填写供应商名称";
            }
            String area = String.valueOf(lo.get(2)).trim();
            String postal = String.valueOf(lo.get(3)).trim();
            String address = String.valueOf(lo.get(4)).trim();
            String website = String.valueOf(lo.get(5)).trim();
            String industry = String.valueOf(lo.get(6)).trim();
            String nature = String.valueOf(lo.get(7)).trim();
            String brand = String.valueOf(lo.get(8)).trim();
            String payWay = String.valueOf(lo.get(9)).trim();
            String payCondition = String.valueOf(lo.get(10)).trim();
            String contract = String.valueOf(lo.get(11)).trim();
            String phone = String.valueOf(lo.get(12)).trim();
            if(!"".equals(phone)){
                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                if(!pattern.matcher(phone).matches()){
                    return "供应商名录——第"+no+"行请填写供应商名称";
                }
            }
            String supplierType = String.valueOf(lo.get(13)).trim();
            String supplierLevel = String.valueOf(lo.get(14)).trim();
            String principal = String.valueOf(lo.get(15)).trim();
            String buymoneyOne = String.valueOf(lo.get(16)).trim();
            if(!"".equals(buymoneyOne)){
                try{
                    BigDecimal buyAmountOne = new BigDecimal(buymoneyOne);
                }catch (Exception e){
                    return "供应商名录——第"+no+"行前年采购金额填写错误(只能填写数字)";
                }
            }
            String buymoneyTwo = String.valueOf(lo.get(17)).trim();
            if(!"".equals(buymoneyTwo)){
                try{
                    BigDecimal buyAmountTwo = new BigDecimal(buymoneyTwo);
                }catch (Exception e){
                    return "供应商名录——第"+no+"行去年采购金额填写错误(只能填写数字)";
                }
            }
        }

        /**
         * 刀具采购清单
         */
        List<List<Object>> buyList = null;
        try{
            buyList = new OfflineDataUtil().getBuyListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到刀具采购清单";
        }
        for(int i = 0 ; i < buyList.size() ; i++){
            int no = i + 1;
            List<Object> lo = buyList.get(i);

            String assetCode = String.valueOf(lo.get(0)).trim();
            if("".equals(assetCode)){
                return "刀具采购清单——第"+no+"行请填写固定资产编号";
            }
            String partCode = String.valueOf(lo.get(1)).trim();
            if("".equals(partCode)){
                return "刀具采购清单——第"+no+"行请填写零件编号";
            }
            String materialCode = String.valueOf(lo.get(2)).trim();
            if("".equals(materialCode)){
                return "刀具采购清单——第"+no+"行请填写物料编号";
            }
            String knifeName = String.valueOf(lo.get(3)).trim();
            String type = String.valueOf(lo.get(4)).trim();
            String model = String.valueOf(lo.get(5)).trim();
            String brand = String.valueOf(lo.get(6)).trim();
            String life = String.valueOf(lo.get(7)).trim();
            if(!"".equals(life)){
                try{
                    int lifeNum = Integer.parseInt(life);
                }catch (Exception e){
                    return "刀具采购清单——第"+no+"行刀具寿命填写错误(只能填写整数)";
                }
            }
            String receiveAmount = String.valueOf(lo.get(8)).trim();
            if(!"".equals(receiveAmount)){
                try{
                    int receiveNum = Integer.parseInt(receiveAmount);
                }catch (Exception e){
                    return "刀具采购清单——第"+no+"行年领用数量填写错误(只能填写整数)";
                }
            }
            String receiveDay = String.valueOf(lo.get(9)).trim();
            if(!"".equals(receiveDay)){
                try{
                    LocalDate receiveDate = LocalDate.parse(receiveDay , fmt);
                }catch (Exception e){
                    return "刀具采购清单——第"+no+"行最近领用日期填写错误(格式:yyyy-MM-dd)";
                }
            }
            String note = String.valueOf(lo.get(11)).trim();
        }

        /**
         * 刀具领用清单
         */
        List<List<Object>> receiveList = null;
        try{
            receiveList = new OfflineDataUtil().getReceiveListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到刀具领用清单";
        }
        for(int i = 0 ; i < receiveList.size() ; i++){
            int no = i + 1;
            List<Object> lo = receiveList.get(i);
            String materialCode = String.valueOf(lo.get(0)).trim();
            if("".equals(materialCode)){
                return "刀具领用清单——第"+no+"行请填写物料编号";
            }
            String materialName = String.valueOf(lo.get(1)).trim();
            if("".equals(materialName)){
                return "刀具领用清单——第"+no+"行请填写物料编号";
            }
            String model = String.valueOf(lo.get(2)).trim();
            String brand = String.valueOf(lo.get(3)).trim();
            String receiveNum = String.valueOf(lo.get(4)).trim();
            if(!"".equals(receiveNum)){
                try{
                    BigDecimal receiveAmount = new BigDecimal(receiveNum);
                }catch (Exception e){
                    return "刀具领用清单——第"+no+"行领用数量填写错误(只能填写数字)";
                }
            }
            String receivePrice = String.valueOf(lo.get(5)).trim();
            if(!"".equals(receivePrice)){
                try{
                    BigDecimal onePrice = new BigDecimal(receivePrice);
                }catch (Exception e){
                    return "刀具领用清单——第"+no+"行领用单价填写错误(只能填写数字)";
                }
            }
            String receiveMoney = String.valueOf(lo.get(6)).trim();
            if(!"".equals(receiveMoney)){
                try{
                    BigDecimal money = new BigDecimal(receiveMoney);
                }catch (Exception e){
                    return "刀具领用清单——第"+no+"行领用金额填写错误(只能填写数字)";
                }
            }
            String receiveDept = String.valueOf(lo.get(7)).trim();
            String receiveDay = String.valueOf(lo.get(8)).trim();
            if(!"".equals(receiveDay)){
                try{
                    LocalDate receiveDate = LocalDate.parse(receiveDay , fmt);
                }catch (Exception e){
                    return "刀具领用清单——第"+no+"行最后一次领用时间填写错误(格式:yyyy-MM-dd)";
                }
            }
            String note = String.valueOf(lo.get(9)).trim();
        }

        return "OK";
    }

    @Transactional
    public String writeData(HttpServletRequest request, HttpServletResponse response , HttpSession session){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("customerFile");
        int mId = Integer.parseInt(request.getParameter("mId"));
        if (file.isEmpty()) {
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String salePlanID = request.getParameter("salePlanID");
        IUser user = (IUser)session.getAttribute("user");

        /**
         * 固定资产清单
         */
        List<List<Object>> assetList = null;
        try {
            assetList = new OfflineDataUtil().getAssetListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到固定资产清单";
        }
        for(int i = 0 ; i < assetList.size() ; i++){
            List<Object> lo = assetList.get(i);
            Asset asset = new Asset();
            asset.setSalePlanID(salePlanID);
            String assetType = String.valueOf(lo.get(0)).trim();
            asset.setAssetType(assetType);
            String assetId = String.valueOf(lo.get(1)).trim();
            asset.setAssetNum(assetId);
            String assetName = String.valueOf(lo.get(2)).trim();
            asset.setAssetName(assetName);
            String model = String.valueOf(lo.get(3)).trim();
            if(!"".equals(model)){
                asset.setModel(model);
            }
            String brand = String.valueOf(lo.get(4)).trim();
            if(!"".equals(brand)){
                asset.setBrand(brand);
            }
            String supplier = String.valueOf(lo.get(5)).trim();
            if(!"".equals(supplier)){
                asset.setSupplier(supplier);
            }
            String position = String.valueOf(lo.get(6)).trim();
            if(!"".equals(position)){
                asset.setPosition(position);
            }
            String amount = String.valueOf(lo.get(7)).trim();
            if(!"".equals(amount)){
                asset.setAmount(Integer.parseInt(amount));
            }
            String residual = String.valueOf(lo.get(8)).trim();
            if(!"".equals(residual)){
                asset.setResidual(new BigDecimal(residual));
            }
            String status = String.valueOf(lo.get(9)).trim();
            if(!"".equals(status)){
                asset.setStatus(status);
            }
            String ports = String.valueOf(lo.get(10)).trim();
            if(!"".equals(ports)){
                asset.setPort(ports);
            }
            String coolWay = String.valueOf(lo.get(11)).trim();
            if(!"".equals(coolWay)){
                asset.setCoolWay(coolWay);
            }
            String note = String.valueOf(lo.get(12)).trim();
            if(!"".equals(note)){
                asset.setNote(note);
            }
            assetDAO.save(asset);
        }

        /**
         * 员工花名册
         */
        List<List<Object>> employeeList = null;
        try {
            employeeList = new OfflineDataUtil().getEmployeeListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到员工花名册";
        }
        for(int i = 0 ; i < employeeList.size() ; i++){
            Employee employee = new Employee();
            List<Object> lo = employeeList.get(i);
            employee.setSalePlanID(salePlanID);
            String account = String.valueOf(lo.get(0)).trim();
            employee.setAccount(account);
            String name = String.valueOf(lo.get(1)).trim();
            employee.setName(name);
            String identityID = String.valueOf(lo.get(2)).trim();
            if(!"".equals(identityID)){
                employee.setIdentityNum(identityID);
            }
            String education = String.valueOf(lo.get(3)).trim();
            if(!"".equals(education)){
                employee.setEducation(education);
            }
            String major = String.valueOf(lo.get(4)).trim();
            if(!"".equals(major)){
                employee.setMajor(major);
            }
            String enter = String.valueOf(lo.get(5)).trim();
            if(!"".equals(enter)){
                employee.setEnter(enter);
            }
            String sex = String.valueOf(lo.get(6)).trim();
            if(!"".equals(sex)){
                employee.setSex(sex);
            }
            String dept = String.valueOf(lo.get(7)).trim();
            if(!"".equals(dept)){
                employee.setDept(dept);
            }
            String duty = String.valueOf(lo.get(8)).trim();
            if(!"".equals(duty)){
                employee.setDuty(duty);
            }
            String contact = String.valueOf(lo.get(9)).trim();
            if(!"".equals(contact)){
                employee.setContact(contact);
            }
            String relevanceAsset = String.valueOf(lo.get(10)).trim();
            if(!"".equals(relevanceAsset)){
                employee.setRelevanceAsset(relevanceAsset);
            }
            String relevancePart = String.valueOf(lo.get(11)).trim();
            if(!"".equals(relevancePart)){
                employee.setRelevancePart(relevancePart);
            }
            String note = String.valueOf(lo.get(12)).trim();
            if(!"".equals(note)){
                employee.setNote(note);
            }
            employeeDAO.save(employee);
        }

        /**
         * 刀具即时库龄
         */
        List<List<Object>> stockList = null;
        try {
            stockList = new OfflineDataUtil().getStockListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "未找到刀具即时库龄";
        }
        for(int i = 0 ; i < stockList.size() ; i++){
            List<Object> lo = stockList.get(i);
            Stock stock = new Stock();
            stock.setSalePlanID(salePlanID);
            String type = String.valueOf(lo.get(0)).trim();
            stock.setType(type);
            String num1 = String.valueOf(lo.get(1)).trim();
            stock.setNumone(new BigDecimal(num1));
            String num2 = String.valueOf(lo.get(2)).trim();
            stock.setNumtwo(new BigDecimal(num2));
            String num3 = String.valueOf(lo.get(3)).trim();
            stock.setNumthree(new BigDecimal(num3));
            String num4 = String.valueOf(lo.get(4)).trim();
            stock.setNumfour(new BigDecimal(num4));
            String num5 = String.valueOf(lo.get(5)).trim();
            stock.setNumfive(new BigDecimal(num5));
            String total = String.valueOf(lo.get(6)).trim();
            stock.setSum(new BigDecimal(total));
            stockDAO.save(stock);
        }

        /**
         * 付款账期现状
         */
        List<List<Object>> paymentList = null;
        try{
            paymentList = new OfflineDataUtil().getPaymentListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到付款账期现状";
        }
        PayCycle payCycle = new PayCycle();
        payCycle.setSalePlanID(salePlanID);
        String num1 = String.valueOf(paymentList.get(0).get(1)).trim();
        payCycle.setPayone(new BigDecimal(num1));
        String num2 = String.valueOf(paymentList.get(1).get(1)).trim();
        payCycle.setPaytwo(new BigDecimal(num2));
        String num3 = String.valueOf(paymentList.get(2).get(1)).trim();
        payCycle.setPaythree(new BigDecimal(num3));
        String num4 = String.valueOf(paymentList.get(3).get(1)).trim();
        payCycle.setPayfour(new BigDecimal(num4));
        payCycleDAO.save(payCycle);

        /**
         * 生产产量
         */
        List<List<Object>> productionList = null;
        try{
            productionList = new OfflineDataUtil().getProductionListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到生产产量报表";
        }
        for(int i = 0 ; i < productionList.size() ; i++){
            List<Object> lo = productionList.get(i);
            Product product = new Product();
            product.setSalePlanID(salePlanID);
            String productDate = String.valueOf(lo.get(0)).trim();
            product.setProductDate(productDate);
            String partNum = String.valueOf(lo.get(1)).trim();
            product.setPartNum(partNum);
            String partName = String.valueOf(lo.get(2)).trim();
            product.setPartName(partName);
            String allAmount = String.valueOf(lo.get(3)).trim();
            product.setAllAmount(Integer.parseInt(allAmount));
            String amount1 = String.valueOf(lo.get(4)).trim();
            product.setAmountOne(Integer.parseInt(amount1));
            String amount2 = String.valueOf(lo.get(5)).trim();
            product.setAmountTwo(Integer.parseInt(amount2));
            String amount3 = String.valueOf(lo.get(6)).trim();
            product.setAmountThree(Integer.parseInt(amount3));
            productDAO.save(product);
        }

        /**
         * 销售额
         */
        List<List<Object>> saleAmountList = null;
        try{
            saleAmountList = new OfflineDataUtil().getSaleAmountListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到销售额";
        }
        for(int i = 0 ; i < saleAmountList.size() ; i++){
            List<Object> lo = saleAmountList.get(i);
            SaleAmount saleAmount = new SaleAmount();
            saleAmount.setSalePlanID(salePlanID);
            String year = String.valueOf(lo.get(0)).trim();
            saleAmount.setYear(year);
            String money = String.valueOf(lo.get(1)).trim();
            saleAmount.setAmount(new BigDecimal(money));
            String note = String.valueOf(lo.get(2)).trim();
            saleAmount.setNote(note);
            saleAmountDAO.save(saleAmount);
        }

        /**
         * 零件清单
         */
        List<List<Object>> partList = null;
        try{
            partList = new OfflineDataUtil().getPartListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到零件清单";
        }
        for(int i = 0 ; i < partList.size() ; i++){
            List<Object> lo = partList.get(i);
            PartList part = new PartList();
            part.setSalePlanID(salePlanID);
            String partNum = String.valueOf(lo.get(0)).trim();
            if("".equals(partNum)){
                break;
            }
            part.setPartNum(partNum);
            String partName = String.valueOf(lo.get(1)).trim();
            part.setPartName(partName);
            String partType = String.valueOf(lo.get(2)).trim();
            part.setPartType(partType);
            String material = String.valueOf(lo.get(3)).trim();
            part.setMaterial(material);
            String hardness = String.valueOf(lo.get(4)).trim();
            part.setHardness(hardness);
            String origin = String.valueOf(lo.get(5)).trim();
            part.setOrigin(origin);
            String yield = String.valueOf(lo.get(6)).trim();
            part.setYield(yield);
            String standard = String.valueOf(lo.get(7)).trim();
            part.setStandard(standard);
            String scrap = String.valueOf(lo.get(8)).trim();
            part.setScrap(new BigDecimal(scrap));
            String pageNum = String.valueOf(lo.get(9)).trim();
            part.setPageNum(pageNum);
            String approveDate = String.valueOf(lo.get(10)).trim();
            part.setApproveDate(approveDate);
            String spendTime = String.valueOf(lo.get(11)).trim();
            part.setSpendTime(spendTime);
            partListDAO.save(part);
        }

        /**
         * 刀具BOM清单
         */
        List<List<Object>> knifeList = null;
        try{
            knifeList = new OfflineDataUtil().getKnifeListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到刀具BOM清单";
        }
        for(int i = 0 ; i < knifeList.size() ; i++){
            List<Object> lo = knifeList.get(i);
            KnifeList knife = new KnifeList();
            String workpiece = String.valueOf(lo.get(0)).trim();
            if("".equals(workpiece)){
                break;
            }
            knife.setWorkpiece(workpiece);
            String processCode = String.valueOf(lo.get(1)).trim();
            knife.setProcessCode(processCode);
            String processName = String.valueOf(lo.get(2)).trim();
            knife.setProcessName(processName);
            String materialCode = String.valueOf(lo.get(3)).trim();
            if(!"".equals(materialCode)){
                knife.setMaterialCode(materialCode);

            }
            String brand = String.valueOf(lo.get(4)).trim();
            if(!"".equals(brand)){
                knife.setBrand(brand);
            }
            String type = String.valueOf(lo.get(5)).trim();
            if(!"".equals(type)){
                knife.setType(type);
            }
            String model = String.valueOf(lo.get(6)).trim();
            if(!"".equals(model)){
                knife.setModel(model);
            }
            String bladeNum = String.valueOf(lo.get(7)).trim();
            if(!"".equals(bladeNum)){
                knife.setBladeNum(Integer.parseInt(bladeNum));
            }
            String knifeNum = String.valueOf(lo.get(8)).trim();
            if(!"".equals(knifeNum)){
                knife.setKnifeNum(Integer.parseInt(knifeNum));
            }
            String fixtureCode = String.valueOf(lo.get(9)).trim();
            if(!"".equals(fixtureCode)){
                knife.setFixtureCode(fixtureCode);
            }
            String fixtureBrand = String.valueOf(lo.get(10)).trim();
            if(!"".equals(fixtureBrand)){
                knife.setFixtureBrand(fixtureBrand);
            }
            String coolWay = String.valueOf(lo.get(11)).trim();
            if(!"".equals(coolWay)){
                knife.setCoolWay(coolWay);
            }
            String rotateSpeed = String.valueOf(lo.get(12)).trim();
            if(!"".equals(rotateSpeed)){
                knife.setRotateSpeed(rotateSpeed);
            }
            String aoligei = String.valueOf(lo.get(13)).trim();
            if(!"".equals(aoligei)){
                knife.setAoligei(aoligei);
            }
            String machineNum = String.valueOf(lo.get(14)).trim();
            if(!"".equals(machineNum)){
                knife.setMachineNum(machineNum);
            }
            String life = String.valueOf(lo.get(15)).trim();
            if(!"".equals(life)){
                knife.setLife(life);
            }
            String asset = String.valueOf(lo.get(16)).trim();
            if(!"".equals(asset)){
                knife.setAsset(asset);
            }
            knifeListDAO.save(knife);
        }

        /**
         * 供应商名录
         */
        List<List<Object>> supplierList = null;
        try{
            supplierList = new OfflineDataUtil().getSupplierListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到供应商名录";
        }
        for(int i = 0 ; i < supplierList.size() ; i++){
            List<Object> lo = supplierList.get(i);
            Supplier supplier = new Supplier();
            supplier.setSalePlanID(salePlanID);
            String supplierCode = String.valueOf(lo.get(0)).trim();
            if("".equals(supplierCode)){
                break;
            }
            supplier.setSupplierCode(supplierCode);
            String supplierName = String.valueOf(lo.get(1)).trim();
            supplier.setSupplierName(supplierName);
            String area = String.valueOf(lo.get(2)).trim();
            if(!"".equals(area)){
                supplier.setArea(area);
            }
            String postal = String.valueOf(lo.get(3)).trim();
            if(!"".equals(postal)){
                supplier.setPostal(postal);
            }
            String address = String.valueOf(lo.get(4)).trim();
            if(!"".equals(address)){
                supplier.setAddress(address);
            }
            String website = String.valueOf(lo.get(5)).trim();
            if(!"".equals(website)){
                supplier.setWebsite(website);
            }
            String industry = String.valueOf(lo.get(6)).trim();
            if(!"".equals(industry)){
                supplier.setIndustry(industry);
            }
            String nature = String.valueOf(lo.get(7)).trim();
            if(!"".equals(nature)){
                supplier.setNature(nature);
            }
            String brand = String.valueOf(lo.get(8)).trim();
            if(!"".equals(brand)){
                supplier.setBrand(brand);
            }
            String payWay = String.valueOf(lo.get(9)).trim();
            if(!"".equals(payWay)){
                supplier.setPayWay(payWay);
            }
            String payCondition = String.valueOf(lo.get(10)).trim();
            if(!"".equals(payCondition)){
                supplier.setPayCondition(payCondition);
            }
            String contract = String.valueOf(lo.get(11)).trim();
            if(!"".equals(contract)){
                supplier.setContract(contract);
            }
            String phone = String.valueOf(lo.get(12)).trim();
            if(!"".equals(phone)){
                supplier.setPhone(phone);
            }
            String supplierType = String.valueOf(lo.get(13)).trim();
            if(!"".equals(supplierType)){
                supplier.setSupplierType(supplierType);
            }
            String supplierLevel = String.valueOf(lo.get(14)).trim();
            if(!"".equals(supplierLevel)){
                supplier.setSupplierLevel(supplierLevel);
            }
            String principal = String.valueOf(lo.get(15)).trim();
            if(!"".equals(principal)){
                supplier.setPrincipal(principal);
            }
            String buymoneyOne = String.valueOf(lo.get(16)).trim();
            if(!"".equals(buymoneyOne)){
                supplier.setBuymoneyOne(new BigDecimal(buymoneyOne));
            }
            String buymoneyTwo = String.valueOf(lo.get(17)).trim();
            if(!"".equals(buymoneyTwo)){
                supplier.setBuymoneyTwo(new BigDecimal(buymoneyTwo));
            }
            supplierDAO.save(supplier);
        }

        /**
         * 刀具采购清单
         */
        List<List<Object>> buyList = null;
        try{
            buyList = new OfflineDataUtil().getBuyListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到刀具采购清单";
        }
        for(int i = 0 ; i < buyList.size() ; i++){
            List<Object> lo = buyList.get(i);
            BuyList buy = new BuyList();
            buy.setSalePlanID(salePlanID);
            String assetCode = String.valueOf(lo.get(0)).trim();
            if("".equals(assetCode)){
                break;
            }
            buy.setAssetCode(assetCode);
            String partCode = String.valueOf(lo.get(1)).trim();
            buy.setPartCode(partCode);
            String materialCode = String.valueOf(lo.get(2)).trim();
            buy.setMaterialCode(materialCode);
            String knifeName = String.valueOf(lo.get(3)).trim();
            if(!"".equals(knifeName)){
                buy.setKnifeName(knifeName);
            }
            String type = String.valueOf(lo.get(4)).trim();
            if(!"".equals(type)){
                buy.setKnifeType(type);
            }
            String model = String.valueOf(lo.get(5)).trim();
            if(!"".equals(model)){
                buy.setModel(model);
            }
            String brand = String.valueOf(lo.get(6)).trim();
            if(!"".equals(brand)){
                buy.setBrand(brand);
            }
            String life = String.valueOf(lo.get(7)).trim();
            if(!"".equals(life)){
                buy.setLife(life);
            }
            String receiveAmount = String.valueOf(lo.get(8)).trim();
            if(!"".equals(receiveAmount)){
                buy.setReceiveAmount(receiveAmount);
            }
            String receiveDate = String.valueOf(lo.get(9)).trim();
            if(!"".equals(receiveDate)){
                buy.setReceiveDate(receiveDate);
            }
            String note = String.valueOf(lo.get(10)).trim();
            if(!"".equals(note)){
                buy.setNote(note);
            }
            buyListDAO.save(buy);
        }

        /**
         * 刀具领用清单
         */
        List<List<Object>> receiveList = null;
        try{
            receiveList = new OfflineDataUtil().getReceiveListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到刀具领用清单";
        }
        for(int i = 0 ; i < receiveList.size() ; i++){
            List<Object> lo = receiveList.get(i);
            ReceiveList receive = new ReceiveList();
            receive.setSalePlanID(salePlanID);
            String materialCode = String.valueOf(lo.get(0)).trim();
            if("".equals(materialCode)){
                break;
            }
            receive.setMaterialCode(materialCode);
            String materialName = String.valueOf(lo.get(1)).trim();
            receive.setMaterialName(materialName);
            String model = String.valueOf(lo.get(2)).trim();
            if(!"".equals(model)){
                receive.setModel(model);
            }
            String type = String.valueOf(lo.get(3)).trim();
            if(!"".equals(type)){
                receive.setType(type);
            }
            String receiveAmount = String.valueOf(lo.get(4)).trim();
            if(!"".equals(receiveAmount)){
                receive.setReceiveAmount(Integer.parseInt(receiveAmount));
            }
            String receivePrice = String.valueOf(lo.get(5)).trim();
            if(!"".equals(receivePrice)){
                receive.setReceivePice(new BigDecimal(receivePrice));
            }
            String receiveMoney = String.valueOf(lo.get(6)).trim();
            if(!"".equals(receiveMoney)){
                receive.setReceiveMoney(new BigDecimal(receiveMoney));
            }
            String receiveDept = String.valueOf(lo.get(7)).trim();
            if(!"".equals(receiveDept)){
                receive.setReceiveDept(receiveDept);
            }
            String receiveDate = String.valueOf(lo.get(8)).trim();
            if(!"".equals(receiveDate)){
                receive.setReceiveDate(receiveDate);
            }
            String note = String.valueOf(lo.get(9)).trim();
            if(!"".equals(note)){
                receive.setNote(note);
            }
            receiveListDAO.save(receive);
        }
        return "OK";
    }

}
