package com.konwhy.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForePageController {

//    @Autowired
//    PictureValidateService pictureValidateService;

    @RequestMapping("/")
    public String first(){
        return "redirect:loginPage";
    }

    @RequestMapping("/loginPage")
    public String login(){
        System.out.println("model值");
        //m.addAttribute("pictureUrl",pictureValidateService.generateValidatePicture(request,response));
        return "fore/login";
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(){
        return "fore/resetPassword";
    }

    @RequestMapping("/registerPage")
    public String register(){
        return "fore/register";
    }

    @RequestMapping("/homePage")
    public String home(){
        return "fore/home";
    }
}
