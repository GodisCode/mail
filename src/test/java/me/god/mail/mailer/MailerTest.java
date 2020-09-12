package me.god.mail.mailer;

import me.god.mail.util.SpringContextUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailerTest {
    // 设置发送人 todo
    private static final String TO = "xxxx@qq.com";

    @Test
    public void sendSimple() {
        Mailer.mail()
                .to(TO)
                .subject("测试邮件")
                .content("这是一封测试邮件！")
                .send();
    }

    @Test
    public void sendMime() throws IOException {
        // 文件绝对路径
        String filePath = new ClassPathResource("mail/attachment2.txt").getFile().getAbsolutePath();
        // 内嵌图片的位置标识
        String contentId = "neo001";

        Mailer.mail(MailType.MIME)
                .to(TO)
                .subject("测试MIME邮件")
                .content("<html><body>这是有图片的邮件：<br>" +
                        "<img src=\'cid:" + contentId + "\' >" +
                        "</body></html>")
                .inline(contentId, "classpath:mail/image.png")
                .attachment(
                        new Attachment("测试附件", "classpath:mail/attachment1.txt"),
                        new Attachment("file:" + filePath),  // 不要使用new Attachment(filePath)，会报错
                        new Attachment("mail.pdf", "http://localhost:8080/mail.pdf")
                ).send();
    }
}