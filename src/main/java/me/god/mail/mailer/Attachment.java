package me.god.mail.mailer;

/**
 * 邮件附件类。
 */
public class Attachment {
    private String fileName;

    private String filePath;

    public Attachment(String filePath) {
        this.filePath = filePath;
    }

    public Attachment(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public Attachment setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public Attachment setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }
}
