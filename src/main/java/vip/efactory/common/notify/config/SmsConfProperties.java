package vip.efactory.common.notify.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ali短信的配置信息，注意请在此处配置自己的信息
 *
 * @author dbdu
 * @since 2021/04/04 下午13:04
 */
@Setter
@Getter
@Component
@ConfigurationProperties("notify.sms")
public class SmsConfProperties {
    /**
     * 是否启用短信通知的功能，true启用，false不启用
     */
    private Boolean enable = true;

    //*******************[以下属性为阿里短信配置属性]**********************//

    /**
     * 模板签名：例如：EFADMIN系统
     */
    private String signName = "EFADMIN 系统";

    /**
     * 例如：dysmsapi.aliyuncs.com
     */
    private String domain = "dysmsapi.aliyuncs.com";

    /**
     * 例如：2017-05-25，不是由我们自己决定的，不可以随便改
     */
    private String version = "2017-05-25";

    /**
     * 例如:SendSms, 除非必要否则不要修改
     */
    private String action = "SendSms";

    /**
     * 例如：cn-hangzhou
     */
    private String regionId = "cn-hangzhou";

    /**
     * 短信接入阿里的访问key
     * 例如：LTAI4GKpS....ChL3XH1
     */
    private String accessKeyId = "LTAI4GKpS....ChL3XH1";

    /**
     * 短信接入阿里的访问密码
     * 例如：cQLt7QuJ1i...5UZY9dKE
     */
    private String secret = "cQLt7QuJ1i...5UZY9dKE";

}
