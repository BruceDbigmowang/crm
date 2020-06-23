package com.knowhy.crm.controller;

import com.knowhy.crm.enums.ImageProperty;
import com.knowhy.crm.util.VerifyImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.Map;
import java.util.Random;

@Controller
public class ForeRestController {

    //滑块图片创建
    @RequestMapping("/createImgValidate")
    public String createImgValidate(){

        try {
            Integer templateNum = new Random().nextInt(4) + 1;
            Integer targetNum = new Random().nextInt(20) + 1;
            File templateFile = ResourceUtils.getFile("classpath:static/images/validate/template/"+templateNum+".png");
            File targetFile = ResourceUtils.getFile("classpath:static/images/validate/target/"+targetNum+".jpg");
            Map<String, String> pictureMap =  VerifyImageUtil.pictureTemplatesCut(templateFile, targetFile,
                    ImageProperty.IMAGE_TYPE_PNG.getValue(),ImageProperty.IMAGE_TYPE_JPG.getValue());
            // 将生成的偏移位置信息设置到redis中
            String key = ImageProperty.WEB_VALID_IMAGE_PREFIX.getValue();
//            boolean verified = redisUtil.exists(key);
//            if(verified){
//                redisUtil.del(key);
//            }
//            redisUtil.set(key,(VerifyImageUtil.getX()+67)+"",SmsUtil.VALID_IMAGE_TIMEOUT);
            return pictureMap.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    //滑块图片验证

    //登录

}
