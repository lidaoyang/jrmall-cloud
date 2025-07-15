package com.jrmall.cloud.common.sms.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.jrmall.cloud.common.sms.property.AliyunSmsProperties;
import com.jrmall.cloud.common.sms.service.SmsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信业务类
 * 
 * @author haoxr
 * @since  3.1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliyunSmsService implements SmsService {

    private final AliyunSmsProperties aliyunSmsProperties;

    private Client client;

    /**
     * 使用AK&SK初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    @PostConstruct
    public void initClient() throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(aliyunSmsProperties.getAccessKeyId())
                // 您的 AccessKey Secret
                .setAccessKeySecret(aliyunSmsProperties.getAccessKeySecret())
                .setRegionId(aliyunSmsProperties.getRegionId())
                .setEndpoint(aliyunSmsProperties.getDomain());
        this.client = new Client(config);
    }

    /**
     * 发送短信验证码
     *
     * @param mobile   手机号 13388886666
     * @param templateCode  短信模板 SMS_194640010
     * @param templateParam 模板参数 "[{"code":"123456"}]"
     *
     * @return  boolean 是否发送成功
     */
    @Override
    public boolean sendSms(String mobile,String templateCode,String templateParam) {
        if(templateParam == null){
            log.error("短信模板参数不能为空");
            return false;
        }
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(mobile)
                .setSignName(aliyunSmsProperties.getSignName())
                .setTemplateCode(templateCode)
                .setTemplateParam(templateParam);
        try {
            RuntimeOptions runtime = new RuntimeOptions();
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            if ("OK".equals(sendSmsResponse.getBody().getCode())) {
                log.info("短信发送成功,phone:{},tempCode:{}", sendSmsRequest.getPhoneNumbers(), sendSmsRequest.getTemplateCode());
                return true;
            } else {
                log.error("发送短信失败, phone:{},tempCode:{},params:{},sendSmsResponse:{}", sendSmsRequest.getPhoneNumbers(),
                        sendSmsRequest.getTemplateCode(), sendSmsRequest.getTemplateParam(), sendSmsResponse.getBody());
                return false;
            }
        } catch (Exception e) {
            log.error("发送短信异常, phone:{},tempCode:{},params:{}", sendSmsRequest.getPhoneNumbers(), sendSmsRequest.getTemplateCode(),
                    sendSmsRequest.getTemplateParam());
            log.error("发送短信异常", e);
            return false;
        }
    }


}
