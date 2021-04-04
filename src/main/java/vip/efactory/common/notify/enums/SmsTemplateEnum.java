package vip.efactory.common.notify.enums;

import lombok.Getter;

/**
 * 短信模板枚举
 *
 * @author dbdu
 * @since 2021/04/04 下午13:04
 */
@Getter
public enum SmsTemplateEnum {
    LOGIN("SMS_170300000", "登录验证码"),
    ;

    SmsTemplateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;
}
