package com.knowhy.crm.util;

public class StaticPerms {
    final static String defaultConnectTimeout  = "sun.net.client.defaultConnectTimeout";
    final static String defaultReadTimeout = "sun.net.client.defaultReadTimeout";
    final static String Timeout = "10000";
    // 初始化ascClient需要的几个参数
    final static String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
    final static String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
    // 替换成你的AK (产品密)
    final static String accessKeyId = "LTAI4GJzdPWj7nuiLczsLYf2";// 你的accessKeyId,填你自己的 上文配置所得  自行配置
    final static String accessKeySecret = "YFhDzHKqWvui9zMDsk1ssyKImHHnPC";// 你的accessKeySecret,填你自己的 上文配置所得 自行配置
    // 必填:短信签名-可在短信控制台中找到

    /**
     * ---------------------------------
     * 此处签名不可用，后期申请新签名时更换
     * ---------------------------------
     */
    final static String SignName = "诺而为";// 阿里云配置你自己的短信签名填入


    // 必填:短信模板-可在短信控制台中找到
    final static String TemplateCode = "SMS_205138809"; // 阿里云配置你自己的短信模板填入
}
