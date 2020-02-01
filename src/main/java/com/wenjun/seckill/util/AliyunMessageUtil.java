package com.wenjun.seckill.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 阿里云短信验证
 * @Author: wenjun
 * @Date: 2019/12/25 18:03
 */
public class AliyunMessageUtil {
    private final static Logger logger = LoggerFactory.getLogger(AliyunMessageUtil.class);

    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private static final String accessKeyId = "LTAI4FrCsHhQyPfv9m6kKFHx";
    private static final String accessKeySecret = "ilq3zyCbC4tD1IM3vGiZQqSs8Zd6R8";

    public static SendSmsResponse sendSms(Map<String, String> paramMap) throws com.aliyuncs.exceptions.ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(paramMap.get("phoneNumber"));
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(paramMap.get("msgSign"));
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(paramMap.get("templateCode"));
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(paramMap.get("jsonContent"));

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }
    //通过阿里云发送短信验证码
    public static void sendMsg(String uphone, String randomNum) throws com.aliyuncs.exceptions.ClientException {
        String jsonContent = "{\"code\":\"" + randomNum + "\"}";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", uphone);
        paramMap.put("msgSign", "秒杀商城");
        paramMap.put("templateCode", "SMS_182674081");
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = sendSms(paramMap);
    }

    //生成6位随机数
    public static String createRandomNum() {
        Random random = new Random();
        int randomInt = random.nextInt(900000);
        randomInt += 100000;
        return String.valueOf(randomInt);
    }
}
