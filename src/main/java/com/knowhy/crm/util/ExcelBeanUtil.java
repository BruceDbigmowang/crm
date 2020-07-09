package com.knowhy.crm.util;

import com.knowhy.crm.entity.CustomerDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelBeanUtil {

    public static List<Map<Integer, Object>> manageCustomerList(final List<CustomerDetail> customerDetailList){
        List<Map<Integer, Object>> dataList=new ArrayList<>();
        if (customerDetailList!=null && customerDetailList.size()>0) {
            int length=customerDetailList.size();

            Map<Integer, Object> dataMap;
            CustomerDetail bean;
            for (int i = 0; i < length; i++) {
                bean=customerDetailList.get(i);

                dataMap=new HashMap<>();
                dataMap.put(0, bean.getCompanyName());
                dataMap.put(1, bean.getContact());
                dataMap.put(2, bean.getContactWay());
                dataMap.put(3, bean.getWechatNum());
                dataMap.put(4, bean.getRegisterMoney());
                dataMap.put(5, bean.getEstablishTime());
                dataMap.put(6, bean.getBussniessNature());
                dataMap.put(7, bean.getSonCompanyNum());
                dataMap.put(8, bean.getEmployeeNum());
                dataMap.put(9, bean.getIndustry());
                dataMap.put(10, bean.getIndustryNature());
                dataMap.put(11, bean.getProduct());
                dataMap.put(12, bean.getMeans());
                dataMap.put(13, bean.getToolManage());
                dataMap.put(14, bean.getFacilitatorName());
                dataMap.put(15, bean.getProblem());
                dataMap.put(16, bean.getConsume());
                dataMap.put(17, bean.getPrinciple());
                dataMap.put(18, bean.getMobile());
                dataMap.put(19, bean.getEmail());
                dataMap.put(20, bean.getProgressAsk());
                dataMap.put(21, bean.getProduction());
                dataMap.put(22, bean.getMarket());
                dataMap.put(23, bean.getCompetitor());
                dataMap.put(24, bean.getProject());
                dataMap.put(25, bean.getAssetType());
                dataMap.put(26, bean.getResource());
                dataMap.put(27, bean.getBrand());
                dataMap.put(28, bean.getAmount());
                dataMap.put(29, bean.getLife());
                dataMap.put(30 , bean.getForm());
                dataMap.put(31 , bean.getBuyMoney());
                dataMap.put(32 , bean.getBuySource());
                dataMap.put(33 , bean.getBuyBrand());
                dataMap.put(34 , bean.getBuyModel());
                dataMap.put(35 , bean.getManageModel());
                dataMap.put(36 , bean.getZtzb());
                dataMap.put(37 , bean.getXdzb());
                dataMap.put(38 , bean.getSzzb());
                dataMap.put(39 , bean.getQtOne());
                dataMap.put(40 , bean.getCdpzb());
                dataMap.put(41 , bean.getXdpzb());
                dataMap.put(42 , bean.getTdpzb());
                dataMap.put(43 , bean.getQtTwo());
                dataMap.put(44 , bean.getCbn());
                dataMap.put(45 , bean.getPcd());
                dataMap.put(46 , bean.getHjzb());
                dataMap.put(47 , bean.getHasGSG());
                dataMap.put(48 , bean.getGsgBrand());
                dataMap.put(49 , bean.getStockMoney());
                dataMap.put(50 , bean.getStockOne());
                dataMap.put(51 , bean.getStockTwo());
                dataMap.put(52 , bean.getStockThree());
                dataMap.put(53 , bean.getStockFour());
                dataMap.put(54 , bean.getNormTool());
                dataMap.put(55 , bean.getUnnormTool());
                dataMap.put(56 , bean.getHasERP());
                dataMap.put(57 , bean.getErpBrand());
                dataMap.put(58 , bean.getHasMES());
                dataMap.put(59 , bean.getMesBrand());
                dataMap.put(60 , bean.getShiftManage());
                dataMap.put(61 , bean.getProductRest());
                dataMap.put(62 , bean.getProductNum());
                dataMap.put(63 , bean.getStockPerson());
                dataMap.put(64 , bean.getGrantPerson());
                dataMap.put(65 , bean.getGrantWay());
                dataMap.put(66 , bean.getReturnWay());
                dataMap.put(67 , bean.getPayCycle());
                dataMap.put(68 , bean.getPayWay());
                dataMap.put(69 , bean.getRepair());
                dataMap.put(70 , bean.getRepairSpend());
                dataMap.put(71 , bean.getOptimize());
                dataMap.put(72 , bean.getAppeal());
                dataList.add(dataMap);
            }
        }
        return dataList;
    }

}
