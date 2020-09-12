package me.god.mail.mailer;


import org.springframework.mail.javamail.JavaMailSender;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * Mailer抽象类。
 * 提取子类的公共方法并提供一些默认实现。
 *
 */
public abstract class AbstractMailer implements Mailer {
    protected static JavaMailSender javaMailSender;

    private String from;
    /**
     * 回复人。发送回复邮件时使用
     */
    private String replyTo;

    private String[] to;

    private String[] cc;
    /**
     * 密送
     */
    private String[] bcc;

    private String subject;

    private String content;
    /**
     * 发送日期
     */
    private Date sentDate;

    AbstractMailer(String from) {
        this.from = from;
    }

    /**
     * 发送邮件方法
     */
    @Override
    public abstract void send();

    public String getFrom() {
        return from;
    }

    @Override
    public Mailer from(String from) {
        this.from = from;
        return this;
    }

    public String[] getTo() {
        return to;
    }

    @Override
    public Mailer to(String to) {
        this.to = new String[]{to};
        return this;
    }

    @Override
    public Mailer to(String... to) {
        this.to = to;
        return this;
    }

    @Override
    public Mailer to(List<String> to) {
        if (to == null || to.isEmpty()) {
            return this;
        }
        this.to = to.toArray(new String[to.size()]);
        return this;
    }

    public String[] getCc() {
        return cc;
    }

    @Override
    public Mailer cc(String cc) {
        this.cc = new String[]{cc};
        return this;
    }

    @Override
    public Mailer cc(String... cc) {
        this.cc  = cc;
        return this;
    }

    @Override
    public Mailer cc(List<String> cc) {
        if (cc == null || cc.isEmpty()) {
            return this;
        }
        this.cc = cc.toArray(new String[cc.size()]);
        return this;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public Mailer subject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    @Override
    public Mailer content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public Mailer content(Supplier<String> supplier) {
        this.content = supplier.get();
        return this;
    }

    public String getReplyTo() {
        return replyTo;
    }

    @Override
    public Mailer replyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public String[] getBcc() {
        return bcc;
    }

    @Override
    public Mailer bcc(String bcc) {
        this.bcc = new String[]{bcc};
        return this;
    }

    @Override
    public Mailer bcc(String... bcc) {
        this.bcc = bcc;
        return this;
    }

    @Override
    public Mailer bcc(List<String> bcc) {
        if (bcc == null || bcc.isEmpty()) {
            return this;
        }
        this.bcc = bcc.toArray(new String[bcc.size()]);
        return this;
    }

    public Date getSentDate() {
        return sentDate;
    }

    @Override
    public Mailer sentDate(Date sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    @Override
    public Mailer attachment(Attachment attachment) {
        return this;
    }

    @Override
    public Mailer attachment(Attachment... attachment) {
        return this;
    }

    @Override
    public Mailer attachment(String filePath) {
        return this;
    }

    @Override
    public Mailer attachment(String fileName, String filePath) {
        return this;
    }

    @Override
    public Mailer attachment(String[] filePath) {
        return this;
    }

    @Override
    public Mailer inline(String contentId, String filePath) {
        return this;
    }

    @Override
    public Mailer inline(Inline inline) {
        return this;
    }

    @Override
    public Mailer inline(Inline... inline) {
        return this;
    }

}
