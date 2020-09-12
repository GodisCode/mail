package me.god.mail.mailer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/**
 * 发送简单文本邮件。
 */
public class SimpleMailer extends AbstractMailer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailer.class);

    public SimpleMailer(String from) {
        super(from);
    }

    @Override
    public void send() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(getFrom());
        message.setReplyTo(getReplyTo());
        message.setTo(getTo());
        message.setCc(getCc());
        message.setBcc(getBcc());
        message.setSubject(getSubject());
        message.setText(getContent());
        message.setSentDate(getSentDate());

        try {
            javaMailSender.send(message);
            LOGGER.info("简单邮件已经发送。");
        } catch (Exception e) {
            LOGGER.error("发送简单邮件时发生异常！", e);
        }
    }
}
