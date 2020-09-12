# Java Mail Fluent API

### 功能说明
封装了Spring的JavaMailSender，并提供了Fluent API用于发送邮件。

核心接口：``me.god.mail.mailer.Mailer``，对应的测试类查看``MailerTest``。
1. 发送简单邮件
    ```java
        Mailer.mail()
                .to("xxxx@qq.com")
                .subject("测试邮件")
                .content("这是一封测试邮件！")
                .send();
    ```
1. 发送MIME邮件
    ```java
    public void sendMime() throws IOException {
            // 文件绝对路径
            String filePath = new ClassPathResource("mail/attachment2.txt").getFile().getAbsolutePath();
            // 内嵌图片的位置标识
            String contentId = "neo001";
    
            Mailer.mail(MailType.MIME)
                    .to("xxxx@qq.com")
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
    ```