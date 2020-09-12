package me.god.mail.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Mailer工厂类。
 * 主要用于注入Spring Bean和配置文件属性。
 */
@Component
public class MailerFactory {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @PostConstruct
    public void initMethod() {
        AbstractMailer.javaMailSender = javaMailSender;
    }

    public Mailer getInstance(MailType mailType) {
        switch (mailType) {
            case SIMPLE:
                return new SimpleMailer(from);
            case MIME:
                return new MimeMailer(from);
            default:
                throw new IllegalArgumentException("无法根据{MailType=" + mailType + "}找到Mailer实现类");
        }
    }
}
