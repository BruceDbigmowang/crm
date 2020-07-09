package com.knowhy.crm.controller;

import com.knowhy.crm.util.PhoneCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class VcodeController {

    @RequestMapping("/sendVcode")
    public String sendMessage(@RequestParam("contactWay")String mobile , HttpSession session){
        return PhoneCode.getPhonemsg(mobile , session);
    }
}
