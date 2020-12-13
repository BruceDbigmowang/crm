package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SalesPlan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SalesPlanDAO extends JpaRepository<SalesPlan, String> {

    List<SalesPlan> findByMakeDate(LocalDate makeDate);
    List<SalesPlan> findByCustomerName(String customerName);

    List<SalesPlan> findByPlanStatusInAndPrincipalAndSaleArrangeOrderByUpdateDateDesc(Pageable pageable , List<String> status , String principal , String saleArrange);
    List<SalesPlan> findByPlanStatusInAndSaleArrangeOrderByUpdateDateDesc(Pageable pageable , List<String> status , String saleArrange);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusInAndPrincipalAndSaleArrangeOrderByUpdateDateDesc(Pageable pageable , String customerName , List<String> status , String principal , String saleArrange);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusInAndSaleArrangeOrderByUpdateDateDesc(Pageable pageable , String customerName , List<String> status , String saleArrange);

    List<SalesPlan> findByPrincipalAndSaleArrange(String principal , String arrange);
    List<SalesPlan> findBySaleArrange(String saleArrange);
    List<SalesPlan> findByCustomerNameLikeAndPrincipalAndSaleArrange(String customerName , String principal , String arrange);
    List<SalesPlan> findByCustomerNameLikeAndSaleArrange(String customerName , String arrange);

    List<SalesPlan> findByPlanStatusAndAssistantAndOperateArrange(String planStatus , String assistant , String operateStatus);
    List<SalesPlan> findByAssistantAndOperateArrange(String assistant , String operateStatus);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndAssistantAndOperateArrange(String customerName , String planStatus , String assistant , String operateStatus);
    List<SalesPlan> findByCustomerNameLikeAndAssistantAndOperateArrange(String customerName , String assistant , String operateStatus);

    List<SalesPlan> findByPrincipalAndPlanStatusAndSaleArrange(String account , String status , String saleArrange);
    List<SalesPlan> findByPlanStatusAndSaleArrange(String status , String saleArrange);
    List<SalesPlan> findByCustomerNameLikeAndPrincipalAndPlanStatusAndSaleArrange(String customerName , String account , String status , String saleArrange);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndSaleArrange(String customerName , String status , String saleArrange);
    List<SalesPlan> findByPlanStatus(String status);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatus(String customerName , String status);
    List<SalesPlan> findByPlanStatusIn(List<String> status);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusIn(String customerName , List<String> status);
    List<SalesPlan> findByPlanStatusAndAssistant(String status , String assistant);
    List<SalesPlan> findByPlanStatusAndOperator(String status , String operator);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndAssistant(String customerName , String status , String assistant);
    List<SalesPlan> findByPlanStatusAndOperatorAndLastTimeAfterAndServiceWriteOrderByUpdateDate(String status , String operator , LocalDate date , String serviceWrite);
    List<SalesPlan> findByPlanStatusAndOperatorAndLastTimeAfterAndReportWriteOrderByUpdateDate(String status , String operator , LocalDate date , String reportWrite);
    List<SalesPlan> findByCustomerCode(String customer);
    List<SalesPlan> findByPlanStatusAndOperatorAndOperateArrange(String planStatus , String operator , String operatorArrange);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndOperatorAndOperateArrange(String customerName , String planStatus , String operator , String operatorArrange);
    List<SalesPlan> findByPlanStatusInAndOperatorAndOperateArrange(List<String> status , String account , String operateStatus);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusInAndOperatorAndOperateArrange(String customerName , List<String> status , String account , String operateStatus);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndOperator(String customerName , String planStatus , String opertor);
    List<SalesPlan> findByPrincipalAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(String principal , String status , LocalDate time , String serviceWrite);
    List<SalesPlan> findByPlanStatusAndAssistantAndLastTimeAfterAndServiceWriteOrderByUpdateDate(String planStatus , String assistant , LocalDate time , String serviceWrite);
    List<SalesPlan> findByPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(String planStatus , LocalDate time , String serviceWrite);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndOperatorAndLastTimeAfterAndServiceWriteOrderByUpdateDate(String customerName , String  status , String operator , LocalDate time , String serviceWrite);
    List<SalesPlan> findByCustomerNameLikeAndPrincipalAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(String customerName , String principal , String planStatus , LocalDate time , String serviceWrite);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndAssistantAndLastTimeAfterAndServiceWriteOrderByUpdateDate(String customerName , String planStatus , String assistant , LocalDate time , String serviceWrite);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(String customerName , String planStatus , LocalDate time , String serviceWrite);
    List<SalesPlan> findByPrincipalAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(String principal , String planStatus , LocalDate time , String reportWrite);
    List<SalesPlan> findByPlanStatusAndAssistantAndLastTimeAfterAndReportWriteOrderByUpdateDate(String planStatus , String assistant , LocalDate time , String reportWrite);
    List<SalesPlan> findByPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(String planStatus , LocalDate time , String reportWrite);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndOperatorAndLastTimeAfterAndReportWriteOrderByUpdateDate(String customerName , String planStatus , String operator , LocalDate time , String reportWrite);
    List<SalesPlan> findByCustomerNameLikeAndPrincipalAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(String customerName , String principal , String planStatus , LocalDate time , String reportWrite);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndAssistantAndLastTimeAfterAndReportWriteOrderByUpdateDate(String customerName , String planStatus , String assistant , LocalDate time , String reportWrite);
    List<SalesPlan> findByCustomerNameLikeAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(String customerName , String planStatus , LocalDate time , String reportWrite);

    void deleteByCustomerCode(String customerCode);
}
