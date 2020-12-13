package com.knowhy.crm.controller;

import com.knowhy.crm.dao.CompanyInfoDAO;
import com.knowhy.crm.entity.CustomerDetail;
import com.knowhy.crm.pojo.CompanyInfo;
import com.knowhy.crm.service.CompanyInfoService;
import com.knowhy.crm.service.CustomerDetailService;
import com.knowhy.crm.util.ExcelBeanUtil;
import com.knowhy.crm.util.ExcelUtil;
import com.knowhy.crm.util.WebUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerDetailController {

    @Autowired
    CustomerDetailService customerDetailService;
    @Autowired
    CompanyInfoService companyInfoService;
    @Autowired
    CompanyInfoDAO companyInfoDAO;

    @RequestMapping("/downloadCustomerByExcel")
    public String download(String salePlanNum , HttpServletResponse response) throws Exception {
        String sheetProductName = "客户在线尽调明细.xls";
        String excelProductName = "客户在线尽调明细.xls";
        List<CompanyInfo> companyInfoList = companyInfoDAO.findBySalePlanID(salePlanNum);
        int cid = companyInfoList.get(0).getId();
        CustomerDetail customerDetail = customerDetailService.findDetailByCid(cid);
        List<CustomerDetail> customerDetailList = new ArrayList<>();
        customerDetailList.add(customerDetail);
        String[] headers=new String[]{"您公司的全称" , "您的姓名" , "您的联系方式" , "您的微信号" , "您公司的注册资本(万元)" , "您公司成立于" , "您公司的性质" , "您公司有几个子公司" , "您公司员工数量" , "您公司所在的行业" , "您公司是" , "您公司主要经营的产品" ,
                "您是通过何种途径了解诺而为" , "您公司之前有做过刀具外包管理吗" , "服务商名称" , "您之前的服务主要碰到的问题" , "您公司的刀具总消耗为（万元/年）" , "目前负责该项目的联系人" , "手机号码" , "邮箱" , "您对该项目的进展要求" ,
                "您公司近三年的平均产值" , "您公司的产品销售市场主要" , "您公司的主要竞争对手" , "您公司未来三年的的发展计划" , "您公司主要使用的设备类型" , "您公司刀具采购的主要来源" , "您公司的主要设备品牌" , "您公司设备数量约为", "您公司主要设备使用年限" , "您公司的产品主要生产形式" ,
                "您公司近3 年刀具采购金额" , "您公司刀具采购的主要来源" , "您公司刀具采购的主要品牌" , "您公司刀具采购的模式" , "您公司的刀具库存管理模式" , "钻铰类头约占(%)" , "铣刀约占(%)" , "丝锥约占(%)" , "其它约占(%)" , "车刀片约占(%)" , "铣刀片约占(%)" , "钻镗刀片类约占(%)" , "其它约占(%)" ,
                "CBN 约占(%)" , "PCD 约占(%)" , "焊接超硬约占(%)" , "您公司是否使用高速钢刀具" , "高速钢刀具品牌" , "您公司目前的刀具库存总金额约为" , "库龄<90 天约占(%)" , "库龄90-180 天约占(%)" , "库龄180-365 天约占(%)" , "库龄365 天以上约占(%)" , "标准刀具约占(%)" , "非标刀具约占(%)" ,
        "是否使用ERP 管理系统" , "ERP品牌" , "是否使用MES 管理系统" , "MES品牌" , "生产现场的换班安排" , "生产休息" , "生产线共有(条)" , "库管的人员约有(人)" , "刀具调试发放人员(人)" , "刀具发放方式(人)" , "刀具回收方式" , "刀具的付款周期" , "付款方式" , "刀具修磨" , "每月修磨费用" , "刀具有优化流程" , "刀具管理的主要诉求"};

        List<Map<Integer, Object>> dataList= ExcelBeanUtil.manageCustomerList(customerDetailList);
        Workbook wb=new HSSFWorkbook();
        ExcelUtil.fillExcelSheetData(dataList, wb, headers, sheetProductName);
        WebUtil.downloadExcel(response, wb, excelProductName);
        return excelProductName;
    }

    @RequestMapping("/downloadAllCustomerByExcel")
    public String downloadAll(HttpServletResponse response) throws Exception {
        String sheetProductName = "客户在线尽调明细.xls";
        String excelProductName = "客户在线尽调明细.xls";
        List<CompanyInfo> companyInfoList = companyInfoService.findAll();
        List<CustomerDetail> customerDetailList = new ArrayList<>();
        for(int i = 0 ; i < companyInfoList.size() ; i++){
            int cid = companyInfoList.get(i).getId();
            CustomerDetail customerDetail = customerDetailService.findDetailByCid(cid);
            customerDetailList.add(customerDetail);
        }
        String[] headers=new String[]{"您公司的全称" , "您的姓名" , "您的联系方式" , "您的微信号" , "您公司的注册资本(万元)" , "您公司成立于" , "您公司的性质" , "您公司有几个子公司" , "您公司员工数量" , "您公司所在的行业" , "您公司是" , "您公司主要经营的产品" ,
                "您是通过何种途径了解诺而为" , "您公司之前有做过刀具外包管理吗" , "服务商名称" , "您之前的服务主要碰到的问题" , "您公司的刀具总消耗为（万元/年）" , "目前负责该项目的联系人" , "手机号码" , "邮箱" , "您对该项目的进展要求" ,
                "您公司近三年的平均产值" , "您公司的产品销售市场主要" , "您公司的主要竞争对手" , "您公司未来三年的的发展计划" , "您公司主要使用的设备类型" , "您公司刀具采购的主要来源" , "您公司的主要设备品牌" , "您公司设备数量约为", "您公司主要设备使用年限" , "您公司的产品主要生产形式" ,
                "您公司近3 年刀具采购金额" , "您公司刀具采购的主要来源" , "您公司刀具采购的主要品牌" , "您公司刀具采购的模式" , "您公司的刀具库存管理模式" , "钻铰类头约占(%)" , "铣刀约占(%)" , "丝锥约占(%)" , "其它约占(%)" , "车刀片约占(%)" , "铣刀片约占(%)" , "钻镗刀片类约占(%)" , "其它约占(%)" ,
                "CBN 约占(%)" , "PCD 约占(%)" , "焊接超硬约占(%)" , "您公司是否使用高速钢刀具" , "高速钢刀具品牌" , "您公司目前的刀具库存总金额约为" , "库龄<90 天约占(%)" , "库龄90-180 天约占(%)" , "库龄180-365 天约占(%)" , "库龄365 天以上约占(%)" , "标准刀具约占(%)" , "非标刀具约占(%)" ,
                "是否使用ERP 管理系统" , "ERP品牌" , "是否使用MES 管理系统" , "MES品牌" , "生产现场的换班安排" , "生产休息" , "生产线共有(条)" , "库管的人员约有(人)" , "刀具调试发放人员(人)" , "刀具发放方式" , "刀具回收方式" , "刀具的付款周期" , "付款方式" , "刀具修磨" , "每月修磨费用" , "刀具有优化流程" , "刀具管理的主要诉求"};

        List<Map<Integer, Object>> dataList= ExcelBeanUtil.manageCustomerList(customerDetailList);
        Workbook wb=new HSSFWorkbook();
        ExcelUtil.fillExcelSheetData(dataList, wb, headers, sheetProductName);
        WebUtil.downloadExcel(response, wb, excelProductName);
        return excelProductName;
    }

    @RequestMapping("/downloadBatchCustomerByExcel")
    public String downloadBatch(@RequestParam("cids")int[] cids ,HttpServletResponse response) throws Exception {
        String sheetProductName = "客户在线尽调明细.xls";
        String excelProductName = "客户在线尽调明细.xls";
        List<CustomerDetail> customerDetailList = new ArrayList<>();
        for(int i = 0 ; i < cids.length ; i++){
            int cid = cids[i];
            CustomerDetail customerDetail = customerDetailService.findDetailByCid(cid);
            customerDetailList.add(customerDetail);
        }
        String[] headers=new String[]{"您公司的全称" , "您的姓名" , "您的联系方式" , "您的微信号" , "您公司的注册资本(万元)" , "您公司成立于" , "您公司的性质" , "您公司有几个子公司" , "您公司员工数量" , "您公司所在的行业" , "您公司是" , "您公司主要经营的产品" ,
                "您是通过何种途径了解诺而为" , "您公司之前有做过刀具外包管理吗" , "服务商名称" , "您之前的服务主要碰到的问题" , "您公司的刀具总消耗为（万元/年）" , "目前负责该项目的联系人" , "手机号码" , "邮箱" , "您对该项目的进展要求" ,
                "您公司近三年的平均产值" , "您公司的产品销售市场主要" , "您公司的主要竞争对手" , "您公司未来三年的的发展计划" , "您公司主要使用的设备类型" , "您公司刀具采购的主要来源" , "您公司的主要设备品牌" , "您公司设备数量约为", "您公司主要设备使用年限" , "您公司的产品主要生产形式" ,
                "您公司近3 年刀具采购金额" , "您公司刀具采购的主要来源" , "您公司刀具采购的主要品牌" , "您公司刀具采购的模式" , "您公司的刀具库存管理模式" , "钻铰类头约占(%)" , "铣刀约占(%)" , "丝锥约占(%)" , "其它约占(%)" , "车刀片约占(%)" , "铣刀片约占(%)" , "钻镗刀片类约占(%)" , "其它约占(%)" ,
                "CBN 约占(%)" , "PCD 约占(%)" , "焊接超硬约占(%)" , "您公司是否使用高速钢刀具" , "高速钢刀具品牌" , "您公司目前的刀具库存总金额约为" , "库龄<90 天约占(%)" , "库龄90-180 天约占(%)" , "库龄180-365 天约占(%)" , "库龄365 天以上约占(%)" , "标准刀具约占(%)" , "非标刀具约占(%)" ,
                "是否使用ERP 管理系统" , "ERP品牌" , "是否使用MES 管理系统" , "MES品牌" , "生产现场的换班安排" , "生产休息" , "生产线共有(条)" , "库管的人员约有(人)" , "刀具调试发放人员(人)" , "刀具发放方式" , "刀具回收方式" , "刀具的付款周期" , "付款方式" , "刀具修磨" , "每月修磨费用" , "刀具有优化流程" , "刀具管理的主要诉求"};

        List<Map<Integer, Object>> dataList= ExcelBeanUtil.manageCustomerList(customerDetailList);
        Workbook wb=new HSSFWorkbook();
        ExcelUtil.fillExcelSheetData(dataList, wb, headers, sheetProductName);
        WebUtil.downloadExcel(response, wb, excelProductName);
        return excelProductName;
    }

    @RequestMapping("/findAllCustomer")
    public List<CompanyInfo> findAll(){
        return companyInfoService.findAll();
    }


}
