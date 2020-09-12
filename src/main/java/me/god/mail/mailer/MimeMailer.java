package me.god.mail.mailer;

import me.god.mail.util.FileUtils;
import me.god.mail.util.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送复杂邮件。
 * 邮件内容可以为html，可包括附件和内嵌资源。
 */
public class MimeMailer extends AbstractMailer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MimeMailer.class);

    /**
     * 资源加载器。
     * Spring ApplicationContext本身就是个ResourceLoader，直接使用即可。
     *
     * 如果想直接支持“绝对路径”，如"D:\\test\\test.txt",可以用{@link FileSystemResourceLoader}，
     * 但这样就无法支持相对路径了。所以还是推荐用ApplicationContext+前缀表达式的方式。
     */
    private static final ResourceLoader resolver = SpringContextUtils.applicationContext;

    /**
     * 附件
     */
    private Attachment[] attachment;
    /**
     * 内嵌资源
     */
    private Inline[] inline;

    public MimeMailer(String from) {
        super(from);
    }

    @Override
    public void send() {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(getFrom());
            helper.setTo(getTo());
            helper.setSubject(getSubject());
            helper.setText(getContent(), true);
            if (getReplyTo() != null) {
                helper.setReplyTo(getReplyTo());
            }
            if (getCc() != null) {
                helper.setCc(getCc());
            }
            if (getBcc() != null) {
                helper.setBcc(getBcc());
            }
            if (getSentDate() != null) {
                helper.setSentDate(getSentDate());
            }

            addAllAttachment(helper);
            addAllInline(helper);

            javaMailSender.send(message);
            LOGGER.info("MIME邮件已经发送。");
        } catch (MessagingException | IOException e) {
            LOGGER.error("发送MIME邮件时发生异常！", e);
        }
    }

    /**
     * 将所有附件添加到邮件中
     * @param helper
     * @throws MessagingException
     */
    private void addAllAttachment(MimeMessageHelper helper) throws MessagingException, IOException {
        if (this.attachment == null) {
            return;
        }

        Resource file = null;
        String fileName = null;

        for (Attachment attach : this.attachment) {
            if (attach == null) {
                continue;
            }

            file = resolver.getResource(attach.getFilePath());

            fileName = FileUtils.getFileName(file.getFilename(), attach.getFileName());

            helper.addAttachment(fileName, file);
        }
    }

    /**
     * 将所有内嵌资源添加到邮件中
     * @param helper
     * @throws MessagingException
     */
    private void addAllInline(MimeMessageHelper helper) throws MessagingException, IOException {
        if (this.inline == null) {
            return;
        }

        for (Inline inlineEle : this.inline) {
            if (inlineEle == null) {
                continue;
            }

            helper.addInline(inlineEle.getContentId(), resolver.getResource(inlineEle.getFilePath()));
        }
    }

    public Attachment[] getAttachment() {
        return attachment;
    }

    @Override
    public Mailer attachment(Attachment attachment) {
        this.attachment = new Attachment[]{attachment};
        return this;
    }

    @Override
    public Mailer attachment(Attachment... attachment) {
        this.attachment = attachment;
        return this;
    }

    @Override
    public Mailer attachment(String filePath) {
        this.attachment = new Attachment[]{new Attachment(filePath)};
        return this;
    }

    @Override
    public Mailer attachment(String fileName, String filePath) {
        this.attachment = new Attachment[]{new Attachment(fileName, filePath)};
        return this;
    }

    @Override
    public Mailer attachment(String[] filePath) {
        List<String> list = new ArrayList<>();
        for (String s : filePath) {
            list.add(s);
        }
        this.attachment = list.toArray(new Attachment[0]);
        return this;
    }

    public Inline[] getInline() {
        return inline;
    }

    @Override
    public Mailer inline(String contentId, String filePath) {
        this.inline = new Inline[]{new Inline(contentId, filePath)};
        return this;
    }

    @Override
    public Mailer inline(Inline inline) {
        this.inline = new Inline[]{inline};
        return this;
    }

    @Override
    public Mailer inline(Inline... inline) {
        this.inline = inline;
        return this;
    }
}
