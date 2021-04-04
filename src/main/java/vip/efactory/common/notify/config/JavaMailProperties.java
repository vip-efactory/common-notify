package vip.efactory.common.notify.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件的配置类，集成SpringJavaMail属性类MailProperties
 *
 * @author dbdu
 * @since 2021/04/04 下午13:04
 */
@Setter
@Getter
@Component
@ConfigurationProperties("notify.email")
public class JavaMailProperties {

    /**
     * 是否启用短信通知的功能，true启用，false不启用
     */
    private Boolean enable = true;
}
