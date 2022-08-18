package com.itheima.utils;

import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-07-21 14:53
 */
public class MsgUtils {

/*    @Value("${access.ACCESS_KEY_ID}")
    private String ACCESS_KEY_ID;*/

    public static String ACCESS_KEY_ID = "P7J2cDLbdYs92d1vArEjGESZu1GoVCjSiwjQoMP4z1oM7aGM6";
    public static final String VALIDATE_CODE = "pub_verif_login";//发送短信验证码
//    public static final String ORDER_NOTICE = "SMS_159771588";//体检预约成功通知

    public static void sendCode(String code, String phone, String msgType) {

        // 初始化
        Uni.init(ACCESS_KEY_ID); // 若使用简易验签模式仅传入第一个参数即可

        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
//        String code = String.valueOf(Math.random()).substring(2, 8);
        templateData.put("code", code);// 这里设置验证码

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo(phone)
                .setSignature("pzistart")//签名无效，要设置好
                .setTemplateId(msgType)
                .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            System.out.println(res);
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
//        return code;
    }


}
