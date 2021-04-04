package vip.efactory.common.notify.service.impl.email;

import vip.efactory.common.notify.service.IEmailNotifyService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件通知服务，以后实现，甚至是基于队列的处理方式
 *
 * @author dbdu
 * @since 2021/04/04 下午13:04
 */
//@Service(NotifyTypeConstant.EMAIL)
@AllArgsConstructor
@Slf4j
public class EmailNotifyServiceImpl implements IEmailNotifyService {

    /**
     * 邮件发送服务
     */
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;


    @Override
    public void sendSimpleTextMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        //message.setFrom(mailProperties.getUsername());//发件人
        message.setTo(to);//收件人
        message.setSubject(subject);//主题
        message.setText(content);//正文
        send(message);  //发送邮件
    }

    @Override
    @SneakyThrows
    public void sendSimpleTextMailWithAttachment(String to, String subject, String content, Map<String, File> attachments) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);//构造消息helper，第二个参数表明这个消息是multipart类型的
        //helper.setFrom(mailProperties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);
        // 加载附件
        if (!CollectionUtils.isEmpty(attachments)) {
            attachments.forEach((fileName, file) -> {
                try {
                    helper.addAttachment(fileName, file);//添加附加，第一个参数为添加到Email中附件的名称，第二个人参数是资源
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        send(message);
    }

    @Override
    @SneakyThrows
    public void sendRichTextEmail(String to, String subject, String content) {
        Map<String, Resource> inlineResources = new HashMap<>();
        sendRichTextEmail(to, subject, content, inlineResources);
    }

    @Override
    @SneakyThrows
    public void sendRichTextEmail(String to, String subject, String content, Map<String, Resource> inlineResources) {
        Map<String, File> attachments = new HashMap<>();
        sendRichTextEmailWithAttachment(to, subject, content, inlineResources, attachments);
    }

    @Override
    @SneakyThrows
    public void sendRichTextEmailWithAttachment(String to, String subject, String content, Map<String, File> attachments) {
        Map<String, Resource> inlineResources = new HashMap<>();
        sendRichTextEmailWithAttachment(to, subject, content, inlineResources, attachments);
    }

    @Override
    @SneakyThrows
    public void sendRichTextEmailWithAttachment(String to, String subject, String content, Map<String, Resource> inlineResources, Map<String, File> attachments) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //helper.setFrom(mailProperties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        //第二个参数表明这是一个HTML
        helper.setText(content, true);
        if (CollectionUtils.isEmpty(inlineResources)) {
            inlineResources.forEach((contentId, resource) -> {
                try {
                    helper.addInline(contentId, resource);  //添加内联资源（图片），第一个参数表明内联图片的标识符，第二个参数是图片的资源引用
                } catch (MessagingException e) {
                    log.warn(e.getMessage());
                }
            });
        }
        if (!CollectionUtils.isEmpty(attachments)) {
            attachments.forEach((fileName, file) -> {
                try {
                    helper.addAttachment(fileName, file);//添加附加，第一个参数为添加到Email中附件的名称，第二个人参数是资源
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        mailSender.send(message);
    }

    //******************[以下的方法为JavaMailSender提供的方法，如果上面方法不能满足需求可以考虑使用JavaMailSender自带的方法]************************//
    @Override
    public MimeMessage createMimeMessage() {
        return mailSender.createMimeMessage();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return mailSender.createMimeMessage(contentStream);
    }


    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        mailSender.send(simpleMessage);
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        mailSender.send(simpleMessages);
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        mailSender.send(mimeMessage);
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        mailSender.send(mimeMessages);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        mailSender.send(mimeMessagePreparator);
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        mailSender.send(mimeMessagePreparators);
    }
}
