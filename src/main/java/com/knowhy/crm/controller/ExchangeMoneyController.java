package com.knowhy.crm.controller;

import com.knowhy.crm.dao.ExchangeMoneyDAO;
import com.knowhy.crm.pojo.ExchangeMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExchangeMoneyController {
    @Autowired
    ExchangeMoneyDAO exchangeMoneyDAO;

    @RequestMapping("/getAllExchange")
    public List<ExchangeMoney> findAllExchange(){
        return exchangeMoneyDAO.findAll();
    }
}
