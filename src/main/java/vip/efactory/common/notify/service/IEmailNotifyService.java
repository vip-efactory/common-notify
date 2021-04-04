package vip.efactory.common.notify.service;

import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * @author dbdu
 * @since 2021/04/04 下午13:04
 */
public interface IEmailNotifyService extends INotifyService {


    /**
     * 发送简单的文本邮件
     *
     * @param to      邮件接收者
     * @param subject 邮件主题
     * @param content 邮件内容，仅是文本的内容
     */
    @Async
    void sendSimpleTextMail(String to, String subject, String content);

    /**
     * 发送简单的文本邮件，但是带有附件
     *
     * @param to          邮件接收者
     * @param subject     邮件主题
     * @param content     邮件内容，仅是文本的内容
     * @param attachments 附件map,Key为附件名,value为附件对象
     */
    void sendSimpleTextMailWithAttachment(String to, String subject, String content, Map<String, File> attachments);

    /**
     * 发送富文本邮件
     *
     * @param to      邮件接收者
     * @param subject 邮件主题
     * @param content 邮件内容，内容是富文本，html
     */
    void sendRichTextEmail(String to, String subject, String content);

    /**
     * 发送富文本邮件
     *
     * @param to              邮件接收者
     * @param subject         邮件主题
     * @param content         邮件内容，内容是富文本，html
     * @param inlineResources 富文本里面需要内联的资源，例如：图片显示，注意不是附件! Key是富文本中引用的id，value是资源
     */
    void sendRichTextEmail(String to, String subject, String content, Map<String, Resource> inlineResources);

    /**
     * 发送富文本邮件
     *
     * @param to          邮件接收者
     * @param subject     邮件主题
     * @param content     邮件内容，内容是富文本，html
     * @param attachments 附件map,Key为附件名,value为附件对象
     */
    void sendRichTextEmailWithAttachment(String to, String subject, String content, Map<String, File> attachments);

    /**
     * 发送富文本邮件
     *
     * @param to              邮件接收者
     * @param subject         邮件主题
     * @param content         邮件内容，内容是富文本，html
     * @param inlineResources 富文本里面需要内联的资源，例如：图片显示，注意不是附件! Key是富文本中引用的id，value是资源
     * @param attachments     附件map,Key为附件名,value为附件对象
     */
    void sendRichTextEmailWithAttachment(String to, String subject, String content, Map<String, Resource> inlineResources, Map<String, File> attachments);

    //******************[以下的方法为JavaMailSender提供的方法，如果上面方法不能满足需求可以考虑使用JavaMailSender自带的方法]************************//

    /**
     * 为此发件人的基础JavaMail会话创建一个新的JavaMail MimeMessage。需要调用以创建可由客户端准备并传递给send（MimeMessage）的MimeMessage实例。
     *
     * @return the new MimeMessage instance
     * @see #send(MimeMessage)
     * @see #send(MimeMessage[])
     */
    MimeMessage createMimeMessage();

    /**
     * 使用给定的输入流作为消息源，为此发送者的基础JavaMail Session创建一个新的JavaMail MimeMessage。
     *
     * @param contentStream the raw MIME input stream for the message
     * @return the new MimeMessage instance
     * @throws org.springframework.mail.MailParseException in case of message creation failure
     */
    MimeMessage createMimeMessage(InputStream contentStream) throws MailException;

    /**
     * 发送给定的简单邮件。
     *
     * @param simpleMessage the message to send
     * @throws MailParseException          in case of failure when parsing the message
     * @throws MailAuthenticationException in case of authentication failure
     * @throws MailSendException           in case of failure when sending the message
     */
    void send(SimpleMailMessage simpleMessage) throws MailException;

    /**
     * 批量发送给定的简单邮件消息数组。
     *
     * @param simpleMessages the messages to send
     * @throws MailParseException          in case of failure when parsing a message
     * @throws MailAuthenticationException in case of authentication failure
     * @throws MailSendException           in case of failure when sending a message
     */
    void send(SimpleMailMessage... simpleMessages) throws MailException;


    /**
     * 发送给定的JavaMail MIME消息。
     * The message needs to have been created with {@link #createMimeMessage()}.
     *
     * @param mimeMessage message to send
     * @throws org.springframework.mail.MailAuthenticationException in case of authentication failure
     * @throws org.springframework.mail.MailSendException           in case of failure when sending the message
     * @see #createMimeMessage
     */
    void send(MimeMessage mimeMessage) throws MailException;

    /**
     * 批量发送给定的JavaMail MIME消息数组。
     * The messages need to have been created with {@link #createMimeMessage()}.
     *
     * @param mimeMessages messages to send
     * @throws org.springframework.mail.MailAuthenticationException in case of authentication failure
     * @throws org.springframework.mail.MailSendException           in case of failure when sending a message
     * @see #createMimeMessage
     */
    void send(MimeMessage... mimeMessages) throws MailException;

    /**
     * 发送由给定的MimeMessagePreparator准备的JavaMail MIME消息。 <p>准备MimeMessage实例的替代方法，
     * 而不是{@link createMimeMessage()}和{@link send（MimeMessage）}调用。负责适当的异常转换。
     *
     * @param mimeMessagePreparator the preparator to use
     * @throws org.springframework.mail.MailPreparationException    in case of failure when preparing the message
     * @throws org.springframework.mail.MailParseException          in case of failure when parsing the message
     * @throws org.springframework.mail.MailAuthenticationException in case of authentication failure
     * @throws org.springframework.mail.MailSendException           in case of failure when sending the message
     */
    void send(MimeMessagePreparator mimeMessagePreparator) throws MailException;

    /**
     * Send the JavaMail MIME messages prepared by the given MimeMessagePreparators.
     * <p>Alternative way to prepare MimeMessage instances, instead of
     * {@link #createMimeMessage()} and {@link #send(MimeMessage[])} calls.
     * Takes care of proper exception conversion.
     *
     * @param mimeMessagePreparators the preparator to use
     * @throws org.springframework.mail.MailPreparationException    in case of failure when preparing a message
     * @throws org.springframework.mail.MailParseException          in case of failure when parsing a message
     * @throws org.springframework.mail.MailAuthenticationException in case of authentication failure
     * @throws org.springframework.mail.MailSendException           in case of failure when sending a message
     */
    void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException;

}
