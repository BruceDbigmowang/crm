package com.knowhy.crm;

import org.apache.commons.lang.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.knowhy.crm.util.SendEmail;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
class CrmApplicationTests {

    @Test
    void testSendEmail() {
        String result = new SendEmail().SendEmailMessage("2483498610@qq.com" , "测试邮件");

    }

}
