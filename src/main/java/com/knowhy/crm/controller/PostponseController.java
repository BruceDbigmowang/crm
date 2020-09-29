package com.knowhy.crm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostponseController {

    @RequestMapping("/createDelay")
    public String makeDelay(String salePlanID , String processCode , String processName , int days, String reason){

        return "OK";
    }
}
