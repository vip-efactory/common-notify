package vip.efactory.common.notify.service.impl.sms;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vip.efactory.common.notify.config.SmsConfProperties;
import vip.efactory.common.notify.service.ISmsNotifyService;

import java.util.List;
import java.util.Map;

/**
 * 短信Sms通知服务
 *
 * @author dbdu
 * @since 2021/04/04 下午13:04
 */
//@Service(NotifyTypeConstant.SMS)
@AllArgsConstructor
@Slf4j
public class SmsNotifyServiceImpl implements ISmsNotifyService {

    private final SmsConfProperties smsConfProperties;

    @Override
    public boolean sendNotify(String smsCode, Map<String, Object> params, String mobile) {
        return sendNotify(smsCode, params, mobile, smsConfProperties.getSignName());
    }

    @Override
    public boolean sendNotifyBatch(String smsCode, Map<String, Object> params, List<String> mobileList) {
        return sendNotifyBatch(smsCode, params, mobileList, smsConfProperties.getSignName());
    }

    @Override
    public boolean sendNotify(@NonNull String smsCode, @NonNull Map<String, Object> params, @NonNull String mobile, @NonNull String signName) {
        // 调用第三方短信平台，发送短信
        DefaultProfile profile = DefaultProfile.getProfile(smsConfProperties.getRegionId(), smsConfProperties.getAccessKeyId(), smsConfProperties.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(smsConfProperties.getDomain());
        request.setVersion(smsConfProperties.getVersion());
        request.setAction(smsConfProperties.getAction());
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("RegionId", smsConfProperties.getRegionId());
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("TemplateCode", smsCode); // 短信模板码
        // 设定模板占位符参数
        request.putQueryParameter("TemplateParam", JSON.toJSONString(params));
        try {
            CommonResponse response = client.getCommonResponse(request);
            if (response.getHttpStatus() == 200 ) {
                SMSBackData backData = JSONObject.parseObject(response.getData(), SMSBackData.class);
                if (!"OK".equalsIgnoreCase(backData.getCode())) {
                    log.warn(mobile + "短信发送失败：" + backData.getMessage());
                    return false;
                }
            } else {
                log.warn(response.getHttpStatus() + ":" + response.getData());
                return false;
            }
        } catch (ServerException e) {
            log.warn(e.getErrMsg());
            return false;
        } catch (ClientException e) {
            log.warn(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean sendNotifyBatch(String smsCode, Map<String, Object> params, List<String> mobileList, String signName) {
        if (CollectionUtils.isEmpty(mobileList)) {
            log.warn("短信接收者不允许为空!");
            return false;
        }
        String mobiles = StringUtils.collectionToCommaDelimitedString(mobileList);
        return sendNotify(smsCode, params, mobiles, signName);
    }

}

/**
 * 接受短信发送返回的数据，例如：
 * {"RequestId":"F2D4CF59-2F9A-4FAC-A520-46632AB104DE","Message":"触发小时级流控Permits:5","Code":"isv.BUSINESS_LIMIT_CONTROL"}
 * {"RequestId":"164D37B1-2220-4C53-9866-8BEADDF5F5E4","Message":"OK","BizId":"895525009386431987^0","Code":"OK"}
 */
@Getter
@Setter
class SMSBackData {
    /**
     * 请求id，
     */
    private String requestId;
    /**
     * 消息
     */
    private String message;
    /**
     * code码
     */
    private String code;
}
