package com.knowhy.crm;

import org.apache.commons.lang.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrmApplicationTests {

    @Test
    void contextLoads() {
        String[] arr = new String[]{"111111" , "222222" , "333333"};
        String str = ArrayUtils.toString(arr);
        System.out.println(str);
    }

}
