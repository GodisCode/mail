package me.god.mail.mailer;

/**
 * 邮件内嵌资源类
 */
public class Inline {
    /**
     * 内容标识ID
     * 如cid:neo006，则neo006为contentId
     */
    private String contentId;
    /**
     * 资源文件路径
     */
    private String filePath;

    public Inline(String contentId, String filePath) {
        this.contentId = contentId;
        this.filePath = filePath;
    }

    public String getContentId() {
        return contentId;
    }

    public Inline setContentId(String contentId) {
        this.contentId = contentId;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public Inline setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }
}
