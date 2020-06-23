package com.knowhy.crm.controller;

import com.knowhy.crm.service.PictureValidateService;
import com.knowhy.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AssistController {

    @Autowired
    PictureValidateService pictureValidateService;
    /**
     * login页面-验证码刷新
     * @return
     */
    @RequestMapping("/createPictureValidate")
    @ResponseBody
    public Object createPictureValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object obj = pictureValidateService.generateValidatePicture(request,response);
        return Result.success(obj);
    }

    @RequestMapping("/forwardResetPassword")
    public Object forwardResetPassword(HttpServletRequest request){

        return null;
    }
}
