package vip.efactory.common.notify.config;

import vip.efactory.common.notify.service.IEmailNotifyService;
import vip.efactory.common.notify.service.ISmsNotifyService;
import vip.efactory.common.notify.service.impl.email.EmailNotifyServiceImpl;
import vip.efactory.common.notify.service.impl.sms.SmsNotifyServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 通知模块的自动化配置
 * @author dusuanyun
 */
@Configuration
@EnableConfigurationProperties(SmsConfProperties.class)
public class NotifyAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "notify.sms", name = "enable", havingValue = "true")
    public ISmsNotifyService iSmsNotifyService(SmsConfProperties smsConfProperties) {
        return new SmsNotifyServiceImpl(smsConfProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "notify.email", name = "enable", havingValue = "true")
    public IEmailNotifyService iEmailNotifyService(JavaMailSender mailSender, MailProperties mailProperties) {
        return new EmailNotifyServiceImpl(mailSender, mailProperties);
    }

}
