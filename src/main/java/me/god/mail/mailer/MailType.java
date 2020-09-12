package me.god.mail.mailer;

/**
 * 邮件类型
 */
public enum MailType {
    /**
     * 简单文本邮件类型
     */
    SIMPLE,
    /**
     * 复杂邮件类型。
     * 邮件内容可以为html，可以添加附件，内嵌图片等。
     */
    MIME;
}
