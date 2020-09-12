package me.god.mail.mailer;

import me.god.mail.util.SpringContextUtils;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * 发送邮件的Fluent API。
 *
 * <p>调用方式查看：MailerTest。
 *
 * <p>简单文本邮件示例：
 * <pre><code>
 *  Mailer.mail()
 *      .to("xxx@qq.com")
 *      .subject("测试邮件")
 *      .content("这是一封测试邮件！")
 *      .send();
 * </code></pre>
 * 注意：简单文本邮件不支持附件和内嵌资源，所以调用{@link #attachment(String)}或{@link #inline(Inline)}
 * 等重载方法都是无效果的。
 *
 * <p>发件人默认是application.yml的spring.mail.username属性，注入可查看{@link MailerFactory#from}。
 *
 * <p>对于附件{@link #attachment(String)}和内嵌资源{@link #inline(Inline)}的文件路径的配置，
 * 采用的是Spring的{@link org.springframework.context.ApplicationContext}。
 * 因此支持前缀表达式，如:
 * <pre>
 * "file:D:\\test\\test.txt" （注意：请不要直接使用"D:\\test\\test.txt"）
 * "classpath:com/test/test.txt"
 * "http://localhost:8082/mail/test.pdf"
 * </pre>
 *
 */
public interface Mailer {
    /**
     * 静态构建器。默认是简单文本邮件。
     * @return
     */
    static Mailer mail() {
        return mail(MailType.SIMPLE);
    }

    /**
     * 静态构建器。根据{@link MailType}构造Mailer。
     * @return
     */
    static Mailer mail(MailType mailType) {
        return SpringContextUtils.getBean(MailerFactory.class).getInstance(mailType);
    }

    /**
     * 终端操作。将所有属性设置好后，最终调用此方法即可发送邮件。
     */
    void send();

    //=========== 以下是Mailer的属性设置方法 ===============
    // 设置：发件人
    Mailer from(String from);
    // 设置：回复人
    Mailer replyTo(String replyTo);
    // 设置：收件人
    Mailer to(String to);
    Mailer to(String... to);
    Mailer to(List<String> to);
    // 设置：抄送人
    Mailer cc(String cc);
    Mailer cc(String... cc);
    Mailer cc(List<String> cc);
    // 设置：密送人
    Mailer bcc(String bcc);
    Mailer bcc(String... bcc);
    Mailer bcc(List<String> bcc);
    // 设置：邮件标题
    Mailer subject(String subject);
    // 设置：邮件内容
    Mailer content(String content);
    Mailer content(Supplier<String> supplier);
    // 设置：发送日期
    Mailer sentDate(Date sentDate);
    // 设置：附件
    Mailer attachment(Attachment attachment);
    Mailer attachment(Attachment... attachment);
    Mailer attachment(String filePath);
    Mailer attachment(String fileName, String filePath);
    Mailer attachment(String[] filePath);
    // 设置：内嵌资源
    Mailer inline(String contentId, String filePath);
    Mailer inline(Inline inline);
    Mailer inline(Inline... inline);
}
