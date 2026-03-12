package com.medical.common.tools;
import com.tencentcloudapi.common.AbstractModel;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.tmt.v20180321.TmtClient;
import com.tencentcloudapi.tmt.v20180321.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 腾讯云翻译
 */
@Component
public class TmtClientTools  {

    @Value("${tencent.secretId}")
    private String secretId;
    @Value("${tencent.secretKey}")
    private String secretKey;

    /**
     * @param sourceCode zh中文 ja日语
     * @param text
     * @param targetCode zh中文 ja日语
     * @return
     */
    public String translationText(String sourceCode, String text, String targetCode){
        try{
            // 密钥信息从环境变量读取，需要提前在环境变量中设置 TENCENTCLOUD_SECRET_ID 和 TENCENTCLOUD_SECRET_KEY
            // 使用环境变量方式可以避免密钥硬编码在代码中，提高安全性
            // 生产环境建议使用更安全的密钥管理方案，如密钥管理系统(KMS)、容器密钥注入等
            // 请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            Credential cred = new Credential(secretId, secretKey);
            // 使用临时密钥示例
            // Credential cred = new Credential("SecretId", "SecretKey", "Token");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tmt.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            TmtClient client = new TmtClient(cred, "ap-tokyo", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            TextTranslateRequest req = new TextTranslateRequest();
            req.setSourceText(text);
            req.setSource(sourceCode);
            req.setTarget(targetCode);
            req.setProjectId(0l);

            // 返回的resp是一个TextTranslateResponse的实例，与请求对象对应
            TextTranslateResponse resp = client.TextTranslate(req);
            // 输出json格式的字符串回包
            System.out.println(AbstractModel.toJsonString(resp));

            return resp.getTargetText();
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return "";
    }





}



